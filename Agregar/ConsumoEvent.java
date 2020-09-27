/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agregar;

import DB.Agregar;
import DB.ListaProductos;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Duerto
 */
public class ConsumoEvent extends Agregar implements Initializable {
    @FXML
    private Label error;
        @FXML
    private JFXComboBox producto,tipo;
    @FXML
    private JFXTextField canti;
    @FXML
    private void agregar(ActionEvent event) {
        try {
            String pro=producto.getSelectionModel().getSelectedItem().toString();
            String codigo=pro.substring(0,pro.indexOf(": "));
            add_consumo(tipo.getSelectionModel().getSelectedItem().toString(),codigo,pro.substring(pro.indexOf(": ")+2),canti.getText());
            if(tipo.getSelectionModel().getSelectedIndex()==0){
                del_local(codigo,canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==1){
                del_materia(codigo,canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==2){
                del_viveres(codigo,canti.getText());
            }
            else if(tipo.getSelectionModel().getSelectedIndex()==3){
                del_charcuteria(codigo,canti.getText());
            }
            error.setText("Consumo agregado");
        } catch (SQLException ex) {
            error.setText("Error al agregar");
            Logger.getLogger(ConsumoEvent.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
        canti.setText("0.00");
        error.setText("");
        tipo.getSelectionModel().select(0);
        ObservableList<String> ite=FXCollections.observableArrayList();
        ListaProductos listaProductos=new ListaProductos();
        ite.addAll(listaProductos.local(""));
        producto.setItems(ite);
        producto.getSelectionModel().select(0);
        tipo.setOnHiding(event -> {
            ObservableList<String> item=FXCollections.observableArrayList();
            if (tipo.getSelectionModel().getSelectedIndex() == 0) {
                item.addAll(listaProductos.local(""));
            }else if (tipo.getSelectionModel().getSelectedIndex() == 1) {
                item.addAll(listaProductos.materiaPrima(""));
            }
            else if (tipo.getSelectionModel().getSelectedIndex() == 2) {
                item.addAll(listaProductos.viveres(""));
            } else if (tipo.getSelectionModel().getSelectedIndex() == 3) {
                item.addAll(listaProductos.charcuteria(""));
            }
            producto.setItems(item);
            producto.getSelectionModel().select(0);
        });
    }    
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

