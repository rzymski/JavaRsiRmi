package crosscircle;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CrossCircleServer extends Remote {
    void registerClient(CrossCircleClient client) throws RemoteException;
    void makeMove(int row, int col, char symbol) throws RemoteException;
    char[][] getBoardState() throws RemoteException;
}

