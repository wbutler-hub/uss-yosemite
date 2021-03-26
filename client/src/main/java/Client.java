import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;


    public static void main(String args[]) throws ClassNotFoundException{
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintStream out = new PrintStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
            Play.start();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
