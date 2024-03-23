Zmień w MyServerMain adres ip na swój własny: 
- System.setProperty("java.rmi.server.codebase", "http://192.168.1.6/mainpc/");
- Naming.rebind("//192.168.1.6/mainpc", obj1);

Zmień w MyClientMain.java adres ip na swój własny:
- String serverAddress = "//192.168.1.13/laptop";

Analogicznie trzeba zmienić adredy ip:
- dla kółka i krzyżyk w: TicTacToeServerImpl.java i TicTacToeClientImpl.java;
- dla chatu w: ChatClientImpl.java i ChatServerImpl.java;

Przy korzystaniu z 'bazy danych' potrzebne jest ustawienie prawidłowej ścieżki do niej:
- w MyServerImpl 15 linijka: String filePath = "D:\\programowanie\\java\\rsi\\JavaRsiRmi\\src\\main\\database\\bazadanych.txt";

W cmd:
- ipConfig żeby sprawdzić lokalny adres ip dla serwera.
- netstat -an | find "1099" żeby sprawdzić czy register serwera pracuje