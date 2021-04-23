public class FireCommand extends Command{
    public FireCommand() { super("fire"); }

    @Override
    public boolean execute(Robot target) {
        if(target.updateBullet()) {
            target.setStatus("Hit");
        }
        else {
            if(!target.getEmptyGun()) {
                target.setStatus("Miss");
            }
            else {
                target.setStatus("Out Of Ammo");
            }
        }
        return true;
    }
}
