import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerCommandLine implements Runnable {
    private final static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Thread> serverThreads = new ArrayList<>();
    public static HashMap<String, String> robotStates = new HashMap<>();
    public static HashMap<String, Integer> robotThreadIndexes = new HashMap<>();

    public ServerCommandLine() {

    }

    @Override
    public void run() {
        String command;
        while(true) {
            command = getInput();
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
                System.out.println(botName + ":\n" + robotStates.get(botName) + "\n");
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
            if (botName.equalsIgnoreCase(name)) {
                serverThreads.get(robotThreadIndexes.get(name)).interrupt();
                robotStates.remove(name);
                robotThreadIndexes.remove(name);
                Server.userNames.remove(name);
                System.out.println("Purged " + name + ".");
            }
        }
    }

    public static String getState(Robot robot) {
        return "state: {\n" +
                "position: [" + robot.getPosition().getX() + "," + robot.getPosition().getY() + "]\n" +
                "direction: " + robot.getCurrentDirection() + "\n" +
                "shields: " + robot.getShield() + "\n" +
                "shots: " + robot.getShots() + "\n" +
                "status: " + robot.getStatus() + "\n"+
                "}";
    }
}
