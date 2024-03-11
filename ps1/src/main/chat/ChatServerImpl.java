package chat;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {

    private final List<ChatClient> clients;

    protected ChatServerImpl() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        clients.add(client);
    }

    @Override
    public void broadcastMessage(String sender, String message) throws RemoteException {
        for (ChatClient client : clients) {
//            System.out.println(client);
//            client.receiveMessage(message);

            if (!client.getClientId().equals(sender)) {
                client.receiveMessage(sender, message);
            }
            else {
                System.out.println("Twoja wiadomosc: " + message);
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
            System.setProperty("java.rmi.server.codebase", "http://192.168.1.6/chat/");

            LocateRegistry.createRegistry(1099);
            ChatServer chatServer = new ChatServerImpl();
            Naming.rebind("ChatServer", chatServer);
            System.out.println("ChatServer is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

