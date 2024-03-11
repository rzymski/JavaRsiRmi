package laptopserver;

import laptopserver.MyServerInt;
import mainpcserver.Person;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

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
            String firstValue = "4.0";
            String secondValue = "1.9";
            String result = myRemoteObject.calculator(operation, firstValue, secondValue);
            System.out.println("Wysłano do servera: " + operation + " z " + firstValue + " " + secondValue);
            System.out.println("Otrzymana z serwera odpowiedź: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mainpcserver.MyServerInt myRemoteObject = (mainpcserver.MyServerInt) Naming.lookup(serverAddress);
            int index = 4;
            Person result = myRemoteObject.getPersonByIndex(index);
            System.out.println("Szukam osoby o indexie = " + index);
            System.out.println("Otrzymana z serwera odpowiedź: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mainpcserver.MyServerInt myRemoteObject = (mainpcserver.MyServerInt) Naming.lookup(serverAddress);
            int index = 1;
            List<Person> result = myRemoteObject.getAllPeople();
            System.out.println("Chce dostać liste osob.");
            System.out.println("Otrzymana z serwera odpowiedź: ");
            for (Person person : result) {
                System.out.println(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mainpcserver.MyServerInt myRemoteObject = (mainpcserver.MyServerInt) Naming.lookup(serverAddress);
            while(true) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Send message: ");
                String message = scanner.nextLine();;
                if(message.equals("0")) { break; }
                myRemoteObject.chat(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}