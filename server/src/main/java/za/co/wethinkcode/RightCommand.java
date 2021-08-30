package za.co.wethinkcode;

public class RightCommand extends Command{

    /**
     * It will check the direction the robot is facing, and update it to 90 deg.<br/>
     * SetState: It will state that the robot was turned in that direction.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true; (meaning program will continue)
     */
    @Override
    public boolean execute(Robot target) {
        target.updateDirection(true);
        target.setStatus("Turned right.");
        return true;
    }

    /**
     * Constructor for za.co.wethinkcode.RightCommand
     * */
    public RightCommand() {
        super("turn right");
    }
}
