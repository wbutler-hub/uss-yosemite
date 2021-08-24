import org.json.JSONArray;
import org.json.JSONObject;

public class RepairResponse extends Response{

    Response response;

    /**
     * Constructor for MineResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public RepairResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }

    /**
     * Sets up a JSONObject, it receives an instruction (command). <br/>
     * Creates the new positions, movements, of robot and all things in the world. <br/>
     * it sets status to repair to set the shield to the max
     * @return: the response that was created.
     * */
    public JSONObject executeRsponse () {


        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "REPAIR";
        Data.put("message", "done");
        State.put("position",position );
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",status);
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }
}
