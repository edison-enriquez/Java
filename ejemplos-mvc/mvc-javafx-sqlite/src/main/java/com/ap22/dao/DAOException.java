package com.ap22.dao;

/**
 * Excepción propia de la capa de datos.
 *
 * JDBC lanza SQLException (una excepción "chequeada" y muy ligada a SQL).
 * El DAO la envuelve en DAOException para que el controlador no dependa
 * de detalles de JDBC: solo sabe que "algo falló al acceder a los datos".
 */
public class DAOException extends RuntimeException {

    public DAOException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
