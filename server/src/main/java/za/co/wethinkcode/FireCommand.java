package za.co.wethinkcode;

public class FireCommand extends Command{
    public boolean hit;
    public boolean miss;

    /**
     * First runs update bullet (see that function), updating the hit and miss states; <br/>
     * If the update bullet fails, check if the gun is empty or not and update the status accordingly. <br/>
     * After, sets the status of the robot to "shoot" indicating it is show.
     * @param target: - being the robot object.
     * @returns: true (continue the program).
     */
    @Override
    public boolean execute(Robot target) {
        if(target.updateBullet()) {
            this.hit = true;
            this.miss = false;
            target.setStatus("Hit");
        }
        else {
            if(!target.getEmptyGun()) {
                this.miss = true;
                this.hit = false;
                target.setStatus("Miss");
            }
            else {
                target.setStatus("Out Of Ammo");
            }
        }
        target.updateShots("shoot");
        return true;
    }

    /**
     * Constructor for za.co.wethinkcode.FireCommand
     * */
    public FireCommand() { super("fire"); }
}
