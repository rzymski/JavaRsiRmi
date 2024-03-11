package crosscircle;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class CrossCircleClientImpl extends UnicastRemoteObject implements CrossCircleClient {
    private final String clientId;

    private final CrossCircleServer CrossCircleServer;
    private char playerSymbol;

    protected CrossCircleClientImpl(String clientId, CrossCircleServer CrossCircleServer) throws RemoteException {
        this.clientId = clientId;
        this.CrossCircleServer = CrossCircleServer;
    }

    @Override
    public void notifyMove(char[][] board, char currentPlayerSymbol) throws RemoteException {
        playerSymbol = (currentPlayerSymbol == 'X') ? 'O' : 'X';
        displayBoard(board);

        if (checkWin(board, playerSymbol)) {
            System.out.println("Congratulations! You won!");
            System.exit(0);
        } else if (isBoardFull(board)) {
            System.out.println("It's a draw! The game ends in a tie.");
            System.exit(0);
        } else {
            System.out.println("It's your turn. Enter row (0-2) and column (0-2) separated by space:");
            Scanner scanner = new Scanner(System.in);
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            CrossCircleServer.makeMove(row, col, playerSymbol);
        }
    }

    // @Override
    // public void notifyMove(char[][] board) throws RemoteException {
    //     displayBoard(board);
    //     if (checkWin(board, playerSymbol)) {
    //         System.out.println("Congratulations! You won!");
    //         System.exit(0);
    //     } else if (isBoardFull(board)) {
    //         System.out.println("It's a draw! The game ends in a tie.");
    //         System.exit(0);
    //     } else {
    //         System.out.println("It's your turn. Enter row (0-2) and column (0-2) separated by space:");
    //         Scanner scanner = new Scanner(System.in);
    //         int row = scanner.nextInt();
    //         int col = scanner.nextInt();
    //         CrossCircleServer.makeMove(row, col, playerSymbol);
    //     }
    // }

    private void displayBoard(char[][] board) {
        System.out.println("Current board state:");
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private boolean checkWin(char[][] board, char symbol) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        // Check diagonals
        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
               (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    // @Override
    // public void notifyMove(char[][] board) throws RemoteException {
    //     displayBoard(board);
    //     System.out.println("It's your turn. Enter row (0-2) and column (0-2) separated by space:");
    //     Scanner scanner = new Scanner(System.in);
    //     int row = scanner.nextInt();
    //     int col = scanner.nextInt();
    //     CrossCircleServer.makeMove(row, col, 'X');
    // }

    // private void displayBoard(char[][] board) {
    //     System.out.println("Current board state:");
    //     for (char[] row : board) {
    //         for (char cell : row) {
    //             System.out.print(cell + " ");
    //         }
    //         System.out.println();
    //     }
    // }

    @Override
    public String getClientId() throws RemoteException {
        return clientId;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            System.out.println("Welcome to the chat, " + name + "! Enter 'exit' to leave.");

            String serverUrl = "//192.168.43.20/CrossCircleServer";
            CrossCircleServer crossCircleServer = (CrossCircleServer) Naming.lookup(serverUrl);
            CrossCircleClientImpl client = new CrossCircleClientImpl(name, crossCircleServer);
            crossCircleServer.registerClient(client);
            System.out.println("Waiting for the opponent...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

