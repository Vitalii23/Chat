package apache.info.ru.ServerSocket;

import apache.info.ru.DataBase.JDBC;
import apache.info.ru.Worker.Worker;

public class Server {


    public void runServer(String port) {
        Worker workerServer = new Worker(port);
        System.out.println("Started ServerSocket with " + " on " + port + "\n");
        while (true) {
            Worker worker = new Worker(workerServer);
            JDBC jdbc = new JDBC();
            System.out.println("ClientSocket accepted");
            new Thread(() -> {
                String line = worker.readLine();
                System.out.println("Accepted: " + line + "\n");
                worker.writeLine(line);
                jdbc.insert(line);
                worker.close();
            }).start();
        }
    }
}
