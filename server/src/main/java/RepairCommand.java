public class RepairCommand extends Command{
    public RepairCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(Robot target) {
        target.updateShield("");
        return true;
    }
}
