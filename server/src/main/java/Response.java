import org.json.JSONArray;
import org.json.JSONObject;

public class Response {
    Robot robot;
    JSONObject response;
    JSONObject state ;
    JSONObject movement;
    JSONObject result;
    JSONObject data;
    JSONArray resp;
    JSONObject Direction;
    String message = new String();

    private int positionX ;
    private int positionY ;
    private Direction direcrion ;
    private Command Forward = new ForwardCommand("");
    private Command Back = new BackCommand("");
    private int[] position ;

    public  Response( Robot robot){
        this.robot = robot;
        this.positionX = robot.getPosition().getX();
        this.positionY = robot.getPosition().getY();
        this.direcrion = robot.getCurrentDirection();
        this.position  = new int[]{positionX, positionY};

    }


    public void setMovement(String instruction){
        String[] args =instruction.toLowerCase().trim().split(" ");


        this.movement = new JSONObject();
        this.Direction = new JSONObject();

        if(args[0].equals("forward")) {
            int nrSteps = Integer.parseInt(args[1]);
            this.movement.put("forward", nrSteps);

        }else if(args[0].equals("back")){
            int nrSteps = Integer.parseInt(args[1]);
            this.movement.put("back",nrSteps);
        }
    }
    public void setStatus() {
        this.state = new JSONObject();
        this.state.put("direction", direcrion);
        this.state.put("position",position);
        this.state.put("movement",this.movement);
    }

    public void setResult(){

        this.result = new JSONObject();
        this.resp = new JSONArray();
        if(Forward.forward = true){
            this.message = "Done";
            this.resp.put("OK");
            this.result.put("data",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);
            System.out.println("this is forward "+ this.resp);
        } else if( Back.back = true){
            this.resp.put("OK");
            this.message = "Done";
            this.result.put("data",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);
            System.out.println("this is back "+ this.resp);
        }
        else{
            this.result.put("Error", "I fucked up");
        }
    }

    public void setData(){
        this.data = new JSONObject();

        this.data.put("message",this.message);
    }

    public void setResponse(){
        this.response = new JSONObject();
        this.response.put("result",this.resp);


        System.out.println(this.resp);

    }

    public JSONObject getStatus(){return this.response;}


}
