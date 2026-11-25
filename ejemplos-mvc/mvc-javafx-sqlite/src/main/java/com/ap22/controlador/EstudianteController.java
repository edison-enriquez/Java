package com.ap22.controlador;

import java.util.List;
import java.util.function.Supplier;

import com.ap22.dao.DAOException;
import com.ap22.dao.EstudianteDAO;
import com.ap22.dao.EstudianteDAOSQLite;
import com.ap22.modelo.Estudiante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * CONTROLADOR de la vista estudiantes.fxml.
 *
 * - Los atributos con @FXML los "inyecta" FXMLLoader: su nombre debe ser
 *   IGUAL al fx:id que se puso en SceneBuilder.
 * - Los métodos con @FXML (onGuardar, onEliminar, ...) son los que se
 *   escribieron en "On Action" en SceneBuilder.
 *
 * Responsabilidades: recibir eventos de la Vista, validar los datos,
 * pedirle al DAO que lea/escriba y actualizar la Vista con el resultado.
 * El controlador NO escribe SQL: eso es trabajo del DAO.
 */
public class EstudianteController {

    // ----- Componentes de la Vista (inyectados desde el FXML) -----
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtNota;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Estudiante> tblEstudiantes;
    @FXML private TableColumn<Estudiante, Integer> colId;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, Double> colNota;
    @FXML private Label lblEstado;

    // ----- Acceso a datos: el controlador depende de la INTERFAZ -----
    private final EstudianteDAO dao = new EstudianteDAOSQLite();

    // Lista "observable": cuando cambia, la TableView se refresca sola.
    private final ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();

    /** FXMLLoader llama a este método automáticamente después de inyectar los @FXML. */
    @FXML
    private void initialize() {
        // Cada columna lee una propiedad del Estudiante usando su getter (getId, getNombre, ...)
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        tblEstudiantes.setItems(estudiantes);
        // Las columnas se reparten todo el ancho de la tabla
        tblEstudiantes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        // Al seleccionar una fila, se copian sus datos al formulario
        tblEstudiantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, seleccionado) -> mostrarEnFormulario(seleccionado));

        cargarEstudiantes();
    }

    // ===================== Eventos (On Action) =====================

    @FXML
    private void onGuardar() {
        Estudiante nuevo = leerFormulario();
        if (nuevo == null) {
            return; // datos inválidos: ya se mostró el mensaje
        }
        // El mensaje se arma DESPUÉS de insertar, cuando SQLite ya asignó el id
        ejecutar(() -> dao.insertar(nuevo), () -> "Estudiante guardado con id " + nuevo.getId());
    }

    @FXML
    private void onActualizar() {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione en la tabla el estudiante que desea actualizar.");
            return;
        }
        Estudiante editado = leerFormulario();
        if (editado == null) {
            return;
        }
        editado.setId(seleccionado.getId());
        ejecutar(() -> dao.actualizar(editado), () -> "Estudiante actualizado");
    }

    @FXML
    private void onEliminar() {
        Estudiante seleccionado = tblEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Seleccione en la tabla el estudiante que desea eliminar.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar a " + seleccionado.getNombre() + "?", ButtonType.YES, ButtonType.NO);
        confirmacion.setHeaderText(null);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            ejecutar(() -> dao.eliminar(seleccionado.getId()), () -> "Estudiante eliminado");
        }
    }

    @FXML
    private void onLimpiar() {
        tblEstudiantes.getSelectionModel().clearSelection();
        txtNombre.clear();
        txtCorreo.clear();
        txtNota.clear();
        txtNombre.requestFocus();
    }

    /** Se ejecuta cada vez que se escribe en el campo Buscar (On Key Released). */
    @FXML
    private void onBuscar() {
        cargarEstudiantes();
    }

    // ===================== Métodos de apoyo =====================

    /** Ejecuta una operación del DAO, limpia el formulario y recarga la tabla. */
    private void ejecutar(Runnable operacion, Supplier<String> mensajeExito) {
        try {
            operacion.run();
            onLimpiar();
            cargarEstudiantes();
            lblEstado.setText(mensajeExito.get());
        } catch (DAOException ex) {
            mostrarError(ex.getMessage() + "\n" + ex.getCause().getMessage());
        }
    }

    /** Pide los datos al DAO (todos o filtrados) y los pone en la tabla. */
    private void cargarEstudiantes() {
        try {
            String filtro = txtBuscar.getText().trim();
            List<Estudiante> lista = filtro.isEmpty() ? dao.listarTodos() : dao.buscarPorNombre(filtro);
            estudiantes.setAll(lista);
            lblEstado.setText(lista.size() + " estudiante(s)");
        } catch (DAOException ex) {
            mostrarError(ex.getMessage());
        }
    }

    /** Valida el formulario. Devuelve un Estudiante o null si hay errores. */
    private Estudiante leerFormulario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String textoNota = txtNota.getText().trim().replace(',', '.');

        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return null;
        }
        if (!correo.contains("@")) {
            mostrarError("El correo no es válido.");
            return null;
        }
        double nota;
        try {
            nota = Double.parseDouble(textoNota);
        } catch (NumberFormatException ex) {
            mostrarError("La nota debe ser un número, por ejemplo 4.5");
            return null;
        }
        if (nota < 0 || nota > 5) {
            mostrarError("La nota debe estar entre 0.0 y 5.0");
            return null;
        }
        return new Estudiante(nombre, correo, nota);
    }

    private void mostrarEnFormulario(Estudiante e) {
        if (e == null) {
            return;
        }
        txtNombre.setText(e.getNombre());
        txtCorreo.setText(e.getCorreo());
        txtNota.setText(String.valueOf(e.getNota()));
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
