package za.co.wethinkcode;

public abstract class Command {
    public static boolean back;
    public static boolean forward;
    public static boolean mine;
    public static boolean repair;
    private final String name;
    private String argument;

    /**
     * Abstract command to execute commands; (will be overridden).
     * @param target: Takes the za.co.wethinkcode.server.robot.za.co.wethinkcode.Robot target as a parameter.
     * */
    public abstract boolean execute(Robot target);

    /**
     * Constructor for Commands (with 0 arguments).
     * */
    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * Constructor for Commands (with arguments).
     * */
    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * Getter to get name (unhighlighted - seems to not be in use)
     * */
    public String getName() {                                                                           //<2>
        return name;
    }

    /**
     * Getter to get the private argument variable.
     * */
    public String getArgument() {
        return this.argument;
    }

    /**
     * Create method, it is used as sort of sorting brain for the project, it will receives a command <br/>
     * and then depending on what command it recieves will run a different function.
     * @param instruction: the instructions (from the Json command turned into a string)
     * @return: depending on the command, it will return a call to a different function.
     * */
    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "forward":
                return new ForwardCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "turn":
                if (args[1].equals("right")) {
                    return new RightCommand();
                }
                if (args[1].equals("left")) {
                    return new LeftCommand();
                }
            case "repair":
                return new RepairCommand();
            case "reload":
                return new ReloadCommand();
            case "mine":
                return new MineCommand();
            case "fire":
                return new FireCommand();
            case "look":
                return new LookCommand();
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
