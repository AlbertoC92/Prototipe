/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import Users.Usuarios;
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

/**
 *
 * @author  alberto garcía castro
 */
public class DataBasePBXControl {
    private Connection connection;//Atribute connection 
    private ArrayList<String>datas;
    
    
    
    public DataBasePBXControl(){
        datas = new ArrayList<String>();
       
    }
    
     private Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//Chargin the connection driver 
            //Here the method connect to the data base by the url,user and pass 

            //Here the method connect to the data base by the url,user and pass 
            conexion = DriverManager.getConnection("jdbc:mysql://example:1234/example"
                    + "?verifyServerCertificate=false"
                    + "&useSSL=true"
                    + "&requireSSL=true", "alber", "example");
            System.out.println("Connected to PRIVILEGE ACCESS by getconnection");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    //Method to connect with the data base
    
    private boolean verifyPBX(String pbx) {
        boolean find = false;

        String newPBX = pbx.toUpperCase();
        try {
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //This is the query used to check the parameters on the data base
            String sql = "SELECT freepbx FROM freepbx where freepbx ='" + newPBX + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                find = true;

            } else {
                find = false;
            }
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find;
    }
    
    
    private ArrayList<String> getPBX() {

        try {

            connection = getConnection();
            Statement sentencia = connection.createStatement();
            String sql;
            sql = "SELECT freepbx FROM freepbx";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) { //se crea un array con los datos de los departamentos		
                    
                    datas.add(resul.getString(1));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return (datas);
    }
    
    
    private void insertPBX(String pbx) {

        PreparedStatement state;
        connection = getConnection();

        //Query to insert the new user 
        String query = "INSERT INTO freepbx (freepbx) VALUES(?)";
        try {
            //get the result of the cuery and insert all the datas from the user it is reciving
            state = connection.prepareStatement(query);
            state.setString(1, pbx);
       
           

            state.executeUpdate();
            //Insert the new user on the arrayList. It is to charge the new user on the current session
            datas.add(pbx);

        } catch (SQLException sql) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        closeConnection();
    }
    
    
     private void deletePBX(String pbx) {
        try {
            connection = getConnection();
            PreparedStatement consult;
            //Query to delete the user by the email
            String query = "DELETE FROM freepbx WHERE freepbx =?";
            consult = connection.prepareStatement(query);
            consult.setString(1, pbx);
            consult.executeUpdate();

            closeConnection();
        } catch (SQLException ex) {

            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     
     private void closeConnection() {
        try {
            connection.close();

            System.out.println("CONECTION CLOSE");
            
            System.out.println("CONECTION FALSE");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public boolean verify(String pbx){
         boolean find = verifyPBX(pbx);
         return find;
     }
     
     public ArrayList<String> get(){
         ArrayList<String> pbx = getPBX();
         return pbx;
     }
     
     
     public void insert(String pbx){
         insertPBX(pbx);
     }
     
     public void delete(String pbx){
         deletePBX(pbx);
     }
     
    
}
