/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import prog_prog3.client.ClientController;
import server.Mail;


/**
 *
 * @author sorre
 */
public class ClientView implements Observer{
    private JFrame mainFrame;

    private JPanel mainPanel; //tutti impilati nel cardlayout
    private ListMailView lmv;
    private ReadMailView rmv;
    private SendMailView smv;
    private CardLayout cl;
    
    private ClientController ctr;
    
    public ClientView(ClientController controller){
        this.ctr = controller;
        smv = new SendMailView(ctr);
        rmv = new ReadMailView(ctr);
        lmv = new ListMailView(ctr);
        
        initCardLayout();
        
        mainFrame.addWindowListener(ctr);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack(); // fissa la dimensione ottimale
        mainFrame.setSize(700, 400);
        mainFrame.setVisible(true); // mette la finestra visibile
    }
    
     public void initCardLayout(){          
        this.mainFrame = new JFrame("Client: "); 
        this.mainPanel = new JPanel();
        cl = new CardLayout(); //manager di layout        

        this.mainPanel.setLayout(cl);
        mainPanel.add(lmv.getFirstPanel(), "h");
        mainPanel.add(smv.getFirstPanel(), "s");
        mainPanel.add(rmv.getFirstPanel(), "r");
        mainFrame.add(mainPanel);
        cl.show(mainPanel, "h");        
    } 
    
    
    public CardLayout getLayoutManager(){
       return cl;
    }
    
    public void setTile(String title){
        this.mainFrame.setTitle("Client: " + title);
    }
    
    public void goHomePage(){
        cl.show(mainPanel, "h");
    }
    
    public void goSendMail(){
        cl.show(mainPanel, "s");
    }

    @Override
    public void update(Observable client, Object arg) {
        ClientModel c = (ClientModel)client;
        switch((String)arg){
            case "userMail":
                this.setTile(c.getUserMail());
            break;
            case "mailArrivata":
                try {   
                    addMail(c.getLastMail());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }                
            break;
            case "mailRimossa":{
                try {
                    listMail(c.getCasella());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "nuoveMails":{
                try {
                    listMail(c.getCasella());
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }  
        this.mainPanel.repaint();
    }  
    
    public void addMail(Mail newMail) throws RemoteException{
        String msg;

        if (newMail.getTipoMail() == Mail.tipoMail.Inviata)
            msg = "<html>\t Mail <b> "+newMail.getTipoMail()+" </b> a <i>"+ newMail.getDestinatario() + "</i> | <b>Oggetto: </b>"+ newMail.getArgomento()+" </html>";
        else
            msg = "<html>\t Mail <b> "+newMail.getTipoMail()+" </b> da  <i>"+ newMail.getMittente() + "</i> | <b>Oggetto: </b>"+ newMail.getArgomento()+" </html>";
        
        JLabel labelMail = new JLabel(msg);
        labelMail.setFont(new Font("serif", Font.PLAIN, 14));
        labelMail.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(1, 1, 0)));
        //labelMail.setBackground(Color.white);

        labelMail.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        rmv.printMail(newMail);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cl.show(mainPanel, "r");
                }
            });
        lmv.listNewMail(labelMail);
    }
    
    public void listMail(CasellaMailImp casella) throws RemoteException{ 
        this.lmv.cleanListView();
        ArrayList mails = (ArrayList)casella.getMail();
        
        for (Object mail : mails){
            addMail((Mail)mail);
        }
    }
    
    
    // Sezione READ mail
    
    public void readMail(Mail mail) throws RemoteException{
        rmv.printMail(mail);
    }
    
    public String getReadMitt() throws RemoteException{
        return rmv.getMitt();
    }
    
    public ArrayList<String> getReadAllMitt() throws RemoteException{
        return rmv.getAllMitt();
    }
    
    public String getReadArg(){
        return rmv.getArg();
    }
    
    public String getReadText(){
        return rmv.getTesto();
    }
    
    public Mail getDelMail(){
        return rmv.getReadMail();
    }
    
    //Sezioen SEND mail
       
    public ArrayList<String> getSendDest(){
        return smv.getDest();      
    }
    
    public void setSendDest(String dst){
        smv.setDest(dst);
    }
    
    public void addSendDest(String dst){
        smv.addDest(dst);
    }
    
    public String getSendArg(){
        return smv.getArg();
    }
    
    public void setSendArg(String arg){
        smv.setArg(arg);
    }
    
    public String getSendText(){
        return smv.getText();  
    }
    
    public void setSendText(String txt){
        smv.setTesto(txt);
    }
    
    public void cleanSendMail(){
        smv.clean();
    }
    
    public boolean checkSendMailInput(String user){
        if (smv.readDest().equals("Inserire Destinatari...")){
            alert(1, "Campo dati mancante" , "Inserire Destinatario");
            return false;
        }
        
        ArrayList<String> allDest = smv.getDest();
        for (String dst : allDest){
            if (dst.equals(user)){
               alert(1, "Destinatario impossibile" , "Non puoi inviare una mail a te stesso");
                return false;
            }
        }
        
        if (smv.getArg().equals("Iserire Oggetto mail...")){
            alert(1, "Campo dati mancante" , "Inserire Argomento");
            return false;
        }
        
        if (smv.getText().equals("Inserire testo mail...")){
            System.out.println("Testo = "+ smv.getText());
            alert(1, "Campo dati mancante" , "Inserire il testo della Mail");
            return false;
        }
        return true;
    }
    
    // Alert
    public void alert(int tipo, String title, String error){ //"campo dati mancante
        switch (tipo){
            case 1:
                JOptionPane.showMessageDialog(mainFrame, error , title, JOptionPane.ERROR_MESSAGE);
            break;
            case 2:
                JOptionPane.showMessageDialog(mainFrame, error , title, JOptionPane.INFORMATION_MESSAGE);
            break;
        }
    }

}
