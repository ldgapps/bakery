/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editar;
import DB.Editar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import util.TimerTexto;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditarproductoEvent extends Editar implements Initializable {
    private Editarproducto ProgramaPrincipal;
    @FXML
    private Label codigo,error,tipo;
    @FXML
    private JFXTextField marcan, producton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setText("");
}

    public void setProgramaPrincipal(Editarproducto ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    public void setpro(String codigoset,String productoset,String marcaset,String tiposet){
        codigo.setText(codigoset);
        producton.setText(productoset);
        marcan.setText(marcaset);
        tipo.setText(tiposet);
    }
    @FXML
    public void edita(){
        try {
            editaproducto(codigo.getText(),producton.getText(),marcan.getText(),tipo.getText());
            error.setText("Produto editado");
        } catch (SQLException e) {
            error.setText("Error al editar");
            e.printStackTrace();
        }
        finally {
            TimerTexto.setTimer(error);
        }
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

