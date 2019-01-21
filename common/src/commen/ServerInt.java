package commen;

import java.rmi.*;
import java.util.List;

public interface ServerInt extends Remote {

    void register(String username, ClientInt clientRef) throws RemoteException;

    void unRegister(String username) throws RemoteException;

    List<Player> getActivePlayer() throws RemoteException;

    Player signin(String username, String password) throws RemoteException;

    boolean signup(Player player) throws RemoteException;

    public boolean sendInvition(String sender, String reciever) throws RemoteException;

    public void acceptInvitation(String sender, String reciever) throws RemoteException;

    public void refuseInvitation(String sender, String reciever) throws RemoteException;

    public void sendGameCell(String sender, String reciever, int rowIndex, int columnIndex, char sumbol) throws RemoteException;

    public boolean recordGame(String sender) throws RemoteException;

    public List<String> getAllSecondPlayerNamesInRecords(String username) throws RemoteException;

    public Game getGameRecord(String firstName, String secondName) throws RemoteException;

    public void sendMsg(String username, String receiverUsername, String text) throws RemoteException;

    public Player getPlayer(String username) throws RemoteException;
}
