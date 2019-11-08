package apache.info.ru.ServerSocket;

import java.io.*;
import java.net.Socket;
import java.sql.*;

/* UserThread отвечает за чтение сообщений, отправленных клиентом и рассылку
   сообщений всем остальным клиентам. Сначала он отправляет список онлайн-
   пользователей новому пользователю. Затем он читает имя пользователя и увем-
   домляет других пользователей о новом пользователе.
 */

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    private static final String url = "jdbc:mysql://localhost:3306/archive_message?serverTimezone=EST5EDT";
    private static final String user = "root";
    private static final String password = "root";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private void insert (String userName, String text){
        String query = "INSERT INTO archive (user_name,text)" + "VALUES(?,?)";
        try {
            connection = DriverManager.getConnection(url, user, password);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, text);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        } finally {
            try { connection.close(); } catch (SQLException se) { se.getMessage(); }
            try { preparedStatement.close(); } catch (SQLException se) { se.getMessage(); }
        }
    }


    private void select () {
        //String query = "SELECT id, user_name, text FROM archive";
        String query = "SELECT id, user_name, text FROM " +
                "(SELECT id, user_name, text FROM archive" +
                " ORDER BY id DESC LIMIT 20) sub" +
                " ORDER BY id ASC";
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String text = resultSet.getString(3);
                writer.println("[" + userName + "]" + " " + text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { connection.close(); } catch (SQLException se) { se.getMessage(); }
            try { statement.close(); } catch (SQLException se) { se.getMessage(); }
            try { resultSet.close(); } catch (SQLException se) { se.getMessage(); }
        }
    }

    Thread utt = new Thread(() -> {
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

            sendAllMessage();

            String clientMessage;

            do{
                clientMessage = reader.readLine();
                insert(userName, clientMessage);
                serverMessage = "[" + userName + "]: " + clientMessage;
                System.out.println(serverMessage);
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

    void sendAllMessage(){
        if(server.hasUsers()){
            select();
        } else {
            writer.println("Error Data Base select");
        }
    }

    // Отправляет сообщение клиенту
    void sendMessage(String message){
        writer.println(message);
    }
}
