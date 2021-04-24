import org.json.JSONArray;
import org.json.JSONObject;

public class MineResponse extends Response {



    Response response;


    public MineResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }


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

        if(!robot.alive){
            status = "DEAD";
        }else if(robot.alive){
            status = "SETMINE";
        }
        Data.put("messgae", "done");
        State.put("position",position );
        State.put("direction", direction);
        State.put("status",status);
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }
}
