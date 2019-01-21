/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commen;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ashraf_R
 */
public interface ClientInt extends Remote {

    public void renewActivePlayers() throws RemoteException;

    public boolean receiveInvition(String sender, String reciever) throws RemoteException;

    public void acceptInvitation(String sender, String reciever) throws RemoteException;

    public void refuseInvitation(String sender, String reciever) throws RemoteException;

    public void recieveGameCell(int rowIndex, int columnIndex, char sumbol) throws RemoteException;

    public void alertWinner() throws RemoteException;

    public void alertLosser() throws RemoteException;

    public void alertDrawen() throws RemoteException;

    public void receiveMessage(String sender, String receiver, String message) throws RemoteException;

    public void hundleExcptionsCases(String title, String description) throws RemoteException;

    public void logout() throws RemoteException;
}
