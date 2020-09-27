package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Agregar extends Conexion{
    public void add_producto(String codigo,String producto,String marca,String tipo) throws SQLException {
        Conectar();
        if (tipo.equals("Materia prima")) {
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `materia prima` (`codigo`, `producto`, `marca`, `unidades`, `costo`) VALUES (?, ?, ?, '0', '0')");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.setString(3,marca);
            preparedStatement.execute();
        } else if (tipo.equals("Viveres")) {
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `viveres` (`codigo`, `producto`, `marca`, `cantidad`, `costo`, `precio`) VALUES (?, ?, ?, '0', '0', '0')");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.setString(3,marca);
            preparedStatement.execute();
        } else if (tipo.equals("Charcuteria")) {
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `charcuteria` (`codigo`, `producto`, `marca`, `peso`, `costo`, `precio`) VALUES (?, ?, ?, '0', '0', '0')");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.setString(3,marca);
            preparedStatement.execute();
        }  else {
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `local` (`codigo`, `producto`, `cantidad`,`costo`, `precio`) VALUES (?, ?, '0', '0','0')");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.execute();
        }
        desconectar();
    }
    public void registro_produccion(String codigo,String producto,String ingredientes,String resultado,String costoUni,String costoTotal) throws SQLException {
        Conectar();
        PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `produccion` (`codigo`, `producto`, `ingredientes`, `resultado`, `costo unitario`,`costo total`, `fecha`) VALUES (?,?, ?,?,?,?,?, CURRENT_DATE ())");
        preparedStatement.setString(1,codigo);
        preparedStatement.setString(2,producto);
        preparedStatement.setString(3,ingredientes);
        preparedStatement.setString(4,resultado);
        preparedStatement.setString(5,resultado);
        preparedStatement.setString(6,costoUni);
        preparedStatement.setString(7,costoTotal);
        preparedStatement.execute();
        desconectar();
    }

    public void add_charcuteria(String codigo,String peso,String costo) throws SQLException{
        Conectar();
        float cantidad=0;
        PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT peso FROM `charcuteria` WHERE `codigo` LIKE ?");
        preparedStatement.setString(1,codigo);
        ResultSet consulta=preparedStatement.executeQuery();
        if(consulta.next()){
            float c=consulta.getFloat(1);
            float c2=Float.parseFloat(peso);
            cantidad=c+c2;
        }
        consulta.close();
        PreparedStatement preparedStatement3=getconnection().prepareStatement("UPDATE `charcuteria` SET `peso`=? WHERE `codigo` LIKE ? ");
        preparedStatement3.setDouble(1,cantidad);
        preparedStatement3.setString(2,codigo);
        preparedStatement3.execute();
        PreparedStatement preparedStatement2=getconnection().prepareStatement("UPDATE `charcuteria` SET `costo`=? WHERE `codigo` LIKE ? ");
        preparedStatement2.setDouble(1,Double.parseDouble(costo));
        preparedStatement2.setString(2,codigo);
        preparedStatement2.execute();
        desconectar();
    }
    //viveres
    public void add_viveres(String codigo,String cantidad,String costo) throws SQLException{
        Conectar();
        float intCantidad=Float.parseFloat(cantidad);
        float nuevaCantidad=0;
        PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT cantidad FROM `viveres` WHERE `codigo` LIKE ?");
        preparedStatement.setString(1,codigo);
        ResultSet consulta=preparedStatement.executeQuery();
        if(consulta.next()){
            int cantidad2=consulta.getInt(1);
            nuevaCantidad=intCantidad+cantidad2;
        }
        consulta.close();
        PreparedStatement preparedStatement2=getconnection().prepareStatement("UPDATE `viveres` SET `cantidad`=? WHERE `codigo` LIKE ? ");
        preparedStatement2.setFloat(1,nuevaCantidad);
        preparedStatement2.setString(2,codigo);
        preparedStatement2.execute();
        PreparedStatement preparedStatement3=getconnection().prepareStatement("UPDATE `viveres` SET `costo`=? WHERE `codigo` LIKE ? ");
        preparedStatement3.setString(1,costo);
        preparedStatement3.setString(2,codigo);
        preparedStatement3.execute();
        desconectar();
    }
    public void add_local(String codigo,String cantidad,String costo) throws SQLException{
        Conectar();
        float intCantidad=Float.parseFloat(cantidad);
        float nuevaCantidad=0;
        PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT cantidad FROM `local` WHERE `codigo` LIKE ?");
        preparedStatement.setString(1,codigo);
        ResultSet consulta=preparedStatement.executeQuery();
        if(consulta.next()){
            float cantidad2=consulta.getFloat(1);
            nuevaCantidad=intCantidad+cantidad2;
        }
        consulta.close();
        PreparedStatement preparedStatement2=getconnection().prepareStatement("UPDATE `local` SET `cantidad`=? WHERE `codigo` LIKE ? ");
        preparedStatement2.setFloat(1,nuevaCantidad);
        preparedStatement2.setString(2,codigo);
        preparedStatement2.execute();
        PreparedStatement preparedStatement3=getconnection().prepareStatement("UPDATE `local` SET `costo`=? WHERE `codigo` LIKE ? ");
        preparedStatement3.setString(1,costo);
        preparedStatement3.setString(2,codigo);
        preparedStatement3.execute();
        desconectar();
    }
        public void add_venta(String tipo,String codigo,String productos,String cantidad,String precio,String preciot,String total,String cliente,String ci) throws SQLException{
            Conectar();
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `ventas` (`codigo`, `tipo`, `productos`, `cantidad`, `precio`, `precio t`,`total`, `cliente`, `ci`, `fecha`,`hora`) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?,?,?, CURRENT_DATE(),CURRENT_TIME ())");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,tipo);
            preparedStatement.setString(3,productos);
            preparedStatement.setString(4,cantidad);
            preparedStatement.setString(5,precio);
            preparedStatement.setString(6,preciot);
            preparedStatement.setString(7,total);
            preparedStatement.setString(8,cliente);
            preparedStatement.setString(9,ci);
            preparedStatement.execute();
            desconectar();
        }
        public void add_compra(String tipo,String codigo,String producto,String proveedor,String precio,String preciot,String total,String cantidad,String factura) throws SQLException{
            Conectar();
            System.out.println("qdasdasd");
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `compras` (`itm`, `codigo`, `tipo`, `producto`, `cantidad`, `precio`, `total producto`, `total`, `proveedor`, `fecha`,`hora`, `factura`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?,CURRENT_DATE(),CURRENT_TIME(), ?)");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,tipo);
            preparedStatement.setString(3,producto);
            preparedStatement.setString(4,cantidad);
            preparedStatement.setString(5,precio);
            preparedStatement.setString(6,preciot);
            preparedStatement.setString(7,total);
            preparedStatement.setString(8,proveedor);
            preparedStatement.setString(9,factura);
            preparedStatement.execute();
            desconectar();
        }

        public void add_materia_prima(String codigo,String unidad,String costo) throws SQLException{
            Conectar();
            PreparedStatement statement=getconnection().prepareStatement("SELECT unidades FROM `materia prima` WHERE `codigo` LIKE ?");
            statement.setString(1,codigo);
            float ncantidad=0;
            ResultSet consulta=statement.executeQuery();
            while(consulta.next()){
                float c=consulta.getFloat(1);
                float c2=Float.parseFloat(unidad);
                ncantidad=c+c2;

            }
            consulta.close();
            PreparedStatement statementunidades=getconnection().prepareStatement("UPDATE `materia prima` SET `unidades`=? WHERE `codigo` LIKE ? ");
            statementunidades.setDouble(1,ncantidad);
            statementunidades.setString(2,codigo);
            statementunidades.execute();
            PreparedStatement statementcosto=getconnection().prepareStatement("UPDATE `materia prima` SET `costo`=? WHERE `codigo` LIKE ? ");
            statementcosto.setDouble(1,Double.parseDouble(costo));
            statementcosto.setString(2,codigo);
            statementcosto.execute();
            desconectar();
        }
        public void add_proveedor(String codigo,String nombre,String rif) throws SQLException {
            Conectar();
            PreparedStatement insertar=getconnection().prepareStatement("INSERT INTO `proveedores` (`id`,`codigo`, `nombre`, `rif`) VALUES (NULL,?,?,?)");
            insertar.setString(1,nombre);
            insertar.setString(2,codigo);
            insertar.setString(3,rif);
            insertar.execute();
            desconectar();
        }
        public void add_perdida(String codigo,String producto,String cantidad,String precio) throws SQLException{
            Conectar();
            float preciof=Float.parseFloat(precio);
            float cantidadf=Float.parseFloat(cantidad);
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `perdidas` (`codigo`, `producto`, `cantidad`, `costo`, `fecha`) VALUES (?,?, ?,?, CURRENT_DATE())");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.setFloat(3,cantidadf);
            preparedStatement.setFloat(4,preciof);
            preparedStatement.execute();
            desconectar();
        }

        public void add_consumo(String tipo,String codigo,String producto,String cantidad) throws SQLException{
            Conectar();
            String gcosto=get_prop_producto(tipo,codigo,"costo");
            PreparedStatement preparedStatement=getconnection().prepareStatement("INSERT INTO `consumo interno` (`codigo`, `producto`, `cantidad`, `costo`, `fecha`) VALUES (?, ?,?, ?,  CURRENT_TIMESTAMP() )");
            preparedStatement.setString(1,codigo);
            preparedStatement.setString(2,producto);
            preparedStatement.setString(4,gcosto);
            preparedStatement.setString(3,cantidad);
            preparedStatement.execute();
            desconectar();
        }
        public float getCantCharcuteria(String codigo) {
            Conectar();
            float cantidad=0;
            try {
                PreparedStatement preparedStatement = getconnection().prepareStatement("SELECT peso FROM `charcuteria` WHERE `codigo` LIKE ?");
                preparedStatement.setString(1, codigo);
                ResultSet consulta = preparedStatement.executeQuery();
                while (consulta.next()) {
                    cantidad = consulta.getFloat(1);
                }
                consulta.close();
                desconectar();
            }catch (Exception e){
                desconectar();
                e.printStackTrace();
            }
            return cantidad;
        }
    public String DisponibleCharcuteria(String codigo,String cantidad){
        float cantidadfloat=Float.parseFloat(cantidad);
        if(getCantCharcuteria(codigo)>=cantidadfloat){
            return "";
        }
        else{
            return codigo;
        }
    }
    public void del_charcuteria(String codigo,String cantidad) throws SQLException {
        float cantidadfloat=Float.parseFloat(cantidad);
        float cantidadn=getCantCharcuteria(codigo)-cantidadfloat;
        Conectar();
        PreparedStatement preparedStatement1=getconnection().prepareStatement("UPDATE `charcuteria` SET `peso`=? WHERE `codigo` LIKE ? ");
        preparedStatement1.setFloat(1,cantidadn);
        preparedStatement1.setString(2,codigo);
        preparedStatement1.execute();
        desconectar();
    }

    public float getCantMateria(String codigo) {
        float cantidad = 0;
        try {
            Conectar();
            PreparedStatement preparedStatement = getconnection().prepareStatement("SELECT unidades FROM `materia prima` WHERE `codigo` LIKE ?");
            preparedStatement.setString(1, codigo);
            ResultSet consulta = preparedStatement.executeQuery();
            if (consulta.next()) {
                cantidad = consulta.getFloat(1);
            }
            consulta.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        finally {
            desconectar();
            return  cantidad;

        }
    }
    public String DisponibleMateria(String codigo,String cantidad){
        float cantidadfloat=Float.parseFloat(cantidad);
        if(getCantMateria(codigo)>=cantidadfloat){
            return "";
        }
        else{
            return codigo;
        }
    }
    public void del_materia(String codigo,String cantidad) throws SQLException {
        float cantidadf=Float.parseFloat(cantidad);
        float ncantidad=getCantMateria(codigo)-cantidadf;
        Conectar();
        PreparedStatement preparedStatement1=getconnection().prepareStatement("UPDATE `materia prima` SET `unidades`=? WHERE `codigo` LIKE ? ");
        preparedStatement1.setFloat(1,ncantidad);
        preparedStatement1.setString(2,codigo);
        preparedStatement1.execute();
        desconectar();
    }
    public float getCantViveres(String codigo) {
        float cantidad = 0;
        try {
            Conectar();
            PreparedStatement preparedStatement = getconnection().prepareStatement("SELECT cantidad FROM `viveres` WHERE `codigo` LIKE ?");
            preparedStatement.setString(1, codigo);
            ResultSet consulta = preparedStatement.executeQuery();
            if (consulta.next()) {
                cantidad = consulta.getFloat(1);
            }
            consulta.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        finally {
            desconectar();
            return  cantidad;

        }
    }
    public String DisponibleViveres(String codigo,String cantidad){
        float cantidadfloat=Float.parseFloat(cantidad);
        if(getCantViveres(codigo)>=cantidadfloat){
            return "";
        }
        else{
            return codigo;
        }
    }
    public void del_viveres(String codigo,String cantidad) throws SQLException{
        float cantidadfloat=Float.parseFloat(cantidad);
        float cantidadn=getCantViveres(codigo)-cantidadfloat;
        Conectar();
        PreparedStatement preparedStatement = getconnection().prepareStatement("UPDATE `viveres` SET `cantidad`=? WHERE `codigo` LIKE ? ");
        preparedStatement.setFloat(1, cantidadn);
        preparedStatement.setString(2, codigo);
        preparedStatement.execute();
        desconectar();
    }


    //local
    public float getCantLocal(String codigo) {
        float cantidad = 0;
        try {
            Conectar();
            PreparedStatement preparedStatement = getconnection().prepareStatement("SELECT cantidad FROM `local` WHERE `codigo` LIKE ?");
            preparedStatement.setString(1, codigo);
            ResultSet consulta = preparedStatement.executeQuery();
            if (consulta.next()) {
                cantidad = consulta.getFloat(1);
            }
            consulta.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        finally {
            desconectar();
            return  cantidad;

        }
    }
    public String DisponibleLocal(String codigo,String cantidad){
        float cantidadfloat=Float.parseFloat(cantidad);
        if(getCantLocal(codigo)>=cantidadfloat){
            return "";
        }
        else{
            return codigo;
        }
    }
    public void del_local(String codigo,String cantidad) throws SQLException{

        float cantidadfloat=Float.parseFloat(cantidad);
        float cantidadn=getCantLocal(codigo)-cantidadfloat;
        Conectar();
        PreparedStatement preparedStatement1= getconnection().prepareStatement("UPDATE `local` SET `cantidad`=? WHERE `codigo` LIKE ? ");
        preparedStatement1.setFloat(1,cantidadn);
        preparedStatement1.setString(2,codigo);
        preparedStatement1.execute();
        desconectar();
    }
    }
