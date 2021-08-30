package za.co.wethinkcode;

public class ForwardCommand extends Command {

    /**
     * Moves the robot forward x number of squares if the move is possible.<br/>
     * SetState: if the robot can move, the state shows how many steps forward it can take, else it reports it cannot move.
     * <br/>
     * @param target: - being the robot object.
     * @returns: true;
     */
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());

        if (target.updatePosition(nrSteps)){
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }

        return true;
    }

    /**
     * Constructor for za.co.wethinkcode.BackCommand
     * */
    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}