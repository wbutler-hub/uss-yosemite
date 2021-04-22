import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public static boolean back;
    public static boolean forward;
    public static boolean mine;
    public static boolean repair;
    private final String name;
    private String argument;


    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }


    public String getArgument() {
        return this.argument;
    }


    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "forward":
                forward = true;
                back = false;
                mine = false;
                repair = false;
                return new ForwardCommand(args[1]);
            case "back":
                back = true;
                forward = false;
                mine = false;
                repair = false;
                return new BackCommand(args[1]);
            case "turn":
                if (args[1].equals("right")) {
                    return new RightCommand();
                }
                if (args[1].equals("left")) {
                    return new LeftCommand();
                }
            case "repair":
                forward = false;
                back = false;
                repair = false;
                mine = false;
                repair = true;

                return new RepairCommand();
            case "mine":
                forward = false;
                back = false;
                repair = false;
                mine = true;
                return new MineCommand();

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
