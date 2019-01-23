/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic_tac_toe_server;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataBaseConnection;
import common.Player;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import model.ServerImplemention;
import views.ServerView;

/**
 *
 * @author Ashraf_R
 */
public class Tic_tac_toe_server extends Application {

    public final String SERVICE_NAME = "ticTacToeServer";

    ServerView serverView;
    DataBaseConnection dataBaseConnection;
    ServerImplemention server;
    Registry registry;
    boolean serverAvilable;

    public Tic_tac_toe_server() {
        dataBaseConnection = new DataBaseConnection();
    }

    @Override
    public void start(Stage primaryStage) {

        openRegistry();
        serverView = new ServerView(this);

        Scene scene = new Scene(serverView, 500, 390);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/background.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            if (server != null) {
                server.closeServer();
            }
            System.exit(0);
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private boolean openRegistry() {
        try {
            server = new ServerImplemention(this);
            if (registry == null) {
                registry = LocateRegistry.createRegistry(2015);
            } else {
                registry = LocateRegistry.getRegistry(2015);
            }
            registry.rebind(SERVICE_NAME, server);
            System.out.println("server on");
            serverAvilable = true;
            return true;
        } catch (RemoteException ex) {
            System.out.println("server is not av");
            ex.printStackTrace();
            return false;
        }
    }

    public List<Player> getPlayers() {
        return dataBaseConnection.getAllPlayers();
    }

    public int playerCount() {
        return dataBaseConnection.getAllPlayers().size();
    }

    public void displayPlayerList() {
        serverView.displayPlayerList();
    }

    public void closeServer() {
        try {
            server.closeServer();
            registry.unbind(SERVICE_NAME);
            UnicastRemoteObject.unexportObject(server, true);
            server = null;
            serverAvilable = false;
        } catch (RemoteException ex) {
            Logger.getLogger(Tic_tac_toe_server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Tic_tac_toe_server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean serverIsOpen() {
        return serverAvilable;
    }

    public void openServer() {
        if (openRegistry()) {
            serverAvilable = true;
        }
    }

    public void renewPlayerNumber(int playerNum) {
        serverView.renewPlayerNumber(playerNum);
    }
}
