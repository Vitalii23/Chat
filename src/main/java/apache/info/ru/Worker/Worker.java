package apache.info.ru.Worker;

import apache.info.ru.DataBase.JDBC;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker {
    private ServerSocket server;
    private Socket client;
    private BufferedReader reader;
    private BufferedWriter writer;

    JDBC jdbc = new JDBC();

    public Worker(String port){ // server
        try{
            server = new ServerSocket(Integer.parseInt(port));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public Worker(Worker workerServer){
        server = null;
        client = workerServer.accept();
        createStreams();
    }

    public Worker(String ip, String port){ // client
        try{
            client = new Socket(ip, Integer.parseInt(port));
            jdbc.select();
            createStreams();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private Socket accept() {
        try{
            return server.accept();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void createStreams() {
        try{
            reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    client.getInputStream()));
            writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    client.getOutputStream()));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public String readLine(){
        try{
            return  reader.readLine();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public void writeLine(String message){
        try{
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    public void close(){
        try {
            reader.close();
            writer.close();
            client.close();
        } catch (IOException e){
            throw  new RuntimeException(e);
        }
    }


    public void closeServer(){
        try {
            server.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
