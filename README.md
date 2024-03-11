Zmień w MyServerMain adres ip na swój własny: 
- System.setProperty("java.rmi.server.codebase", "http://192.168.1.6/mainpc/");
- Naming.rebind("//192.168.1.6/mainpc", obj1);

Zmień w MyClientMain.java adres ip na swój własny:
- String serverAddress = "//192.168.1.13/laptop";

W cmd:
- ipConfig żeby sprawdzić lokalny adres ip dla serwera.
- netstat -an | find "1099" żeby sprawdzić czy register serwera pracuje
