package mainpcserver;

import java.io.BufferedReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;

public class MyServerImpl extends UnicastRemoteObject implements MyServerInt {
    int i = 0;
    private List<Person> people;
    protected MyServerImpl() throws RemoteException {
        super();
        loadDataFromFile("D:\\programowanie\\java\\rsi\\JavaRsiRmi\\ps1\\src\\main\\bazadanych.txt");
        //for (Person person : people) { System.out.println(person); }
    }
    @Override
    public String getDescription(String text) throws RemoteException {
        i++;
        System.out.println("MyServerImpl.getDescription: " + text + " " + i);
        return "getDescription: " + text + " " + i;
    }

    @Override
    public String calculator(String operation, String firstValue, String secondValue) throws RemoteException {
        try {
            double first = Double.parseDouble(firstValue);
            double second = Double.parseDouble(secondValue);
            if (Double.isInfinite(first) || Double.isInfinite(second) || Double.isNaN(first) || Double.isNaN(second)) {
                throw new ArithmeticException("Invalid input: Infinite or NaN values are not allowed.");
            }
            double result = switch (operation) {
                case "+" -> first + second;
                case "-" -> first - second;
                case "*" -> first * second;
                case "/" -> {
                    if (second == 0) {
                        throw new ArithmeticException("Division by zero is not allowed.");
                    }
                    yield first / second;
                }
                default -> throw new IllegalArgumentException("Unsupported operation: " + operation);
            };
            System.out.println("Kalkulator wykonuje operacje dla %.2f %s %.2f. Wynik: %.2f".formatted(first, operation, second, result));
            return String.valueOf(result);
        } catch (NumberFormatException e) {
            return "Invalid input: Please provide valid numeric values.";
        } catch (ArithmeticException e) {
            return "Arithmetic error: " + e.getMessage();
        } catch (Exception e) {
            return "Unexpected error: " + e.getMessage();
        }
    }

    private void loadDataFromFile(String filePath) {
        people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    int index = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String surname = parts[2];
                    int age = Integer.parseInt(parts[3]);
                    double salary = Double.parseDouble(parts[4].replace(',', '.'));
                    Person person = new Person(index, name, surname, age, salary);
                    people.add(person);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person getPersonByIndex(int index) throws RemoteException {
        for (Person person : people) {
            if (person.getIndex() == index) {
                return person;
            }
        }
        return null;
    }
}