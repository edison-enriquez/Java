package com.ap22;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ap22.controlador.EstudianteController;
import com.ap22.dao.EstudianteDAO;
import com.ap22.dao.EstudianteDAOSQLite;
import com.ap22.db.ConexionSQLite;
import com.ap22.vista.VentanaEstudiantes;

/**
 * Punto de entrada de la versión Swing. Aquí se "arma" el MVC:
 *
 *   Modelo (DAO)  ─┐
 *                  ├─> Controlador  (recibe ambos)
 *   Vista         ─┘
 */
public class Main {

    public static void main(String[] args) {
        ConexionSQLite.inicializarBaseDeDatos();

        // Swing no es thread-safe: la interfaz se crea en el hilo de eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Apariencia nativa del sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignorada) {
                // Si falla, se usa la apariencia por defecto de Java
            }
            VentanaEstudiantes vista = new VentanaEstudiantes();
            EstudianteDAO dao = new EstudianteDAOSQLite();
            EstudianteController controlador = new EstudianteController(vista, dao);
            controlador.iniciar();
        });
    }
}
