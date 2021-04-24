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
                if (messageFromServer.equals("This username is already taken")) {
                    continue;
                }
                else {
                    break;
                }
            }

//            Display display = new Display();
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

            Request request = null;
            boolean shouldContinue = true;
            do {
                String instruction = getInput(name + "> What must I do next?").strip().toLowerCase();
                if (instruction.equals("off")) {
                    shouldContinue = false;
                }
                try {
                    request = request.create(instruction, name);
                    out.println(request.getRequest());
                } catch (IllegalArgumentException e) {
                }


            } while (shouldContinue);
            System.out.println("Hello Kiddo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getInput(String prompt) {
        System.out.print(prompt+"\n");
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }

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


}
