package tictactoe;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TicTacToeClientImpl extends UnicastRemoteObject implements TicTacToeClient {
    private static int clientId;
    protected TicTacToeClientImpl() throws RemoteException {
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public int getClientId() throws RemoteException {
        return clientId;
    }

    public static void main(String[] args) {
        try {
            String serverUrl = "//192.168.1.6/TicTacToeServer";
            TicTacToeServer ticTacToeServer = (TicTacToeServer) Naming.lookup(serverUrl);
            TicTacToeClientImpl client = new TicTacToeClientImpl();
            clientId = ticTacToeServer.registerClient(client);
            System.out.println("K Zarejestowales sie do gry. Jesteś graczem nr. %d".formatted(clientId));
            System.out.println("K Oczekiwanie na przeciwnika...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
