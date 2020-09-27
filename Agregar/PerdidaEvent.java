/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agregar;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Duerto
 */
public class PerdidaEvent extends ConsumoEvent {
    @FXML
    private Label error;
        @FXML
    private JFXComboBox producto;
    @FXML
    private JFXTextField canti;
                        @FXML
    private JFXComboBox tipo;

    @FXML
    private void agregar(ActionEvent event) {
        try {
            String pro=producto.getSelectionModel().getSelectedItem().toString();
            if(tipo.getSelectionModel().getSelectedIndex()==0){
                del_local(pro.substring(0,pro.indexOf(": ")),canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==1){
                del_materia(pro.substring(0,pro.indexOf(": ")),canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==2){
                del_viveres(pro.substring(0,pro.indexOf(": ")),canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==3){
                del_charcuteria(pro.substring(0,pro.indexOf(": ")),canti.getText());
            }
            add_perdida(pro.substring(0,pro.indexOf(": ")),pro.substring(pro.indexOf(": ")+2),canti.getText(),get_prop_producto(tipo.getSelectionModel().getSelectedItem().toString(),pro.substring(0,pro.indexOf(": ")),"costo"));
            error.setText("Perdida agregada");
        } catch (SQLException ex) {
            error.setText("Error al agregar");

            Logger.getLogger(PerdidaEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                    t.cancel();
                }
            };
            t.schedule(tt, 2000 );
        }
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

