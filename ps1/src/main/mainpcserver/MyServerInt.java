package mainpcserver;

import database.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface MyServerInt extends Remote{
    String getDescription(String text) throws RemoteException;

    String calculator(String operation, String firstValue, String secondValue) throws RemoteException;

    Person getPersonByIndex(int index) throws RemoteException;
    Person getPersonByName(String name) throws RemoteException;
    List<Person> getAllPeople() throws RemoteException;

    void chat(String message) throws RemoteException;
}
