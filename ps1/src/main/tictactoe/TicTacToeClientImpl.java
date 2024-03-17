package tictactoe;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(server.getBoard());
                System.out.println("Twój ruch. Podaj wiersz (0-2) i kolumnę (0-2) oddzielone spacją:");
                server.sendMessageToPlayer((clientId + 1) % 2, "Oczekiwanie na ruch gracza nr. " + clientId);
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                return server.makeMove(clientId, row, col);
            } catch (InputMismatchException e) {
                System.out.println("Niewłaściwy format danych. Podaj ruch jeszcze raz.");
                scanner.nextLine(); // Czyści bufor wejściowy
            }
        }
    }

    public static void main(String[] args) {
        try {
            String serverUrl = "//192.168.1.6/TicTacToeServer";
            TicTacToeServer ticTacToeServer = (TicTacToeServer) Naming.lookup(serverUrl);
            TicTacToeClientImpl client = new TicTacToeClientImpl();
            client.setClientId(ticTacToeServer.registerClient(client));
            System.out.println("Zarejestowales sie do gry. Jesteś graczem nr. %d".formatted(client.getClientId()));
            int myMove;
            while(true){
                myMove = ticTacToeServer.checkGameState(client.getClientId());
                if(myMove == 0){
                    int result = client.tryMakeMove(ticTacToeServer);
                    if(result == -1) { System.out.println("Niewlasciwy ruch. Podaj ruch jeszcze raz."); }
                }
                else if((myMove == 3 && client.getClientId() == 0) || (myMove== 4 && client.getClientId() == 1)) { System.out.println("Wygrałeś."); break; }
                else if((myMove == 4 && client.getClientId() == 0) || (myMove== 3 && client.getClientId() == 1)) { System.out.println("Przegrałeś."); break; }
                else if(myMove== 5) { System.out.println("Remis."); break; }
                else{
                    TimeUnit.SECONDS.sleep(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
