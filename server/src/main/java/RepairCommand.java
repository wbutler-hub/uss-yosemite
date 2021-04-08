public class RepairCommand extends Command{
    public RepairCommand() {
        super("repair");
    }

    @Override
    public boolean execute(Robot target) {
        target.updateShield("repair");
        target.setStatus("REPAIR");
        return true;
    }
}
