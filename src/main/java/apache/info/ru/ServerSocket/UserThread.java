package apache.info.ru.ServerSocket;

import java.io.*;
import java.net.Socket;

/* UserThread отвечает за чтение сообщений, отправленных клиентом и рассылку
   сообщений всем остальным клиентам. Сначала он отправляет список онлайн-
   пользователей новому пользователю. Затем он читает имя пользователя и увем-
   домляет других пользователей о новом пользователе.
 */

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    Thread ut = new Thread(() -> {
        try{
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            writer =
                    new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream()), true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            String serverMessage = "New user connected: " + userName;
            server.sendMessage(serverMessage, this);

            String clientMessage;

            do{
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.sendMessage(serverMessage, this);
            } while (!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();

            serverMessage = userName + " has quitted";
            server.sendMessage(serverMessage, this);

        } catch (IOException e) {
            System.out.println("Error in UserThread: " + e.getMessage());
            e.printStackTrace();
        }
    });

    // Отправляет список онлайн-пользователей вновь подключенному пользователю
    void printUsers(){
        if (server.hasUsers()){
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    // Отправляет сообщение клиенту
    void sendMessage(String message){
        writer.println(message);
    }
}
