import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSetup implements Runnable{
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;

    public ServerSetup(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");
    }

    public void run() {
        try {
            String messageFromClient;
            boolean requestUsed;  //Boolean used to determined if a request is being sent or if a name is being used
            while((messageFromClient = in.readLine()) != null) {
                requestUsed = messageFromClient.contains("{");
                if (requestUsed) {
                    System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                    out.println("Thanks for this message: " + messageFromClient);
                }
                else {
                    if(Server.userNames.contains(messageFromClient)) {
                        out.println("This username is already taken");
                    }
                    else {
                        Server.userNames.add(messageFromClient);
                        out.println("Username accepted!");
                    }
                }
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }


}

