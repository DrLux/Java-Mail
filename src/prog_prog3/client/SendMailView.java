/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog_prog3.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author sorre
 */
public class SendMailView{
    private JPanel firstPanel;
    private JTextArea dest;
    private JTextField ogg; 
    private JTextArea corpo;
    
    public SendMailView(ClientController ctr){
        firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setBackground(Color.white);
        
        JPanel mailPanel = new JPanel();
        mailPanel.setBackground(Color.white);
        mailPanel.setLayout(new GridLayout(0,1));
        
        JPanel panel_button = new JPanel();
        panel_button.setBackground(Color.white);
        
        JButton back = new JButton("Homepage");
        back.addActionListener(ctr);
        
        JButton send = new JButton("Invia Mail");
        send.addActionListener(ctr);
        
        corpo = new javax.swing.JTextArea("Inserire testo mail...");       	
        corpo.setForeground(Color.GRAY);
        corpo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (corpo.getText().equals("Inserire testo mail...")) {
                    corpo.setText("");
                    corpo.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (corpo.getText().isEmpty()) {
                    corpo.setForeground(Color.GRAY);
                    corpo.setText("Inserire testo mail...");
                }
            }
        });
        
        
        dest = new JTextArea("Inserire Destinatari...");
        dest.setForeground(Color.GRAY);
        dest.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dest.getText().equals("Inserire Destinatari...")) {
                    dest.setText("");
                    dest.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (dest.getText().isEmpty()) {
                    dest.setForeground(Color.GRAY);
                    dest.setText("Inserire Destinatari...");
                }
            }
        });
        
        ogg = new JTextField("Iserire Oggetto mail...", 10);
        ogg.setForeground(Color.GRAY);
        ogg.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ogg.getText().equals("Iserire Oggetto mail...")) {
                    ogg.setText("");
                    ogg.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (ogg.getText().isEmpty()) {
                    ogg.setForeground(Color.GRAY);
                    ogg.setText("Iserire Oggetto mail...");
                }
            }
        });
        
        
        mailPanel.add(dest);
        mailPanel.add(ogg);
        mailPanel.add(corpo);
        
        panel_button.add(back);
        panel_button.add(send);
        
        firstPanel.add(mailPanel, BorderLayout.CENTER);
        firstPanel.add(panel_button, BorderLayout.SOUTH);
    }
    
    public JPanel getFirstPanel(){
        return firstPanel;
    }
    
    public void setDest(String dst){
        dest.setText(dst);
        dest.setForeground(Color.BLACK);
    }
    
    public void addDest(String dst){
        dest.append(","+dst);
    }
    
    public ArrayList<String> getDest(){
        ArrayList<String> allDest = new ArrayList<String>();
        
        String[] tokens = dest.getText().replaceAll("\\s","").split(","); 

        for (String t : tokens)
           allDest.add(t);
        
        return allDest;
    }
    
    public String readDest(){
        return dest.getText();
    }
    
    public void setArg(String arg){
        ogg.setText(arg);
        ogg.setForeground(Color.BLACK);
    }
    
    public String getArg(){
        return ogg.getText();
    }
    
    public void setTesto(String txt){
        corpo.setText(txt);
        corpo.setForeground(Color.BLACK);
    }
    
    public String getText(){
        return corpo.getText();
    }
    
    public void clean(){
        dest.setText("Inserire Destinatari...");
        dest.setForeground(Color.GRAY);
        ogg.setText("Iserire Oggetto mail...");
        ogg.setForeground(Color.GRAY);
        corpo.setText("Inserire testo mail...");
        corpo.setForeground(Color.GRAY);
        firstPanel.validate();
        firstPanel.repaint();
    }
    
}
