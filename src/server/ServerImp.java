/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;
import java.rmi.registry.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author Sorrentino
 */
public class ServerImp extends UnicastRemoteObject implements Server {
    private Map<String, CasellaMail> caselleUtenti;
    JTextArea dump;
    
    public ServerImp(JTextArea area_dump) throws RemoteException {      
        super();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy_[HH:mm]").format(Calendar.getInstance().getTime());               
        dump = area_dump;
        caselleUtenti = new HashMap<>();
        caselleUtenti.put("alice@mail.it", new CasellaMail());
        caselleUtenti.put("bob@mail.it", new CasellaMail( new Mail("Sviluppatore", "bob", "Benvenuto", "Benvenuto nella tua casella mail.", 1,timeStamp, Mail.tipoMail.Ricevuta)) );
        caselleUtenti.put("carlo@mail.it", new CasellaMail());
    }

    public ArrayList scaricaMail(String username) throws RemoteException{
        ArrayList retMail = null;
        try {
            CasellaMail casella = caselleUtenti.get(username);
            if (casella != null){
                dump.append("L'utente "+username+" si è connesso al server."+"\n");
                retMail = casella.getMail();
            } 
        } catch(Exception e) {
            e.printStackTrace();
        }
        return retMail;
    }
    
    public boolean checkUser(String username) throws RemoteException{
        CasellaMail casellaTemp = caselleUtenti.get(username);
        return casellaTemp != null;
    }
    
    public boolean checkNewMail(String username) throws RemoteException{
        CasellaMail casellaTemp = caselleUtenti.get(username);
        if (casellaTemp != null)
            return casellaTemp.checkNewMail();
        else
            return false;
    }
    
    public ArrayList scaricaNuoveMail(String username) throws RemoteException{
        dump.append("L' utente: "+username+" ha scaricato le nuove mail."+"\n");
        ArrayList<Mail> nuoveMail =  caselleUtenti.get(username).getNewMail();
        return nuoveMail;
    }

    public boolean sendMail(String user, Mail newMail) throws RemoteException{
        boolean ret = true;
        ArrayList allDest = newMail.getDestinatario();
        for (Object dst : allDest){
            if (checkUser((String)dst)){
                dump.append(dst + "ha ricevuto una mail da " + newMail.getMittente()+"\n");
                caselleUtenti.get((String)dst).addMail(new Mail(newMail.getMittente(), newMail.getDestinatario(), newMail.getArgomento(), newMail.getTesto(), newMail.getPrio(), newMail.getData(), Mail.tipoMail.Ricevuta));
            } else {
                ret = false;
                dump.append("Il destinatario: " +dst + " non è disponibile \n");
            }            
        }
       return ret;
    }
    
    public void closeConnection(String user) throws RemoteException{
        dump.append("L' utente: "+user+" ha terminato la connessione!\n");
    }
    
    public void delMail(String user, Mail mail) throws RemoteException{
        this.caselleUtenti.get(user).delMail(mail);
        dump.append("L' utente: "+user+" ha rimosso una mail!\n");
    }
}
