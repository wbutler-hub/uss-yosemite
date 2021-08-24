public class BackCommand extends Command {

    /**
     * Moves the robot back x number of squares if the move is possible.<br/>
     * SetState: if the robot can move, the state shows howmany steps back it can take, else it reports it cannot move.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true:
     */
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());

        if (target.updatePosition(-nrSteps)){
            target.setStatus("Moved back by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }

        return true;
    }

    /**
     * Constructor for BackCommand
     * */
    public BackCommand(String argument) {
        super("forward", argument);
    }
}
