package apache.info.ru.ClientSocket;

import apache.info.ru.Worker.Worker;

import java.util.Scanner;

public class Client {
    public void runClient(String ip, String port) {
        Worker worker = new Worker(ip, port);
        Scanner string = new Scanner(System.in);
        System.out.println("Message: ");
        String line = string.nextLine();
        if (line.equals("-r")){
            String comand = string.nextLine();
            StringBuilder buffer = new StringBuilder(comand);
            buffer.reverse();
            System.out.println(buffer.toString());
            worker.writeLine(buffer.toString());
            worker.close();
        }
        worker.writeLine(line);
        worker.close();
    }
}
