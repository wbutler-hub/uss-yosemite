package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class FireResponse extends Response{
    Response response;
    FireCommand fireCommand;

    /**
     * Constructor for za.co.wethinkcode.FireResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public FireResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }

    /**
     * Sets up a JSONObject, it receives an instruction (command). <br/>
     * Creates the new positions, movements, of robot and all things in the world. <br/>
     * puts the movement, steps, turn, and all other relevant data about the command to the response. <br/>
     * as well as checking if it created a hit or miss from the fire
     * @return: the response that was created.
     * */
    public JSONObject executeRsponse () {

        fireCommand = new FireCommand();
        response = new Response(robot);
        String message = "";
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "FIRE";
        if(fireCommand.hit && !fireCommand.miss){
            message = "HIT";
        }else if(fireCommand.miss && !fireCommand.hit){
            message = "MISS";
        }
        Data.put("message", robot.getStatus());

        State.put("position",position );
        State.put("direction", direction);

        State.put("status",status);
        State.put("shots",robot.getShots());
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status","FIRE");
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }
}
