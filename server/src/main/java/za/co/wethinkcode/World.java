package za.co.wethinkcode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

public class World {
    private int width;
    private int height;
    private int visibility;
    private int reloadSpeed; // Reload speed in seconds
    private int repairSpeed; // Repair speed in seconds
    private int mineSpeed;   // Speed of which a mine is placed in seconds

    private static ArrayList<Obstacle> obstacleList;
    private static ArrayList<Pit> pitList;
    private ArrayList<Mine> mineList;

    private HashMap<String, Robot> robots = new HashMap<String, Robot>();
    private ArrayList<Robot> robotList;

    /**
     * constructor to set up the world base lists and config / obstructions
     * */
    public World() throws  IOException {
        obstacleList = new ArrayList<>();
        pitList = new ArrayList<>();
        mineList = new ArrayList<>();
        robotList = new ArrayList<>();
        setConfig();
        generateObstructions();
    }

    /**
     * Sets up the config of the world; passing through all of the parameters (from the config properties). <br/>
     * throws exception if the config file was not found.
     * */
    private void setConfig() throws IOException {

        Properties prop = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("config.properties");

        prop.load(is);

//        System.out.println("is: " + is);
//
//
//        URL configPath = this.getClass().getClassLoader().getResource("config.properties");
//        String rootConfig = this.getClass().getClassLoader().getName();
//        System.out.println(configPath);
//        System.out.println(rootConfig);
//        if ((configPath != null ? configPath.getPath() : null) != null) {
//            InputStream propertyStream = new FileInputStream(configPath.getPath());
//            prop.load(propertyStream);
//        } else {
//            throw new FileNotFoundException("config.properties not found.");
//        }

        this.width = Integer.parseInt(prop.getProperty("width")) / 2;
        this.height = Integer.parseInt(prop.getProperty("height")) / 2;
        this.visibility = Integer.parseInt(prop.getProperty("visibility"));
        this.reloadSpeed = Integer.parseInt(prop.getProperty("reloadSpeed"));
        this.repairSpeed = Integer.parseInt(prop.getProperty("repairSpeed"));
        this.mineSpeed = Integer.parseInt(prop.getProperty("mineSpeed"));
    }

    /**
     * Generate a random number of obstructions, using a random number generator (Random()). <br/>
     * creates a new random position to make an obstacle and a pit and then adds them to the lists.
     * */
    public void generateObstructions() {
        Random random = new Random();
        int randNum = random.nextInt(9) +1;

        for(int i = 1; i <= randNum; i ++){
//            int randX = random.nextInt(this.width + this.width) - this.width;
//            int randY = random.nextInt(this.height + this.height) - this.height;
            int randX = (int) Math.floor(Math.random() * (this.width - (- this.width) + 1) + (-this.width));
            int randY = (int) Math.floor(Math.random() * (this.height - (- this.height) + 1) + (-this.height));
            Position randPosition1 = new Position(randX,randY);
            Position randPosition2 = new Position(randX+4,randY-4);
            Obstacle obstacle = new Obstacle(randPosition1,randPosition2);
            this.obstacleList.add(obstacle);

//            randX = random.nextInt(this.width + this.width) - this.width;
//            randY = random.nextInt(this.height + this.height) - this.height;
            randX = (int) Math.floor(Math.random() * (this.width - (- this.width) + 1) + (-this.width));
            randY = (int) Math.floor(Math.random() * (this.height - (- this.height) + 1) + (-this.height));
            randPosition1 = new Position(randX,randY);
            randPosition2 = new Position(randX+4,randY-4);
            Pit pit = new Pit(randPosition1,randPosition2);
            this.pitList.add(pit);
        }
    }

    /**
     * add a mine to the mine lists
     * */
    public void addMine(Mine mine) {
        mineList.add(mine);
    }

    /**
     * sets up the obstructions (being lists and pits) to new empty lists.
     * */
    public void setObstructionsEmpty() {
        this.obstacleList = new ArrayList<>();
        this.pitList = new ArrayList<>();
    }

    /**
     * Used to remove a mine from the mine list
     * */
    public void removeMine(Mine mine) {
        this.mineList.remove(mine);
    }

    /**
     * @return the height
     * */
    public int getHeight() {
        return height;
    }

    /**
     * @return the width
     * */
    public int getWidth() {
        return width;
    }

    /**
     * @return the reload speed
     * */
    public int getReloadSpeed() {
        return reloadSpeed;
    }

    /**
     * @return the repair speed
     * */
    public int getRepairSpeed() {
        return repairSpeed;
    }

    /**
     * @return the visibility speed
     * */
    public int getVisibility() {
        return visibility;
    }

    /**
     * @return the mine speed
     * */
    public int getMineSpeed() { return mineSpeed; }

    /**
     * @return the List of obstacles
     * */
    public static ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    /**
     * @return the List of pits
     * */
    public static ArrayList<Pit> getPitList() {
        return pitList;
    }

    /**
     * @return the List of mines
     * */
    public ArrayList<Mine> getMineList() {
        return mineList;
    }

    /**
     * adds a pair of robots, to the list. Passes through the robot name and the robot object.
     * */
    public void addRobotPair(String name, Robot robot) {
        this.robots.put(name,robot);
        this.robotList.add(robot);
    }

    /**
     * @return the List of robots
     * */
    public ArrayList<Robot> getRobotList() {
        return robotList;
    }

    /**
     * @return the key/value pair of robots
     * */
    public HashMap<String, Robot> getRobots() {
        return robots;
    }
}
