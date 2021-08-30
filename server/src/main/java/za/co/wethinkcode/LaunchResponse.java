package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class LaunchResponse extends Response {

    Response response;

    /**
     * Constructor for za.co.wethinkcode.LaunchResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public LaunchResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }

    /**
     * Sets up a JSONObject, it receives an instruction (command). <br/>
     * Creates the new positions, movements, of robot and all things in the world. <br/>
     * puts the movement, steps, turn, and all other relevant data about the command to the response.
     * @param instruction: String instruction (which is the command - user input)
     * @return: the response that was created.
     * */
    public JSONObject executeRsponse (String instruction) {

        String movement = "";
        String steps = "";
        String turn = "";
        String[] args = instruction.toLowerCase().trim().split(" ");

        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();

        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();

        System.out.println(position);
        String result = "OK";

        Data.put("message", "done");
        Data.put("position",position);

        State.put("position",position);
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",robot.getStatus());

        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        System.out.println("{result:OK,data:{visibility:1,position:[0,0],objects:[]},state:{position:[0,0],direction:NORTH,shields:0,shots:0,status:TODO}}");
        System.out.println(response);

        return response;
    }

}