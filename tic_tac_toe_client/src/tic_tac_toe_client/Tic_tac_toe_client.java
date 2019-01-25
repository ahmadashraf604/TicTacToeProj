/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe_client;

import clientViews.AboutUsScreen;
import clientViews.LoginScreen;
import clientViews.MultiPlayerScreen;
import clientViews.PlayRecordGame;
import clientViews.RegisterScreen;
import clientViews.offlineViews.OffLinePlayeScreen;
import commen.Chat;
import commen.Game;
import commen.Player;
import commen.ServerInt;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Ashraf_R
 */
public class Tic_tac_toe_client extends Application {

    ServerInt serverInt;
    Player player;
    ClientImplemention client;
    Stage primaryStage;
    MultiPlayerScreen multiPlayerScreen;
    RegisterScreen registerScreen;
    LoginScreen loginScreen;
    AboutUsScreen aboutUsScreen;
    PlayRecordGame playRecordGame;
    OffLinePlayeScreen offLinePlayeScreen;
    boolean isMyTurn = false;
    boolean isBeginer = false;
    String reciever, sender;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        try {
            client = new ClientImplemention(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (connectToRegisery()) {
            System.out.println("client on");
        }

        openLoginScreen();
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/background.png")));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            try {
                if (player != null) {
                    if (serverInt != null) {
                        serverInt.unRegister(player.getUsername());
                    }
                    System.err.println("logout");
                    System.exit(0);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //try to connect to server
    //if it fail to connect to server it will return false
    public boolean connectToRegisery() {
        if (serverInt == null) {
            //connect to server
            try {
//                Registry registry = LocateRegistry.getRegistry("10.0.1.184", 2015);
                Registry registry = LocateRegistry.getRegistry(2015);
                serverInt = (ServerInt) registry.lookup("ticTacToeServer");
            } catch (RemoteException | NotBoundException ex) {
                serverInt = null;
                System.out.println("Server not found");
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    public Player signin(String username, String password) {
        if (connectToRegisery()) {
            try {
                player = serverInt.signin(username, password);
                if (player != null) {
                    //register the user in server
                    serverInt.register(username, client);
                    openMultiPlayerScreen();
                    loginScreen.getServerErrorLabel().setVisible(false);
                    loginScreen.getInValidLabel().setVisible(false);
                    return player;
                } else {
                    loginScreen.getServerErrorLabel().setVisible(false);
                    loginScreen.getInValidLabel().setVisible(true);
                }
            } catch (RemoteException ex) {
                serverInt = null;
                loginScreen.getServerErrorLabel().setVisible(true);
                loginScreen.getInValidLabel().setVisible(false);
            }
        } else {
            System.out.println("out");
            loginScreen.getServerErrorLabel().setVisible(true);
            loginScreen.getInValidLabel().setVisible(false);

        }
        return null;
    }

    public List<Player> getActivePlayer() {
        List<Player> activePlayer = null;
        if (connectToRegisery()) {
            try {
                activePlayer = serverInt.getActivePlayer();
                for (int i = 0; i < activePlayer.size(); i++) {
                    if (activePlayer.get(i).getUsername().equals(player.getUsername())) {
                        activePlayer.remove(activePlayer.get(i));
                    }
                }
                return activePlayer;
            } catch (RemoteException ex) {
                Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean signup(Player player) {
        if (connectToRegisery()) {
            try {
                return serverInt.signup(player);
            } catch (RemoteException ex) {
                Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public void sendInvition(String reciever) {
        if (connectToRegisery()) {
            this.isBeginer = true;
            this.sender = this.player.getUsername();
            this.reciever = reciever;
            try {
                serverInt.sendInvition(player.getUsername(), reciever);
            } catch (RemoteException ex) {
                Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("server not found");
        }
    }

    public void receiveInvition(String sender, String reciever) {
        this.isBeginer = false;
        this.sender = sender;
        this.reciever = reciever;
        final String text = "your friend " + sender + " ask you to play ";

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Playing invitation");
                alert.setHeaderText(null);
                alert.setContentText(text);
                Optional<ButtonType> action = alert.showAndWait();
                try {
                    if (action.get() == ButtonType.OK) {//oke button is pressed
                        serverInt.acceptInvitation(sender, reciever);
                        multiPlayerScreen.startGame();
                    } else {
                        // cancel button is pressed
                        serverInt.refuseInvitation(sender, reciever);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public Player getPlayer() {
        if (player != null) {
            return player;
        }
        return null;
    }

    public void startGame() {
        this.isMyTurn = true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                multiPlayerScreen.startGame();
            }
        });
    }

    public void openMultiPlayerScreen() {
        multiPlayerScreen = new MultiPlayerScreen(primaryStage, this);
        Scene scene = new Scene(multiPlayerScreen, 900, 500);
        primaryStage.setTitle("Tic-tac-toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isItMyTurn() {
        return isMyTurn;
    }

    public boolean isbaginer() {
        return isBeginer;
    }

    public void sendGameCell(int rowIndex, int columnIndex, char sumbol) {
        try {
            serverInt.sendGameCell(sender, reciever, rowIndex, columnIndex, sumbol);
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void recieveGameCell(int rowIndex, int columnIndex, char sumbol) {
        isMyTurn = !isMyTurn;
        Platform.runLater(() -> multiPlayerScreen.drawCell(rowIndex, columnIndex, sumbol));
    }

    public void alertWinner() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                makeAlert("Winner", "You Are Winner");
            }
        });
    }

    public void alertLosser() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (makeAlert("Losing", "You Are Lose the Game for Sorry")) {
                    //     startGame();
                }
            }
        });
    }

    public void alertDrawen() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                makeAlert("Drawen", "You Are Draw");

            }
        });
    }

    boolean makeAlert(String title, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    void renewActivePlayers() {
        if (multiPlayerScreen != null) {
            multiPlayerScreen.displayPlayerList();
        }
    }

    public void openRegisterScreen() {
        registerScreen = new RegisterScreen(primaryStage, this);
        Scene scene = new Scene(registerScreen, 900, 500);
        primaryStage.setTitle("Sign up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openLoginScreen() {
        loginScreen = new LoginScreen(primaryStage, this);
        Scene scene = new Scene(loginScreen, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openOfflineScreen() {
        offLinePlayeScreen = new OffLinePlayeScreen(this);
        Scene scene = new Scene(offLinePlayeScreen, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean recordGame() {
        try {
            return serverInt.recordGame(sender);
        } catch (RemoteException ex) {
            return false;
        }
    }

    public void logout() {
        try {
            serverInt.unRegister(player.getUsername());
            openLoginScreen();
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void logoutAbnormal() {
        serverInt = null;
        Platform.runLater(() -> {
            openLoginScreen();
            //handel an action when server is off
        });
    }

    // open about us screen by about us icon
    public void openAboutUsScreen() {
        aboutUsScreen = new AboutUsScreen(this);
        Scene scene = new Scene(aboutUsScreen, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // open play record screen by about us icon
    public void openPlayRecordScreen() {
        playRecordGame = new PlayRecordGame(this);
        Scene scene = new Scene(playRecordGame, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //open about us screen by about us button
    public void openAboutUsScreenBtn() {
        aboutUsScreen.getAboutAnchorPane().setVisible(true);
        aboutUsScreen.getOfflineAnchorPane().setVisible(false);
        aboutUsScreen.getOnlineAnchorPane1().setVisible(false);

    }

    //open how to play offline screen
    public void openHowToPlayOfflineScreen() {
        aboutUsScreen.getAboutAnchorPane().setVisible(false);
        aboutUsScreen.getOfflineAnchorPane().setVisible(true);
        aboutUsScreen.getOnlineAnchorPane1().setVisible(false);

    }

    //open how to play online screen
    public void openHowToPlayOnlineScreen() {
        aboutUsScreen.getAboutAnchorPane().setVisible(false);
        aboutUsScreen.getOfflineAnchorPane().setVisible(false);
        aboutUsScreen.getOnlineAnchorPane1().setVisible(true);
    }

    public List<String> getRecordedPlayers() {
        List<String> recordedPlayers = new ArrayList<>();
        try {
            recordedPlayers = serverInt.getAllSecondPlayerNamesInRecords(player.getUsername());
            return recordedPlayers;
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Game getGameRecord(String username) {
        Game recordedGame = new Game();
        try {
            recordedGame = serverInt.getGameRecord(player.getUsername(), username);
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recordedGame;
    }

    public Chat getRecordedChatMessages(String userName) {
        Chat recordedChatMessages = new Chat();
        try {
            recordedChatMessages = serverInt.getRecordedChatMessages(player.getUsername(), userName);
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recordedChatMessages;
    }

    public void sendMsg(String username, String receiverUsername, String text) {
        try {

            serverInt.sendMsg(username, receiverUsername, text);
            serverInt.saveMessages(player.getUsername(), receiverUsername);

        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void saveMsg(String receiverUsername) {
//        try {
//        } catch (RemoteException ex) {
//            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    public void receiveMsg(String sender, String receiver, String message) {
        multiPlayerScreen.receiveMsg(sender, receiver, message);
        multiPlayerScreen.getTextArea().appendText("\n");

    }

}
