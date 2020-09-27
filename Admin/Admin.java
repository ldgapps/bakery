/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import DB.Usuario;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Gonzalez Duerto
 */
public class Admin extends Application {
        private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

            public void mostrarVentanaSecundaria(String usuario) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                VBox ventanaDos = loader.load();
                Stage ventana = new Stage();
                ventana.getIcons().add(new Image(getClass().getResource("logo2.png").toExternalForm()));

                ventana.setTitle("Bakery Administrador");
                ventana.initOwner(stage);

                Scene scene = new Scene(ventanaDos);
                ventana.setScene(scene);
                ventana.setMaximized(true);
                AdminEvent controller = loader.getController();
                controller.setUsuario(usuario);
                Usuario usuarioInstance=new Usuario();
                controller.setIdUsuario(usuarioInstance.getId(usuario));
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
