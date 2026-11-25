/**
 * Descriptor del módulo (Java Platform Module System).
 * El archetype de JavaFX lo genera porque JavaFX se distribuye como módulos.
 *
 * - requires: módulos que este proyecto necesita.
 * - opens ... to: permite que JavaFX use reflexión sobre nuestros paquetes
 *   (FXMLLoader inyecta los @FXML del controlador; TableView lee los getters del modelo).
 * - exports: paquetes visibles para otros módulos (App debe ser visible para JavaFX).
 */
module com.ap22 {
    requires transitive javafx.controls; // transitive: App expone tipos de JavaFX (Stage)
    requires javafx.fxml;
    requires java.sql;              // JDBC: Connection, PreparedStatement, ResultSet
    requires org.xerial.sqlitejdbc; // Driver de SQLite

    opens com.ap22.controlador to javafx.fxml;
    opens com.ap22.modelo to javafx.base;

    exports com.ap22;
}
