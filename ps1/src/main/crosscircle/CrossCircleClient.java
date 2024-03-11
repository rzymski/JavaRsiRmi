package crosscircle;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CrossCircleClient extends Remote {
    String getClientId() throws RemoteException;
    void notifyMove(char[][] board, char currentPlayerSymbol) throws RemoteException;
}

