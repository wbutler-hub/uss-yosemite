import org.json.JSONArray;
import org.json.JSONObject;

public class ForwardResponse extends Response {

    Response response;


    public ForwardResponse(Robot robot){

        super(robot);
        this.robot = robot;
    }

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
            if(args[0].equals("forward")){
               movement = args[0];
               steps = args[1];
            }
            if(args[0].equals("right") ){
                turn = "right";
            }else if( args[0].equals("left")){
                turn = "left";
            }

            String result = "OK";

            Data.put("messgae", "done");
            Data.put("movement",movement);
            Data.put("steps",steps);
            Data.put("turn",turn);
            State.put("position",position );
            State.put("direction", direction);

            response.put("result",result);
            response.put("data",Data);
            response.put("state",State);

            return response;
        }

}