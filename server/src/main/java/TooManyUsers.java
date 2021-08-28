import org.json.JSONArray;
import org.json.JSONObject;

public class TooManyUsers extends Response {

    Response response;

    /**
     * Constructor for FailedCommand, from extending response;
     * @param robot: takes the robot object;
     * sets the robot.
     * */
    public TooManyUsers(Robot robot){
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
    public JSONObject executeRsponse () {

        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();

        String result = "ERROR";
        Data.put("message", "Too many users are currently connected, Please wait for an opening.");


        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }

}