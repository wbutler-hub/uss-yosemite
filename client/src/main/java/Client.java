import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Client {
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;
    private static Scanner scanner = new Scanner(System.in);
    private static String name;
    private static Display display;
    private static boolean killed = false;

    private static ArrayList<String> types = new ArrayList<String>
            (List.of("standard","sniper", "tank", "fighter"));


    public static void main(String args[]) throws ClassNotFoundException{
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintStream out = new PrintStream(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {

            String messageFromServer;
            while (true) {
                name = getInput("What do you want to name your robot?");
                out.println(name.toLowerCase());
                messageFromServer = in.readLine();
                System.out.println(messageFromServer);
                if (messageFromServer.equals("Too many of you in this world")) {
                    continue;
                }
                else {
                    break;
                }
            }

            display = new Display();
//            int[] array = new int[]{50,50};
//            display.drawPlayer(array);
////            display.resetPlayer();
//            array = new int[]{20,20};
//            display.drawPlayer(array);
            System.out.println("Robot Classes Available: \n" +
                    "Standard \n" +
                    "Fighter \n" +
                    "Tank \n" +
                    "Sniper");
            while (true) {
                String type = getInput("Pick your robot class:");
                type = type.toLowerCase().trim();
                if (!types.contains(type)) {
                    System.out.println("Invalid Robot Class!");
                    continue;
                }
                else {
                    out.println(launchRequest(type).getRequest());
                    break;
                }
            }

            System.out.println("Hello Kiddo!");
            Request request = null;
            boolean shouldContinue = true;
            do {
                String instruction = getInput(name + "> What must I do next?").strip().toLowerCase();
                if (instruction.equals("off")) {
                    shouldContinue = false;
                }
                try {

                    request = request.create(instruction, name);
                    if (!request.equals("off")) {
                        out.println(request.getRequest());
                        messageFromServer = in.readLine();
                    }

                    if(messageFromServer == null) {
                        shouldContinue = false;
                        killed = true;
                    }
                    else {
                        JSONObject jsonObject = new JSONObject(messageFromServer);
                        showResponse(jsonObject);
                    }
                } catch (IllegalArgumentException e) {
                    if (!request.equals("off")) {
                        System.out.println("Invalid Command or Arguments");
                    }
                }

            } while (shouldContinue);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(killed) {
            System.out.println("You have died (✖╭╮✖)");
        }
        else {
            System.out.println("GoodBye!");
        }
        System.exit(0);
    }

    /**
     * Gets the input from the user
     * @param prompt
     * @return
     */
    public static String getInput(String prompt) {
        System.out.print(prompt+"\n");
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * Checks what robot type the player chooses and adjusts the 'maxShieldStrength' and 'maxShots'
     according to the robot type
     * @param type
     * @return launch
     */
    private static LaunchRequest launchRequest(String type) {
        LaunchRequest launch = new LaunchRequest(name,"standard",
                3,3);
        if (type.equals("sniper")) {
            launch = new LaunchRequest(name,"sniper",
                    1,1);
        }
        else if (type.equals("standard")) {
            launch = new LaunchRequest(name,"standard",
                    3,3);
        }
        else if (type.equals("tank")) {
            launch = new LaunchRequest(name,"tank",
                    5,1);
        }
        else if (type.equals("fighter")) {
            launch = new LaunchRequest(name,"fighter",
                    2,5);
        }
        return launch;
    }

    /**
     * Gets the state, data, result, message, objects, shield, position, shots, direction and status from the server
     * Prints those out in the client
     * @param jsonObject
     */
    private static void showResponse(JSONObject jsonObject) {
        JSONObject state = (JSONObject) jsonObject.get("state");
        JSONObject data = (JSONObject) jsonObject.get("data");
        System.out.println("**********************************"+"\n");
        System.out.println("Result: "+jsonObject.get("result"));
        System.out.println("Data: ");
        System.out.println("Message: "+data.get("message"));
        if (data.has("objects")) {
            System.out.println("Objects: "+data.get("objects"));
        }
        System.out.println("State: ");
        System.out.println("Shield: "+state.get("shield"));
        System.out.println("Position: "+state.get("position"));
        display.drawPlayer((JSONArray) state.get("position"));
        System.out.println("Shots: "+state.get("shots"));
        System.out.println("Direction: "+state.get("direction"));
        if (data.has("objects")) {
            display.drawObstruction((JSONArray) data.get("objects"));
        }
        System.out.println("Status: "+state.get("status"));
        System.out.println("\n"+"**********************************");

    }

}
