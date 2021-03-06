package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerSetup implements Runnable{
    public static final int PORT = 5000;
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private JSONObject JsonData;
    private Response response;
    private final int index;
    private static ArrayList<String> validCommands = new ArrayList<String>
            (List.of("forward","back", "mine", "repair", "fire", "look", "reload", "turn"));

    /**
     * Used to set up the server, Takes the socket as a parameter. <br/>
     * Gets the clients hostname and prints that they are connected to the server <br/>
     * sets the PrintStream, and the Buffered reader, and waits for the clients to connect.<br/>
     * Then waits for the threads and/or server commands
     * */
    public ServerSetup(Socket socket) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");

        index = ServerCommandLine.serverThreads.size();

    }

    /**
     * Run the server; <br/>
     * It sets up the robot from standards, and generates a base response. <br/>
     * while the client is sending through responses, the thread is not interrupted and the robot is alive <br/>
     * the the robot will run commands to make it function, else the thread will close and the robot will removed <br/>
     * from the list of robots that are currently in the world. <br/>
     * */
    public void run() {
        try {
            Robot robot = new ShooterRobot("HAL");
            Command command;

            response = new Response(robot);

            int nameCounter = 0;
            Object responseString;
            String messageFromClient;
            String jsonString; //String that was converted from a string to a JsonObject
            boolean requestUsed;  //Boolean used to determined if a request is being sent or if a name is being used

            while((messageFromClient = in.readLine()) != null && !Thread.interrupted() && robot.isAlive()) {
                requestUsed = messageFromClient.contains("{");

                if (requestUsed) {
                    JsonData = new JSONObject(messageFromClient);
                    if (nameCounter == 0) {
                        if (Server.userNames.contains(getName())) {
                            out.println(Response.setResult("NameInUse", robot).toString());
                            continue;
                        } else if (Server.userNames.size() >= 2) {
                            out.println(Response.setResult("tooManyUsers", robot).toString());
                            continue;
                        } else {
                            nameCounter++;
                            Server.userNames.add(getName());
                            ServerCommandLine.robotStates.put(getName(), "");
                            ServerCommandLine.robotThreadIndexes.put(getName(), index);
                        }
                    }

                    jsonString = getCommand();

                    if (jsonString.equals("launch")) {
                        robot = Robot.create(getName(),getArgument().get(0).toString());
                        robot.addRobotPair();

                        out.println(Objects.requireNonNull(Response.setResult(jsonString, robot)).toString());
//                        continue;
                    } else if (validCommands.contains(jsonString)) {
                        jsonString = jsonString.concat(" ");
                        for (int i = 0; i < getArgument().length(); i++) {
                            jsonString = jsonString.concat(getArgument().get(i).toString());
                        }

                        jsonString = jsonString.trim();
                        command = Command.create(jsonString);
                        boolean shouldContinue = robot.handleCommand(command);

                        ServerCommandLine.robotStates.put(robot.getName(), ServerCommandLine.getState(robot));

                        System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);

                        out.println(Response.setResult(jsonString, robot).toString());
                    } else {
                        out.println(Response.setResult(JsonData.toString(), robot).toString());
                    }
                }
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            Server.userNames.remove(getName());
            closeQuietly();
        }
    }

    /**
     * Used to close the input BufferedReader and out being PrintStream from server.
     * */
    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }

    /**
     * used to get the command
     * @return the command in a string format (from JSON format)
     * */
    private String getCommand(){return (String) this.JsonData.get("command");}

    /**
     * used to get the argument
     * @return the argument in a string format (from JSON format)
     * */
    private JSONArray getArgument(){return (JSONArray) this.JsonData.get("arguments");}

    /**
     * used to get the name
     * @return the name in a string format (from JSON format)
     * */
    private String getName(){return (String) this.JsonData.get("robot");}


}

