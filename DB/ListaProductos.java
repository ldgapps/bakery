package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListaProductos extends Conexion {
    public ArrayList materiaPrima(String busqueda){
        Conectar();
        ArrayList charcuteria=new ArrayList();
        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,marca FROM `materia prima` WHERE `codigo` LIKE ? OR `producto` LIKE ? or `marca` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            preparedStatement.setString(3,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3));
            }
            consulta.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            charcuteria.add("Error al obtener producto");
        }
        finally {
            desconectar();
            return charcuteria;
        }
    }
    
    public ArrayList viveres(String busqueda){
        Conectar();
        ArrayList materia=new ArrayList();

        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,marca  FROM `viveres` WHERE `codigo` LIKE ? OR `producto` LIKE ? or `marca` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            preparedStatement.setString(3,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3));
            }
            consulta.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            materia.add("Error al obtener producto");
        }finally {
            desconectar();
            return materia;
        }
    }

    public ArrayList local(String busqueda){
        Conectar();
        ArrayList materia=new ArrayList();
        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto FROM `local` WHERE `codigo` LIKE ? OR `producto` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2));
            }
            consulta.close();
        } catch (SQLException ex) {
            materia.add("Error al obtener producto");
        }finally {
            desconectar();
            return materia;
        }
    }
    public ArrayList charcuteria(String busqueda){
        Conectar();
        ArrayList charcuteria=new ArrayList();
        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,marca  FROM `charcuteria` WHERE `codigo` LIKE ? OR `producto` LIKE ? or `marca` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            preparedStatement.setString(3,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3));
            }
            consulta.close();
        } catch (SQLException ex) {
            charcuteria.add("Error al obtener producto");
        }
        finally {
            desconectar();
            return charcuteria;
        }
    }
    public ArrayList proveedores(String busqueda){
        Conectar();
        try {
            ArrayList proveedor=new ArrayList();
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT * FROM `proveedores` WHERE `codigo` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                proveedor.add(consulta.getString(1)+": "+consulta.getString(2)+" ("+consulta.getString(3)+")");
            }
            return proveedor;
        } catch (SQLException ex) {
            return null;
        }
        finally {
            desconectar();
        }

    }
}
