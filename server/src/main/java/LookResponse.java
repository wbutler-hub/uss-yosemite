import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LookResponse extends Response {

    Response response;
    ArrayList<HashMap<String, String>> objects = new ArrayList<>();

    /**
     * Constructor for LookResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public LookResponse(Robot robot) {
        super(robot);
        this.robot = robot;
    }

    /**
     * Sets up a JSONObject, it receives an instruction (command). <br/>
     * Creates the new positions, movements, of robot and all things in the world. <br/>
     * puts all relevent data relating to the robot.
     * @return: the response that was created.
     * */
    public JSONObject executeRsponse() {


        response = new Response(robot);
        String message = "DONE";
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "LOOK";

        Data.put("message", message);
        Data.put("objects", setObjects());
        System.out.println(Data.get("objects"));
        State.put("position", position);
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",status);
        State.put("status", status);



        State.put("shots", robot.getShots());
        response.put("result", result);
        response.put("data", Data);
        response.put("state", State);

        return response;
    }

    /**
     * Creates an array list of object data, <br/>
     * Makes some Hashmaps (basically key-value pair/dictionary). <br/>
     * Foreach key in the object in the robot (ln 71), and for each key (individual loop item), <br/>
     * Creates the temp data for each type, distance and direction of robot.
     * */
    private ArrayList<HashMap<String,String>> setObjects() {
        HashMap<Object, ArrayList<Object>> robotData = robot.getObjects();
        HashMap<String, String> tempData;
        ArrayList tempArray;
        ArrayList<HashMap<String,String>> objectData = new ArrayList();
        for (Object key : robotData.keySet()) {
            tempData = new HashMap<>();
            tempData.put("type",checkObjectType(key));
            tempArray = robotData.get(key);
            tempData.put("Distance",tempArray.get(0).toString());
            tempData.put("Direction",checkDirectionType(tempArray.get(1)));
            objectData.add(tempData);
        }
        return objectData;
    }

    /**
     * Checks if what class type of the value is passed through.
     * @return the type of object it encountered. <br/>
     * Robot, Obstacle, Pit or Mine
     * */
    private String checkObjectType(Object value) {
        if (value.getClass() == SniperRobot.class ||
                value.getClass() == TankRobot.class ||
                value.getClass() == ShooterRobot.class ||
                value.getClass() == FighterRobot.class) {
            return "Robot";
        } else if (value.getClass() == Obstacle.class) {
            return "Obstacle";
        } else if (value.getClass() == Pit.class) {
            return "Pit";
        } else if (value.getClass() == Mine.class) {
            return "Mine";
        }
        return "Robot";
    }

    /**
     * Checks the direction of the type, to see where in relation to the robot it is.
     * @return the direction, defaulting to north.
     * */
    private String checkDirectionType(Object value) {
        if (value.equals(Direction.NORTH)) {
            return "North";
        } else if (value.equals(Direction.EAST)) {
            return "East";
        } else if (value.equals(Direction.WEST)) {
            return "West";
        } else if (value.equals(Direction.SOUTH)) {
            return "South";
        }
        return "North";
    }
}
