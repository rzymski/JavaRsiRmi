package laptopserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MyServerMain {
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new SecurityManager());
//            }

            //System.setProperty("java.rmi.server.codebase","file:\\D:\\programowanie\\java\\rsi\\ps1\\out\\production");
            System.setProperty("java.rmi.server.codebase", "http://82.139.1.13/laptop/");

            LocateRegistry.createRegistry(1099);

            System.out.println("Codebase: " + System.getProperty("java.rmi.server.codebase"));

            MyServerImpl obj1 = new MyServerImpl();
            Naming.rebind("//192.168.1.13/laptop", obj1);
            System.out.println("Serwer oczekuje ...");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
            System.out.println("No i nie dziala");
        }}
}