package apache.info.ru.ClientSocket;

import apache.info.ru.Worker.Worker;

import java.util.Scanner;

public class Client {
    public void runClient(String ip, String port) {
        Worker worker = new Worker(ip, port);
        Scanner string = new Scanner(System.in);
        System.out.println("Message: ");
        String line = string.nextLine();
        worker.writeLine(line);
        worker.close();
    }
}
