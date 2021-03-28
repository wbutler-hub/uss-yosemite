import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

public class Server {
    public final static ArrayList<String> userNames = new ArrayList<>();
    private int width;
    private int height;
    private int visibility;

    public Server() throws IOException{
        getConfig();
    }

    private void getConfig() throws IOException {
        Properties prop = new Properties();
        URL configPath = this.getClass().getClassLoader().getResource("config.properties");
        if ((configPath != null ? configPath.getPath() : null) != null) {
            InputStream propertyStream = new FileInputStream(configPath.getPath());
            prop.load(propertyStream);
        } else {
            throw new FileNotFoundException("config.properties not found.");
        }
        this.width = Integer.parseInt(prop.getProperty("width"));
        this.height = Integer.parseInt(prop.getProperty("height"));
        this.visibility = Integer.parseInt(prop.getProperty("visibility"));
    }


    public static void main(String[] args) throws IOException {
        Server s = new Server();
        System.out.println(s.getHeight());

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVisibility() {
        return visibility;
    }
}
