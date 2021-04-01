import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_HOST= "localhost";
    private static final int SERVER_PORT= 5000;
    private static Scanner scanner = new Scanner(System.in);
    private static String name;


    public static void main(String args[]) throws ClassNotFoundException{
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintStream out = new PrintStream(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
        )
        {
//            Play.start();
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

//                dumpResponse(response);

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


}
