/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ml.alberto.prototipe.model;

/**
 *
 * @author alber
 */
public class User {
   private String id;
   private String name;
   private String lastaName;
   private String emailML;
   private String MLPass;
   private String PCPass;
   private String SkypeUser;
   private String BitrixUser;
   private String Telephone; 
    
    
    public User(){
        setId("");
        setName("");
        setLastaName("");
        setEmailML("");
        setMLPass("");
        setPCPass("");
        setSkypeUser("");
        setBitrixUser("");
        setTelephone("");
    }
    public User(String id,String n,String l, String ml,String mlp , String pcp, String sku, String btu, String t ){
        setId(id);
        setName(n);
        setLastaName(l);
        setEmailML(ml);
        setMLPass(mlp);
        setPCPass(pcp);
        setSkypeUser(sku);
        setBitrixUser(btu);
        setTelephone(t);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastaName() {
        return lastaName;
    }

    public void setLastaName(String lastaName) {
        this.lastaName = lastaName;
    }

    public String getEmailML() {
        return emailML;
    }

    public void setEmailML(String emailML) {
        this.emailML = emailML;
    }

    public String getMLPass() {
        return MLPass;
    }

    public void setMLPass(String MLPass) {
        this.MLPass = MLPass;
    }

    public String getPCPass() {
        return PCPass;
    }

    public void setPCPass(String PCPass) {
        this.PCPass = PCPass;
    }

    public String getSkypeUser() {
        return SkypeUser;
    }

    public void setSkypeUser(String SkypeUser) {
        this.SkypeUser = SkypeUser;
    }

   

    public String getBitrixUser() {
        return BitrixUser;
    }

    public void setBitrixUser(String BitrixUser) {
        this.BitrixUser = BitrixUser;
    }


    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

  
    
    
}
