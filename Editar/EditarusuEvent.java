/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editar;

import Admin.AdminEvent;
import DB.Usuario;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import DB.Metodos;
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
public class EditarusuEvent extends Usuario implements Initializable {
    private AdminEvent adminEvent=new AdminEvent();
    @FXML
    private Label nombre;
    @FXML
    private JFXPasswordField contra,contran;
    @FXML
    private JFXTextField usuario;
    private String vusuario,nusuario;
    public String getUsuario(){
        return nusuario;
    }
    public void setUsuario(String usua,String contrasena){
        usuario.setText(usua);
        contran.setText(contrasena);
        vusuario=usua;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    public void eliminar(){
        if(editanombre(vusuario,contra.getText(),usuario.getText(),contran.getText())){
            nombre.setText("Usuario cambiado");
        }
        else {
            nombre.setText("Contraseña incorrecta");
        }
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void parametros(AdminEvent stage,Label texto,String string){
        adminEvent=stage;
        texto.setText(string);
    }
}

