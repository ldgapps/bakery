/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agregar;

import DB.Agregar;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 *
 * @author Gonzalez Duerto
 */
public class ProductoEvent implements Initializable {
    @FXML
    private Label error;
        @FXML
    private JFXTextField producto, marca,codigo;
                        @FXML
    private JFXComboBox tipo;

    @FXML
    private void agregar(ActionEvent event) {
        Agregar agregar=new Agregar();

        try {
            if(codigo.getText().equals("")||producto.getText().equals("")){
                error.setText("Complete los campos");
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
            else {
                agregar.add_producto(codigo.getText().trim(), producto.getText().trim(), marca.getText().trim(), tipo.getSelectionModel().selectedItemProperty().getValue().toString());
                codigo.setText("");
                producto.setText("");
                marca.setText("");
                error.setText("Producto agregado");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            error.setText("Codigo duplicado");

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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("Local","Materia prima","Viveres","Charcuteria");
        tipo.setItems(items);
    }    
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void local(ActionEvent event) {
       if(tipo.getSelectionModel().selectedItemProperty().getValue().toString().equals("Local")||tipo.getSelectionModel().selectedItemProperty().getValue().toString().equals("Galletas")){
           marca.clear();
           marca.setDisable(true);
       }
       else{
           marca.setDisable(false);
       }
    }
}

