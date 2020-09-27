package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Editar extends ListaProductos{
    public void editarprecio(String tipo,String codigo,String nprecio) throws SQLException {
        Conectar();
        PreparedStatement preparedStatement =getconnection().prepareStatement("UPDATE "+tipo.toLowerCase()+" SET `precio`=? WHERE `codigo` LIKE ? ");
        preparedStatement.setString(1,nprecio);
        preparedStatement.setString(2,codigo);
        preparedStatement.execute();
        desconectar();
    }
    public void editarCosto(String tipo,String codigo,String ncosto) throws SQLException {
        Conectar();
        PreparedStatement preparedStatement =getconnection().prepareStatement("UPDATE `"+tipo.toLowerCase()+"` SET `costo`=? WHERE `codigo` LIKE ? ");
        preparedStatement.setString(1,ncosto);
        preparedStatement.setString(2,codigo);
        preparedStatement.execute();
        desconectar();
    }
    public void editaproducto(String codigo,String producto,String marca,String tipo) throws SQLException {
        tipo=tipo.trim();
        marca=marca.trim();
        producto=producto.trim();
        Conectar();
        PreparedStatement preparedStatement=getconnection().prepareStatement("UPDATE `"+tipo+"` SET `producto`=? WHERE `codigo` LIKE ? ");
        preparedStatement.setString(1,producto);
        preparedStatement.setString(2,codigo);
        preparedStatement.execute();
        if(!tipo.equals("Local")){
            PreparedStatement preparedStatement1= getconnection().prepareStatement("UPDATE `"+tipo+"` SET `marca`=? WHERE `codigo` LIKE ? ");
            preparedStatement1.setString(1,marca);
            preparedStatement1.setString(2,codigo);
            preparedStatement1.execute();
        }
        desconectar();
    }
    public void editaproveedor(String id,String codigo,String nombre,String rif) throws SQLException {
        Conectar();
        PreparedStatement preparedStatement=getconnection().prepareStatement("UPDATE `proveedores` SET `codigo`=?,  `nombre`=?, `rif`=? WHERE `id` LIKE ? ");
        preparedStatement.setString(1,codigo);
        preparedStatement.setString(2,nombre);
        preparedStatement.setString(3,rif);
        preparedStatement.setString(4,id);

        preparedStatement.execute();
        desconectar();
    }
    public boolean eliminarproveedor(String id,String usuario,String contra) throws SQLException {
        if(iniciar(usuario,contra).equals("admin")) {
            Conectar();

            PreparedStatement preparedStatement = getconnection().prepareStatement("DELETE FROM `proveedores` WHERE `id`= ?");
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            desconectar();
            return true;
        }
        else{
            return false;
        }
    }
}
