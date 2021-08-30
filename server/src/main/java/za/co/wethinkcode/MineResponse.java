package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class MineResponse extends Response {



    Response response;

    /**
     * Constructor for za.co.wethinkcode.MineResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public MineResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }

    /**
     * Sets up a JSONObject, it receives an instruction (command). <br/>
     * Creates the new positions, movements, of robot and all things in the world. <br/>
     * it has the main check to see if the robot is dead or not, and if it can set a mine or not.
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
        String status = "";

        if(!robot.isAlive()){
            status = "DEAD";
        }else if(robot.isAlive()){
            status = "SETMINE";
        }
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
