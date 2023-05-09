/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.crud.crud;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gadym-01
 */
public class CRUD {
    
    public static Connection getConnection(){
        
        Connection conn = null;
        
        try {
            // Cargar la biblioteca JDBC de SQLite
            Class.forName("org.sqlite.JDBC");
            // Conectar a la base de datos SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:Escuela.db");
            JOptionPane.showMessageDialog(null, "Conexion Exitosa");

        } catch (HeadlessException | ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                // Cerrar la conexión a la base de datos
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Conectar a la base de datos SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

            // ...

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                // Cerrar la conexión a la base de datos
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
