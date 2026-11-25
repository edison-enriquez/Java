package com.ap22.modelo;

/**
 * MODELO: representa un estudiante (una fila de la tabla "estudiante").
 *
 * Es un POJO (Plain Old Java Object): solo tiene atributos privados,
 * constructores, getters y setters. No sabe nada de la interfaz gráfica
 * ni de la base de datos. Por eso se puede reutilizar igual en JavaFX y en Swing.
 */
public class Estudiante {

    private int id;          // Lo asigna SQLite (AUTOINCREMENT). 0 = aún no guardado.
    private String nombre;
    private String correo;
    private double nota;     // De 0.0 a 5.0

    public Estudiante() {
    }

    /** Constructor para un estudiante NUEVO (todavía sin id). */
    public Estudiante(String nombre, String correo, double nota) {
        this(0, nombre, correo, nota);
    }

    /** Constructor para un estudiante que YA existe en la base de datos. */
    public Estudiante(int id, String nombre, String correo, double nota) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Estudiante{id=" + id + ", nombre='" + nombre + "', correo='" + correo + "', nota=" + nota + "}";
    }
}
