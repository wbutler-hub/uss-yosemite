import java.util.Random;


public abstract class Robot {


    public final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private World world;
    private final String name;
    public static boolean obs;

    private int shield;
    private int shots;
    private int maxNumberOfShots;
    private int maxShield;
    private boolean alive;
    private final int repairSpeed;
    private final int mineSpeed;
    private final Random random = new Random();

    private int width;
    private int height;


    public final Position START;

    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;

//    private List<Obstacle> obstacleList;

    public void setMaxShield(int maxShield) {this.maxShield = maxShield;}

    public void setMaxNumberOfShots(int maxNumberOfShots) {this.maxNumberOfShots = maxNumberOfShots;}

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public Robot(String name) {

        this.name = name;
        this.world = Server.getWorld();
        this.currentDirection = Direction.NORTH;
        this.alive = true;
        this.height = world.getHeight();
        this.width = world.getWidth();
        this.repairSpeed = world.getRepairSpeed();
        this.mineSpeed = world.getMineSpeed();
        TOP_LEFT = new Position((-this.width),this.height);
        BOTTOM_RIGHT =new Position(this.width,(-this.height));
        START = new Position(random.nextInt(this.width + this.width) - this.width,
                random.nextInt(this.height + this.height) - this.height);
        this.position = this.START;
    }

    public Robot(String name, World world) {

        this.name = name;
        this.world = world;
        this.currentDirection = Direction.NORTH;
        this.alive = true;
        this.height = world.getHeight();
        this.width = world.getWidth();
        this.repairSpeed = world.getRepairSpeed();
        this.mineSpeed = world.getMineSpeed();
        TOP_LEFT = new Position((-this.width),this.height);
        BOTTOM_RIGHT =new Position(this.width,(-this.height));
        START = new Position(random.nextInt(this.width + this.width) - this.width,
                random.nextInt(this.height + this.height) - this.height);
        this.position = this.START;
    }


    public boolean updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }
        else if (Direction.SOUTH.equals(this.currentDirection)) {
            newY = newY - nrSteps;
        }
        else if (Direction.WEST.equals(this.currentDirection)) {
            newX = newX - nrSteps;
        }
        else if (Direction.EAST.equals(this.currentDirection)) {
            newX = newX + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        for (Obstacle obstacle: world.getObstacleList()) {
            if (obstacle.blocksPosition(newPosition) || obstacle.blocksPath(this.position, newPosition)) {
               // obs = true;
                return false;
            }
        }

        for (Pit pit: world.getPitList()) {
            if (pit.blocksPosition(newPosition) || pit.blocksPath(this.position, newPosition)) {
                this.alive = false;
                this.position = new Position(pit.getBottomLeftPosition().getX(),pit.getBottomLeftPosition().getX());
                return true;
            }
        }

        for (Mine mine: world.getMineList()) {
            if (mine.blocksPosition(newPosition) || mine.blocksPath(this.position, newPosition)) {
                this.position = new Position(mine.getBottomLeftPosition().getX(),mine.getBottomLeftPosition().getX());
                this.updateShield("mine");
                this.world.removeMine(mine);
                return true;
            }
        }

        if (isNewPositionAllowed(newPosition)){
            this.position = newPosition;

            return true;
        }
        return false;
    }

    public void updateShield(String option){

        if(option.equals("shot")) {
            shield -= 1;
        }
        if(option.equals("mine")) {
            shield -= 3;
        }
        if (shield < 0) {
            alive = false;
        }
        if (option.equals("repair")) {

            try
            {
                Long millisecs = this.repairSpeed * 1000L;
                Thread.sleep(millisecs);
                System.out.println();
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }


            sleep(this.repairSpeed);

            shield = maxShield;
        }

    }

    public void setMine() {
        int originalShield = this.shield;
        this.shield = 0;
        sleep(this.mineSpeed);
        Position position1 = new Position(this.position.getX() + 4,
                this.position.getY()-4);
        Mine mine = new Mine(this.position,position1);
        world.addMine(mine);
        this.shield = originalShield;
        if(this.currentDirection.equals(Direction.NORTH) ||
        this.currentDirection.equals(Direction.SOUTH)) {
            this.position = new Position(position.getX(), position.getY()+1);
        }
        else {
            this.position = new Position(position.getX()-1, position.getY());
        }

    }

    public void updateShots(String option) {

        if(option.equals("shoot")) {
            if(shots > 0) {
                this.shots -= 1;
            }
        }
        else if(option.equals("reload")) {
            this.shots = maxNumberOfShots;
        }

    }

    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            this.currentDirection = this.currentDirection.right();
        }
        else {
            this.currentDirection = this.currentDirection.left();
        }
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Robot create(String name, String type) {
        switch (type) {
            case "test":
                return new StandardRobot(name);
            case "sniper":
                return new SniperRobot(name);
            case "robot":
            case "standard":
                return new StandardRobot(name);


            default:
                throw new IllegalArgumentException("Unsupported type: "+type );
        }
    }

    public static Robot create(String name, String type, World world) {
        switch (type) {
            case "test":
                return new StandardRobot(name,world);

            default:
                throw new IllegalArgumentException("Unsupported type: "+type );
        }
    }

    public String getStatus() { return status; }

    public void addRobotPair() {
        world.addRobotPair(this.name,this);
    }

    public int getShield() {
        return shield;
    }

    private void sleep(int seconds) {
        try
        {
            Long millisecs = seconds * 1000L;
            Thread.sleep(millisecs);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}

