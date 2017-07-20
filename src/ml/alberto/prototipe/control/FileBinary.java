/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.control;

import Users.Usuarios;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alberto garcia castro
 */
public class FileBinary {

    
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    
    //Method to create the temp file te program use to record the session of the user is log in 
    public void createFile(){
        try {
            //create the file
            file = File.createTempFile("login",null);
        } catch (IOException ex) {
            Logger.getLogger(FileBinary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Method to writte the log in user on the temp file
    public void writeOnFile(Usuarios user) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream("LogIn.dat", false));//openning the buffer to write into the file
            //Sending the data of the user to the file
            out.writeUTF(user.getId());
            out.writeUTF(user.getName());
            out.writeUTF(user.getLastname());
            out.writeUTF(user.getEmailml());
            out.writeUTF(user.getSkypepass());
            out.writeUTF(user.getMlpass());
            out.writeUTF(user.getPcpass());
            out.writeUTF(user.getSkypeuser());
            out.writeUTF(user.getBitrixuser());
            out.writeUTF(user.getTelephone());
            out.writeUTF(user.getPreviousID());

            out.close();
            
            
        } catch (IOException ioe) {
        }
    }
    //Method to read the user from the file 
    public Usuarios readFile() {
        //Creating the new user 
       Usuarios user = new Usuarios();
       
        File file = new File("LogIn.dat");
        //If the file exist then read from the file 
        if (file.exists()) {
            try {
                DataInputStream in = null;
                try {
                    //openning the buffer to read from the file
                    in = new DataInputStream(new FileInputStream("LogIn.dat"));
                    for (;;) {
                        //creating the user with the data from readed from teh file
                       user = new Usuarios(in.readUTF(), in.readUTF(), in.readUTF(),in.readUTF(),in.readUTF(), in.readUTF(), in.readUTF(),in.readUTF(), in.readUTF(), in.readUTF(),in.readUTF());
                    }
                } catch (IOException ioe) {
                } finally {
                    in.close();
                }
            } catch (IOException ioe) {
            }
        }
        return user;
    }
    //Method to delete the temp file and the user from the sistem.
     public void deleteFile(){
            File fich = new File("LogIn.dat");
            Usuarios user = readFile();
            user.clear();
            fich.delete();
            
            
        }

}
