import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public final static ArrayList<String> userNames = new ArrayList<>();
//    HashMap<String, Robot> robots = new HashMap<String, Robot>();

    private static World world;



    public static void main(String[] args) throws IOException {
        world = new World();


        ServerSocket serverSocket = new ServerSocket( ServerSetup.PORT);
        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);

                Runnable r = new ServerSetup(socket);
                Thread task = new Thread(r);
                task.start();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static World getWorld() {
        return world;
    }

}
