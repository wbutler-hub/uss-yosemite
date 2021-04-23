import org.json.JSONArray;
import org.json.JSONObject;

public class BackResponse extends Response{

    private Robot robots;

    Response response;






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

        Data.put("messgae", "done");
        State.put("position",position );
        State.put("direction", direction);

        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }

}

