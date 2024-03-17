package chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void registerClient(ChatClient client) throws RemoteException;
    void broadcastMessage(String sender, String message) throws RemoteException;
}

