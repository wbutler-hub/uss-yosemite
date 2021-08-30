package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class BackResponse extends Response{

    Response response;

    /**
     * Constructor for za.co.wethinkcode.BackResponse, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public BackResponse(Robot robot){
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

        if(args[0].equals("back")){
            movement = args[0];
            steps = args[1];
        }
        if(args[0].equals("right") ){
            turn = "right";
        }else if( args[0].equals("left")){
            turn = "left";
        }
        String result = "OK";

        Data.put("message", "done");
        Data.put("movement",movement);
        Data.put("steps",steps);
        Data.put("turn",turn);
        State.put("position",position );
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",robot.getStatus());

        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }

}

