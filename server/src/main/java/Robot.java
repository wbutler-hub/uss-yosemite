
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * sets the max shields
     * */
    public void setMaxShield(int maxShield) {this.maxShield = maxShield;}

    /**
     * sets the max number of shots
     * */
    public void setMaxNumberOfShots(int maxNumberOfShots) {this.maxNumberOfShots = maxNumberOfShots;}

    /**
     * sets the current shields
     * */
    public void setShield(int shield) {
        this.shield = shield;
    }

    /**
     * sets the current shots
     * */
    public void setShots(int shots) {
        this.shots = shots;
    }

    /**
     * Getter to receive the robots name
     * */
    public String getName() { return this.name;}

    /**
     * Constructor for the robot, sets up the whole state and everything relating to the robot.<br/>
     * *if no world is passed through*
     * */
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
//        START = new Position(random.nextInt(this.width + this.width) - this.width,
//                random.nextInt(this.height + this.height) - this.height);
        START = new Position((int) Math.floor(Math.random() * (this.width - (- this.width) + 1) + (-this.width)),
                (int) Math.floor(Math.random() * (this.height - (- this.height) + 1) + (-this.height)));
        this.position = this.START;
    }

    /**
     * Constructor for the robot, sets up the whole state and everything relating to the robot.<br/>
     * *if there is a world passed through*
     * */
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
        BOTTOM_RIGHT = new Position(this.width,(-this.height));
//        START = new Position(random.nextInt(this.width + this.width) - this.width,
//                random.nextInt(this.height + this.height) - this.height);
        START = new Position((int) Math.floor(Math.random() * (this.width - (- this.width) + 1) + (-this.width)),
                (int) Math.floor(Math.random() * (this.height - (- this.height) + 1) + (-this.height)));
        this.position = this.START;
    }

    /**
     * Used to update the position of the robot taking in the certain number of steps. <br/>
     * checks if the position is possible to move, based on pits/mines/obstacles/robots. <br/>
     * @return if it is possible it moves you there else returns false.
     * */
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

    /**
     * used to update shield based on certain criteria; <br/>
     * 1. if you get shot you lose 1 shield. <br/>
     * 2. if you step on a mine you lose 3 shields. </br>
     * 3. if you repair you gain back all your shields.
     * */
    public void updateShield(String option){

        if(option.equals("shot")) {
            shield -= 1;
        }
        if(option.equals("mine")) {
            shield -= 3;
        }
        if (shield < 0) {
            alive = false;
            shield = 0;
        }
        if (option.equals("repair")) {

            sleep(this.repairSpeed);

            shield = maxShield;
        }
    }

    /**
     * Setting of a mine, checks your shield (incase you place under you). <br/>
     * removes you shield, then stops you from input while you place a mine. <br/>
     * if successfully place you get shield back and you position is moved to new place, <br/>
     * else you place a mine under you and get hit.
     * */
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

    /**
     * Update the number of shots remaining. <br/>
     * 1. if the gun was not empty and you said shoot, -1 bullet, until its empty.<br/>
     * 2. if the option is reload, then set the maximum number of bullets.
     * */
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

    /**
     * checks which direction you are turning and calls either the left or right turn on it.
     * */
    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            this.currentDirection = this.currentDirection.right();
        }
        else {
            this.currentDirection = this.currentDirection.left();
        }
    }

    /**
     * @return the current direction
     * */
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     * @return the current position
     * */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @return if the new position is in the border of the world.
     * */
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    /**
     * @return if the command was executed.
     * */
    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    /**
     * sets the status.
     * */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return creates the class of the robot type, passing in the name of the robot. default is standard, else throws an exception
     * */
    public static Robot create(String name, String type) {
        System.out.println(type);
        switch (type) {
            case "test":
            case "robot":
            case "shooter":
                return new ShooterRobot(name);
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

    /**
     * @return same as the robot create above, however it also takes in the world as an argument.
     * */
    public static Robot create(String name, String type, World world) {
        switch (type) {
            case "test":
                return new ShooterRobot(name,world);

            default:
                throw new IllegalArgumentException("Unsupported type: "+type );
        }
    }

    /**
     * @return the current status.
     * */
    public String getStatus() { return status; }

    /**
     * adding a pair of robots to the world, being the robot name and robot itself.
     * */
    public void addRobotPair() {
        world.addRobotPair(this.name,this);
    }

    /**
     * @return the current shield
     * */
    public int getShield() {
        return shield;
    }

    /**
     * makes the robot sleep for certain duration, dependant from the config files params <br/>
     * used for reload, placing mine, and repairing shields.
     * */
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

    /**
     * Checks if the current position given is blocked.
     * @return false if it is not blocked, true if it is blocked
     * */
    public boolean blocksPosition(Position position) {
        Boolean checkY = this.position.getY() == position.getY();
        Boolean checkX = this.position.getX() == position.getX();

        if (checkX && checkY) {
            return true;
        }
        return  false;
    }

    /**
     * Checks if the path from the original position to the new position is blocked.
     * @return false if it is not blocked, true if it is blocked
     * */
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

    /**
     * @returns a key-value pair of objects.
     * */
    public HashMap<Object, ArrayList<Object>> getObjects() { return this.objects; }

    /**
     * Looks around in each direction of the robot. <br/>
     * checking for: obstacles, mines, pits, robots
     * */
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

    /**
     * Updating the number of bullets (based on number of distance, comapared to number of shots). <br/>
     * if your fun is not empty, checks if you shot into an obstacle or a robot. <br/>
     * @return true if you hit a robot, else if you hit an obstacle/nothing returns false.
     * */
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
                            ServerCommandLine.robotStates.put(robot.getName(), ServerCommandLine.getState(robot));
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * setting your postion.
     * */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return number of shot you have left
     * */
    public int getShots() {

        return shots;
    }

    /**
     * @return ais your gun empty or not
     * */
    public Boolean getEmptyGun() { return emptyGun; }

    /**
     * @return are still alive or not
     * */
    public boolean isAlive() {
        return this.alive;
    }

}

