/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.Chat;
import common.ClientInt;
import common.Player;
import common.ServerInt;
import common.Game;
import common.Messages;
import gamestatexml.JAXBUtils;
import common.SavedGameState;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
    JAXBUtils xmlUtils;
    Chat chat;

    public ServerImplemention(Tic_tac_toe_server controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void register(String username, ClientInt clientRef) {
        clients.forEach((playerName, playerInt) -> {
            try {
                playerInt.popNotification("Tic Tac Toe", "your friend " + username + " is online now!");
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
        ArrayList<GameState> arrayList = new ArrayList<GameState>(gameStateMap.values());
        for (GameState board : arrayList) {
            if (username.equals(board.getSender())) {
                try {
                    clients.get(board.getRecriver()).alertWinner("because your frined " + board.getSender() + " is logout");
                    dataBaseConnection.setPlayerOutGame(board.getRecriver());
                    gameStateMap.remove(board.getSender());
                } catch (RemoteException ex) {
                    System.out.println("the user is not found");
                }
            } else if (username.equals(board.getRecriver())) {
                try {
                    clients.get(board.getSender()).alertWinner("because your frined " + board.getRecriver() + " is logout");
                    dataBaseConnection.setPlayerOutGame(board.getSender());
                    gameStateMap.remove(board.getSender());
                } catch (RemoteException ex) {
                    System.out.println("the user is not found");
                }
            }
        }
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
        if (dataBaseConnection.registerPlayer(player)) {
            controller.renewPlayerNumber(dataBaseConnection.getPlayerNum());
            return true;
        }
        return false;

    }

    @Override
    public boolean sendInvition(String sender, String receiver) {
        ClientInt receiverClient = clients.get(receiver);
        if (receiverClient != null) {
            try {
                receiverClient.receiveInvition(sender, receiver);
                return true;
            } catch (RemoteException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void acceptInvitation(String sender, String receiver) {
        ClientInt senderClient = clients.get(sender);
        if (senderClient != null) {
            dataBaseConnection.setPlayerInGame(sender, receiver);
            gameStateMap.put(sender, new GameState(sender, receiver));
            try {
                senderClient.acceptInvitation(sender, receiver);
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void refuseInvitation(String sender, String receiver) {
        ClientInt sendClient = clients.get(sender);
        try {
            sendClient.refuseInvitation(sender, receiver);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendGameCell(String sender, String receiver, int rowIndex, int columnIndex, char symbol) {
        if (receiver != null && sender != null) {
            try {
                ClientInt senderClient = clients.get(sender);
                ClientInt receiverClient = clients.get(receiver);
                if (senderClient == null) {
                    receiverClient.hundleExcptionsCases("lose Connection",
                            "Unfortionately your friend logout for now ,please try play with other friend");
                } else if (receiverClient == null) {
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
                            savedGameState.setUserName(receiver);
                        }
                        savedGameState.setRowPosition(rowIndex);
                        savedGameState.setColPosition(columnIndex);
                        savedGameState.setSymbol(symbol);
                        game.add(savedGameState);
                    }
                    //check whose win
                    if (symbol == 'x') {
                        if (gameBoard.isWin(symbol)) {
                            //make player is active not in game
                            dataBaseConnection.setPlayerOutGame(sender);
                            dataBaseConnection.setPlayerOutGame(receiver);
                            //delete the game board from hash map
                            gameStateMap.remove(sender);
                            //recording the game
                            if (game != null) {
                                game.setResult(sender);
                                saveXmlInFile(sender, receiver);
                            }
                            senderClient.alertWinner("");
                            dataBaseConnection.incrementPointsWin(sender);
                            receiverClient.alertLosser();
                        } else if (gameBoard.isDraw()) {
                            //make player is active not in game
                            dataBaseConnection.setPlayerOutGame(sender);
                            dataBaseConnection.setPlayerOutGame(receiver);
                            //delete the game board from hash map
                            gameStateMap.remove(sender);
                            //recording the game
                            if (game != null) {
                                game.setResult("Draw");
                                saveXmlInFile(sender, receiver);
                            }
                            senderClient.alertDrawen();
                            receiverClient.alertDrawen();
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(receiver);
                        }
                    } else if (symbol == 'o') {
                        if (gameBoard.isWin(symbol)) {
                            //make player is active not in game
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(receiver);
                            //delete the game board from hash map
                            gameStateMap.remove(sender);
                            //recording the game
                            if (game != null) {
                                game.setResult(receiver);
                                saveXmlInFile(sender, receiver);
                            }
                            receiverClient.alertWinner("");
                            senderClient.alertLosser();
                            dataBaseConnection.incrementPointsWin(receiver);
                        } else if (gameBoard.isDraw()) {
                            //make player is active not in game
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(receiver);
                            //delete the game board from hash map
                            gameStateMap.remove(sender);
                            //recording the game
                            if (game != null) {
                                game.setResult("Draw");
                                saveXmlInFile(sender, receiver);
                            }
                            senderClient.alertDrawen();
                            receiverClient.alertDrawen();
                            dataBaseConnection.incrementPointsDraw(sender);
                            dataBaseConnection.incrementPointsDraw(receiver);
                        }
                    }
                    senderClient.recieveGameCell(rowIndex, columnIndex, symbol);
                    receiverClient.recieveGameCell(rowIndex, columnIndex, symbol);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImplemention.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveXmlInFile(String sender, String receiver) {
        xmlUtils.generateXml(game, sender, receiver);
        String fileName = "./" + sender + "&" + receiver + "Xml" + ".xml";
        dataBaseConnection.setRecordName(sender, receiver, fileName);
    }

    @Override
    public boolean recordGame(String sender) {
        GameState board = gameStateMap.get(sender);
        if (board != null && !board.getIsRecording()) {
            try {
                clients.get(sender).setIsRecording();
                clients.get(board.getRecriver()).setIsRecording();
                board.setIsRecording(true);
                xmlUtils = new JAXBUtils();
                game = new Game();
                return true;
            } catch (RemoteException ex) {
                return false;
            }
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
    public Game getGameRecord(String firstName, String secondName) {
        Game recorededGame = new Game();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((new Class[]{Game.class, SavedGameState.class}));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            recorededGame = (Game) jaxbUnmarshaller.unmarshal(
                    new File(dataBaseConnection.getRecord(firstName, secondName)));
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
                chat = new Chat();
                Messages chatMessages = new Messages();
                receiverClient.receiveMessage(sender, receiver, msg);
                senderClient.receiveMessage(sender, receiver, msg);
                chatMessages.setSender(sender);
                chatMessages.setReceiver(receiver);
                chatMessages.setMessageContent(msg);
                chat.add(chatMessages);
            }
        } catch (RemoteException ex) {

        }
    }

    @Override
    public Player getPlayer(String username) throws RemoteException {
        return dataBaseConnection.getPlayer(username);
    }

    @Override
    public boolean checkIfActive(String username) throws RemoteException {
        return dataBaseConnection.isNotActive(username);
    }

    @Override
    public boolean isPlayerOnline(String username) {
        return clients.get(username) != null;
    }

}
