import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class World {
    private int width;
    private int height;
    private int visibility;
    private int reloadSpeed; // Reload speed in seconds
    private int repairSpeed; // Repair speed in seconds
    private int mineSpeed;   // Speed of which a mine is placed in seconds
    private ArrayList<Obstacle> obstacleList;
    private ArrayList<Pit> pitList;

    public World() throws  IOException {
        obstacleList = new ArrayList<>();
        pitList = new ArrayList<>();
        setConfig();
        generateObstructions();
    }

    private void setConfig() throws IOException {
        Properties prop = new Properties();
        URL configPath = this.getClass().getClassLoader().getResource("config.properties");
        if ((configPath != null ? configPath.getPath() : null) != null) {
            InputStream propertyStream = new FileInputStream(configPath.getPath());
            prop.load(propertyStream);
        } else {
            throw new FileNotFoundException("config.properties not found.");
        }
        this.width = Integer.parseInt(prop.getProperty("width"));
        this.height = Integer.parseInt(prop.getProperty("height"));
        this.visibility = Integer.parseInt(prop.getProperty("visibility"));
        this.reloadSpeed = Integer.parseInt(prop.getProperty("reloadSpeed"));
        this.repairSpeed = Integer.parseInt(prop.getProperty("repairSpeed"));
        this.mineSpeed = Integer.parseInt(prop.getProperty("mineSpeed"));
    }


    public void generateObstructions() {
        Random random = new Random();
        int randNum = random.nextInt(9) +1;

        for(int i = 1; i <= randNum; i ++){
            int randX = random.nextInt(this.width + this.width) - this.width;
            int randY = random.nextInt(this.height + this.height) - this.height;
            Position randPosition1 = new Position(randX,randY);
            Position randPosition2 = new Position(randX+4,randY-4);
            Obstacle obstacle = new Obstacle(randPosition1,randPosition2);
            this.obstacleList.add(obstacle);

            randX = random.nextInt(this.width + this.width) - this.width;
            randY = random.nextInt(this.height + this.height) - this.height;
            randPosition1 = new Position(randX,randY);
            randPosition2 = new Position(randX+4,randY-4);
            Pit pit = new Pit(randPosition1,randPosition2);
            this.pitList.add(pit);
        }


    }

    public void setObstructionsEmpty() {
        this.obstacleList = new ArrayList<>();
        this.pitList = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public int getRepairSpeed() {
        return repairSpeed;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getMineSpeed() { return mineSpeed; }

    public ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public ArrayList<Pit> getPitList() {
        return pitList;
    }
}
