package com.ap22.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Se encarga de lo relacionado con la conexión a SQLite.
 *
 * SQLite no necesita un servidor: la base de datos es un solo archivo
 * (estudiantes.db) que se crea en la carpeta desde donde se ejecuta la app.
 *
 * La URL JDBC tiene la forma: jdbc:sqlite:RUTA_DEL_ARCHIVO
 */
public final class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:estudiantes.db";

    private static final String SQL_CREAR_TABLA = """
            CREATE TABLE IF NOT EXISTS estudiante (
                id     INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT    NOT NULL,
                correo TEXT    NOT NULL,
                nota   REAL    NOT NULL
            )
            """;

    // Clase utilitaria: no se crean objetos de ella.
    private ConexionSQLite() {
    }

    /**
     * Abre una conexión NUEVA. Quien la pide debe cerrarla, idealmente con
     * try-with-resources:  try (Connection con = ConexionSQLite.getConexion()) { ... }
     */
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /** Crea la tabla si no existe. Se llama una sola vez al arrancar la aplicación. */
    public static void inicializarBaseDeDatos() {
        try (Connection con = getConexion();
             Statement st = con.createStatement()) {
            st.execute(SQL_CREAR_TABLA);
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo inicializar la base de datos", e);
        }
    }
}
