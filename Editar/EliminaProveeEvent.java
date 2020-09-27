/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editar;

import Admin.AdminEvent;
import DB.Editar;
import DB.ListaProductos;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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

/**
 *
 * @author Gonzalez Duerto
 */
public class EliminaProveeEvent extends ListaProductos implements Initializable {
    AdminEvent bakery=new AdminEvent();
    @FXML
    private Label error,nombre;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXComboBox usuario;
    public void setUsuario(String usua){
        ObservableList<String> itm=FXCollections.observableArrayList();
        error.setText(usua);
        itm.addAll(proveedores(""));
        usuario.setItems(itm);
        usuario.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void eliminar(){
        Editar editar =new Editar();
        try {
            System.out.println(error.getText()+" "+contra.getText());
            if (editar.eliminarproveedor(usuario.getSelectionModel().getSelectedItem().toString().substring(0, usuario.getSelectionModel().getSelectedItem().toString().indexOf(":")), error.getText(), contra.getText())) {
                nombre.setText("Eliminado");
            } else {
                nombre.setText("Contraseña incorrecta");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

