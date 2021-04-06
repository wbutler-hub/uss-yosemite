import java.util.List;
import java.time.Duration;
import java.util.Random;


public abstract class Robot {

    public final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private World world;
    private final String name;

    private int shield;
    private int shots;
    private int maxNumberOfShots;
    private int maxShield;
    private boolean alive;
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
        if (shield == 0 && option.equals("shot")) {
            alive = false;
        }
        if (option.equals("repair")) {
            shield = maxShield;
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



    }

