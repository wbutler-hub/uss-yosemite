import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    public static boolean back;
    public static boolean forward;
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
                return new ForwardCommand(args[1]);
            case "back":
                back = true;
                return new BackCommand(args[1]);
            case "turn":
                if (args[1].equals("right")) {
                    return new RightCommand();
                }
                if (args[1].equals("left")) {
                    return new LeftCommand();
                }
            case "repair":
                System.out.println(new RepairCommand());
                return new RepairCommand();

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
