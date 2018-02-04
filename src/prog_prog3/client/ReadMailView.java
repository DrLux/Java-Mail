/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import server.Mail;

/**
 *
 * @author sorre
 */
public class ReadMailView {
    
    JPanel firstPanel;
    JLabel dest;
    JLabel mitt;
    JLabel ogg;
    JLabel corpo;
    JLabel data;
    JPanel pnl_mail;
    JButton reply;
    JButton replyAll;
    JButton forward;
    Mail mail;
    
    
    public ReadMailView(ClientController ctr){
        firstPanel = new JPanel();
        this.firstPanel.setLayout(new BorderLayout());
        this.firstPanel.setBackground(Color.white);
        
        pnl_mail = new JPanel();
        pnl_mail.setLayout(new GridLayout(0,1));
        pnl_mail.setBackground(Color.white);
        
        dest = new JLabel("DESTINATARI: ");
        dest.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        mitt = new JLabel("MITTENTE: ");
        mitt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        ogg = new JLabel("OGGETTO MAIL: ");
        ogg.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        corpo = new JLabel("TESTO MAIL.");
        corpo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        data = new JLabel("DATA: ");
        data.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        
        pnl_mail.add(dest);
        pnl_mail.add(mitt);
        pnl_mail.add(ogg);
        pnl_mail.add(corpo);
        pnl_mail.add(data);

        JPanel panel_button = new JPanel();

        reply = new JButton("Rispondi");
        reply.addActionListener(ctr);

        replyAll = new JButton("Rispondi a Tutti");
        replyAll.addActionListener(ctr);

        forward = new JButton("Inoltra");
        forward.addActionListener(ctr);
        
        JButton delete = new JButton("Elimina");
        delete.addActionListener(ctr);

        JButton back = new JButton("Homepage");
        back.addActionListener(ctr);    


        panel_button.add(reply);
        panel_button.add(replyAll);
        panel_button.add(forward);
        panel_button.add(delete);
        panel_button.add(back);        

        panel_button.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        firstPanel.add(pnl_mail, BorderLayout.CENTER);
        firstPanel.add(panel_button, BorderLayout.SOUTH);
    }
    
    public JPanel getFirstPanel(){
        return this.firstPanel;
    }
    
    
    public void printMail(Mail mail) throws RemoteException{
        this.mail = mail;
        
        if (Mail.tipoMail.Inviata == mail.getTipoMail()){
            reply.setEnabled(false);
            replyAll.setEnabled(false);
            forward.setEnabled(false);
        } else {
            reply.setEnabled(true);
            replyAll.setEnabled(true);
            forward.setEnabled(true);
        }
        
        dest.setText("<html><strong>DESTINATARI: </strong>"+mail.getDestinatario().toString()+"</html>");
        mitt.setText("<html><strong>MITTENTE: </strong>"+mail.getMittente()+"</html>");
        ogg.setText("<html><strong>OGGETTO MAIL: </strong>"+mail.getArgomento()+"</html>");
        corpo.setText("<html><strong>TESTO MAIL: </strong>"+ mail.getTesto()+"</html>");
        data.setText("<html><strong>DATA: </strong>"+ mail.getData()+"</html>");
        
        if (mail.getDestinatario().size() < 2)
            replyAll.setEnabled(false);
    }
    
    public ArrayList<String> getAllMitt() throws RemoteException{
        return mail.getDestinatario();
    }
    
    public String getMitt() throws RemoteException{
        return mail.getMittente();
    }
    
    public String getArg(){
        return mail.getArgomento();
    }
    
    public String getTesto(){
        return mail.getTesto();
    }
    
    public Mail getReadMail(){
        return this.mail;
    }
}
