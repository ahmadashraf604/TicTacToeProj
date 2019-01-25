/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import chatstate.JAXBUtilsChat;
import commen.Chat;
import commen.ClientInt;
import commen.Player;
import commen.ServerInt;
import commen.Game;
import commen.Messages;
import gamestatexml.JAXBUtils;
import commen.SavedGameState;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import tic_tac_toe_server.Tic_tac_toe_server;

/**
 *
 * @author Ashraf_R
 */
public class ServerImplemention extends UnicastRemoteObject implements ServerInt {

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    Map<String, ClientInt> clients = new HashMap<>();
    //hash map of two player board object
    //sender , object of GameState
    Map<String, GameState> gameStateMap = new HashMap<>();
    Tic_tac_toe_server controller;
    SavedGameState savedGameState;
    Game game;
    JAXBUtilsChat jAXBUtilsChat;
    JAXBUtils xmlUtils;
    Chat chat;

    public ServerImplemention(Tic_tac_toe_server controller) throws RemoteException {
        this.controller = controller;
        jAXBUtilsChat = new JAXBUtilsChat();
        chat = new Chat();
    }

    @Override
    public void register(String username, ClientInt clientRef) {
        clients.put(username, clientRef);
        controller.displayPlayerList();
        renewActivePlayer();
    }

    private void renewActivePlayer() {
        clients.forEach((playerName, ClientObject) -> {
            try {
                ClientObject.renewActivePlayers();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void unRegister(String username) {
        clients.remove(username);
        dataBaseConnection.logout(username);
        controller.displayPlayerList();
        renewActivePlayer();
    }

    @Override
    public List<Player> getActivePlayer() {
        return dataBaseConnection.getActivePlayers();
    }

    @Override
    public Player signin(String username, String password) {
        return dataBaseConnection.login(username, password);
    }

    @Override
    public boolean signup(Player player) {
        return dataBaseConnection.registerPlayer(player);
    }

    @Override
    public void sendInvition(String sender, String reciever) {
        ClientInt recieverClient = clients.get(reciever);
        try {
            recieverClient.receiveInvition(sender, reciever);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void acceptInvitation(String sender, String reciever) {
        ClientInt sendClient = clients.get(sender);
        gameStateMap.put(sender, new GameState());
        try {
            sendClient.acceptInvitation(sender, reciever);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void refuseInvitation(String sender, String reciever) throws RemoteException {
        ClientInt sendClient = clients.get(sender);
        try {
            sendClient.refuseInvitation(sender, reciever);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendGameCell(String sender, String reciever, int rowIndex, int columnIndex, char symbol) {
        if (reciever != null && sender != null) {
            try {
                ClientInt senderClient = clients.get(sender);
                ClientInt recieverClient = clients.get(reciever);
                if (senderClient == null) {
                    recieverClient.hundleExcptionsCases("lose Connection",
                            "Unfortionately your friend logout for now ,please try play with other friend");
                } else if (recieverClient == null) {
                    senderClient.hundleExcptionsCases("lose Connection",
                            "Unfortionately your friend logout for now ,please try play with other friend");
                } else {
                    GameState gameBoard = gameStateMap.get(sender);
                    gameBoard.setGameBoardCell(rowIndex, columnIndex, symbol);

                    //recording the game
                    if (gameBoard.getIsRecording()) {
                        savedGameState = new SavedGameState();
                        if (symbol == 'x') {
                            savedGameState.setUserName(sender);
                        } else {
                            savedGameState.setUserName(reciever);
                        }
                        savedGameState.setRowPosition(rowIndex);
                        savedGameState.setColPosition(columnIndex);
                        savedGameState.setSymbol(symbol);
                        System.err.println(rowIndex + " " + columnIndex + " " + symbol);
                        System.err.println(savedGameState.getRowPosition() + " " + savedGameState.getColPosition() + " " + savedGameState.getSymbol());
                        game.add(savedGameState);
                    }

                    if (symbol == 'x') {
                        if (gameBoard.isWin(symbol)) {
                            //recording the game
                            if (game != null) {
                                game.setResult(sender);
                                saveXmlInFile(sender, reciever);
                            }
                            senderClient.alertWinner();
                            dataBaseConnection.incrementPointsWin(sender);
                            recieverClient.alertLosser();
                        } else if (gameBoard.isDraw()) {
                            //recording the game
                            if (game != null) {
                                game.setResult("Draw");
                                saveXmlInFile(sender, reciever);
                            }
                            senderClient.alertDrawen();
                            recieverClient.alertDrawen();
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(reciever);
                        }
                    } else if (symbol == 'o') {
                        if (gameBoard.isWin(symbol)) {
                            //recording the game
                            if (game != null) {
                                game.setResult(reciever);
                                saveXmlInFile(sender, reciever);
                            }
                            recieverClient.alertWinner();
                            senderClient.alertLosser();
                            dataBaseConnection.incrementPointsWin(reciever);
                        } else if (gameBoard.isDraw()) {
                            //recording the game
                            if (game != null) {
                                game.setResult("Draw");
                                saveXmlInFile(sender, reciever);
                            }
                            senderClient.alertDrawen();
                            recieverClient.alertDrawen();
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(reciever);
                        }
                    }
                    senderClient.recieveGameCell(rowIndex, columnIndex, symbol);
                    recieverClient.recieveGameCell(rowIndex, columnIndex, symbol);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveXmlInFile(String sender, String reciever) {
        xmlUtils.generateXml(game, sender, reciever);
        String fileName = "./" + sender + "&" + reciever + "Xml" + ".xml";
        dataBaseConnection.setRecordName(sender, reciever, fileName);
    }

    private void saveChatXmlInFile(String sender, String reciever) {       
        String fileName = "./" + sender + "_" + reciever + "Xml" + ".xml";
        
        jAXBUtilsChat.generateXml(chat, sender, reciever);
        dataBaseConnection.setChatMessages(sender, reciever, fileName);
    }

    @Override
    public boolean recordGame(String sender) {
        GameState board = gameStateMap.get(sender);
        if (board != null && !board.getIsRecording()) {
            board.setIsRecording(true);
            xmlUtils = new JAXBUtils();
            game = new Game();
            return true;
        } else {
            return false;
        }
    }

    public void closeServer() {
        clients.forEach((username, client) -> {
            try {
                client.logout();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //to set all player is not active in database
        dataBaseConnection.colseServer();
        clients.clear();
    }

    @Override
    public List<String> getAllSecondPlayerNamesInRecords(String username) throws RemoteException {
        List<String> recordedPlayers = new ArrayList<>();
        recordedPlayers = dataBaseConnection.getAllSecondPlayerNamesInRecords(username);
        return recordedPlayers;
    }

    @Override
    public Game getGameRecord(String firstName, String secondName) throws RemoteException {
        Game recorededGame = new Game();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((new Class[]{Game.class, SavedGameState.class}));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            recorededGame = (Game) jaxbUnmarshaller.unmarshal(new File("./" + firstName + "&" + secondName + "Xml" + ".xml"));
        } catch (JAXBException ex) {
            ex.printStackTrace();
            Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recorededGame;
    }

    @Override
    public void sendMsg(String sender, String receiver, String msg) {
        ClientInt receiverClient = clients.get(receiver);
        ClientInt senderClient = clients.get(sender);
        try {
            if (senderClient == null) {
                receiverClient.hundleExcptionsCases("lose Connection",
                        "Unfortunately your friend logout for now ,the message will not be sent to " + sender);
            } else if (receiverClient == null) {
                senderClient.hundleExcptionsCases("lose Connection",
                        "Unfortunately your friend logout for now ,the message will not be sent to " + receiver);
            } else {

                Messages chatMessages = new Messages();
                receiverClient.receiveMessage(sender, receiver, msg);
                senderClient.receiveMessage(sender, receiver, msg);
                chatMessages.setSender(sender);
                chatMessages.setReceiver(receiver);
                chatMessages.setMessageContent(sender + " : " + msg);
                chat.add(chatMessages);

            }
        } catch (RemoteException ex) {

        }
        for (int i = 0; i < chat.getMessges().size(); i++) {
            System.out.println("messages " + chat.getMessges().get(i).getMessageContent());

        }
    }

    @Override
    public void saveMessages(String sender, String receiver) throws RemoteException {
        saveChatXmlInFile(sender, receiver);

    }

    @Override
    public Chat getRecordedChatMessages(String sender, String recevier) throws RemoteException {
        String messageXmlFileName = dataBaseConnection.getMessagesFileNameFromDataBase(sender, recevier);
        System.out.println("xml file name" + messageXmlFileName);
        return jAXBUtilsChat.readXml(messageXmlFileName);
    }

}
