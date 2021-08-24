
import java.io.*;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public final static ArrayList<String> userNames = new ArrayList<>();

    private static World world;

    public static DataOutputStream Output;

    /**
     * The main place to run the server, sets up the world, the and waits for threads. <br/>
     * while the server is up, it will wait for clients to connect to the server, and run continuously <br/>
     * It will send the commands through to server.
     * */
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

                ServerCommandLine.serverThreads.add(task);

                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * @return the world.
     * */
    public static World getWorld() {
        return world;
    }

    /**
     * force quits the server from running.
     * */
    public static void endServer() {
        System.exit(0);
    }
}
