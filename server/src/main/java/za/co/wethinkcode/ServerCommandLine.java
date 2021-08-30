package za.co.wethinkcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerCommandLine implements Runnable {
    private final static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Thread> serverThreads = new ArrayList<>();
    public static HashMap<String, String> robotStates = new HashMap<>();
    public static HashMap<String, Integer> robotThreadIndexes = new HashMap<>();

    /**
     * An empty default constructor?
     * */
    public ServerCommandLine() {

    }

    /**
     * An override for the run function, it creates a command from user input <br/>
     * and checks if it is run, and subsequently runs the command on server depending on <br/>
     * which command was given.
     * */
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
            else if (command.equalsIgnoreCase("dump")) {
                dumpCommand();
            }
        }
    }

    /**
     * Used to get the input from the user, and return it.
     * */
    public static String getInput() {
        String input = scanner.nextLine();

        while (input.isBlank()) {
            input = scanner.nextLine();
        }
        return input;
    }

    /**
     * for each robot on the server it returns the state of that robot, and their name.
     * */
    public void robotsCommand() {
        for(int i = 0; i < Server.userNames.size(); i++){
            String botName = Server.userNames.get(i);
            if (botName != null) {
                System.out.println(botName + ":\n" + robotStates.get(botName) + "\n");
            }
        }
    }

    /**
     * Ends the server
     * */
    public void quitCommand() {
        for (Thread t : serverThreads) {
            t.interrupt();
        }
        Server.endServer();
    }

    /**
     * Removes a robots from the server, looks for the certain robot name and removes it.
     * */
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

    /**
     * Dumps all the world obstructions (being the pits and the obstacles).
     * */
    public void dumpCommand() {
        robotsCommand();
        System.out.println("Obstacles: ");
        for(Obstacle obs : World.getObstacleList()) {
            System.out.println(obs);
        }
        System.out.println("\nPits: ");
        for(Pit p : World.getPitList()) {
            System.out.println(p);
        }
    }

    /**
     * returns the state of a robot, takes the robot as an argument and returns <br/>
     * the state, position, direction, as well as how many shields, shots and status of it.
     * */
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
