package apache.info.ru.ClientSocket;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private PrintWriter writer;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try{
            writer =
                    new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream()), true);
        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

    Thread wtt = new Thread(() -> {
        Console console = System.console();
        String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do{
            System.out.println("Reverse mode: </reverse>\r\n" +
                    "Exit chat: </bye>");
            text = console.readLine("[" + userName + "]");
            if (text.equals("/reverse")){
                System.out.println("Mode reverse, please enter text: ");
                text = console.readLine();
                StringBuffer buffer = new StringBuffer(text);
                System.out.println(buffer.reverse());
                writer.println(buffer);
            } else {
                writer.println(text);
            }
        } while (!text.equals("/bye"));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }
    });
}
