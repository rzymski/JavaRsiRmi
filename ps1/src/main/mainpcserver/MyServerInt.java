package mainpcserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyServerInt extends Remote{
    String getDescription(String text) throws RemoteException;

    String calculator(String operation, String firstValue, String secondValue) throws RemoteException;


    Person getPersonByIndex(int index) throws RemoteException;
}
