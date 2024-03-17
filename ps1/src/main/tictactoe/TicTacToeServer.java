package tictactoe;

import crosscircle.CrossCircleClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToeServer extends Remote {
    int registerClient(TicTacToeClient client) throws RemoteException;
    void broadcastMessage(int clientId, String message) throws RemoteException;
}
