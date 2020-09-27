/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Editar;

import DB.Editar;
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
import util.CampoTexto;
import util.TimerTexto;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class EditarcostoEvent extends Editar implements Initializable {
    @FXML
    private JFXComboBox producto, tipo;
    @FXML
    private JFXTextField nprecio,busqueda;
    @FXML
    private Label vprecio, error;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO

        CampoTexto textoampo=new CampoTexto();
        textoampo.setdouble(nprecio);
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("Local","Viveres","Charcuteria","Materia Prima");
        tipo.setItems(items);
        tipo.getSelectionModel().select(0);
        error.setText("");
        setProducto();
    }
    @FXML
    private void setCosto(){
        try {
            String pr = producto.getSelectionModel().getSelectedItem().toString();
            vprecio.setText(get_prop_producto(tipo.getSelectionModel().getSelectedItem().toString().toLowerCase(), pr.substring(0, pr.indexOf(":")), "costo"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void setProducto(){
        ObservableList<String> item2=FXCollections.observableArrayList();
        if(tipo.getSelectionModel().getSelectedIndex()==0){
            item2.addAll(local(busqueda.getText()));
        }
        else if(tipo.getSelectionModel().getSelectedIndex()==1){
            item2.addAll(viveres(busqueda.getText()));
        }
        else if(tipo.getSelectionModel().getSelectedIndex()==2){
            item2.addAll(charcuteria(busqueda.getText()));
        }
        else if(tipo.getSelectionModel().getSelectedIndex()==3){
            item2.addAll(materiaPrima(busqueda.getText()));
        }
        producto.setItems(item2);
        producto.getSelectionModel().select(0);
        setCosto();
    }
    @FXML
    public void cambio(){
        try {
            String pr = producto.getSelectionModel().getSelectedItem().toString();
            System.out.println(tipo.getSelectionModel().getSelectedItem().toString());
            editarCosto(tipo.getSelectionModel().getSelectedItem().toString(),pr.substring(0,pr.indexOf(":")),nprecio.getText());
            setCosto();
            error.setText("Costo cambiado");
        } catch (SQLException e) {
            error.setText("Error");
            setCosto();
            e.printStackTrace();
        }
        finally {
            TimerTexto.setTimer(error);
        }
    }
    public void setProducto(String tipoProcducto, String codigo) {
        if(tipoProcducto.equals("local")){
            tipo.getSelectionModel().select(0);
        }
        else if(tipoProcducto.equals("viveres")){
            tipo.getSelectionModel().select(1);
        }
        else if(tipoProcducto.equals("charcuteria")){
            tipo.getSelectionModel().select(2);
        }
        else if(tipoProcducto.equals("materia prima")){
            tipo.getSelectionModel().select(3);
        }
        busqueda.setText(codigo);
        setProducto();
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }

}

