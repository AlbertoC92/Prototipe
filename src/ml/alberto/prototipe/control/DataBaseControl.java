/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ml.alberto.prototipe.model.User;
import ml.alberto.prototipe.view.WindowFullAccess;

/**
 *
 * @author alber
 */
public class DataBaseControl {
      
    //This atribute is must be changed when you use a different data base 
    private static String URL_DB="jdbc:derby://localhost:1527/Prototipe";
    private static String USUARIO="Alberto";
    private static String PASSWD="alberto";
    private static String URL_DB_REAL="jdbc:derby://localhost:1527/RealPasswords";
    private Statement sentencia;
    private Connection conexion;
    private Connection conexion2;
    private ArrayList<User> contactos;

    
    public DataBaseControl(){
      contactos = new ArrayList<User>();
      
    }
    public void conection2(){
        try {
            System.out.println("Comenzado la conxión con la BBDD...");
            System.out.println("Cargando el controlador...");
            String controlador = "org.apache.derby.jdbc.ClientDriver"; 
            Class.forName(controlador).newInstance();
            
            //creando la conexión
            conexion=DriverManager.getConnection(URL_DB_REAL,USUARIO,PASSWD);
            
            //lanzando una sentencia
            sentencia = conexion.createStatement();
           
             
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }catch (InstantiationException ex) {
                Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
            }
      
    }
    public void conectarse() {
        
        try {
            System.out.println("Comenzado la conxión con la BBDD...");
            System.out.println("Cargando el controlador...");
            String controlador = "org.apache.derby.jdbc.ClientDriver"; 
            Class.forName(controlador).newInstance();
           
            //creando la conexión
            conexion=DriverManager.getConnection(URL_DB,USUARIO,PASSWD);
            
            //lanzando una sentencia
            sentencia = conexion.createStatement();
          
             
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }catch (InstantiationException ex) {
                Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
            }
      
    }
   
    public boolean verifyUser(String mail,String pass){
        boolean find=false;
        conectarse();
        
        try{
             ResultSet resultado = sentencia.executeQuery("SELECT emailml, mlpass, id FROM Usuarios where emailml ='"+mail+"' and mlpass ='"+pass+"'");
             if(resultado.next()){
                 find=true;
             }else{
                 find=false;
             }
        }catch(SQLException ex){
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find;
    }
    
    public User recuperar(String email){
       
        conectarse();
        User user = null;
      
       try{
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Usuarios where emailml ='"+email+"'");
            while(resultado.next()){
                user = new User(resultado.getString(1),resultado.getString(2),resultado.getString(3),email,resultado.getString(5),resultado.getString(6),
                resultado.getString(7),resultado.getString(8),resultado.getString(9));
            
                
            }
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       cerrarConexion();
       return user;
    }
    
    public ArrayList<User> recorrerBD(){
        conectarse();
        try{
            ResultSet resultado = sentencia.executeQuery("select * from Usuarios");
                while (resultado.next()){
                       System.out.println(String.format("%s%s%s%s",resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),
                                resultado.getString(5),resultado.getString(6),
                                resultado.getString(7),resultado.getString(8),resultado.getString(9)));
                    contactos.add(new User(resultado.getString(1),resultado.getString(2),resultado.getString(3),resultado.getString(4),
                                resultado.getString(5),resultado.getString(6),
                                resultado.getString(7),resultado.getString(8),resultado.getString(9)));
                }
            
        }catch(SQLException sql){
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        cerrarConexion();
        return contactos;
    }
    
    public void insertar(User user){
        conectarse();
        PreparedStatement estado;
        String query = "INSERT INTO Usuarios (id,name,lastname,emailml,mlpass,pcpass,skypeuser,bitrixuser,telephone) VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            estado= conexion.prepareStatement(query);
            estado.setString(1, user.getId());
            estado.setString(2, user.getName());
            estado.setString(3, user.getLastaName());
            estado.setString(4, user.getEmailML());
            estado.setString(5, user.getMLPass());
            estado.setString(6, user.getPCPass());
            estado.setString(7, user.getSkypeUser());
            estado.setString(8, user.getBitrixUser());          
            estado.setString(9, user.getTelephone());
           
            estado.execute();
           
            contactos.add(user);
           
        }catch(SQLException sql){
             Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        cerrarConexion();
    }
               
    
    public void borrar(String mail){
       
           conectarse();
        try {
            PreparedStatement consulta;
            String query= "DELETE FROM Usuarios where emailml =?";
            consulta = conexion.prepareStatement(query);
            consulta.setString(1, mail);
            consulta.execute();
            
            System.out.println("ELEMENTO BORRADO");
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        cerrarConexion();
    }
    
    public void actualizar(User user){
        conectarse();
        try{
            PreparedStatement consulta;
            String query ="UPDATE Usuarios SET  id=?, name=?, lastname=? ,emailml=?, mlpass=?, pcpass=?, skypeuser=?, bitrixuser=?, telephone=? where emailml =?";
            consulta = conexion.prepareStatement(query);
            consulta.setString(1, user.getId());
            consulta.setString(2, user.getName());
            consulta.setString(3, user.getLastaName());
            consulta.setString(4, user.getEmailML());
            consulta.setString(5, user.getMLPass());
            consulta.setString(6, user.getPCPass());
            consulta.setString(7, user.getSkypeUser());
            consulta.setString(8, user.getBitrixUser()); 
            consulta.setString(9, user.getTelephone());
            consulta.setString(10,user.getEmailML());
            
            
            consulta.execute();
            
            JOptionPane.showMessageDialog(null,"User Upgrade");
            System.out.println("ELEMENTO ACTUALIZADO");
        }catch(SQLException sql){
             Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
    }
    
    public void cerrarConexion(){
        try {
           
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
