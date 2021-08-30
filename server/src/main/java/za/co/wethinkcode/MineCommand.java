package za.co.wethinkcode;

public class MineCommand extends Command{

    /**
     * Makes a call to the setMine function to place a mine.<br/>
     * SetState: to Set mine.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true:
     */
    @Override
    public boolean execute(Robot target) {
        target.setMine();
        target.setStatus("SETMINE");
        return true;
    }

    /**
     * Constructor for za.co.wethinkcode.MineCommand
     * */
    public MineCommand() { super("mine"); }
}
