package laptopserver;

import laptopserver.MyServerInt;
import java.rmi.Naming;

public class MyClientMain {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "security.policy");
        //System.setSecurityManager(new SecurityManager());
        String serverAddress = "//192.168.1.13/laptop";
//        try {
//            MyServerInt myRemoteObject = (MyServerInt) Naming.lookup(serverAddress);
//            String text = "Tajna wiadomosc";
//            String result = myRemoteObject.getDescription(text);
//            System.out.println("Wysłano do servera: " + text);
//            System.out.println("Otrzymana z serwera odpowiedź: " + result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            MyServerInt myRemoteObject = (MyServerInt) Naming.lookup(serverAddress);
            String operation = "-";
            String firstValue = "7.0";
            String secondValue = "1.9";
            String result = myRemoteObject.calculator(operation, firstValue, secondValue);
            System.out.println("Wysłano do servera: " + operation + " z " + firstValue + " " + secondValue);
            System.out.println("Otrzymana z serwera odpowiedź: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}