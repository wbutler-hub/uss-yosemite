import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerSetup implements Runnable{
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private JSONObject JsonData;

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
            Robot robot = new SniperRobot("kay");
            Command command;
            String messageFromClient;
            String jsonString; //String that was converted from a string to a JsonObject
            boolean requestUsed;  //Boolean used to determined if a request is being sent or if a name is being used
            while((messageFromClient = in.readLine()) != null) {
                requestUsed = messageFromClient.contains("{");
                if (requestUsed) {
                    JsonData = new JSONObject(messageFromClient);
                    jsonString = getCommand().concat(" ");
                    for (int i = 0; i < getArgument().length(); i++) {
                        jsonString = jsonString.concat(getArgument().get(i).toString());
                    }
                    jsonString = jsonString.trim();
                    command = Command.create(jsonString);
                    boolean shouldContinue = robot.handleCommand(command);
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

    public String getCommand(){return (String) this.JsonData.get("command");}

    public JSONArray getArgument(){return (JSONArray) this.JsonData.get("arguments");}

}

