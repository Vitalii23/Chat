package apache.info.ru.ServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class Server {
    private final int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();

    public Server(int port){
        this.port = port;
    }

    public void execute(){
        try(ServerSocket server = new ServerSocket(port)){
            System.out.println("Started Server with " + " on " + port);
            while(true){
                Socket socket = server.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.ut.start();
            }
        } catch (IOException e) {
            System.out.println("Error server" + e.getMessage());
            e.printStackTrace();
        }
    }

    // Доставляет сообщение от одного пользователя к другим
    void sendMessage(String message, UserThread outUser){
        for(UserThread aUser : userThreads){
            if (aUser != outUser){
                aUser.sendMessage(message);
            }
        }
    }

    // Хранит имя пользователя подключенного клиента
    void addUserName(String userName){
        userNames.add(userName);
    }

    // Когда клиент отключен, удаляет имя пользователя и пользовательский поток
    void removeUser(String userName, UserThread aUser){
        boolean removed = userNames.remove(userName);
        if (removed){
            userThreads.remove(aUser);
            System.out.println("The user " + userThreads + " quitted");
        }
    }

    Set<String> getUserNames(){
        return this.userNames;
    }

    // Возвращает true, если подключены другие пользователи(не считая текущего подключенного пользователя)
    boolean hasUsers(){
        return !this.userNames.isEmpty();
    }
}
