package com.ap22.controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.function.Supplier;

import com.ap22.dao.DAOException;
import com.ap22.dao.EstudianteDAO;
import com.ap22.modelo.Estudiante;
import com.ap22.vista.VentanaEstudiantes;

/**
 * CONTROLADOR de la versión Swing.
 *
 * Recibe la Vista y el DAO por el constructor (inyección de dependencias):
 * así el controlador no sabe si los datos vienen de SQLite, MySQL o memoria.
 *
 * En JavaFX los eventos se conectan en SceneBuilder (On Action = #onGuardar).
 * En Swing se conectan con código: boton.addActionListener(e -> onGuardar()).
 * La LÓGICA (validar, llamar al DAO, refrescar) es la misma en ambos proyectos.
 */
public class EstudianteController {

    private final VentanaEstudiantes vista;
    private final EstudianteDAO dao;

    public EstudianteController(VentanaEstudiantes vista, EstudianteDAO dao) {
        this.vista = vista;
        this.dao = dao;
    }

    /** Conecta los eventos, carga los datos y muestra la ventana. */
    public void iniciar() {
        registrarEventos();
        cargarEstudiantes();
        vista.setVisible(true);
    }

    /** Conecta cada componente de la vista con el método que lo atiende. */
    private void registrarEventos() {
        vista.getBtnGuardar().addActionListener(e -> onGuardar());
        vista.getBtnActualizar().addActionListener(e -> onActualizar());
        vista.getBtnEliminar().addActionListener(e -> onEliminar());
        vista.getBtnLimpiar().addActionListener(e -> onLimpiar());

        // Buscar mientras se escribe
        vista.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarEstudiantes();
            }
        });

        // Al seleccionar una fila, se copian sus datos al formulario
        vista.getTblEstudiantes().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                vista.cargarFilaSeleccionadaEnFormulario();
            }
        });
    }

    // ===================== Eventos =====================

    private void onGuardar() {
        Estudiante nuevo = leerFormulario();
        if (nuevo == null) {
            return;
        }
        // El mensaje se arma DESPUÉS de insertar, cuando SQLite ya asignó el id
        ejecutar(() -> dao.insertar(nuevo), () -> "Estudiante guardado con id " + nuevo.getId());
    }

    private void onActualizar() {
        int id = vista.getIdSeleccionado();
        if (id < 0) {
            vista.mostrarError("Seleccione en la tabla el estudiante que desea actualizar.");
            return;
        }
        Estudiante editado = leerFormulario();
        if (editado == null) {
            return;
        }
        editado.setId(id);
        ejecutar(() -> dao.actualizar(editado), () -> "Estudiante actualizado");
    }

    private void onEliminar() {
        int id = vista.getIdSeleccionado();
        if (id < 0) {
            vista.mostrarError("Seleccione en la tabla el estudiante que desea eliminar.");
            return;
        }
        if (vista.confirmar("¿Eliminar a " + vista.getTxtNombre().getText() + "?")) {
            ejecutar(() -> dao.eliminar(id), () -> "Estudiante eliminado");
        }
    }

    private void onLimpiar() {
        vista.limpiarFormulario();
    }

    // ===================== Métodos de apoyo =====================

    private void ejecutar(Runnable operacion, Supplier<String> mensajeExito) {
        try {
            operacion.run();
            vista.limpiarFormulario();
            cargarEstudiantes();
            vista.setEstado(mensajeExito.get());
        } catch (DAOException ex) {
            vista.mostrarError(ex.getMessage() + "\n" + ex.getCause().getMessage());
        }
    }

    private void cargarEstudiantes() {
        try {
            String filtro = vista.getTxtBuscar().getText().trim();
            List<Estudiante> lista = filtro.isEmpty() ? dao.listarTodos() : dao.buscarPorNombre(filtro);
            vista.mostrarEstudiantes(lista);
            vista.setEstado(lista.size() + " estudiante(s)");
        } catch (DAOException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    /** Valida el formulario. Devuelve un Estudiante o null si hay errores. */
    private Estudiante leerFormulario() {
        String nombre = vista.getTxtNombre().getText().trim();
        String correo = vista.getTxtCorreo().getText().trim();
        String textoNota = vista.getTxtNota().getText().trim().replace(',', '.');

        if (nombre.isEmpty()) {
            vista.mostrarError("El nombre es obligatorio.");
            return null;
        }
        if (!correo.contains("@")) {
            vista.mostrarError("El correo no es válido.");
            return null;
        }
        double nota;
        try {
            nota = Double.parseDouble(textoNota);
        } catch (NumberFormatException ex) {
            vista.mostrarError("La nota debe ser un número, por ejemplo 4.5");
            return null;
        }
        if (nota < 0 || nota > 5) {
            vista.mostrarError("La nota debe estar entre 0.0 y 5.0");
            return null;
        }
        return new Estudiante(nombre, correo, nota);
    }
}
