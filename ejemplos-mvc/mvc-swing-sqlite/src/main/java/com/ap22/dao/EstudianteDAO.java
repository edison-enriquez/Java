package com.ap22.dao;

import java.util.List;

import com.ap22.modelo.Estudiante;

/**
 * DAO = Data Access Object (Objeto de Acceso a Datos).
 *
 * Esta INTERFAZ define QUÉ operaciones se pueden hacer con los estudiantes
 * guardados, pero NO dice CÓMO se hacen. El controlador solo conoce esta
 * interfaz, así que si mañana cambiamos SQLite por MySQL o por un archivo,
 * basta con escribir otra clase que la implemente: el controlador no cambia.
 *
 * Operaciones CRUD: Create (insertar), Read (listar/buscar), Update (actualizar), Delete (eliminar).
 */
public interface EstudianteDAO {

    /** Guarda un estudiante nuevo y le asigna el id generado por la base de datos. */
    void insertar(Estudiante estudiante);

    /** Devuelve todos los estudiantes ordenados por nombre. */
    List<Estudiante> listarTodos();

    /** Devuelve los estudiantes cuyo nombre contenga el texto indicado. */
    List<Estudiante> buscarPorNombre(String texto);

    /** Actualiza nombre, correo y nota del estudiante con ese id. */
    void actualizar(Estudiante estudiante);

    /** Elimina el estudiante con ese id. */
    void eliminar(int id);
}
