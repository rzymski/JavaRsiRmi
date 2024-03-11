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
    String filePath = "D:\\programowanie\\java\\rsi\\JavaRsiRmi\\ps1\\src\\main\\bazadanych.txt";
    protected MyServerImpl() throws RemoteException {
        super();
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

    private List<Person> loadDataFromFile(String filePath) {
        List<Person> people = new ArrayList<>();
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
            return people;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Person getPersonByIndex(int index) throws RemoteException {
        List<Person> people = loadDataFromFile(filePath);
        //for (Person person : people) { System.out.println(person); }
        for (Person person : people) {
            if (person.getIndex() == index) {
                System.out.println("Serwer znalazł w bazie danych osobę o indexie %d %s".formatted(index, person));
                return person;
            }
        }
        System.out.println("Serwer nie znalazł w bazie danych osoby o indexie %d".formatted(index));
        return null;
    }

    @Override
    public List<Person> getAllPeople() throws RemoteException {
        List<Person> people = loadDataFromFile(filePath);
        System.out.println("Serwer zwrócił listę %d osób.".formatted(people.size()));
        for(Person person : people) {
            System.out.println(person);
        }
        return people;
    }
}