package mainpcserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServerImpl extends UnicastRemoteObject implements MyServerInt {
    int i = 0;
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
//    @Override
//    public String calculator(String operation, String firstValue, String secondValue) throws RemoteException {
//        double first = Double.parseDouble(firstValue);
//        double second = Double.parseDouble(secondValue);
//        System.out.println("Kalkulator wykonuje operacje " + operation + " dla " + first + " i " + second);
//        double result = switch (operation) {
//            case "+" -> first + second;
//            case "-" -> first - second;
//            case "*" -> first * second;
//            case "/" -> first / second;
//            default -> 0;
//        };
//        return String.valueOf(result);
//    }
}