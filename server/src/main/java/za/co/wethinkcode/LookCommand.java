package za.co.wethinkcode;

public class LookCommand extends Command{

    /**
     * Makes a call to the look around function, to look,<br/>
     * SetState: Looking.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true:
     */
    @Override
    public boolean execute(Robot target) {
        target.lookAround();
        target.setStatus("LOOKING");
        return true;
    }

    /**
     * Constructor for lookCommand
     * */
    public LookCommand() {super("look");}
}
