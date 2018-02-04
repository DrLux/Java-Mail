/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import server.Mail;

/**
 *
 * @author Sorrentino
 */

public class ClientModel  extends Observable {
    String userMail;
    CasellaMailImp casella;
    
    public ClientModel(){  
        this.userMail = "Anonimo";
        casella = new CasellaMailImp(userMail);
    }
    
    public ClientModel(String userMail){  
        this.userMail = userMail;
        casella = new CasellaMailImp(userMail);
    }
    
    public void newMail(Mail mail){
        casella.addMail(mail);
        setChanged();
        notifyObservers("mailArrivata");
    }
    
    public String getUserMail(){
        return this.userMail;
    }
    
    public CasellaMailImp getCasella(){
        return this.casella;
    }
    
    public void addNewMails(ArrayList mails){
        this.casella.addNewMails(mails);
        setChanged();
        notifyObservers("nuoveMails");
    }
    
    public void setUserMail(String user){
        this.userMail = user;
        setChanged();
        notifyObservers("userMail");
    }
    
    public Mail getLastMail(){
        return this.casella.getLastMail();
    }
   
    public void removeMail(Mail mail){
        setChanged();
        notifyObservers("mailRimossa");
        this.casella.delMail(mail);
    }
    
}

