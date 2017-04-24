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

/**
 *
 * @author alber
 */
public class DataBaseOutUsersControl {
     //This atribute is must be changed when you use a different data base 
    private static String URL_DB="jdbc:derby://localhost:1527/Prototipe";
    private static String USUARIO="Alberto";
    private static String PASSWD="alberto";
    private Statement sentence;
    private Connection connection;
    private ArrayList<User> contacts;

    
    public DataBaseOutUsersControl(){
      contacts = new ArrayList<User>();
      
    }
   
    public void connect() {
        
        try {
            System.out.println("Comenzado la conxión con la BBDD...");
            System.out.println("Cargando el controlador...");
            String controlador = "org.apache.derby.jdbc.ClientDriver"; 
            Class.forName(controlador).newInstance();
           
            //creando la conexión
            connection=DriverManager.getConnection(URL_DB,USUARIO,PASSWD);
            
            //lanzando una sentence
            sentence = connection.createStatement();
          
             
            
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
        connect();
        
        try{
             ResultSet result = sentence.executeQuery("SELECT emailml, mlpass, id FROM UserOut where emailml ='"+mail+"' and mlpass ='"+pass+"'");
             if(result.next()){
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
       
        connect();
        User user = null;
      
       try{
            ResultSet result = sentence.executeQuery("SELECT * FROM UserOut where emailml ='"+email+"'");
            while(result.next()){
                user = new User(result.getString(1),result.getString(2),result.getString(3),email,result.getString(5),result.getString(6),
                result.getString(7),result.getString(8),result.getString(9));
            
                
            }
       } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
       closeConnection();
       return user;
    }
    
    public ArrayList<User> recorrerBD(){
        connect();
        try{
            ResultSet result = sentence.executeQuery("select * from UserOut");
                while (result.next()){
                       System.out.println(String.format("%s%s%s%s",result.getString(1),result.getString(2),result.getString(3),result.getString(4),
                                result.getString(5),result.getString(6),
                                result.getString(7),result.getString(8),result.getString(9)));
                    contacts.add(new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),
                                result.getString(5),result.getString(6),
                                result.getString(7),result.getString(8),result.getString(9)));
                }
            
        }catch(SQLException sql){
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        closeConnection();
        return contacts;
    }
    
    public void insert(User user){
        connect();
        PreparedStatement estado;
        String query = "INSERT INTO UserOut (id,name,lastname,emailml,mlpass,pcpass,skypeuser,bitrixuser,telephone) VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            estado= connection.prepareStatement(query);
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
           
            contacts.add(user);
           
        }catch(SQLException sql){
             Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        closeConnection();
    }
               
    
    public void delete(String mail){
       
           connect();
        try {
            PreparedStatement consult;
            String query= "DELETE FROM UserOut where emailml =?";
            consult = connection.prepareStatement(query);
            consult.setString(1, mail);
            consult.execute();
            
            System.out.println("ELEMENTO BORRADO");
      
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection();
    }
    
    public void upgrade(User user){
        connect();
        try{
            PreparedStatement consult;
            String query ="UPDATE UserOut SET  id=?, name=?, lastname=? ,emailml=?, mlpass=?, pcpass=?, skypeuser=?, bitrixuser=?, telephone=? where emailml =?";
            consult = connection.prepareStatement(query);
            consult.setString(1, user.getId());
            consult.setString(2, user.getName());
            consult.setString(3, user.getLastaName());
            consult.setString(4, user.getEmailML());
            consult.setString(5, user.getMLPass());
            consult.setString(6, user.getPCPass());
            consult.setString(7, user.getSkypeUser());
            consult.setString(8, user.getBitrixUser()); 
            consult.setString(9, user.getTelephone());
            consult.setString(10,user.getEmailML());
            
            
            consult.execute();
            
            JOptionPane.showMessageDialog(null,"User Upgrade");
            System.out.println("ELEMENTO ACTUALIZADO");
        }catch(SQLException sql){
             Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
    }
    
    public void closeConnection(){
        try {
           
            sentence.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
