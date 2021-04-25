import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LookResponse extends Response {

    Response response;
    ArrayList<HashMap<String, String>> objects = new ArrayList<>();


    public LookResponse(Robot robot) {
        super(robot);
        this.robot = robot;
    }


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

    private String checkObjectType(Object value) {
        if (value.getClass() == SniperRobot.class ||
                value.getClass() == TankRobot.class ||
                value.getClass() == StandardRobot.class ||
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
