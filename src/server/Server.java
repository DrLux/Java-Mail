/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Sorrentino
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public interface Server extends Remote {
   public ArrayList scaricaMail(String username) throws RemoteException; 
   public boolean checkNewMail(String username) throws RemoteException;
   public boolean checkUser(String username) throws RemoteException;
    public ArrayList scaricaNuoveMail(String username) throws RemoteException;
    public boolean sendMail(String user, Mail newMail) throws RemoteException;
    public void closeConnection(String user) throws RemoteException;
    public void delMail(String user, Mail mail) throws RemoteException;
}

