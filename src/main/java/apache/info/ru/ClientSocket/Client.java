package apache.info.ru.ClientSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private int port;
    private String userName;

    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void execute(){
        try{
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server");
            ReadThread readThread = new ReadThread(socket, this);
            readThread.rtt.start();
            WriteTread writeTread = new WriteTread(socket, this);
            writeTread.wtt.start();
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

}