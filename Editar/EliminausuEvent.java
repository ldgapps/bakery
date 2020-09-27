/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editar;

import Admin.AdminEvent;
import DB.Usuario;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import DB.Metodos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Gonzalez Duerto
 */
public class EliminausuEvent extends Usuario implements Initializable {
    @FXML
    private Label error,nombre;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXComboBox usuario;
    private String Admin;
    public void setUsuario(String usua){
        ObservableList<String> itm=FXCollections.observableArrayList();
        itm.addAll(getusuarios(usua));
        usuario.setItems(itm);
        Admin=usua;
        usuario.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void eliminar(){
        if(eliminausuario(usuario.getSelectionModel().getSelectedItem().toString(),Admin,contra.getText())){
            nombre.setText("Eliminado");
        }
        else {
            nombre.setText("Contraseña incorrecta");
        }
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

