
import java.io.*;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
//    HashMap<String, Robot> robots = new HashMap<String, Robot>();
    public static ArrayList<String> userStatuses = new ArrayList<>();
    public final static ArrayList<String> userNames = new ArrayList<>();

    private static World world;



    public static DataOutputStream Output;

    public static void main(String[] args) throws IOException {
        world = new World();

        Runnable cliRun = new ServerCommandLine();
        Thread cliThread = new Thread(cliRun);
        cliThread.start();

        ServerSocket serverSocket = new ServerSocket( ServerSetup.PORT);
        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);

                Output = new DataOutputStream(socket.getOutputStream());

                Runnable r = new ServerSetup(socket);
                Thread task = new Thread(r);

                ServerCommandLine.serverSetups.add(r);
                ServerCommandLine.serverThreads.add(task);

                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }



    public static World getWorld() {
        return world;
    }

    public static void endServer() {
        System.exit(0);
    }
}
