package tictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicTacToeServer extends Remote {
    int registerClient(TicTacToeClient client) throws RemoteException;
    void notifyPlayers(String message) throws RemoteException;
    int checkGameState(int clientId) throws RemoteException;
    int makeMove(int clientId, int row, int col) throws RemoteException;
}
