import org.json.JSONArray;
import org.json.JSONObject;

public class ForwardResponse extends Response {
    private Robot robot;
    //JSONObject Direction;
    Response response;
    private JSONObject movements;
    private int positionX;
    private int positionY;
    private int[] position;
    private JSONObject objects;



    public ForwardResponse(Robot robot){
        super(robot);
    }

    public JSONObject executeRsponse () {


            response = new Response(robot);
            JSONObject response = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject Data = new JSONObject();
            JSONObject State = new JSONObject();

//            this.positionX = robot.getPosition().getX();
//
//            this.positionY = robot.getPosition().getY();
//            this.position = new int[]{positionX, positionY};
//            this.Direction = new JSONObject();
            jsonArray.put(0);
            jsonArray.put(0);
            Direction direction = Direction.NORTH;
            String result = "OK";

            Data.put("messgae", "done");
            State.put("position",jsonArray );
            State.put("direction", direction);

            response.put("result",result);
            response.put("data",Data);
            response.put("state",State);

            return response;
        }

}