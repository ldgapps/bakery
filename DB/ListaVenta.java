package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaVenta extends Conexion {
    public ArrayList getViveres(String busqueda){
        Conectar();
        ArrayList materia=new ArrayList();

        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,marca,precio  FROM `viveres` WHERE `codigo` LIKE ? OR `producto` LIKE ? or `marca` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            preparedStatement.setString(3,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3)+" $:"+consulta.getString(4));
            }
            consulta.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            materia.add("Error al obtener productos");
        }finally {
            desconectar();
            return materia;
        }
    }

    public ArrayList getLocal(String busqueda){
        Conectar();
        ArrayList materia=new ArrayList();
        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,precio FROM `local` WHERE `codigo` LIKE ? OR `producto` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" $:"+consulta.getString(3));
            }
            consulta.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            materia.add("Error al obtener productos");
        }finally {
            desconectar();
            return materia;
        }
    }
    public ArrayList getCharcuteria(String busqueda){
        Conectar();
        ArrayList charcuteria=new ArrayList();
        try {
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT codigo,producto,marca,precio  FROM `charcuteria` WHERE `codigo` LIKE ? OR `producto` LIKE ? or `marca` LIKE ?");
            preparedStatement.setString(1,"%"+busqueda+"%");
            preparedStatement.setString(2,"%"+busqueda+"%");
            preparedStatement.setString(3,"%"+busqueda+"%");
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3)+" $:"+consulta.getString(4));
            }
            consulta.close();
        } catch (SQLException ex) {ex.printStackTrace();
            charcuteria.add("Error al obtener productos");
        }
        finally {
            desconectar();
            return charcuteria;
        }
    }

}
