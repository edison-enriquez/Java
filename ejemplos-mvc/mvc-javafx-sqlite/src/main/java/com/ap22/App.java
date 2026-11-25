package com.ap22;

import java.io.IOException;

import com.ap22.db.ConexionSQLite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación JavaFX.
 *
 * 1. Prepara la base de datos (crea la tabla si no existe).
 * 2. Carga la VISTA (estudiantes.fxml). Al cargarla, FXMLLoader crea
 *    automáticamente el CONTROLADOR indicado en fx:controller.
 * 3. Muestra la ventana.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ConexionSQLite.inicializarBaseDeDatos();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("vista/estudiantes.fxml"));
        Parent raiz = loader.load();

        stage.setTitle("Gestión de Estudiantes - JavaFX");
        stage.setScene(new Scene(raiz));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
