package com.ap22.vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.ap22.modelo.Estudiante;

/**
 * VISTA en Swing: la ventana con el formulario, los botones y la tabla.
 *
 * A diferencia de JavaFX (donde la vista es un archivo FXML hecho en
 * SceneBuilder), en Swing la vista se construye con código Java.
 *
 * Regla de oro de MVC: la vista SOLO muestra datos y expone sus componentes.
 * No valida, no decide y no habla con la base de datos; eso lo hace el controlador.
 */
public class VentanaEstudiantes extends JFrame {

    // ----- Formulario -----
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtNota = new JTextField();
    private final JTextField txtBuscar = new JTextField();

    // ----- Botones -----
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    // ----- Tabla -----
    // DefaultTableModel guarda las filas; isCellEditable=false para que no se edite en la tabla.
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[] {"ID", "Nombre", "Correo", "Nota"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tblEstudiantes = new JTable(modeloTabla);
    private final JLabel lblEstado = new JLabel("0 estudiante(s)");

    public VentanaEstudiantes() {
        super("Gestión de Estudiantes - Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construirInterfaz();
        setSize(640, 560);
        setLocationRelativeTo(null); // centrar en pantalla
    }

    /** Arma la interfaz con paneles y layouts (equivale a lo que se hace en SceneBuilder). */
    private void construirInterfaz() {
        JLabel titulo = new JLabel("Gestión de Estudiantes");
        titulo.setFont(titulo.getFont().deriveFont(Font.PLAIN, 20f));

        // Formulario: 3 filas x 2 columnas (etiqueta | campo)
        JPanel formulario = new JPanel(new GridLayout(3, 2, 8, 6));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);
        formulario.add(new JLabel("Nota (0.0 - 5.0):"));
        formulario.add(txtNota);

        // Botones en fila
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.add(btnGuardar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        // Buscador
        JPanel buscador = new JPanel(new BorderLayout(8, 0));
        buscador.add(new JLabel("Buscar por nombre:"), BorderLayout.WEST);
        buscador.add(txtBuscar, BorderLayout.CENTER);

        // Parte superior: título + formulario + botones + buscador, uno debajo del otro
        JPanel superior = new JPanel();
        superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
        for (JPanel p : new JPanel[] {envolver(titulo), formulario, botones, buscador}) {
            p.setAlignmentX(LEFT_ALIGNMENT);
            superior.add(p);
            superior.add(Box.createVerticalStrut(10));
        }

        tblEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel raiz = new JPanel(new BorderLayout(0, 6));
        raiz.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        raiz.add(superior, BorderLayout.NORTH);
        raiz.add(new JScrollPane(tblEstudiantes), BorderLayout.CENTER);
        raiz.add(lblEstado, BorderLayout.SOUTH);
        setContentPane(raiz);
    }

    private static JPanel envolver(JLabel etiqueta) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.add(etiqueta);
        return panel;
    }

    // ===================== Métodos que usa el controlador =====================

    /** Reemplaza el contenido de la tabla con la lista recibida. */
    public void mostrarEstudiantes(List<Estudiante> estudiantes) {
        modeloTabla.setRowCount(0);
        for (Estudiante e : estudiantes) {
            modeloTabla.addRow(new Object[] {e.getId(), e.getNombre(), e.getCorreo(), e.getNota()});
        }
    }

    /** Devuelve el id de la fila seleccionada o -1 si no hay selección. */
    public int getIdSeleccionado() {
        int fila = tblEstudiantes.getSelectedRow();
        return fila < 0 ? -1 : (int) modeloTabla.getValueAt(fila, 0);
    }

    /** Copia la fila seleccionada al formulario. */
    public void cargarFilaSeleccionadaEnFormulario() {
        int fila = tblEstudiantes.getSelectedRow();
        if (fila >= 0) {
            txtNombre.setText(String.valueOf(modeloTabla.getValueAt(fila, 1)));
            txtCorreo.setText(String.valueOf(modeloTabla.getValueAt(fila, 2)));
            txtNota.setText(String.valueOf(modeloTabla.getValueAt(fila, 3)));
        }
    }

    public void limpiarFormulario() {
        tblEstudiantes.clearSelection();
        txtNombre.setText("");
        txtCorreo.setText("");
        txtNota.setText("");
        txtNombre.requestFocus();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmar(String pregunta) {
        return JOptionPane.showConfirmDialog(this, pregunta, "Confirmación",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void setEstado(String texto) {
        lblEstado.setText(texto);
    }

    // ----- Getters de componentes (el controlador les agrega los listeners) -----
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtNota() { return txtNota; }
    public JTextField getTxtBuscar() { return txtBuscar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTable getTblEstudiantes() { return tblEstudiantes; }
}
