/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.List;
import java.util.ArrayList;
import server.Mail;

/**
 *
 * @author sorre
 */
public class CasellaMailImp{
    private String userMail;
    private ArrayList mails;
    
    public CasellaMailImp(String userMail){
        this.userMail = userMail;
        this.mails = new ArrayList<>();
    }

    
    public boolean checkUserMail(String userName) {
        if (this.userMail.compareToIgnoreCase(userMail) == 0)
           return true;
        else
            return false;
    }

    
    public ArrayList getMail() {
        return mails;
    }
    
    public void addMail(Mail mail){
        mails.add(mail);
    }
    
    public void delMail(Mail mail){
        mails.remove(mail);
    }
    
    public Mail getLastMail(){
        if (this.mails.get(0) instanceof Mail)
            return (Mail)this.mails.get(mails.size()-1);
        else
            return null;
    }
    
    public void addNewMails(ArrayList newMails){
        mails.addAll(newMails);
    }
}
