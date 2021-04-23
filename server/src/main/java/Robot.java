import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public abstract class Robot {
    private int index;

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
    private boolean emptyGun;
    private final int repairSpeed;
    private final int mineSpeed;
    private final int reloadSpeed;
    private final Random random = new Random();

    private final int width;
    private final int height;
    private final int visibility;

    private HashMap<Object,ArrayList<Object>> objects = new HashMap<>();


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
        this.emptyGun = false;
        this.height = world.getHeight();
        this.width = world.getWidth();
        this.repairSpeed = world.getRepairSpeed();
        this.visibility = world.getVisibility();
        this.mineSpeed = world.getMineSpeed();
        this.reloadSpeed = world.getReloadSpeed();
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
        this.visibility = world.getVisibility();
        this.mineSpeed = world.getMineSpeed();
        this.reloadSpeed = world.getReloadSpeed();
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

        for (Mine mine: world.getMineList()) {
            if (mine.blocksPosition(newPosition) || mine.blocksPath(this.position, newPosition)) {
                this.position = new Position(mine.getBottomLeftPosition().getX(),mine.getBottomLeftPosition().getX());
                this.updateShield("mine");
                this.world.removeMine(mine);
                return true;
            }
        }

        for (Robot robot: world.getRobotList()) {
            if (!robot.equals(this)) {
                if (robot.blocksPosition(newPosition) || robot.blocksPath(this.position, newPosition)) {
                    return false;
                }
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

        if (!emptyGun) {
            if (option.equals("shoot")) {
                if (shots > 0) {
                    this.shots -= 1;
                    if (shots == 0) {
                        emptyGun = true;
                    }
                }
            }
        }
        if(option.equals("reload")) {
            sleep(this.reloadSpeed);
            this.shots = maxNumberOfShots;
            this.emptyGun = false;
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
            case "robot":
            case "standard":
                return new StandardRobot(name);
            case "sniper":
                return new SniperRobot(name);
            case "fighter":
                return new FighterRobot(name);
            case "tank":
                return new TankRobot(name);
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

    public boolean blocksPosition(Position position) {
        Boolean checkY = this.position.getY() == position.getY();
        Boolean checkX = this.position.getX() == position.getX();

        if (checkX && checkY) {
            return true;
        }
        return  false;
    }


    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX() && (this.position.getX() <= a.getX() && a.getX() <= this.position.getX())) {
            if (b.getY() < this.position.getY()) {
                return a.getY() >= this.position.getY();
            }
            else if (b.getY() > (this.position.getY())) {
                return a.getY() <= this.position.getY();
            }
        }
        else if (a.getY() == b.getY() && (this.position.getY() <= a.getY() && a.getY() <= this.position.getY())) {
            if (b.getX() < this.position.getX()) {
                return a.getX() >= this.position.getX();
            }
            else if (b.getX() > (this.position.getX())) {
                return a.getX() <= this.position.getX();
            }
        }

        return false;
    }

    public HashMap<Object, ArrayList<Object>> getObjects() { return this.objects; }

    public void lookAround() {
        int newX;
        int newY;
        ArrayList<Object> objectData = new ArrayList<>();
        this.objects = new HashMap<>();
        Direction direction = Direction.NORTH;
        int mineVisibility = (int) Math.floor(this.visibility / 4);


        for (int degree = 0; degree <= 270 ; degree+=90) {
            for (int i = 0; i <= this.visibility; i++) {
                newX = this.position.getX();
                newY = this.position.getY();

                if (degree == 0) {
                    newY = newY + i;
                    direction = Direction.NORTH;
                } else if (degree == 90) {
                    newX = newX + i;
                    direction = Direction.EAST;
                } else if (degree == 180) {
                    newY = newY - i;
                    direction = Direction.SOUTH;
                } else if (degree == 270) {
                    newX = newX - i;
                    direction = Direction.WEST;
                }

                Position newPosition = new Position(newX, newY);

                for (Obstacle obstacle : world.getObstacleList()) {
                    if (obstacle.blocksPosition(newPosition)) {
                        objectData = new ArrayList<>();
                        objectData.add(i);
                        objectData.add(direction);
                        if (!this.objects.containsKey(obstacle)) {
                            this.objects.put(obstacle,objectData);
                        }
                    }
                }

                for (Pit pit: world.getPitList()) {
                    if (pit.blocksPosition(newPosition) || pit.blocksPath(this.position, newPosition)) {
                        this.alive = false;
                        this.position = new Position(pit.getBottomLeftPosition().getX(),pit.getBottomLeftPosition().getX());
                        objectData = new ArrayList<>();
                        objectData.add(i);
                        objectData.add(direction);
                        if (!this.objects.containsKey(pit)) {
                            this.objects.put(pit,objectData);
                        }
                    }
                }

                for (Robot robot : world.getRobotList()) {
                    if (!robot.equals(this)) {
                        if (robot.blocksPosition(newPosition)) {
                            objectData = new ArrayList<>();
                            objectData.add(i);
                            objectData.add(direction);
                            if (!this.objects.containsKey(robot)) {
                                this.objects.put(robot,objectData);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i <= mineVisibility; i++) {
                newX = this.position.getX();
                newY = this.position.getY();

                if (degree == 0) {
                    newY = newY + i;
                    direction = Direction.NORTH;
                } else if (degree == 90) {
                    newX = newX + i;
                    direction = Direction.EAST;
                } else if (degree == 180) {
                    newY = newY - i;
                    direction = Direction.SOUTH;
                } else if (degree == 270) {
                    newX = newX - i;
                    direction = Direction.WEST;
                }

                Position newPosition = new Position(newX, newY);

                for (Mine mine: world.getMineList()) {
                    if (mine.blocksPosition(newPosition)) {
                        objectData = new ArrayList<>();
                        objectData.add(i);
                        objectData.add(direction);
                        if (!objects.containsKey(mine)) {
                            objects.put(mine,objectData);
                        }
                    }
                }


            }
        }
    }

    public boolean updateBullet() {
        int newX = this.position.getX();
        int newY = this.position.getY();

        int distance = 0;

        if (maxNumberOfShots == 5) {distance = 1;}
        if (maxNumberOfShots == 4) {distance = 2;}
        if (maxNumberOfShots == 3) {distance = 3;}
        if (maxNumberOfShots == 2) {distance = 4;}
        if (maxNumberOfShots == 1) {distance = 5;}

        if (!emptyGun) {
            for (int i = 0; i <= distance; i++) {

                if (Direction.NORTH.equals(this.currentDirection)) {
                    newY = newY + i;
                } else if (Direction.SOUTH.equals(this.currentDirection)) {
                    newY = newY - i;
                } else if (Direction.WEST.equals(this.currentDirection)) {
                    newX = newX - i;
                } else if (Direction.EAST.equals(this.currentDirection)) {
                    newX = newX + i;
                }

                Position newPosition = new Position(newX, newY);

                for (Obstacle obstacle : world.getObstacleList()) {
                    if (obstacle.blocksPosition(newPosition)) {
                        return false;
                    }
                }



                for (Robot robot : world.getRobotList()) {
                    if (!robot.equals(this)) {
                        if (robot.blocksPosition(newPosition)) {
                            robot.updateShield("shot");
                            Server.userStatuses.set(robot.getIndex(), ServerCommandLine.getState(robot));
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getShots() {
        return shots;
    }

    public Boolean getEmptyGun() { return emptyGun; }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

