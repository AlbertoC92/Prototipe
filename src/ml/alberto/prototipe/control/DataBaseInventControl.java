/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import Users.Inventory;

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
 * @author alberto garcía castro
 */
public class DataBaseInventControl {

    private Connection connection;//Atribute connection 
    private ArrayList<Inventory> staff;//ArrayList used when the data base is readed

    public DataBaseInventControl() {
        staff = new ArrayList<Inventory>();

    }

    private Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//Chargin the connection driver 
            //Here the method connect to the data base by the url,user and pass 

            //Here the method connect to the data base by the url,user and pass 
            conexion = DriverManager.getConnection("jdbc:mysql://46.101.112.185:3306/usuarios"
                    + "?verifyServerCertificate=false"
                    + "&useSSL=true"
                    + "&requireSSL=true", "manager", "coffeetime");
            System.out.println("Connected to PRIVILEGE ACCESS by getconnection");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    private Inventory chargeInvent(String of, String idcompo) {
        Inventory invent = null;
        try {
            //Getting the current connection
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //Query to check the data base
            String sql = "SELECT * FROM inventory where office = '" + of + "' and idcomponent = '" + idcompo + "' ";
            ResultSet resul = sentencia.executeQuery(sql);
            //check if the query has elements 
            while (resul.next()) { //se crea un array con los datos de los departamentos
                //If it has elements, then create new users with the data and insert it on the arrayList
                invent = new Inventory(resul.getString(2),resul.getString(3),resul.getInt(4),resul.getInt(5),resul.getInt(6),resul.getInt(7),resul.getInt(8));
                //Inactive users arrayList
               
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return invent;
    }

    
    private void upgrade(Inventory in) {
        
        PreparedStatement consulta;
        connection = getConnection();
        try {
        String query = "UPDATE inventory SET  idcomponent =? , office =? , quantity =? , available =? , usd =? , needed =? , orders =?  where office =? and idcomponent =?";
        
       
            consulta = connection.prepareStatement(query);
            consulta.setString(1, in.getIdcomponent());
            consulta.setString(2, in.getOffice());
            consulta.setInt(3, in.getQuantity());
            consulta.setInt(4, in.getAvail());
            consulta.setInt(5, in.getUsd());
            consulta.setInt(6, in.getNeed());
            consulta.setInt(7, in.getOrders());
            consulta.setString(8, in.getOffice());
            consulta.setString(9, in.getIdcomponent());
              consulta.executeUpdate();
              JOptionPane.showMessageDialog(null, "STOCK UPDATE");
         } catch (SQLException ex) {
            Logger.getLogger(DataBaseInventControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
    public void upgradeInventory(Inventory in){
        upgrade(in);
    }
    public Inventory charge(String of ,String idcompo){
        Inventory in = new Inventory();
        in = chargeInvent(of, idcompo);
        return in;
    }
   
}
