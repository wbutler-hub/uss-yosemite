package za.co.wethinkcode;

public class FighterRobot extends Robot{
    private final int maxNumberOfShots = 5;
    private final int maxShield = 2;

    /**
     * Constructor for robot when world is not set. <br/>
     * It sets the name from param, and the number of shots and shields to the max
     * @param name: String name of the robot.
     * */
    public FighterRobot(String name) {
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
    public FighterRobot(String name, World world) {
        super(name,world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }
}