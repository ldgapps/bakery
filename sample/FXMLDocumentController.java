/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import Admin.Admin;
import Cajero.Caja;
import DB.Conexion;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


import DB.Metodos;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


/**
 *
 * @author Gonzalez Duerto
 */
public class FXMLDocumentController extends Conexion implements Initializable {
    private Main ProgramaPrincipal;
    private Admin admin=new Admin();
    private Caja cajero=new Caja();
    @FXML
    private Label label;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXTextField usuario;

        @FXML
    private JFXPasswordField contra;
     //   private Timer t=new Timer();
    public void Mensaje(String mensaje){
}
    @FXML
    private void iniciar(ActionEvent event) {
        Conectar();
        String usu=usuario.getText().trim();

        String contras=contra.getText().trim();
        String conusu=iniciar(usu, contras);
       if(conusu.equals("admin")){
           Metodos metodos=new Metodos();
           admin.mostrarVentanaSecundaria(usu);
           cerrar(event);
      }
       else if(conusu.equals("cajero")){
           cajero.mostrarVentanaSecundaria(usu);
           cerrar(event);
       }
       else{
           label.setText("Contraseña Incorrecta");

           Timer t=new Timer();
           TimerTask tt=new TimerTask() {
               @Override
               public void run() {
                   Platform.runLater(() -> label.setText(""));
                   t.cancel();
               }
           };
           t.schedule(tt,3000);
       }
       desconectar();
    }
    @FXML
    private void iniciart(KeyEvent event) {
        Conectar();
        String usu=usuario.getText().trim();

        String contras=contra.getText().trim();
        String inicio=iniciar(usu, contras);
        if(inicio.equals("admin")){
            admin.mostrarVentanaSecundaria(usu);
            cerrar(event);
        }
        else if(inicio.equals("cajero")){

            cajero.mostrarVentanaSecundaria(usu);
            cerrar(event);
        }
        else {
            label.setText("Contraseña Incorrecta");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> label.setText(""));
                    t.cancel();
                }
            };
            t.schedule(tt, 2000 );
        }
        desconectar();
    }
    public void setProgramaPrincipal(Main ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        contra.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                iniciart(event);

            }
        });
        usuario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                iniciart(event);
            }
        });
    }

    public void cerrar(KeyEvent event){
        (((Node) event.getSource())).getScene().getWindow().hide();
    }
    public void cerrar(ActionEvent event){
        (((Node) event.getSource())).getScene().getWindow().hide();
    }

}
