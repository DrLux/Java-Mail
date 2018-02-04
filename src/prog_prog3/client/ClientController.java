/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import server.Mail;
import server.Server;

/**
 *
 * @author sorre
 */
public class ClientController implements ActionListener, WindowListener {
    ClientModel cm;
    ClientView cw;
    Server s;
        
    public ClientController(){
        try {
            Context namingContext = new InitialContext();
            s = (Server)Naming.lookup("//localhost/server");            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addModel(ClientModel cm){
        this.cm = cm;
    }
    
    public void addView(ClientView cw){
        this.cw = cw;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){

        switch (e.getActionCommand()){
            case "Homepage":
                cw.goHomePage();
            break;
            case "Nuova Mail":
                cw.cleanSendMail();
                cw.goSendMail();
            break;
            case "Invia Mail":
                if (cw.checkSendMailInput(cm.getUserMail())){
                    String timeStamp = new SimpleDateFormat("dd/MM/yyyy_[HH:mm]").format(Calendar.getInstance().getTime());
                    Mail newMail = new Mail(cm.getUserMail(), cw.getSendDest(), cw.getSendArg(), cw.getSendText(), 0, timeStamp, Mail.tipoMail.Inviata);
                    try {
                        if (s.sendMail(cm.getUserMail() ,newMail)){
                            cw.cleanSendMail();
                            cm.newMail(newMail);
                            cw.goHomePage();
                        } else {
                            cw.alert(1, "Errore destinatario", "Inserito un destinatario errato");
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            break;
            case "Rispondi":
                cw.setSendArg(cw.getReadArg());
                {
                    try {
                        cw.setSendDest(cw.getReadMitt());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                cw.goSendMail();
            break;
            case "Rispondi a Tutti":
                ArrayList<String> allMitt; 
                {
                    try {
                        allMitt = cw.getReadAllMitt();
                        cw.setSendDest(cw.getReadMitt());
                        for (int i = 0; i < allMitt.size(); i++){
                            if (!allMitt.get(i).equals(cm.getUserMail()))
                                cw.addSendDest(allMitt.get(i));
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   
                                                  
                cw.setSendArg(cw.getReadArg());
                cw.goSendMail();
            break;
            case "Inoltra":
                cw.cleanSendMail();
                cw.setSendText(cw.getReadText());
                cw.setSendArg(cw.getReadArg());
                cw.goSendMail();
            break;
            case "Elimina":{
                Mail delMail = cw.getDelMail();
                cm.removeMail(delMail);
                try {
                    s.delMail(cm.getUserMail(), delMail);
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    cw.listMail(cm.getCasella());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
                cw.goHomePage();
            break;
            }            
        }
    }
    
    public void startDeamon(){
        Server s = this.s;
        String user = this.cm.getUserMail();
        ClientView cw = this.cw;
         
        Runnable run = new Runnable() {            
            @Override
            public void run() {
                try {
                    if (s.checkUser(user)){
                        cm.addNewMails(s.scaricaMail(user));
                        try {
                            for (;;) {
                                Thread.sleep(3000);
                                if (s.checkNewMail(user)){
                                    ArrayList mailDonwloaded = s.scaricaNuoveMail(user);
                                    if (mailDonwloaded.size() > 1){
                                        cw.alert(2, "Nuove mail", "Ricevuta nuove Mail");
                                        cm.addNewMails(mailDonwloaded);
                                    } else {
                                        cw.alert(2, "Nuove mail", "Ricevute una nuova Mail");
                                        cm.newMail((Mail) mailDonwloaded.get(0));
                                    }


                                }
                            }

                        } catch (InterruptedException e) {
                            System.out.println(" interrupted");
                        } 
                    } else {
                        cw.alert(1, "Error Username", "Username non esistente");
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }       
                    
        };
        Thread t = new Thread(run);
        t.setDaemon(true);
        t.start();
    }
    
    public void windowClosing(WindowEvent e){
        try {
            s.closeConnection(cm.getUserMail());
            System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowOpened(WindowEvent we) {
        ;
    }

    @Override
    public void windowClosed(WindowEvent we) {
        ;
    }

    @Override
    public void windowIconified(WindowEvent we) {
        ;
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
        ;
    }

    @Override
    public void windowActivated(WindowEvent we) {
        ;
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
        ;
    }
} 

