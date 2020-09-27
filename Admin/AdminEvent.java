
package Admin;

import DB.*;
import Agregar.AgregausuEvent;
import Editar.EliminausuEvent;
import Model.ListaTabla;
import com.jfoenix.controls.*;
import Editar.EditarusuEvent;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Editar.EditarprecioEvent;
import Editar.EditarproductoEvent;
import Editar.EditarcostoEvent;
import Editar.EliminaProveeEvent;
import Editar.EditarproveedorEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import util.Fecha;
import util.Pdf;
import util.CampoTexto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Acerca;
import sample.Main;
import util.TimerTexto;

public class AdminEvent extends ListaProductos implements Initializable {
    private int estado;
    ListaVenta listaProductoVenta=new ListaVenta();
    private ObservableList<ObservableList> data;
    private String usuarion, datefecha, datefecha2,contra,idUsuario;

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @FXML
    private VBox parent;
/*    String find = "file://192.168.2.18/pm8/data/pmin/2017/201708/20170816/cuts/940.jpg";    
      try {
          URL file = new File(find).toURI().toURL();
          System.out.println("file="+file);
      }
      catch(Exception e){
          e.printStackTrace();
      } ;*/
    @FXML
    private JFXTabPane tabs;
    ObservableList<ListaTabla> listacom  = FXCollections.observableArrayList();
    ObservableList<ListaTabla> listaVenta  = FXCollections.observableArrayList();
    ObservableList<ListaTabla> listaProduccion  = FXCollections.observableArrayList();
    @FXML
    private TableView<ListaTabla> ventaTabla,compratabla,tablaProduccion;

    @FXML
    private TableColumn<ListaTabla, String> tipop, codigol, productol, cantidadl, precioul, preciotl,
            tipopVenta, codigoVenta, productoVenta, cantidadVenta, preciouVenta, preciotVenta,
            codigoProduccion,productoProduccion,cantidadProduccion,costoProduccion,costoTotalProduccion;
    @FXML
    private TableColumn<ListaTabla,JFXButton> elimina,elimina1,eliminarproduccion;

    @FXML
    private TableView table;
    @FXML
    private JFXComboBox materiaProduccion, productoLocalProduccion, proveedor, co_vive, mate_p,pro_char,co_viveVenta,localVenta,pro_charcuertiaVenta;
    @FXML
    private Label usuario, titulo, empresa, error, lprecio,lprecio1,costoUnitarioProducc,errorCompra,errorVenta,errorMantenimiento;
    @FXML
    private JFXDatePicker fecha1, fecha2;
    @FXML
    private JFXTextField buscaProveedor,precioukgchar,pesounichar,precioumap,cantimap,busqueda, fact, resultante, canti, busvive, busmap,buschar,preciouvive,cantivive,
            busviveVenta,cantiviveresVenta,cantiLocalVenta,pesounicharVenta,buscharVenta,buslocalVenta,buscalocal,buscamateriaPr;
    @FXML
    private JFXButton full;
    private Fecha getfecha = new Fecha();
    private Pdf pdfs;
    private float precc;

    private boolean datepickerboo() {
        datefecha = fecha1.getEditor().getText();
        datefecha2 = fecha2.getEditor().getText();
        return datefecha.length() != 0 && datefecha2.length() != 0;
    }

    private String datepicker() {
        datefecha = fecha1.getEditor().getText();
        datefecha2 = fecha2.getEditor().getText();
        if (datefecha.length() == 0 || datefecha2.length() == 0) {
            return "0";
        } else {
            String dia = datefecha.substring(0, 2);
            String mes = datefecha.substring(3, 5);
            String agno = datefecha.substring(6);
            String dia2 = datefecha2.substring(0, 2);
            String mes2 = datefecha2.substring(3, 5);
            String agno2 = datefecha2.substring(6);
            return agno + "-" + mes + "-" + dia + "' AND '" + agno2 + "-" + mes2 + "-" + dia2;
        }
    }

    @FXML
    public void compra() {
        Agregar metodos=new Agregar();
        try {
            String tipo_s = "";
            String precio_s = "";
            String preciot_s = "";
            String codigo_s = "";
            String producto_s = "";
            String cantidad_s = "";
            for (int i = 0; i < compratabla.getItems().size(); i++) {
                codigo_s += compratabla.getItems().get(i).getCodigo() + '\n';
                producto_s += compratabla.getItems().get(i).getProducto() + '\n';
                cantidad_s += compratabla.getItems().get(i).getCantidad() + '\n';
                precio_s += compratabla.getItems().get(i).getPreciou() + '\n';
                preciot_s += compratabla.getItems().get(i).getPreciot() + '\n';
                tipo_s += compratabla.getItems().get(i).getTipo() + '\n';
            }
            metodos.add_compra(tipo_s, codigo_s, producto_s, proveedor.getSelectionModel().getSelectedItem().toString(), precio_s, preciot_s,  lprecio.getText(), cantidad_s, fact.getText());
            for (int i = 0; i < compratabla.getItems().size(); i++) {
                if (compratabla.getItems().get(i).getTipo().equals("Materia prima")) {
                    metodos.add_materia_prima(compratabla.getItems().get(i).getCodigo(), compratabla.getItems().get(i).getCantidad(), compratabla.getItems().get(i).getPreciou());
                } else if (compratabla.getItems().get(i).getTipo().equals("Viveres")) {
                    metodos.add_viveres(compratabla.getItems().get(i).getCodigo(), compratabla.getItems().get(i).getCantidad(), compratabla.getItems().get(i).getPreciou());
                } else {
                    metodos.add_charcuteria(compratabla.getItems().get(i).getCodigo(), compratabla.getItems().get(i).getCantidad(), compratabla.getItems().get(i).getPreciou());
                }
            }
            errorCompra.setText("Compra realizada");
            eliminart();
        } catch (SQLException e) {
            errorCompra.setText("Factura repetida");
            e.printStackTrace();
        }
        finally {
           TimerTexto.setTimer(errorCompra);
        }
    }


    public void agregarProducto(TableView<ListaTabla> tabla,ObservableList<ListaTabla> lista,String pro,JFXTextField cantidad,String precio,String tipo,boolean compra) {
        try {
            System.out.println(precio);
            JFXButton eli = new JFXButton("Borrar");
            eli.setId("elimina");
            eli.setOnMouseReleased(event -> {
                if(compra){
                    setprecio_eliminar(compratabla,lprecio);
                }
                else{
                    setprecio_eliminar(ventaTabla,lprecio1);
                }
            });
            float can = Float.parseFloat(cantidad.getText());
            float preci = Float.parseFloat(precio);
            float precit = can * preci;

            boolean encontrado = false;
            int nencontrado = 0;
            for (int i = 0; i < tabla.getItems().size(); i++) {
                if (tabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))) {
                    encontrado = true;
                    nencontrado = i;
                }
            }
            if (encontrado) {
                tabla.getItems().get(nencontrado).preciouProperty().setValue(preci + "");
                float anterior = Float.parseFloat(tabla.getItems().get(nencontrado).cantidadProperty().getValue());
                float anteriort = Float.parseFloat(tabla.getItems().get(nencontrado).preciotProperty().getValue());
                tabla.getItems().get(nencontrado).cantidadProperty().setValue(can + anterior + "");
                float nuevaCantidad = can + anterior;
                precit = preci * nuevaCantidad;
                tabla.getItems().get(nencontrado).preciotProperty().setValue(precit + "");
                precit = precit - anteriort;
            } else {
                if(pro.contains("$")){
                    pro=pro.substring(0,pro.indexOf('$'));
                }
                lista.add(new ListaTabla(tipo, pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2), can + "", preci + "", precit + "", eli, tabla));
            }
            tabla.setItems(lista);
            if(compra) {
                tipop.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
                codigol.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
                productol.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
                cantidadl.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
                preciotl.setCellValueFactory(cellData -> cellData.getValue().preciotProperty());
                precioul.setCellValueFactory(cellData -> cellData.getValue().preciouProperty());
                elimina.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
                lprecio.setText(precc + "");
                setprecio_eliminar(compratabla,lprecio);
            }
            else {
                tipopVenta.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
                codigoVenta.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
                productoVenta.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
                cantidadVenta.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
                preciotVenta.setCellValueFactory(cellData -> cellData.getValue().preciotProperty());
                preciouVenta.setCellValueFactory(cellData -> cellData.getValue().preciouProperty());
                elimina1.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
                setprecio_eliminar(ventaTabla,lprecio1);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setprecio_eliminar(TableView<ListaTabla> tabla,Label label){
        precc=0;
        for(int i=0;i<tabla.getItems().size();i++){
            precc+=Float.parseFloat(tabla.getItems().get(i).preciotProperty().getValue());
        }
        label.setText(precc + "");
    }
    @FXML
    public void agrega() {
        agregarProducto(compratabla,listacom,co_vive.getSelectionModel().getSelectedItem().toString(),cantivive,preciouvive.getText(),"Viveres",true);
    }
    @FXML
    private void agregamateriacom() {
        agregarProducto(compratabla,listacom,mate_p.getSelectionModel().getSelectedItem().toString(),cantimap,precioumap.getText(),"Materia prima",true);
    }
    @FXML
    private void agregamchracuteriacom() {
        agregarProducto(compratabla,listacom,pro_char.getSelectionModel().getSelectedItem().toString(),pesounichar,precioukgchar.getText(),"Charcuteria",true);
    }
    @FXML
    public void agregaViveresVenta() {
        String producto=co_viveVenta.getSelectionModel().getSelectedItem().toString();
        agregarProducto(ventaTabla,listaVenta,co_viveVenta.getSelectionModel().getSelectedItem().toString(),cantiviveresVenta,producto.substring(producto.indexOf("$:")+2),"Viveres",false);
    }
    @FXML
    private void agregaLocalVenta() {
        String producto=co_viveVenta.getSelectionModel().getSelectedItem().toString();
        agregarProducto(ventaTabla,listaVenta,localVenta.getSelectionModel() .getSelectedItem().toString(),cantiLocalVenta,producto.substring(producto.indexOf("$:")+2),"Local",false);
    }
    @FXML
    private void agregamCharcuteriaVenta() {
        String producto=pro_charcuertiaVenta.getSelectionModel().getSelectedItem().toString();
        agregarProducto(ventaTabla,listaVenta,pro_charcuertiaVenta.getSelectionModel().getSelectedItem().toString(),pesounicharVenta,producto.substring(producto.indexOf("$:")+2),"Charcuteria",false);
    }
    @FXML
    public void eliminart() {
        lprecio.setText("0.00");
        compratabla.getItems().clear();
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
                            errorCodigoCandidad+=" "+codigoError;
                            errorCantidad++;
                        }
                    }
                    else if(ventaTabla.getItems().get(i).getTipo().equals("Viveres")){
                        String codigoError=agregar.DisponibleViveres(ventaTabla.getItems().get(i).getCodigo(),ventaTabla.getItems().get(i).getCantidad());
                        if(!codigoError.equals("")){
                            errorCodigoCandidad+=" "+codigoError;
                            errorCantidad++;
                        }
                    }
                    else{
                        String codigoError=agregar.DisponibleLocal(ventaTabla.getItems().get(i).getCodigo(),ventaTabla.getItems().get(i).getCantidad());
                        if(!codigoError.equals("")){
                            errorCodigoCandidad+=" "+codigoError;
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
                    errorVenta.setText("Venta realizada");
                }
                else{
                	errorVenta.setText("Error de cantidad en codigo(s): "+errorCodigoCandidad);
                }
            }
        } catch (SQLException e) {
            errorVenta.setText("Error al realizar venta");
            e.printStackTrace();
        }
        finally {
            TimerTexto.setTimer(errorVenta);
        }
        desconectar();
    }
    @FXML
    private void agregarMateriaProduccion(ActionEvent event) {
        JFXButton eli = new JFXButton("Borrar");
        eli.setId("elimina");
        eli.setOnMouseReleased(event2 -> {
            costo();
        });
        String producto=materiaProduccion.getSelectionModel().getSelectedItem().toString();
        String codigo=producto.substring(0,producto.indexOf(": "));
        float cantidad=Float.parseFloat(canti.getText());
        float costomateria=Float.parseFloat(get_prop_producto("materia prima",codigo,"costo"));
        float costot=costomateria*cantidad;
        listaProduccion.add(new ListaTabla(codigo, producto.substring(producto.indexOf(": ") + 2),  canti.getText(), costomateria+"", costot+"", eli, tablaProduccion));
        setTablaProduccion();
        costo();
    }
    private void setTablaProduccion(){
        codigoProduccion.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        productoProduccion.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        cantidadProduccion.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        costoProduccion.setCellValueFactory(cellData -> cellData.getValue().preciouProperty());
        costoTotalProduccion.setCellValueFactory(cellData -> cellData.getValue().preciotProperty());
        eliminarproduccion.setCellValueFactory(cellData->cellData.getValue().eliminaProperty());
        tablaProduccion.setItems(listaProduccion);
    }
    @FXML
    private void agregarProduccion(ActionEvent event) {
        Agregar agregar=new Agregar();
        String ingredientes = "";
        String codigo_s="";
        String cantidad_s="";
        String precio_s="";
        String preciot_s="";
        try {
            String errorCodigoCandidad="";
            String producto=productoLocalProduccion.getSelectionModel().getSelectedItem().toString();
            String codigo=producto.substring(0,producto.indexOf(": "));
            for(int i=0;i<tablaProduccion.getItems().size();i++){
                codigo_s+=tablaProduccion.getItems().get(i).getCodigo()+'\n';
                ingredientes+=tablaProduccion.getItems().get(i).getProducto()+'\n';
                cantidad_s+=tablaProduccion.getItems().get(i).getCantidad()+'\n';
                precio_s+=tablaProduccion.getItems().get(i).getPreciou()+'\n';
                preciot_s+=tablaProduccion.getItems().get(i).getPreciot()+'\n';
                String codigoError=agregar.DisponibleMateria(tablaProduccion.getItems().get(i).getCodigo(),tablaProduccion.getItems().get(i).getCantidad());
                if(!codigoError.equals("")){
                    errorCodigoCandidad+=codigoError;
                }
            }
            if(errorCodigoCandidad.equals("")){
                for (int i = 0; i < tablaProduccion.getItems().size(); i++) {
                    agregar.del_materia(tablaProduccion.getItems().get(i).getCodigo(), tablaProduccion.getItems().get(i).getCantidad());
                }
            agregar.add_local(codigo,resultante.getText(),"10");
                agregar.registro_produccion(codigo,producto,ingredientes,resultante.getText(),precio_s,preciot_s);
                error.setText("Produción realizada correctamente");}
            else{

            }
        } catch (Exception ex) {
            error.setText("Error al producir");
            ex.printStackTrace();
        }
        finally {
            TimerTexto.setTimer(error);
        }
    }
    @FXML
    public void costo() {
        float costoUnitario=0;
        for(int i=0;i<tablaProduccion.getItems().size();i++){
            costoUnitario+=Float.parseFloat(tablaProduccion.getItems().get(i).getPreciot());
        }
        costoUnitario /= Float.parseFloat(resultante.getText());
        costoUnitarioProducc.setText(costoUnitario+"");
    }
    @FXML
    public void agrega_usuario() {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Agregar/AgregarUsuario.fxml"));
            AnchorPane ventanaDos = loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Usuario");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("logo2.png").toExternalForm()));
            AgregausuEvent controller = loader.getController();
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void recetapro(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Nueva receta");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Recetas de productos", "*.bakery"));
        File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        try {
            BufferedWriter escribir = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < tablaProduccion.getItems().size(); i++) {
                escribir.write(tablaProduccion.getItems().get(i).getCodigo() + '\n');
                escribir.write(tablaProduccion.getItems().get(i).getCantidad() + '\n');
            }
            escribir.write(resultante.getText() + '\n');
            String codpro = productoLocalProduccion.getSelectionModel().getSelectedItem().toString();
            escribir.write(codpro.substring(0, codpro.indexOf(":")));
            escribir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void seleccreceta(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Recetas de productos", "*.bakery"));
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
                int i = lineas(file)-2;
                BufferedReader leer2 = new BufferedReader(new FileReader(file));

                for (int j = 0; j < i / 2; j++) {
                    JFXButton eli = new JFXButton("Borrar");
                    eli.setId("elimina");
                    eli.setOnMouseReleased(event2 -> {
                            costo();
                    });
                    String codigo=leer2.readLine();
                    float cantidad=Float.parseFloat(leer2.readLine());
                    String costo= get_prop_producto("materia prima",codigo,"costo");
                    float costot=Float.parseFloat(costo)*cantidad;
                    listaProduccion.add(new ListaTabla(codigo, get_prop_producto("materia prima",codigo,"producto"), cantidad+"" ,costot+"", "", eli, tablaProduccion));
                    //lista.getItems().add(leer2.readLine());
                    setTablaProduccion();
                }
            resultante.setText(leer2.readLine());
            //buscalocal.setText(leer2.readLine());
            leer2.close();
            costo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int lineas(File file) {
        int i = 0;
        BufferedReader leer = null;
        try {
            leer = new BufferedReader(new FileReader(file));
            String cadena;
            while ((cadena = leer.readLine()) != null) {
                i++;
            }
            leer.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @FXML
    public void pdf(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        pdfs = new Pdf();
        fileChooser.setInitialFileName(titulo.getText() + ".pdf");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        pdfs.utilJTableToPdf(file, titulo.getText(), table);
    }
    public void cambiaem() {

        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Empresa.fxml"));
            AnchorPane ventanaDos = loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Cambiar empresa");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("logo2.png").toExternalForm()));
            EmpresaEvent controller = loader.getController();
            Metodos metodos=new Metodos();
            controller.parametros(this,empresa,metodos.empresa());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private Usuario usuarioInstance=new Usuario();
    public void cambiausuario() {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/Editarusuario.fxml"));
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
            ventana.setOnHiding(event -> {
                usuario.setText(usuarioInstance.getUsuario(idUsuario));
            });
            ventana.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    public void eliminausu() {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/EliminarUsuario.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            // ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EliminausuEvent controller = loader.getController();
            controller.setUsuario(usuario.getText());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setUsuario(String usua) {
        usuarion = usua;
        usuario.setText(usua);
    }

    public String getUsuario() {
        return usuario.getText();
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
            table.setItems(data);
            titulo.setText(sql);
            if (sql.equals("viveres") || sql.equals("local") || sql.equals("materia prima") || sql.equals("charcuteria")){
                table.setOnMouseReleased(event -> {
                    ObservableList<String> e = FXCollections.observableArrayList();
                    e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
                    if (event.getButton().equals(MouseButton.PRIMARY) && event.isControlDown()) {
                        ventanaEditarProducto(e.get(0), e.get(1), e.get(2), sql);
                    } else if (event.getButton().equals(MouseButton.PRIMARY) && !sql.equals("materia prima")) {
                        ventanaPrecios(sql, e.get(0));
                    } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                        ventanaEditarCosto(sql, e.get(0));
                    }
                });
        }
            else if(sql.equals("proveedores")){
                table.setOnMouseReleased(event -> {
                    ObservableList<String> e = FXCollections.observableArrayList();
                    e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
                    ventanaEditarProveedor(e.get(0),e.get(1),e.get(2),e.get(3));
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en tabla");
        }
        finally {
            desconectar();
        }
    }

    @FXML
    public void productoter() {
        estado = 0;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("local", "");
    }
    @FXML
    public void comprast() {
        estado = 2;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        tabla("compras", "WHERE `fecha` BETWEEN '" + getfecha.getFechaSql() + "' AND '" + getfecha.getFechaSql() + "'");
    }
    @FXML
    public void buscar() {
        if (estado == 0) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("local", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 1) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("viveres", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 2) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("compras", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `proveedor` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 3) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("charcuteria", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        }  else if (estado == 6) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("consumo interno", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%");
        } else if (estado == 8) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("proveedores", "WHERE `nombre` LIKE '%" + busqueda.getText() + "%' OR `rif` LIKE '%" + busqueda.getText() + "%' OR `ciudad` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 9) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("ventas", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `cliente` LIKE '%" + busqueda.getText() + "%' OR `ci` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 10) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("perdidas", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 11) {

            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("materia prima", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 12) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("produccion", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%'");
        }
    }

    @FXML
    public void viverest() {
        estado = 1;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("viveres", "");
        table.setOnKeyPressed(event -> {
        });
    }

    @FXML
    public void producciont() {
        estado = 12;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("produccion", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("produccion", "WHERE `fecha` BETWEEN '" + getfecha.getFechaSql() + "' AND '" + getfecha.getFechaSql() + "'");
        }
    }

    @FXML
    public void acerca() {
        Acerca acerca = new Acerca();
        acerca.mostrarVentanaSecundaria();
    }

    @FXML
    public void fecha() {
        if (estado == 6) {
            consumot();
        } else if (estado == 9) {
            ventast();
        } else if (estado == 10) {
            perdidast();
        } else if (estado == 12) {
            producciont();
        }
    }
    @FXML
    public void charcuteriat() {
        estado = 3;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        table.setOnKeyPressed(event -> {
        });
        tabla("charcuteria", "");
    }

    @FXML
    public void consumot() {

        estado = 6;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("consumo interno", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("consumo interno", "WHERE `fecha` BETWEEN '" + getfecha.getFechaSql() + "" + "' AND '" + getfecha.getFechaSql() + "'");
        }
    }


    @FXML
    public void proveedorest() {
        estado = 8;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        table.setOnKeyPressed(event -> {
        });
        tabla("proveedores", "");
    }

    @FXML
    public void ventast() {
        estado = 9;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("ventas", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("ventas", "WHERE `fecha` BETWEEN '" + getfecha.getFechaSql() + "' AND '" + getfecha.getFechaSql() + "'");
        }
    }

    @FXML
    public void perdidast() {
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        estado = 10;
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("produccion", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("perdidas", "WHERE `fecha` BETWEEN '" + getfecha.getFechaSql() + "" + "' AND '" + getfecha.getFechaSql() + "'");
        }
    }

    @FXML
    public void materiap() {
        estado = 11;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("materia prima", "");
    }
    public void ventanaEditarProducto(String codigo,String producto,String marca,String tipo){
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/Editarproducto.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            // ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EditarproductoEvent controller = loader.getController();
            controller.setpro(codigo,producto,marca,tipo);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
// https://dxj1e0bbbefdtsyig.woldrssl.net/custom/rate2.jpg?nc=1
    @FXML
    public void precios() {
        ventanaPrecios("","");
    }
    public void ventanaPrecios(String tipo,String codigo){
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/Editarprecios.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar precios");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            // ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EditarprecioEvent controller = loader.getController();
            controller.setProducto(tipo,codigo);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void editarCostos() {
        ventanaEditarCosto("","");
    }
    public void ventanaEditarCosto(String tipo,String codigo){
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/Editarcosto.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar Costo");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            EditarcostoEvent controller=loader.getController();
            controller.setProducto(tipo,codigo);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void ventanaEditarProveedor(String id,String codigo,String nombre,String rif){
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/EditarProveedor.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar Costo");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            EditarproveedorEvent controller=loader.getController();
            controller.setproveedor(id,codigo,nombre,rif);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                proveedorest();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void cerrar_sesion(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        main.mostrarVentanaSecundaria();
    }

    @FXML
    private void proveedor(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Agregar/proveedor.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Proveedor");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                setProveedores();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    private void elimninarproveedor(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Editar/EliminarProvee.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Eliminar Proveedor");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            EliminaProveeEvent controller=loader.getController();
            controller.setUsuario(usuario.getText());
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                setProveedores();
                proveedorest();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    private void consumo(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Agregar/Consumo.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void producto(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Agregar/producto.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void Perdida(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Agregar/Perdida.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Perdida");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            // ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                actualizaReportes();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CampoTexto textoampo = new CampoTexto();
        Metodos metodos=new Metodos();
        empresa.setText(metodos.empresa());
        full.setOnAction(event -> {
            if (((Stage) full.getScene().getWindow()).isFullScreen()) {
                ((Stage) full.getScene().getWindow()).setFullScreen(false);
                full.setText("Pantalla completa");
            } else {
                ((Stage) full.getScene().getWindow()).setFullScreen(true);
                full.setText("Pantalla normal");
            }
        });
        error.setText("");
        textoampo.setdouble(canti);
        textoampo.setint(cantivive);
        textoampo.setint(cantiLocalVenta);
        textoampo.setint(cantiviveresVenta);
        textoampo.setdouble(precioukgchar);
        textoampo.setdouble(pesounichar);
        textoampo.setdouble(preciouvive);
        textoampo.setdouble(precioumap);
        textoampo.setint(cantimap);
        textoampo.setdouble(pesounicharVenta);
        textoampo.setint(resultante);
        cantivive.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agrega();
            }
        });
        cantiLocalVenta.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregaLocalVenta();
            }
        });

        cantiviveresVenta.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregaViveresVenta();
            }
        });
        precioukgchar.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamchracuteriacom();
            }
        });
        pesounichar.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamchracuteriacom();
            }
        });
        preciouvive.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agrega();
            }
        });
        precioumap.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamateriacom();
            }
        });
        cantimap.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamateriacom();
            }
        });
        precioumap.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamateriacom();
            }
        });
        pesounicharVenta.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                agregamCharcuteriaVenta();
            }
        });


        actualizarProductos();
        setProveedores();
        parent.setOnKeyReleased(event -> {
            if(event.isControlDown()) {
                if (event.getCode() == KeyCode.DIGIT1) {
                    tabs.getSelectionModel().select(0);
                } else if (event.getCode() == KeyCode.DIGIT2) {
                    tabs.getSelectionModel().select(1);
                } else if (event.getCode() == KeyCode.DIGIT3) {
                        tabs.getSelectionModel().select(2);
                    } else if (event.getCode() == KeyCode.DIGIT4) {
                        tabs.getSelectionModel().select(3);
                    } else if (event.getCode() == KeyCode.DIGIT5) {
                        tabs.getSelectionModel().select(4);
                    }
                }
            else if (tabs.getTabs().get(0).isSelected()) {
                    if (event.getCode() == KeyCode.DIGIT1) {
                        charcuteriat();
                    } else if (event.getCode() == KeyCode.DIGIT2) {
                        viverest();
                    } else if (event.getCode() == KeyCode.DIGIT3) {
                        materiap();
                    } else if (event.getCode() == KeyCode.DIGIT4) {
                        productoter();
                    } else if (event.getCode() == KeyCode.DIGIT5) {
                        comprast();
                    } else if (event.getCode() == KeyCode.DIGIT6) {
                        proveedorest();
                    } else if (event.getCode() == KeyCode.DIGIT7) {
                        perdidast();
                    } else if (event.getCode() == KeyCode.DIGIT8) {
                        consumot();
                    } else if (event.getCode() == KeyCode.DIGIT9) {
                        ventast();
                    } else if (event.getCode() == KeyCode.DIGIT0) {
                        producciont();
                    }
                } else if (tabs.getTabs().get(2).isSelected()) {
                    if(event.isAltDown()) {
                        if(event.getCode() == KeyCode.V) {
                            busvive.requestFocus();
                        }
                    }
                }
            });
    }
    public void setProveedores(){
        ObservableList<String> itempro = FXCollections.observableArrayList();
        itempro.addAll(proveedores(buscaProveedor.getText()));
        proveedor.setItems(itempro);
        proveedor.getSelectionModel().select(0);
    }
    public void actualizarProductos(){
        materiap();
        buscarvi();
        buscarviveresVenta();
        buscarLocalVenta();
        buscarCharcuteriaVenta();
        buscarviveresVenta();
        buscarMateriProduccion();
        buscarlocalProduccion();
        buscarmp();
        buscachr();
    }
    public void buscarviveresVenta(){
        ObservableList<String> items=FXCollections.observableArrayList();

        items.addAll(listaProductoVenta.getViveres( busviveVenta.getText()));
        co_viveVenta.setItems(items);
        co_viveVenta.getSelectionModel().select(0);
    }
    public void buscarLocalVenta(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(listaProductoVenta.getLocal(buslocalVenta.getText()));
        localVenta.setItems(items);
        localVenta.getSelectionModel().select(0);
    }
    public void buscarCharcuteriaVenta(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(listaProductoVenta.getCharcuteria(buscharVenta.getText()));
        pro_charcuertiaVenta.setItems(items);
        pro_charcuertiaVenta.getSelectionModel().select(0);
    }
@FXML
    public void buscachr() {
    ObservableList<String> items2=FXCollections.observableArrayList();
    items2.addAll(charcuteria(buschar.getText()));
    pro_char.setItems(items2);
    pro_char.getSelectionModel().select(0);
    }

    public void buscarvi(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(viveres(busvive.getText()));
        co_vive.setItems(items);
        co_vive.getSelectionModel().select(0);
    }
    @FXML
    public void buscarmp(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(materiaPrima(busmap.getText()));
        mate_p.setItems(items);
        mate_p.getSelectionModel().select(0);
    }
    @FXML
    public void buscarMateriProduccion(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(materiaPrima(buscamateriaPr.getText()));
        materiaProduccion.setItems(items);
        materiaProduccion.getSelectionModel().select(0);
    }
    @FXML
    public void buscarlocalProduccion(){
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(local(buscalocal.getText()));
        productoLocalProduccion.setItems(items);
        productoLocalProduccion.getSelectionModel().select(0);
    }
    @FXML
    private void mantenimiento(){
        try {
            realizarMantenimiento();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void actualizaReportes(){
        if(estado==0){
            productoter();
        }
        else if(estado==1){
            viverest();
        }
        else if(estado==3){
            charcuteriat();
        }
        else if(estado==11){
            materiap();
        }
    }
}