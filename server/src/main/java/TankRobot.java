public class TankRobot extends Robot{
    private final int maxNumberOfShots = 1;
    private final int maxShield = 5;

    /**
     * Constructor for robot when world is not set. <br/>
     * It sets the name from param, and the number of shots and shields to the max
     * @param name: String name of the robot.
     * */
    public TankRobot(String name) {
        super(name);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }

    /**
     * Constructor for robot when world is set. <br/>
     * It sets the name from param, and the number of shots and shields to the max
     * @param name: String name of the robot.
     * @param world: Sets it to the world.
     * */
    public TankRobot(String name, World world) {
        super(name,world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }
}