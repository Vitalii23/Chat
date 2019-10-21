package apache.info.ru.Main;
import apache.info.ru.ClientSocket.Client;
import apache.info.ru.ServerSocket.Server;

public class Socketor {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage:\r\n" +
                    "java apache.info.ru.Main.Socketor server 8000 \r\n" +
                    "java apache.info.ru.Main.Socketor client 127.0.0.1 8000");
            return;
        }
        Server server = new Server();
        Client client = new Client();
        if (args[0].equals("server")) {
            server.runServer(args[1]);
        }
        if (args[0].equals("client")) {
            client.runClient(args[1], args[2]);
        }
    }
}

