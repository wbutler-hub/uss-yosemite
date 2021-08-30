package za.co.wethinkcode;

public class ReloadCommand extends Command{

    /**
     * Updates the number of shots, by passing through the reload string.<br/>
     * Sets the state of the robot to "reload"
     * @param target: - being the robot object.
     * @returns: true;
     */
    @Override
    public boolean execute(Robot target) {
        target.updateShots("reload");
        target.setStatus("RELOAD");
        return true;
    }

    /**
     * Constructor for Reload za.co.wethinkcode.Command
     * */
    public ReloadCommand() { super("reload"); }
}
