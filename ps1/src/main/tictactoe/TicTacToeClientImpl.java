package tictactoe;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TicTacToeClientImpl extends UnicastRemoteObject implements TicTacToeClient {
    private int clientId = -1;
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
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString(){
        return "Klient nr. %d".formatted(clientId);
    }

    public int tryMakeMove(TicTacToeServer server) throws RemoteException {
        System.out.println("Twoj ruch. Podaj wiersz (0-2) i kolumne (0-2) oddzielone spacją:");
        server.sendMessageToOtherPlayer(clientId, "Oczekiwanie na ruch gracza nr. %d".formatted(clientId));
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return server.makeMove(clientId, row, col);
    }

    public static void main(String[] args) {
        try {
            String serverUrl = "//192.168.1.6/TicTacToeServer";
            TicTacToeServer ticTacToeServer = (TicTacToeServer) Naming.lookup(serverUrl);
            TicTacToeClientImpl client = new TicTacToeClientImpl();
            int localClientId = ticTacToeServer.registerClient(client);
            client.setClientId(localClientId);
            System.out.println("K Zarejestowales sie do gry. Jesteś graczem nr. %d".formatted(client.getClientId()));
            int myMove;
            while(true){
                myMove = ticTacToeServer.checkGameState(client.getClientId());
                if(myMove == 0){
                    int result = client.tryMakeMove(ticTacToeServer);
                    if(result == -2) { System.out.println("Niewlasciwy ruch. Podaj ruch jeszcze raz."); }
                    if(result == 1) { System.out.println("Wygrałeś."); break; }
                    if(result == 2) { System.out.println("Remis."); break; }
                }
                else if (myMove == 3) {
                    break;
                }
                else{
                    TimeUnit.SECONDS.sleep(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
