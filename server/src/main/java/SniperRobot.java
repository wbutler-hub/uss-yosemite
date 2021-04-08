public class SniperRobot extends Robot{
    private final int maxNumberOfShots = 1;
    private final int maxShield = 3;


    public SniperRobot(String name) {
        super(name);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }

}
