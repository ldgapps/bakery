/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agregar;

import Admin.Admin;
import DB.Agregar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 *
 * @author Gonzalez Duerto
 */
public class ProveedorEvent extends Agregar implements Initializable {
    Admin bakery=new Admin();
    @FXML
    private JFXTextField nproveedor,rifproveedor,tlfproveedor;
                                @FXML
                                private Label error;
    @FXML
    private void agregar(ActionEvent event) {
        try {
            if(nproveedor.getText().equals("")){
                error.setText("Agregue un nombre");
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                        t.cancel();
                    }
                };
                t.schedule(tt, 2000);            }
            else {
                String nombre = nproveedor.getText().trim();
                String rif = rifproveedor.getText().trim();
                String tlf = tlfproveedor.getText().trim();

                add_proveedor(nombre, rif, tlf);

                nproveedor.setText("");
                rifproveedor.setText("");
                tlfproveedor.setText("");
                error.setText("Agregado");
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                        t.cancel();
                    }
                };
                t.schedule(tt, 2000);
            }
        } catch (SQLException e) {
            error.setText("Error al agregar");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                    t.cancel();
                }
            };
            t.schedule(tt, 2000 );
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

