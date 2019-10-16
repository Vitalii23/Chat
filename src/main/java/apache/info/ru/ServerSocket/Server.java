package apache.info.ru.ServerSocket;

import apache.info.ru.DAO.ArchiveDAOImpl;
import apache.info.ru.Worker.Worker;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;

public class Server {
    public void runServer(String port) {
        Worker workerServer = new Worker(port);
       // JdbcChannelMessageStore jdbcChannelMessageStore = new JdbcChannelMessageStore();
        ArchiveDAOImpl dao = new ArchiveDAOImpl();
        System.out.println("Started ServerSocket with " + " on " + port + "\n");
        while (true) {
            Worker worker = new Worker(workerServer);
            System.out.println("ClientSocket accepted");
            new Thread(() -> {
                String line = worker.readLine();
                System.out.println("Accepted: " + line + "\n");
              // jdbcChannelMessageStore.addMessageToGroup(dao.saveMessageOrUpdate(line);)
                worker.writeLine(line);
                worker.close();
            }).start();
        }
    }
}
