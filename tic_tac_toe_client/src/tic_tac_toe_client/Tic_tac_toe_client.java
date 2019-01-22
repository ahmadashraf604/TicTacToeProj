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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        if (!isInGame()) {
            boolean sendInvition = false;
            if (connectToRegisery()) {
                this.isBeginer = true;
                this.sender = this.player.getUsername();
                this.reciever = reciever;
                try {
                    sendInvition = serverInt.sendInvition(player.getUsername(), reciever);
                    if (sendInvition) {
                        this.isMyTurn = true;
                        multiPlayerScreen.startGame();
                        setInGame(true);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("server not found");
            }
            return sendInvition;
        } else {
            makeAlert("Error", "You can not play with other friend after the game is finished");
            return false;
        }
    }

    public boolean receiveInvition(String sender, String reciever) {
        boolean invitationState = false;
        if (!isInGame()) {
            this.isBeginer = false;
            this.sender = sender;
            this.reciever = reciever;
            final String text = "your friend " + sender + " ask you to play ";

            final FutureTask query = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    if (makeInfoAlert("Playing invitation", text)) {
                        setInGame(true);
                        multiPlayerScreen.startGame();
                        return true;
                    } else {
                        setInGame(false);
                        makeAlert("invitation refuse", "we are sorry your friend refuse the request");
                        return false;
                    }
                }
            });
            Platform.runLater(query);
            try {
                invitationState = (boolean) query.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Tic_tac_toe_client.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                    makeAlert("Error", "You Are sorry server is down");
                    logout();
                }
            });
            return null;
        }
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
                player = renewPlayerInfo();
                multiPlayerScreen.updateScore(player.getPoints());
            }
        });
    }

    public void alertLosser() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (makeAlert("Losing", "You Are Lose the Game for Sorry")) {
//                    startGame();
                }
            }
        });
    }

    public void alertDrawen() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                makeAlert("Drawen", "You Are Draw");
                player = renewPlayerInfo();
                multiPlayerScreen.updateScore(player.getPoints());
            }
        });
    }

    public boolean makeAlert(String title, String text) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.getDialogPane().setStyle("-fx-background-color: #2c3e50; -fx-fill: #292929;");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/backgroundB.png")));
        alert.setHeaderText(null);
        alert.setContentText(text);
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream("/images/background.png")));
        imageView.setFitHeight(50.0);
        imageView.setFitWidth(50.0);
        alert.setGraphic(imageView);
        Optional<ButtonType> action = alert.showAndWait();
        return action.isPresent();
    }

//    public void makeInfoAlert(String title, String text) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.set
//        alert.setHeaderText(null);
//        alert.setContentText(text);
//        Optional<ButtonType> action = alert.showAndWait();
//       }
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
        if (!isInGame()) {
            aboutUsScreen = new AboutUsScreen(this);
            Scene scene = new Scene(aboutUsScreen, 900, 500);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
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

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    private boolean makeInfoAlert(String title, String text) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {//ok button is pressed
            return true;
        } else {
            // cancel button is pressed
            setInGame(false);
            return false;
        }
    }

}
