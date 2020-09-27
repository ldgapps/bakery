package DB;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Metodos extends Conexion{
    public String precio(String tipo,String codi){
        Conectar();
        try {
            ResultSet consulta = getconnection().createStatement().executeQuery("SELECT precio FROM `"+tipo+"` where `codigo` LIKE '"+codi+"'");
            while(consulta.next()){
                return consulta.getString(1);}
            return "0.0";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            desconectar();
        }
        return "0.0";
    }

    public String empresa() {
        Conectar();
        try {
            ResultSet consulta=getconnection().createStatement().executeQuery("SELECT * FROM `nombre`");
            if(consulta.next()){
                return consulta.getString(1);}
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            desconectar();
        }
        return "";
    }
    public void setempresa(String empresa) throws SQLException {
        Conectar();
        getconnection().prepareStatement("UPDATE `nombre` SET `empresa` = '"+empresa+"'").executeUpdate();
        desconectar();
    }
}
