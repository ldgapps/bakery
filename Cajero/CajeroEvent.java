/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import DB.Agregar;
import DB.ListaVenta;
import DB.Metodos;
import DB.Usuario;
import Editar.EditarusuEvent;
import Model.ListaTabla;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import util.CampoTexto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Main;

/**
 *
 * @author Gonzalez Duerto
 */
public class CajeroEvent extends ListaVenta implements Initializable {
    private Caja ProgramaPrincipal;
    private float precc;
    private String usuarion,idUsuario;
    private ObservableList<ObservableList> data;
    @FXML
    private JFXTabPane tabs;
    @FXML
    private JFXTextField cantichar,buscalocal,buscacharcu,buscavive,cantivive,cantilo;
    @FXML
    private Label tprecio, error,usuario,empresa;
    @FXML
    private JFXComboBox local, charcuteria,viveres;
    @FXML
    private TableColumn<ListaTabla,JFXButton> elimina;
    @FXML
    private TableView<ListaTabla> ventaTabla;
    @FXML
    private TableView table;
    @FXML
    private VBox ventana;
    @FXML
    private TableColumn<ListaTabla, String> tipop, codigol, productol, cantidadl, precioul, preciotl;
    ObservableList<ListaTabla> lista=FXCollections.observableArrayList();

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    public void elimina(KeyEvent event){
         ventaTabla.getItems().get(ventaTabla.getSelectionModel().getSelectedIndex());
    }
    @FXML
    private void vender() {
        Conectar();
        Agregar agregar=new Agregar();
        try {
            if(ventaTabla.getItems().size()<1){
                error.setText("Campos vacios");
            }else{
                int errorCantidad=0;
                String errorCodigoCandidad="";
                String tipo_s="";
                String precio_s="";
                String preciot_s="";
                String codigo_s="";
                String producto_s="";
                String cantidad_s="";

                for(int i=0;i<ventaTabla.getItems().size();i++){

                    codigo_s+=ventaTabla.getItems().get(i).getCodigo()+'\n';
                    producto_s+=ventaTabla.getItems().get(i).getProducto()+'\n';
                    cantidad_s+=ventaTabla.getItems().get(i).getCantidad()+'\n';
                    precio_s+=ventaTabla.getItems().get(i).getPreciou()+'\n';
                    preciot_s+=ventaTabla.getItems().get(i).getPreciot()+'\n';
                    tipo_s+=ventaTabla.getItems().get(i).getTipo()+'\n';
                    if(ventaTabla.getItems().get(i).getTipo().equals("Charcuteria")){
                        String codigoError=agregar.DisponibleCharcuteria(ventaTabla.getItems().get(i).getCodigo(),ventaTabla.getItems().get(i).getCantidad());
                        if(!codigoError.equals("")){
                            errorCodigoCandidad+=codigoError;
                            errorCantidad++;
                        }
                    }
                    else if(ventaTabla.getItems().get(i).getTipo().equals("Viveres")){
                        String codigoError=agregar.DisponibleViveres(ventaTabla.getItems().get(i).getCodigo(),ventaTabla.getItems().get(i).getCantidad());
                        if(!codigoError.equals("")){
                            errorCodigoCandidad+=codigoError;
                            errorCantidad++;
                        }
                    }
                    else{
                        String codigoError=agregar.DisponibleLocal(ventaTabla.getItems().get(i).getCodigo(),ventaTabla.getItems().get(i).getCantidad());
                        if(!codigoError.equals("")){
                            errorCodigoCandidad+=codigoError;
                            errorCantidad++;
                        }
                    }
                }
                if(errorCantidad==0) {
                    for (int i = 0; i < ventaTabla.getItems().size(); i++) {
                        if (ventaTabla.getItems().get(i).getTipo().equals("Charcuteria")) {
                            agregar.del_charcuteria(ventaTabla.getItems().get(i).getCodigo(), ventaTabla.getItems().get(i).getCantidad());
                        } else if (ventaTabla.getItems().get(i).getTipo().equals("Viveres")) {
                            agregar.del_viveres(ventaTabla.getItems().get(i).getCodigo(), ventaTabla.getItems().get(i).getCantidad());
                        } else {
                            agregar.del_local(ventaTabla.getItems().get(i).getCodigo(), ventaTabla.getItems().get(i).getCantidad());
                        }
                    }
                    agregar.add_venta(tipo_s, codigo_s, producto_s, cantidad_s, precio_s, preciot_s, "", "", "");
                    precc=0;
                    eliminart();
                    error.setText("Venta realizada");
                    error.setStyle("fx-background-color: #2cc231;");
                    viverest();
                }
                else{
                    System.out.println(errorCodigoCandidad);
                }
            }
        } catch (SQLException e) {
            error.setText("Error al realizar venta");
            error.setStyle("fx-background-color: #cc1313;");
            e.printStackTrace();
        }
        finally {
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                    Platform.runLater(() -> error.setStyle("fx-background-color: #fafafa;"));
                    t.cancel();
                }
            };
            t.schedule(tt, 4000 );
        }
        desconectar();
    }

    private void setprecio_eliminar(){
        precc=0;
        for(int i=0;i<ventaTabla.getItems().size();i++){
            precc+=Float.parseFloat(ventaTabla.getItems().get(i).preciotProperty().getValue());
        }
        tprecio.setText(precc + "");
    }
    public void agregarProducto(String pro,JFXTextField cantidad,String precio,String tipo) {
        try {
            JFXButton eli = new JFXButton("Borrar");
            eli.setId("elimina");
            eli.setOnMouseReleased(event -> {
                //setcancompr();
                setprecio_eliminar();
            });
            float can = Float.parseFloat(cantidad.getText());
            float preci = Float.parseFloat(precio);
            float precit = can * preci;

            boolean encontrado = false;
            int nencontrado = 0;
            for (int i = 0; i < ventaTabla.getItems().size(); i++) {
                if (ventaTabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))) {
                    encontrado = true;
                    nencontrado = i;
                }
            }
            if (encontrado) {
                ventaTabla.getItems().get(nencontrado).preciouProperty().setValue(preci + "");
                float anterior = Float.parseFloat(ventaTabla.getItems().get(nencontrado).cantidadProperty().getValue());
                float anteriort = Float.parseFloat(ventaTabla.getItems().get(nencontrado).preciotProperty().getValue());
                ventaTabla.getItems().get(nencontrado).cantidadProperty().setValue(can + anterior + "");
                float nuevaCantidad = can + anterior;
                precit = preci * nuevaCantidad;
                ventaTabla.getItems().get(nencontrado).preciotProperty().setValue(precit + "");
                precit = precit - anteriort;
            } else {
                    pro=pro.substring(0,pro.indexOf("$:"));
                lista.add(new ListaTabla(tipo, pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2), can + "", preci + "", precit + "", eli, ventaTabla));
            }
                tipop.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
                codigol.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
                productol.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
                cantidadl.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
                preciotl.setCellValueFactory(cellData -> cellData.getValue().preciotProperty());
                precioul.setCellValueFactory(cellData -> cellData.getValue().preciouProperty());
                elimina.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
                precc += precit;
                tprecio.setText(precc + "");
            ventaTabla.setItems(lista);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void agregaviveres(){
        String pro=viveres.getSelectionModel().getSelectedItem().toString();
        agregarProducto(pro,cantivive,pro.substring(pro.indexOf("$:")+2),"Viveres");

    }
    @FXML
    public void agregacharcuteria(){


        String pro=charcuteria.getSelectionModel().getSelectedItem().toString();
        agregarProducto(pro,cantichar,pro.substring(pro.indexOf("$:")+2),"Charcuteria");

    }
    @FXML
    public void agregalocal(){
        String pro=local.getSelectionModel().getSelectedItem().toString();
        agregarProducto(pro,cantilo,pro.substring(pro.indexOf("$:")+2),"Local");

    }
    @FXML
    public void eliminart(){
        tprecio.setText("0,00");
        precc=0;
        ventaTabla.getItems().clear();
    }
    @FXML
    public void buscarvi(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(getViveres(buscavive.getText()));
        viveres.setItems(items);
        viveres.getSelectionModel().select(0);
    }
    @FXML
    public void buscarloca(){
        ObservableList<String> items4=FXCollections.observableArrayList();
        items4.addAll(getLocal(buscalocal.getText()));
        local.setItems(items4);
        local.getSelectionModel().select(0);
    }
    @FXML
    public void buscachr() {
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(getCharcuteria(buscacharcu.getText()));
        charcuteria.setItems(items2);
        charcuteria.getSelectionModel().select(0);
    }
    private boolean fullscreen=false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Metodos metodos=new Metodos();
        buscarvi();
        buscachr();
        buscarloca();
        CampoTexto textoampo=new CampoTexto();
        textoampo.setdouble(cantichar);
        empresa.setText(metodos.empresa());
        ventaTabla.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try{
                    ventaTabla.getSelectionModel().select(ventaTabla.getSelectionModel().getFocusedIndex());
                    float elp=Float.parseFloat(ventaTabla.getSelectionModel().getSelectedItem().getPreciot());
                    ventaTabla.getItems().remove(ventaTabla.getSelectionModel().getSelectedIndex());
                    precc-=elp;
                    tprecio.setText(precc+"");
                }catch(Exception e){
                    tprecio.setText("0,00");
                }}
        });
        full.setOnAction(event -> {
            if(fullscreen){
                ((Stage)full.getScene().getWindow()).setFullScreen(false);
                full.setText("Pantalla completa");

                fullscreen=false;
            }else{
                ((Stage)full.getScene().getWindow()).setFullScreen(true);
                full.setText("Pantalla normal");
                fullscreen=true;
            }
        });
        ventana.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.F1){
                tabs.getSelectionModel().select(0);
            }
            else if(event.getCode()== KeyCode.F2){
                tabs.getSelectionModel().select(1);
            }
        });
    }
    @FXML
    private JFXButton full;
    private Usuario usuarioInstance=new Usuario();
    @FXML
    private void cerrar_sesion(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main=new Main();
        main.mostrarVentanaSecundaria();
    }
    public void cambiausuario(){
        try {
        Stage stage=new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Editarusuario.fxml"));
        AnchorPane ventanaDos = loader.load();
        Stage ventana = new Stage();
        ventana.setTitle("Editar usuario");
        ventana.initOwner(stage);
        Scene scene = new Scene(ventanaDos);
        ventana.setScene(scene);
        ventana.setResizable(false);
        ventana.getIcons().add(new Image(getClass().getResource("logo2.png").toExternalForm()));
        EditarusuEvent controller = loader.getController();
        controller.setUsuario(usuario.getText(),"12345");
        // controller.parametros(this,usuario,metodos.getusuario(id));
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.show();
        ventana.setOnCloseRequest(event -> {
            usuario.setText(usuarioInstance.getUsuario(idUsuario));
        });
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }
    public void setProgramaPrincipal(Caja ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    public void setUsuario(String usuarion) {
        this.usuarion=usuarion;
        usuario.setText(usuarion);
    }
    @FXML
    public void productoter() {
        //estado = 0;
        tabla("local", "");
    }
    @FXML
    public void viverest() {
       // estado = 1;
        tabla("viveres", "");
    }

    @FXML
    public void charcuteriat() {
        //estado = 3;
        tabla("charcuteria", "");
    }
    @FXML
    public void tabla(String sql, String sql2) {
        Connection c;
        Conectar();
        data = FXCollections.observableArrayList();
        try {
            c = getconnection();
            table.getColumns().clear();
            String SQL = "SELECT * FROM `bakery`.`" + sql + "` " + sql2 + "";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                table.getColumns().addAll(col);
            }
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            rs.close();
            table.setItems(data);
            //titulo.setText(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en tabla");
        }
        finally {
            desconectar();
        }
    }
}

