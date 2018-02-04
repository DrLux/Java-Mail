/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author sorre
 */
public class Mail implements Serializable {
   
    public enum tipoMail{Inviata, Ricevuta}

    private static long globalId = 1;
    private long id = 1;
    private String mitt = null;
    private ArrayList dest;
    private String arg = null;
    private String text = null;
    private int prio = 0;
    private String data = null;
    private tipoMail tipo = null;
    
    public Mail(String mit, String dest, String arg, String text, int prio, String data, tipoMail tipo){
        this.id = globalId;
        this.mitt = mit;
        this.dest = new ArrayList<String>();
        this.dest.add(dest);
        this.arg = arg;
        this.text = text;
        this.prio = prio;
        this.data = data;
        this.tipo = tipo;
        
        globalId++;
    }
    
    public Mail(String mit, ArrayList<String> dest,String arg, String text, int prio, String data, tipoMail tipo){
        this.id = globalId;
        this.mitt = mit;
        this.dest = new ArrayList<String>();
        this.dest = dest;
        this.arg = arg;
        this.text = text;
        this.prio = prio;
        this.data = data;
        this.tipo = tipo;
        
        globalId++;
    }
    
    public String getMittente(){
        return mitt; 
    }
    
    public ArrayList<String> getDestinatario() throws RemoteException {
        return dest;
    }

    public String getArgomento() {
        return arg;
    }

    public String getTesto() {
        return text;
    }

    public tipoMail getTipoMail() {
        return this.tipo;
    }

    public void setMittente(String mit){
        this.mitt = mit;
    }

    public void setDestinatario(ArrayList<String> dst) throws RemoteException {
        this.dest = dst;
    }

    public void setArgomento(String arg) {
        this.arg = arg;
    }

    public void setTesto(String tst) {
        this.text = tst;
    }

    public void setTipoMail(tipoMail tipo) {
        this.tipo = tipo;
    }
    
    public int getId(){
        return (int)id;
    }
    
    public String toString(){
        return "Arg: "+ this.arg +" | Mitt: "+ this.mitt +" | Dest: "+ this.dest +" | Testo: "+ this.text +" | Data: "+ this.data +" | Tipo: "+this.tipo;
    }
    
    public int getPrio(){
        return this.prio;
    }
    
    public String getData(){
        return this.data;
    }
    
}
