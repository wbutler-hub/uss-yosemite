import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerSetup implements Runnable{
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private JSONObject JsonData;
    private Response response;
    private final int index;

    public ServerSetup(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");
        index = Server.userNames.size();
    }

    public void run() {
        try {
            Robot robot = new StandardRobot("Init");
            Command command;
            response = new Response(robot);
            robot.setIndex(index);
            Server.userStatuses.add(ServerCommandLine.getState(robot));

            String messageFromClient;
            String jsonString; //String that was converted from a string to a JsonObject
            boolean requestUsed;  //Boolean used to determined if a request is being sent or if a name is being used
            while((messageFromClient = in.readLine()) != null && !Thread.interrupted()) {
                requestUsed = messageFromClient.contains("{");
                if (requestUsed) {
                    JsonData = new JSONObject(messageFromClient);
                    jsonString = getCommand();
                    if (jsonString.equals("launch")) {
                        robot = Robot.create(getName(),getArgument().get(0).toString());
                        robot.addRobotPair();
                        continue;
                    }
                    jsonString = jsonString.concat(" ");
                    for (int i = 0; i < getArgument().length(); i++) {
                        jsonString = jsonString.concat(getArgument().get(i).toString());
                    }
                    jsonString = jsonString.trim();
                    command = Command.create(jsonString);
                    boolean shouldContinue = robot.handleCommand(command);

                    Server.userStatuses.set(index, ServerCommandLine.getState(robot));
                    System.out.println(Server.userNames.get(index) + ": " + robot.getStatus());

                    System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);
                    out.println("Thanks for this message: " + messageFromClient);

//                    response.setStatus();
//                    response.setResponse();
//                    response.setMovement(jsonString);
//                    System.out.println(response.getStatus());


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

    private String getCommand(){return (String) this.JsonData.get("command");}

    private JSONArray getArgument(){return (JSONArray) this.JsonData.get("arguments");}

    private String getName(){return (String) this.JsonData.get("name");}


}

