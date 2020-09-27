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

public class EditarproveedorEvent extends Editar implements Initializable {
    private Editarproducto ProgramaPrincipal;
    @FXML
    private Label error,id;
    @FXML
    private JFXTextField nombre,rif,codigo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setText("");
}

    public void setProgramaPrincipal(Editarproducto ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    public void setproveedor(String id,String codigo,String nombre,String rif){
        this.nombre.setText(nombre);
        this.id.setText(id);
        this.rif.setText(rif);
        this.codigo.setText(codigo);
    }
    @FXML
    public void edita(){
        try {
            editaproveedor(id.getText(),codigo.getText(), nombre.getText(),rif.getText());
            error.setText("Proveedor editado");
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

