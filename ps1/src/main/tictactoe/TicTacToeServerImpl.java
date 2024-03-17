package tictactoe;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class TicTacToeServerImpl extends UnicastRemoteObject implements TicTacToeServer {
    private TicTacToeClient[] clients;
    private int numberOfPlayers = 0;
    private char[][] board;
    private int playerMove = 0;

    protected TicTacToeServerImpl() throws RemoteException {
        clients = new TicTacToeClient[2];
        board = new char[3][3];
        for(int i=0; i<3; i++){
            for(int j=-0; j<3; j++){
                board[i][j] = ' ';
            }
        }
    }

    @Override
    public int registerClient(TicTacToeClient client) throws RemoteException {
        if (numberOfPlayers < 2){
            clients[numberOfPlayers] = client;
            numberOfPlayers++;
            System.out.println("Zarejestrowano gracza nr. %d".formatted(numberOfPlayers-1));
        }
        return numberOfPlayers-1;
    }

    @Override
    public void notifyPlayers(String message) throws RemoteException {
        for (int i = 0; i < numberOfPlayers; i++) {
            clients[i].receiveMessage(message);
            System.out.println("Wysłano wiadomosc: " + message);
        }
    }

    @Override
    public int checkGameState(int clientId) throws RemoteException {
        String message = "";
        if (numberOfPlayers < 2){
            message = "Gra się jeszcze nie rozpoczęła. Oczekiwanie na dołączenie drugiego gracza.";
            notifyPlayers(message);
            return 2;
        }
        return playerMove == clientId ? 0 : 1;
    }

    private boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // -2 bledny ruch
    // -1 to nie twoj ruch
    // 0 ok
    // 1 koniec gry wygrana
    // 2 koniec gry remis
    @Override
    public int makeMove(int clientId, int row, int col) throws RemoteException {
        if(clientId != playerMove){ return -1; }

        char symbol = playerMove == 0 ? 'X' : 'O';
        if (isValidMove(row, col)) {
            board[row][col] = symbol;
            notifyPlayers(getBoard());
            playerMove = (playerMove+1) % 2;
            System.out.println("Ruch gracza nr. %d".formatted(playerMove));

            if(checkWin(symbol)) {
                notifyPlayers("Koniec gry. Wygrał gracz nr. %d".formatted(clientId));
                return 1;
            } else {
                if(isBoardFull()){
                    notifyPlayers("Koniec gry. Remis.");
                    return 2;
                }
            }
            return 0;
        } else {
            return -2;
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    public String getBoard() throws RemoteException {
        String boardDraw = "\n   |   |   \n %c | %c | %c \n   |   |   \n---+---+---\n   |   |   \n %c | %c | %c \n   |   |   \n---+---+---\n   |   |   \n %c | %c | %c ".formatted(board[0][0], board[0][1], board[0][2],board[1][0],board[1][1],board[1][2], board[2][0], board[2][1], board[2][2]);
        return boardDraw;
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
