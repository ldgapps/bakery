package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usuario extends Conexion{
    private EncriptadorPassword1 en=new EncriptadorPassword1("bakerysoft");
    public void add_usuario(String nombre,String contra,String tipo) throws SQLException {
        Conectar();
        PreparedStatement nuevoUsuario=getconnection().prepareStatement("INSERT INTO `usuarios` (`id`,`nombre`, `pass`, `tipo`) VALUES (NULL,?,?,?)");
        nuevoUsuario.setString(1,nombre);
        nuevoUsuario.setString(2,en.encrypt(contra));
        nuevoUsuario.setString(3,tipo);
        nuevoUsuario.execute();
        desconectar();
    }
    public boolean eliminausuario(String nombre,String usuario,String contra){
        Conectar();
        System.out.println(usuario+" "+contra+" "+nombre);
        if(iniciar(usuario,contra).equals("admin")){
            try {
                Conectar();
                PreparedStatement usuarios=getconnection().prepareStatement("DELETE FROM `usuarios` WHERE `usuarios`.`nombre` = ?");
                usuarios.setString(1,nombre);
                usuarios.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                desconectar();
            }
        }
        else{
            System.out.println("error");
            return false;
        }

    }
    public boolean editanombre(String usuario,String contra,String nusuario,String ncontra){
        Conectar();
        String encry=en.encrypt(ncontra);
        try {
            if(!iniciar(usuario,contra).equals("")){
                PreparedStatement usuarios=getconnection().prepareStatement("UPDATE `usuarios` SET `pass`=? WHERE `nombre` LIKE ? ");
                usuarios.setString(1,encry);
                usuarios.setString(2,usuario);
                usuarios.execute();
                PreparedStatement preparedStatement=getconnection().prepareStatement("UPDATE `usuarios` SET `nombre`=? WHERE `nombre` LIKE ? ");
                preparedStatement.setString(1,nusuario);
                preparedStatement.setString(2,usuario);
                preparedStatement.execute();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            desconectar();
        }
    }
    public ArrayList getusuarios(String usuario){
        Conectar();
        try {
            ArrayList usuarios=new ArrayList();
            ResultSet consulta=getconnection().createStatement().executeQuery("SELECT nombre FROM `usuarios` WHERE `nombre` NOT LIKE '"+usuario+"'");
            while(consulta.next()){
                usuarios.add(consulta.getString(1));
            }
            return usuarios;
        } catch (SQLException ex) {
            return null;
        }finally {
            desconectar();
        }
    }
    public String getId(String usuario){
        String retorna="";
        Conectar();
        try {
            PreparedStatement usuarios=getconnection().prepareStatement("SELECT id FROM `usuarios` WHERE `nombre` LIKE ? ");
            usuarios.setString(1,usuario);
            ResultSet consulta=usuarios.executeQuery();
            if(consulta.next()){
                retorna=consulta.getString(1);
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
        return retorna;
    }
    public String getContra(String usuario){
        String retorna="";
        Conectar();
        try {
            PreparedStatement usuarios=getconnection().prepareStatement("SELECT pass FROM `usuarios` WHERE `nombre` LIKE ? ");
            usuarios.setString(1,usuario);
            ResultSet consulta=usuarios.executeQuery();
            if(consulta.next()){
                retorna=en.decrypt(consulta.getString(1));
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
        return retorna;
    }
    public String getUsuario(String id){
        String retorna="";
        Conectar();
        try {
            PreparedStatement usuarios=getconnection().prepareStatement("SELECT nombre FROM `usuarios` WHERE `id` LIKE ? ");
            usuarios.setString(1,id);
            ResultSet consulta=usuarios.executeQuery();
            if(consulta.next()){
                retorna=consulta.getString(1);
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
        return retorna;
    }

}
