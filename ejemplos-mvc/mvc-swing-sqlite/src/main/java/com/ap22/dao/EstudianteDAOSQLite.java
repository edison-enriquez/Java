package com.ap22.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ap22.db.ConexionSQLite;
import com.ap22.modelo.Estudiante;

/**
 * Implementación del DAO usando JDBC + SQLite.
 *
 * Aquí (y SOLO aquí) se escribe SQL. Puntos clave:
 *  - PreparedStatement con parámetros "?": evita la inyección SQL y maneja
 *    las comillas automáticamente.
 *  - try-with-resources: cierra Connection, PreparedStatement y ResultSet
 *    aunque ocurra un error.
 *  - Cada fila del ResultSet se convierte en un objeto Estudiante (mapeo).
 */
public class EstudianteDAOSQLite implements EstudianteDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO estudiante (nombre, correo, nota) VALUES (?, ?, ?)";
    private static final String SQL_LISTAR =
            "SELECT id, nombre, correo, nota FROM estudiante ORDER BY nombre";
    private static final String SQL_BUSCAR =
            "SELECT id, nombre, correo, nota FROM estudiante WHERE nombre LIKE ? ORDER BY nombre";
    private static final String SQL_ACTUALIZAR =
            "UPDATE estudiante SET nombre = ?, correo = ?, nota = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM estudiante WHERE id = ?";

    @Override
    public void insertar(Estudiante e) {
        try (Connection con = ConexionSQLite.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setDouble(3, e.getNota());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    e.setId(claves.getInt(1)); // id asignado por SQLite
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al insertar el estudiante", ex);
        }
    }

    @Override
    public List<Estudiante> listarTodos() {
        try (Connection con = ConexionSQLite.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            return mapearLista(rs);
        } catch (SQLException ex) {
            throw new DAOException("Error al listar los estudiantes", ex);
        }
    }

    @Override
    public List<Estudiante> buscarPorNombre(String texto) {
        try (Connection con = ConexionSQLite.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                return mapearLista(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al buscar estudiantes", ex);
        }
    }

    @Override
    public void actualizar(Estudiante e) {
        try (Connection con = ConexionSQLite.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setDouble(3, e.getNota());
            ps.setInt(4, e.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar el estudiante", ex);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionSQLite.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar el estudiante", ex);
        }
    }

    /** Convierte cada fila del ResultSet en un objeto Estudiante. */
    private List<Estudiante> mapearLista(ResultSet rs) throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getDouble("nota")));
        }
        return lista;
    }
}
