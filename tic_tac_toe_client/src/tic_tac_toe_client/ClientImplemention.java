/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe_client;

import common.ClientInt;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javafx.application.Platform;

/**
 *
 * @author Ashraf_R
 */
public class ClientImplemention extends UnicastRemoteObject implements ClientInt {

    Tic_tac_toe_client controller;

    public ClientImplemention(Tic_tac_toe_client controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public boolean receiveInvition(String sender, String reciever) {
        return controller.receiveInvition(sender, reciever);
    }

    @Override
    public void acceptInvitation(String sender, String reciever) {
        controller.startGame(reciever);
    }

    @Override
    public void refuseInvitation(String sender, String reciever) {
        controller.refuseInvitation(sender, reciever);
    }

    @Override
    public void recieveGameCell(int rowIndex, int columnIndex, char sumbol) {
        controller.recieveGameCell(rowIndex, columnIndex, sumbol);
    }

    @Override
    public void alertWinner(String body) {
        controller.alertWinner(body);
    }

    @Override
    public void alertLosser() {
        controller.alertLosser();
    }

    @Override
    public void alertDrawen() {
        controller.alertDrawen();
    }

    @Override
    public void renewActivePlayers() {
        controller.renewActivePlayers();
    }

    @Override
    public void receiveMessage(String sender, String receiver, String message) {
        controller.receiveMsg(sender, receiver, message);
    }

    @Override
    public void logout() {
        controller.logoutAbnormal();
    }

    @Override
    public void hundleExcptionsCases(String title, String description) {
        Platform.runLater(() -> controller.makeAlert(title, description));
    }

    @Override
    public void setIsRecording() throws RemoteException {
        controller.setIsRecording();
    }

    @Override
    public void popNotification(String title, String body) throws RemoteException {
       controller.popNotification(title, body);
    }
}
