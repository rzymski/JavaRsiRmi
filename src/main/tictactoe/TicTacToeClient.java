package tictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToeClient extends Remote {
    void receiveMessage(String message) throws RemoteException;
    int getClientId() throws RemoteException;
}
