import org.json.JSONArray;
import org.json.JSONObject;

public class LookResponse extends Response{

    Response response;
    FireCommand fireCommand;


    public LookResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }


    public JSONObject executeRsponse () {

        fireCommand = new FireCommand();
        response = new Response(robot);
        String message = "DONE";
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();
        JSONObject objects = new JSONObject();
        JSONObject mines = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "LOOK";

        Data.put("messgae", message);

        //mines.put("Mines", robot.)
        State.put("position",position );
        State.put("direction", direction);
        State.put("status",status);

        State.put("shots",robot.getShots());
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }
}
