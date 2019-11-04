package apache.info.ru.Main;

import apache.info.ru.ClientSocket.Client;
import apache.info.ru.ServerSocket.Server;

import java.util.Scanner;

public class Socketor {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage:\r\n" +
                    "java apache.info.ru.Main.Socketor server\r\n" +
                    "java apache.info.ru.Main.Socketor client");
            return;
        }
        Scanner line = new Scanner(System.in);
        if (args[0].equals("server")) {
            System.out.println("Enter port-number: ");
            String port = line.nextLine();
            Server server = new Server(Integer.parseInt(port));
            server.execute();
        }
        if (args[0].equals("client")) {
            System.out.println("Enter hostname: ");
            String hostname = line.nextLine();
            System.out.println("Enter port-number: ");
            String port = line.nextLine();
            Client client = new Client(hostname, Integer.parseInt(port));
            client.execute();
        }
    }
}
