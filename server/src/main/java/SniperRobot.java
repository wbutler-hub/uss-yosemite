public class SniperRobot extends Robot{
    private final int maxNumberOfShots = 1;
    private final int maxShield = 1;

    /**
     * Constructor for robot when world is not set. <br/>
     * It sets the name from param, and the number of shots and shields to the max
     * @param name: String name of the robot.
     * */
    public SniperRobot(String name) {
        super(name);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }

}
