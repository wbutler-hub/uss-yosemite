package za.co.wethinkcode;

public class LeftCommand extends Command{

    /**
     * It will check the direction the robot is facing, and update it to 90 deg.<br/>
     * SetState: It will state that the robot was turned in that direction.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true; (meaning program will continue)
     */
    @Override
    public boolean execute(Robot target) {
        target.updateDirection(false);
        target.setStatus("Turned left.");
        return true;
    }

    /**
     * Constructor for za.co.wethinkcode.LeftCommand
     * */
    public LeftCommand() {
        super("turn left");
    }
}
