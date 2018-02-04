/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;


/**
 *
 * @author sorre
 */
public class CasellaMail {
    private ArrayList<Mail> mails;
    private ArrayList<Mail> newMails;
    
    public CasellaMail(){
        this.mails = new ArrayList<>();
        this.newMails = new ArrayList<>();
    }
    
    public CasellaMail(Mail newMail){
        this.mails = new ArrayList<>();
        this.newMails = new ArrayList<>();
        mails.add(newMail);
    }
       
    public synchronized ArrayList getMail() {
        return mails;
    }
    
    public synchronized void addMail(Mail mail){
        newMails.add(mail);
    }
    
    public synchronized void delMail(Mail mail){
        mails.remove(mail);
    }
    
    public synchronized Mail getLastMail(){
        if (this.mails.get(0) instanceof Mail)
            return (Mail)this.mails.get(mails.size()-1);
        else
            return null;
    }
    
    public synchronized ArrayList<Mail> getNewMail(){
        ArrayList<Mail> tempRet = new ArrayList<>();
        
        for (Mail m: this.newMails){
            tempRet.add(m);
            mails.add(m);    
        }
        this.newMails.clear();
        return tempRet;
    }
    
    public synchronized boolean checkNewMail(){
        return newMails.size() > 0;
    }
    
}
