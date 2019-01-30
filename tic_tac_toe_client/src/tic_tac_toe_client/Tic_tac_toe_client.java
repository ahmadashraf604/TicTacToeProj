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
import common.Game;
import common.Player;
import common.ServerInt;
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Ashraf_R
 */
public class Tic_tac_toe_client extends Application {

    public static final String SCENE_BACKGROUND = "#2c3e50";
    public static final String SCENE_FORGROUND = "#ecf0f1";
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
    //to show state of user if he is in game or not
    boolean inGame = false;

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
                        if (!player.isInGame()) {
                            serverInt.unRegister(player.getUsername());
                        } else {
                            if (makeInfoAlert("Error", "are you sure to close ,you will lose the game")) {
                                openLoginScreen();
                                serverInt.unRegister(player.getUsername());
                            }
                        }
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
                if (serverInt.checkIfActive(username)) {
                    System.out.println("not logged");

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
                        loginScreen.getInValidLabel().setText("*wrong username or password");
                        loginScreen.getInValidLabel().setVisible(true);
                    }
                } else {
                    System.out.println(serverInt.checkIfActive(username));
                    System.out.println("logged");
                    loginScreen.getServerErrorLabel().setVisible(false);
                    loginScreen.getInValidLabel().setText("already logged in!");
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

    public boolean sendInvition(String reciever) {
        if (!player.isInGame()) {
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
            return false;
        } else {
            makeAlert("Error", "Sorry you should finish this game first to start a new game! ");
            return false;
        }
    }

    public boolean receiveInvition(String sender, String reciever) {
        boolean invitationState = false;
        if (!player.isInGame()) {
            this.isBeginer = false;
            this.sender = sender;
            this.reciever = reciever;
            final String text = "Hi "+reciever+", your friend " + sender + " asks you to play a game";

            Platform.runLater(() -> {
                if (makeInfoAlert("Playing invitation", text)) {
                    try {
                        if (serverInt.isPlayerOnline(sender)) {
                            serverInt.acceptInvitation(sender, reciever);
                            multiPlayerScreen.startGame(sender);
                            player = renewPlayerInfo();
                        } else {
                            client.hundleExcptionsCases("Error", "Sorry " + sender + " is offline now!");
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        if (serverInt.isPlayerOnline(sender)) {
                            serverInt.refuseInvitation(sender, reciever);
                        } else {
                            client.hundleExcptionsCases("Error", "Sorry " + sender + " is offline now!");
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        return invitationState;
    }

    public Player getPlayer() {
        if (player != null) {
            return player;
        }
        return null;
    }

    private Player renewPlayerInfo() {
        try {
            return serverInt.getPlayer(player.getUsername());
        } catch (RemoteException ex) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    makeAlert("Error", "Oops!, server is down now! ");
                    logout();
                }
            });
            return null;
        }
    }

    public void startGame(String playerName) {
        this.isMyTurn = true;
        player = renewPlayerInfo();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                multiPlayerScreen.startGame(playerName);
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

    public void alertWinner(String body) {
        Platform.runLater(() -> {
            makeAlert("Winner", "Congrats!, You are the winner." + body);
            player = renewPlayerInfo();
            multiPlayerScreen.endGame(player.getPoints());
        });
    }

    public void alertLosser() {
        Platform.runLater(() -> {
            makeAlert("Losing", "Hard luck!, Try to play again.");
            multiPlayerScreen.endGame(player.getPoints());
        });
    }

    public void alertDrawen() {
        Platform.runLater(() -> {
            makeAlert("Drawen", "It's a draw!");
            player = renewPlayerInfo();
            multiPlayerScreen.endGame(player.getPoints());
        });
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
        offLinePlayeScreen = new OffLinePlayeScreen(loginScreen);
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
            if (makeInfoAlert("Error", "Do you want to logout?")) {
                openLoginScreen();
                serverInt.unRegister(player.getUsername());
            }
        } catch (RemoteException ex) {
            serverInt = null;
        }

    }

    void logoutAbnormal() {
        serverInt = null;
        Platform.runLater(() -> {
            //handel an action when server is off
            makeAlert("Error", "We are sorry server is down, please try again");
            openLoginScreen();
        });
    }

    // open about us screen by about us icon
    public void openAboutUsScreen() {
        if (!player.isInGame()) {
            aboutUsScreen = new AboutUsScreen(this);
            Scene scene = new Scene(aboutUsScreen, 900, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            makeAlert("Error", "Sorry, you should continue the game first");
        }
    }

    // open play record screen by about us icon
    public void openPlayRecordScreen() {
        if (!player.isInGame()) {
            playRecordGame = new PlayRecordGame(this);
            Scene scene = new Scene(playRecordGame, 900, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            makeAlert("Error", "Sorry, you should continue the game first");
        }

    }

    //open about us screen by about us button
    public void openAboutUsScreenBtn() {
        aboutUsScreen.getAboutAnchorPane().setVisible(true);
        aboutUsScreen.getOfflineAnchorPane().setVisible(false);
        aboutUsScreen.getOnlineAnchorPane1().setVisible(false);
        aboutUsScreen.hideRulesLabel();
    }

    //open how to play offline screen
    public void openHowToPlayOfflineScreen() {
        aboutUsScreen.getAboutAnchorPane().setVisible(false);
        aboutUsScreen.getOfflineAnchorPane().setVisible(true);
        aboutUsScreen.setOfflineRules();
        aboutUsScreen.getOnlineAnchorPane1().setVisible(false);
    }

    //open how to play online screen
    public void openHowToPlayOnlineScreen() {
        aboutUsScreen.getAboutAnchorPane().setVisible(false);
        aboutUsScreen.getOfflineAnchorPane().setVisible(false);
        aboutUsScreen.getOnlineAnchorPane1().setVisible(true);
        aboutUsScreen.setOnlineRules();
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

    public void sendMsg(String username, String receiverUsername, String text) {
        try {
            serverInt.sendMsg(username, receiverUsername, text);
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveMsg(String sender, String receiver, String message) {
        multiPlayerScreen.receiveMsg(sender, receiver, message);
    }

    private boolean setAlertSetting(Alert alert, String title, String body) {
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/backgroundB.png")));
        alert.setHeaderText(null);
        alert.setContentText(body);
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("/images/background.png")));
        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        alert.setGraphic(imageView);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    public boolean makeAlert(String title, String body) {
        Alert alert = new Alert(AlertType.INFORMATION);
        return setAlertSetting(alert, title, body);
    }

    private boolean makeInfoAlert(String title, String body) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        return setAlertSetting(alert, title, body);
    }

    void refuseInvitation(String sender, String reciever) {
        Platform.runLater(() -> {
            makeAlert("refusing", "Unfortunately, Your invitation is refused.");
            multiPlayerScreen.setIsInvitationBtnClicked();
        });
    }

    void setIsRecording() {
        multiPlayerScreen.setRecording();
    }

    public void popNotification(String title, String body) {
        Platform.runLater(() -> {
            ImageView logoImageView = new ImageView(new Image("/images/backgroundB.png"));
            logoImageView.setFitHeight(50.0);
            logoImageView.setFitWidth(50.0);
            Notifications.create()
                    .title(title)
                    .text(body)
                    .graphic(logoImageView)
                    .hideAfter(Duration.seconds(5.0))
                    .position(Pos.BOTTOM_RIGHT)
                    .show();
        });
    }
}
