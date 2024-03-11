package crosscircle;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CrossCircleServerImpl extends UnicastRemoteObject implements CrossCircleServer {

    private final List<CrossCircleClient> clients;
    private char[][] board;
    private char currentPlayerSymbol;

    protected CrossCircleServerImpl() throws RemoteException {
        clients = new ArrayList<>();
        board = new char[3][3];
        currentPlayerSymbol = 'X';
    }

    @Override
    public void registerClient(CrossCircleClient client) throws RemoteException {
        clients.add(client);
        client.notifyMove(board, currentPlayerSymbol);
        currentPlayerSymbol = (currentPlayerSymbol == 'X') ? 'O' : 'X';
    }

    @Override
    public void makeMove(int row, int col, char symbol) throws RemoteException {
        if (isValidMove(row, col) && symbol == currentPlayerSymbol) {
            board[row][col] = symbol;
            notifyAllClients();
            currentPlayerSymbol = (currentPlayerSymbol == 'X') ? 'O' : 'X';
            displayBoard(board);
        }
    }

    private void displayBoard(char[][] board) {
        System.out.println("Current board state:");
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    @Override
    public char[][] getBoardState() throws RemoteException {
        return board;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == 0;
    }

    private void notifyAllClients() throws RemoteException {
        for (CrossCircleClient client : clients) {
            client.notifyMove(board, currentPlayerSymbol);
        }
    }

    // @Override
    // public void broadcastMessage(String sender, String message) throws RemoteException {
    //     for (CrossCircleClient client : clients) {
    //         if (!client.getClientId().equals(sender)) {
    //             client.receiveMessage(sender, message);
    //         }
    //         System.out.println("Otrzymano wiadomosc: " + message);
    //     }
    // }

    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            System.setProperty("java.rmi.server.codebase", "http://192.168.43.20/CrossCircle/");

            LocateRegistry.createRegistry(1099);
            CrossCircleServer CrossCircleServer = new CrossCircleServerImpl();
            Naming.rebind("CrossCircleServer", CrossCircleServer);
            System.out.println("CrossCircleServer is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

