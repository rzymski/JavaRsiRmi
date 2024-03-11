package chat;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private final String clientId;

    protected ChatClientImpl(String clientId) throws RemoteException {
        this.clientId = clientId;
    }

    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        System.out.println("["+sender+"] " + message);
        //System.out.println("["+sender+"] " + message);
    }

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

            String serverUrl = "//192.168.1.6/ChatServer";
            ChatServer chatServer = (ChatServer) Naming.lookup(serverUrl);
            ChatClientImpl client = new ChatClientImpl(name);
            chatServer.registerClient(client);

            while (true) {
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Leaving the chat...");
                    break;
                }
                chatServer.broadcastMessage(name, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

