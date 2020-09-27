/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Duerto
 */
public class Conexion {
        private static Connection con;
        EncriptadorPassword1 en=new EncriptadorPassword1("bakerysoft");
    private String host2="jdbc:mysql://localhost:3306";
    private Statement statement;
  //  private String host2="jdbc:mysql://192.168.0.100:3306"2;
    private static final String user2="root";
    //private static final String user2="bakery";12345
    private static final String driver="com.mysql.jdbc.Driver";
    public void Conectar(){
        con=null;
        try{
            Class.forName(driver);
            con=DriverManager.getConnection(host2,user2,"");
            if(con!=null){
                con.prepareStatement("USE bakery").execute();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public boolean conecta(){
        return con != null;
    }
    public Connection getconnection(){
        return con;
    }
    public void desconectar(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public String iniciar(String usuario,String pass){
        Conectar();
        String retorna="";
        try {
            PreparedStatement usuarios=con.prepareStatement("SELECT tipo FROM `usuarios` WHERE `nombre` LIKE ? AND `pass` LIKE ? LIMIT 1");
            usuarios.setString(1,usuario);
            usuarios.setString(2,en.encrypt(pass));
            ResultSet consulta=usuarios.executeQuery();
            if(consulta.next()){
                    if(consulta.getString("tipo").equals("admin")){
                        retorna= "admin";
                    }
                    else if(consulta.getString("tipo").equals("cajero")){
                        retorna= "cajero";
                    }
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
        return retorna;
    }
    public String get_prop_producto(String inventario,String codigo,String propiedad) {
        try {
            Conectar();
            PreparedStatement preparedStatement=getconnection().prepareStatement("SELECT * FROM `"+inventario.toLowerCase()+"` WHERE `codigo` LIKE ?");
            preparedStatement.setString(1,codigo);
            ResultSet consulta=preparedStatement.executeQuery();
            while(consulta.next()){
                return consulta.getString(propiedad);
            }   } catch (SQLException ex) {
            return "";
        }
        desconectar();
        return null;
    }
    public void realizarMantenimiento() throws SQLException {
        Conectar();
            getconnection().prepareStatement("OPTIMIZE TABLE `charcuteria`").execute();
            getconnection().prepareStatement("FLUSH TABLE `charcuteria`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `compras`").execute();
            getconnection().prepareStatement("FLUSH TABLE `compras`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `consumo interno`").execute();
            getconnection().prepareStatement("FLUSH TABLE `consumo interno`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `local`").execute();
            getconnection().prepareStatement("FLUSH TABLE `local`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `materia prima`").execute();
            getconnection().prepareStatement("FLUSH TABLE `materia prima`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `perdidas`").execute();
            getconnection().prepareStatement("FLUSH TABLE `perdidas`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `produccion`").execute();
            getconnection().prepareStatement("FLUSH TABLE `produccion`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `proveedores`").execute();
            getconnection().prepareStatement("FLUSH TABLE `proveedores`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `usuarios`").execute();
            getconnection().prepareStatement("FLUSH TABLE `usuarios`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `ventas`").execute();
            getconnection().prepareStatement("FLUSH TABLE `ventas`").execute();
            getconnection().prepareStatement("OPTIMIZE TABLE `viveres`").execute();
            getconnection().prepareStatement("FLUSH TABLE `viveres`").execute();
            desconectar();
    }
    public void respaldo(){
        try {
            Process p = Runtime
                    .getRuntime()
                    .exec("C:/xampp/mysql/bin -u root -p  database");

            OutputStream os = p.getOutputStream();
            FileInputStream fis = new FileInputStream("backup_pruebas.sql");
            byte[] buffer = new byte[1000];

            int leido = fis.read(buffer);
            while (leido > 0) {
                os.write(buffer, 0, leido);
                leido = fis.read(buffer);
            }

            os.flush();
            os.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //C:\xampp\mysql\binmysqldump.exe
    }
}