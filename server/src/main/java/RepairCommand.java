public class RepairCommand extends Command{

    /**
     * Updates the shield, with the Update shield method, passing in repair.
     * prints out a new line,
     * and sets the Status to "REPAIR"
     * @param target: - being the robot object.
     * @returns: true:
     */
    @Override
    public boolean execute(Robot target) {
        target.updateShield("repair");
        System.out.println();
        target.setStatus("REPAIR");
        return true;
    }

    /**
     * Constructor for RepairCommand
     * */
    public RepairCommand() {
        super("repair");
    }
}
