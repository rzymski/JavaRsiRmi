package tictactoe;

import chat.ChatClient;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeServerImpl extends UnicastRemoteObject implements TicTacToeServer {
    private TicTacToeClient[] clients;
    private int numberOfPlayers = 0;
    private char[][] board;

    protected TicTacToeServerImpl() throws RemoteException {
        clients = new TicTacToeClient[2];
        board = new char[3][3];
    }

    @Override
    public int registerClient(TicTacToeClient client) throws RemoteException {
        if (numberOfPlayers < 2){
            clients[numberOfPlayers] = client;
            numberOfPlayers++;
            System.out.println("Zarejestrowano gracza nr. %d".formatted(numberOfPlayers));
            broadcastMessage(0, "S Zarejestowales sie do gry. Jesteś graczem nr. %d".formatted(numberOfPlayers));
            if(numberOfPlayers == 1){
                broadcastMessage(0, "S Oczekiwanie na przeciwnika...");
            } else {
                broadcastMessage(0, "S Start gry");
            }
        }
        return numberOfPlayers;
    }

    @Override
    public void broadcastMessage(int clientId, String message) throws RemoteException {
        for (TicTacToeClient client : clients) {
            if (client.getClientId() != clientId) {
                client.receiveMessage(message);
            }
            System.out.println("Wysłano wiadomosc: " + message);
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            System.setProperty("java.rmi.server.codebase", "http://192.168.1.6/tictactoe/");

            LocateRegistry.createRegistry(1099);
            TicTacToeServer ticTacToeServer = new TicTacToeServerImpl();
            Naming.rebind("TicTacToeServer", ticTacToeServer);
            System.out.println("Serwer kółka i krzyżyk działa...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
