public class LeftCommand extends Command{
    @Override
    public boolean execute(Robot target) {
        target.updateDirection(false);
        target.setStatus("Turned left.");
        return true;
    }

    public LeftCommand() {
        super("turn left");
    }
}
