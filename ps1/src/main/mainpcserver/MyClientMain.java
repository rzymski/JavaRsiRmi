package mainpcserver;

import java.rmi.Naming;
import mainpcserver.MyServerInt;

public class MyClientMain {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "security.policy");
        //System.setSecurityManager(new SecurityManager());
        String serverAddress = "//192.168.1.6/mainpc";
        // try {
        //     MyServerInt myRemoteObject = (MyServerInt) Naming.lookup(serverAddress);
        //     String text = "Tajna wiadomosc";
        //     String result = myRemoteObject.getDescription(text);
        //     System.out.println("Wysłano do servera: " + text);
        //     System.out.println("Otrzymana z serwera odpowiedź: " + result);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        try {
            MyServerInt myRemoteObject = (MyServerInt) Naming.lookup(serverAddress);
            String operation = "/";
            String firstValue = "32.50";
            String secondValue = "10.1";
            String result = myRemoteObject.calculator(operation, firstValue, secondValue);
            System.out.println("Wysłano do servera: " + firstValue + " " + operation + " " + secondValue);
            System.out.println("Otrzymana z serwera odpowiedź: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}