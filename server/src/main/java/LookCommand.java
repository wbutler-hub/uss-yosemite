public class LookCommand extends Command{
    public LookCommand() {super("look");}

    @Override
    public boolean execute(Robot target) {
        target.lookAround();
        target.setStatus("LOOKING");
        return true;
    }
}
