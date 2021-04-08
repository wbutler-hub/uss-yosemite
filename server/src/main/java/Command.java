import java.util.ArrayList;
import java.util.List;

public abstract class Command {
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

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}