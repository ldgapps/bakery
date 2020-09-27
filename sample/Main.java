package sample;

import DB.Conexion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private Stage stage;

    @Override
    public void init() throws Exception {
        Thread.sleep(2000);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
/*
        Parent rooti = FXMLLoader.load(getClass().getResource("carga.fxml"));
        primaryStage.setTitle("Iniciar sesión");
        primaryStage.setScene(new Scene(rooti));
        primaryStage.setResizable(false);
        primaryStage.show();
*/

        Conexion conexion=new Conexion();

        conexion.Conectar();
        if(conexion.conecta()){
            primaryStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage.getIcons().add(new Image(getClass().getResource("logo2.png").toExternalForm()));
            primaryStage.setTitle("Iniciar sesión");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

        }
        else{
            primaryStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("ErrorConexion.fxml"));
            primaryStage.setTitle("Error");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        conexion.desconectar();
    }

    public void mostrarVentanaSecundaria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            AnchorPane ventanaDos = loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Iniciar sesión");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.centerOnScreen();

            FXMLDocumentController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            try {
                start(ventana);

            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
