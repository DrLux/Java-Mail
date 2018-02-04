/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author sorre
 */
public class ListMailView {
    private JPanel firstPanel;
    private JPanel listMailPanel;
    
    public ListMailView(ClientController ctr){
        firstPanel = new JPanel();
        listMailPanel = new JPanel();
        //this.listMailPanel.setBackground(Color.white);

        
        this.firstPanel.setLayout(new BorderLayout());
        
        JPanel delete = new JPanel();
        delete.setBackground(Color.white);
        JPanel panel_button = new JPanel();
        
        JButton newMail = new JButton("Nuova Mail");
        newMail.addActionListener(ctr);
        
             
        JLabel initMessage = new JLabel("Lista mail arrivate: ");
        initMessage.setFont(new Font("serif", Font.PLAIN, 20));
        
        GridLayout delLayout = new GridLayout(0,1); 
        delLayout.setVgap(20);
        
        listMailPanel.setLayout(delLayout);
        listMailPanel.setBackground(Color.white);
        delete.setLayout(delLayout);
        panel_button.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 20));
        
              
        panel_button.add(newMail);
        
        initMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        firstPanel.add(listMailPanel, BorderLayout.CENTER);
        firstPanel.add(initMessage, BorderLayout.NORTH);
        firstPanel.add(delete, BorderLayout.WEST);
        firstPanel.add(panel_button, BorderLayout.SOUTH); 
    }
    
    
    public JPanel getFirstPanel(){
        return this.firstPanel;
    }
    
    public void listNewMail(JLabel newMail){
        listMailPanel.add(newMail);
        listMailPanel.validate();
        listMailPanel.repaint();
    }
    
    public void cleanListView(){
        listMailPanel.removeAll();
        listMailPanel.validate();
        listMailPanel.repaint();
    }
  
}

