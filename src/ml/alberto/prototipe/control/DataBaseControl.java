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
import org.hibernate.Session;

/**
 *
 * @author alberto garcia castro
 */
public class DataBaseControl {

    private Connection connection;//Atribute connection 
    private ArrayList<Usuarios> contacts;//ArrayList used when the data base is readed
    private ArrayList<Usuarios> inactives;//ArrayList whit the users whit inactive ID
    private boolean conect;//Boolean use to difference if the program is connect or not 

    //Constructor 
    public DataBaseControl() {
        contacts = new ArrayList<Usuarios>();
        inactives = new ArrayList<Usuarios>();
        conect = false;
    }

    //Method use to get the current connection on the differents methods 
    public Connection getConnection() {
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
    private void connection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");//Chargin the connection driver 
            // connect way #1

            String url1 = "jdbc:mysql://example:1234/example"
                    + "?verifyServerCertificate=false"
                    + "&useSSL=true"
                    + "&requireSSL=true";
            String user = "alert";
            String password = "example";

            connection = DriverManager.getConnection(url1, user, password);
            if (connection != null) {
                System.out.println("Connected to PRIVILEGES ACCESS");
                conect = true;
            }

        } catch (SQLException ex) {

            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();

        } catch (ClassNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //Method to verify if the user is trying to log in exist on the data base
    private boolean verifyUsuarios(String mail, String pass) {
        boolean find = false;

        String email = mail.toUpperCase();
        try {
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //This is the query used to check the parameters on the data base
            String sql = "SELECT emailml,mlpass FROM usuarios where emailml ='" + email + "' and mlpass ='" + pass + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            if (resul.next()) {
                find = true;

            } else {
                find = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find;
    }

    //Method to charge an specific user from the data base. The method returns the user 
    private Usuarios reloadUser(String email) {

        Usuarios user = null;

        try {
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //Query to search the user
            String sql = "SELECT * FROM usuarios where emailml ='" + email + "'";
            ResultSet resul = sentencia.executeQuery(sql);

            if (resul.next()) {
                //Create the user i will return on this method
                user = new Usuarios(resul.getString(1), resul.getString(2), resul.getString(3), email,resul.getString(6), resul.getString(5), resul.getString(7),
                        resul.getString(8), resul.getString(9), resul.getString(10), resul.getString(11));

            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    //Method to verify the email from an specific user
    private boolean verifyUserEmail(String mail) {
        boolean find = false;

        try {
            //this si the sentence to get the current conection
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            String sql = "SELECT emailml FROM usuarios where emailml ='" + mail + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            // if after execute the query the resultset returns something, then the email exist and return true
            //if not, then return falase
            if (resul.next()) {
                find = true;

            } else {
                find = false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find;
    }

    //This is the method used to charge the data base on the tables. The particularity of the method is the access level. 
    //The method reciv an id and depending of the id, the user will see one thing or another.
    private ArrayList<Usuarios> checkUsers(String id) {

        try {

            connection = getConnection();
            Statement sentencia = connection.createStatement();
            String sql;

            if (id == null) {
                JOptionPane.showMessageDialog(null, "ID DIDN'T INTRODUCE");

            } else {
                switch (id) {
                    //if your id level is CEO, then the method charge all the contacts except the inactives
                    case "CEO":
                        sql = "SELECT * FROM usuarios where id <>'Inactive'";
                        break;
                    // if your id level is Manager, then the method will charge all the datas except CEO users and Inactives
                    case "Manager":
                        sql = "SELECT * FROM usuarios where id <> 'CEO' and id <>'Inactive'";
                        break;
                    // if your level is IT, then the method will charge all the levels except CEO, Manager and Inactives
                    case "IT":
                        sql = "SELECT * FROM usuarios where id <> 'CEO' and id <>'Manager' and id <>'Inactive'";
                        break;
                    //For the rest of the users, the method will charge the contacts with the same id of the user is log in
                    default:
                        sql = "SELECT * FROM usuarios where id ='" + id + "'";
                        break;
                }

                ResultSet resul = sentencia.executeQuery(sql);
                while (resul.next()) { //se crea un array con los datos de los departamentos		
                    Usuarios user = new Usuarios(resul.getString("id"), resul.getString("name"), resul.getString("lastname"), resul.getString("emailml"),resul.getString("skypepass"),
                            resul.getString("mlpass"), resul.getString("pcpass"), resul.getString("skypeuser"), resul.getString("bitrixuser"), resul.getString("telephone"), resul.getString("previousID"));
                    //contacts is the arraylist whit all the users to charge on the tables. This will be the return object
                    contacts.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (contacts);
    }

    //This method check for the previous id of an user. This method is use to reinsert users from the inactive table to the general.
    private String checkPreviousUsersID(String mail) {
        String previous = "";
        try {
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            String sql;
            //Query to check the data base by the email introduced
            sql = "SELECT previousid FROM usuarios where emailml ='" + mail + "'";
            ResultSet resul = sentencia.executeQuery(sql);
            //
            if (resul.next()) {
                //get the prevous id from the query
                previous = resul.getString(1);
            }

        } catch (SQLException sql) {
            System.out.println("SQL DOESN'T WORK");
        }
        return previous;
    }

    //Method thtat return an arrayList with all the inactives users on the data base 
    private ArrayList<Usuarios> checkUsersInactives() {
        try {
            //Getting the current connection
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //Query to check the data base
            String sql = "SELECT * FROM usuarios where id ='Inactive' ";
            ResultSet resul = sentencia.executeQuery(sql);
            //check if the query has elements 
            while (resul.next()) { //se crea un array con los datos de los departamentos
                //If it has elements, then create new users with the data and insert it on the arrayList
                Users.Usuarios user = new Users.Usuarios(resul.getString("id"), resul.getString("name"), resul.getString("lastname"), resul.getString("emailml"),resul.getString("skypepass"),
                        resul.getString("mlpass"), resul.getString("pcpass"), resul.getString("skypeuser"), resul.getString("bitrixuser"), resul.getString("telephone"), resul.getString("previousID"));
                //Inactive users arrayList
                inactives.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (inactives);
    }

    //Method to insert a new user on the data base
    private void insertUser(Usuarios user) {

        PreparedStatement state;
        connection = getConnection();

        //Query to insert the new user 
        String query = "INSERT INTO usuarios (id,name,lastname,emailml,skypepass,mlpass,pcpass,skypeuser,bitrixuser,telephone,previousid) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            //get the result of the cuery and insert all the datas from the user it is reciving
            state = connection.prepareStatement(query);
            state.setString(1, user.getId());
            state.setString(2, user.getName());
            state.setString(3, user.getLastname());
            state.setString(4, user.getEmailml());
            state.setString(5, user.getSkypepass());
            state.setString(6, user.getMlpass());
            state.setString(7, user.getPcpass());
            state.setString(8, user.getSkypeuser());
            state.setString(9, user.getBitrixuser());
            state.setString(10, user.getTelephone());
            state.setString(11, user.getPreviousID());

            state.executeUpdate();
            //Insert the new user on the arrayList. It is to charge the new user on the current session
            contacts.add(user);

        } catch (SQLException sql) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, sql);
        }
        closeconnection();
    }

    //Method to delete an specific user from the data base. use the email to get it from the data base 
    private void deleteUser(String mail) {
        try {
            PreparedStatement consult;
            //Query to delet the user by the email
            String query = "DELETE FROM usuarios WHERE emailml =?";
            consult = connection.prepareStatement(query);
            consult.setString(1, mail);
            consult.execute();

            closeconnection();
        } catch (SQLException ex) {

            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Method to update an specific user. This method recive all teh data from the user and update it
    private void upgradeUser(Usuarios user) {
        PreparedStatement consulta;
        connection = getConnection();
        //Query to update the user 

        try {

            if (verifyEmail(user.getEmailml())) {
                String query = "UPDATE usuarios SET  id=? , name =? , lastname =? ,skypepass =?,  mlpass =? , pcpass =? ,skypeuser =? , bitrixuser =? , telephone =? , previousid =?  where emailml =?";
                consulta = connection.prepareStatement(query);

                consulta.setString(1, user.getId());
                consulta.setString(2, user.getName());
                consulta.setString(3, user.getLastname());
                consulta.setString(4, user.getSkypepass());
                consulta.setString(5, user.getMlpass());
                consulta.setString(6, user.getPcpass());
                consulta.setString(7, user.getSkypeuser());
                consulta.setString(8, user.getBitrixuser());
                consulta.setString(9, user.getTelephone());
                consulta.setString(10, user.getPreviousID());
                consulta.setString(11, user.getEmailml());
                
            }else{
                String query2 = "UPDATE usuarios SET  id=? , name =? , lastname =? ,emailml=? ,skypepass =?, mlpass =? , pcpass =?, skypeuser =? , bitrixuser =? , telephone =? , previousid =?  where emailml =?";
                consulta = connection.prepareStatement(query2);

                consulta.setString(1, user.getId());
                consulta.setString(2, user.getName());
                consulta.setString(3, user.getLastname());
                consulta.setString(4, user.getEmailml());
                consulta.setString(5, user.getMlpass());
                consulta.setString(6, user.getMlpass());
                consulta.setString(7, user.getPcpass());
                consulta.setString(8, user.getSkypeuser());
                consulta.setString(9, user.getBitrixuser());
                consulta.setString(10, user.getTelephone());
                consulta.setString(11, user.getPreviousID());
                consulta.setString(12, user.getEmailml());
               
            }

            consulta.executeUpdate();
            
        } catch (SQLException sql) {
           
        }
        //closeconnection();
    }

    private ArrayList<String> selectDates() {
        ArrayList<String> dates = new ArrayList<String>();
         try {
            //Getting the current connection
            connection = getConnection();
            Statement sentencia = connection.createStatement();
            //Query to check the data base
            String sql = "SELECT dates FROM usuarios ";
            ResultSet resul = sentencia.executeQuery(sql);
            //check if the query has elements 
            while (resul.next()) { //se crea un array con los datos de los departamentos
               dates.add(String.format("%s %s", resul.getDate(1).toString(),resul.getTime(1).toString()));
              
               
              
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    //Method to close the current connection. It is call everytime we create a new window or we close the program.
    private void closeConnection() {
        try {
            connection.close();

            System.out.println("CONECTION CLOSE");
            setConect(false);
            System.out.println("CONECTION FALSE");
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isConect() {
        return conect;
    }

    public void setConect(boolean conect) {
        this.conect = conect;
    }

    public void closeconnection() {
        closeConnection();
    }

    public void connect() {
        connection();
    }

    //Those methods are the public methods that use the private methods from this class. That make possible to encapsulate the metho
    //and make impossible to be modified by nobady fromm out of the font code.
    public void upgrade(Usuarios user) {
        upgradeUser(user);
    }

    public void delete(String mail) {
        deleteUser(mail);
    }

    public void insert(Usuarios user) {
        insertUser(user);
    }

    public ArrayList<Usuarios> checkInactives() {
        ArrayList<Usuarios> in = new ArrayList<Usuarios>();
        in = checkUsersInactives();
        return in;
    }

    public String checkPreviousID(String mail) {
        String email = checkPreviousUsersID(mail);
        return email;
    }

    public ArrayList<Usuarios> check(String id) {
        ArrayList<Usuarios> in = new ArrayList<Usuarios>();
        in = checkUsers(id);
        return in;
    }

    public boolean verifyEmail(String mail) {
        boolean ver = verifyUserEmail(mail);
        return ver;
    }

    public Usuarios reload(String email) {
        Usuarios us = new Usuarios();
        us = reloadUser(email);
        return us;
    }

    public boolean verifyUser(String mail, String pass) {
        boolean ver = verifyUsuarios(mail, pass);
        return ver;
    }
    public ArrayList<String> getDates(){
        ArrayList<String> d = new ArrayList<String>();
        d=selectDates();
        return d;
    }
}
