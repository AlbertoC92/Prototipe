/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import Users.InspectorDatas;
import Users.Usuarios;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  alberto garcía castro
 */
public class DataBaseInspectorControl {
    private Connection connection;//Atribute connection 
    private ArrayList<InspectorDatas> datas;//ArrayList used when the data base is readed
   
    
    
    public DataBaseInspectorControl(){
         datas = new ArrayList<InspectorDatas>();
       
        
    }
     //Method use to get the current connection on the differents methods 
    private Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//Chargin the connection driver 
            //Here the method connect to the data base by the url,user and pass 

            //Here the method connect to the data base by the url,user and pass 
            conexion = DriverManager.getConnection("jdbc:mysql://example:1234/example"
                    + "?verifyServerCertificate=false"
                    + "&useSSL=true"
                    + "&requireSSL=true", "alebr", "example");
            System.out.println("Connected to PRIVILEGE ACCESS by getconnection");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }
    
    public ArrayList<InspectorDatas> getDatas() {

        try {

            connection = getConnection();
            Statement sentencia = connection.createStatement();
            String sql;
            sql = "SELECT datas,userlogin,user FROM inspector ";
                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) { //se crea un array con los datos de los departamentos		
                    InspectorDatas ins = new InspectorDatas(resul.getString(1),resul.getString(2),resul.getString(3));
                    datas.add(ins);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeConnection();
        return (datas);
    }
     public void insertDatas(String dat,String userlog,String user) {

        PreparedStatement state;
        connection = getConnection();

        //Query to insert the new user 
        String query = "INSERT INTO inspector (user,userlogin,datas) VALUES(?,?,?)";
        try {
            //get the result of the cuery and insert all the datas from the user it is reciving
            state = connection.prepareStatement(query);
            state.setString(1, user);
            state.setString(2, userlog);
            state.setString(3, dat);
            
           

            state.executeUpdate();
            //Insert the new user on the arrayList. It is to charge the new user on the current session
            datas.add(new InspectorDatas(dat,userlog,user));

        } catch (SQLException sql) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        closeConnection();
    }
      public void closeConnection() {
        try {
            connection.close();

            System.out.println("CONECTION CLOSE");
            
            System.out.println("CONECTION FALSE");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
