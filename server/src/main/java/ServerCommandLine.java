import java.util.ArrayList;
import java.util.Scanner;

public class ServerCommandLine implements Runnable {
    private final static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Runnable> serverSetups = new ArrayList<>();
    public static ArrayList<Thread> serverThreads = new ArrayList<>();

    public ServerCommandLine() {

    }

    @Override
    public void run() {
        String command;
        while(true) {
            command = getInput();
            System.out.println("Received command: " + command);
            if (command.equalsIgnoreCase("robots")) {
                robotsCommand();
            }
            else if (command.equalsIgnoreCase("quit")) {
                quitCommand();
            }
            else if ((command.split(" "))[0].equalsIgnoreCase("purge")) {
                purgeCommand((command.split(" "))[1]);
            }
        }
    }

    public static String getInput() {
        String input = scanner.nextLine();

        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }

    public void robotsCommand() {
        for(int i = 0; i < Server.userNames.size(); i++){
            String botName = Server.userNames.get(i);
            if (botName != null) {
                System.out.println(botName + ":\n" + Server.userResponses.get(i).getStatus());
            }
        }
    }

    public void quitCommand() {
        for (Thread t : serverThreads) {
            t.interrupt();
        }
        Server.endServer();
    }

    public void purgeCommand(String name) {
        for (int i = 0; i < Server.userNames.size(); i++) {
            String botName = Server.userNames.get(i);
            if (botName != null && botName.equalsIgnoreCase(name)) {
                Server.userNames.set(i, null);
                serverThreads.get(i).interrupt();
            }
        }
    }
}
