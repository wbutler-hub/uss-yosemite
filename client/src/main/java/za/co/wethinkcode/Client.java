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
import java.util.Scanner;


public class Client {
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;
    private static Scanner scanner = new Scanner(System.in);
    private static String name;
    private static Display display;
    private static boolean killed = false;

    private static ArrayList<String> types = new ArrayList<String>
            (List.of("shooter","sniper", "tank", "fighter"));


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
                String type = getInput("Please Launch Your Robot: " +
                        "\u001B[32m" + "{name} " + "\u001B[0m" +
                        "\u001B[34m" + "{class} " + "\u001B[0m" +
                        "\u001B[36m" + "{shoots} " + "\u001B[0m" +
                        "\u001B[35m" + "{shield}" + "\u001B[0m \n" +
                        "Robot Classes Available: [Shooter, Fighter, Tank, Sniper]"); // {  "robot": "HAL",  "command": "launch",  "arguments": ["sniper","5","5"]}
                String[] launchedSequence = type.split(" ");

                name = launchedSequence[0];
                String robotType = launchedSequence[1];
                robotType = robotType.toLowerCase().trim();

                if (!types.contains(robotType)) {
                    System.out.println("Invalid Robot Class!");
                    continue;
                }
                else {
                    out.println(launchRequest(type).getRequest());
                }

                messageFromServer = in.readLine();

                System.out.println(messageFromServer);

                if (messageFromServer.contains("Too many of you in this world")) {
                    System.out.println("Too many of you in this world");
                    continue;
//                    out.println(launchRequest(type).getRequest());
                } else if (messageFromServer.contains("Too many users are currently connected, Please wait for an opening.")) {
                    System.out.println("\u001B[31m" + "Please wait for more available space" + "\u001B[0m");
                    continue;
                }
                break;
            }

            display = new Display();
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
     * Gets input from user
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
        LaunchRequest launch = new LaunchRequest(name,"shooter",
                3,3);
        if (type.contains("sniper")) {
            launch = new LaunchRequest(name,"sniper",
                    1,1);
        }
        else if (type.contains("shooter")) {
            launch = new LaunchRequest(name,"shooter",
                    3,3);
        }
        else if (type.contains("tank")) {
            launch = new LaunchRequest(name,"tank",
                    5,1);
        }
        else if (type.contains("fighter")) {
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
