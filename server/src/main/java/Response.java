import org.json.JSONObject;

public class Response {
    Robot robot;
    JSONObject response;
    JSONObject state ;
    JSONObject movement;

    private int positionX ;
    private int positionY ;
    private Direction direcrion ;
    private Command command;
    private int[] position ;


    public void setStatus() {
        this.state = new JSONObject();
        this.state.put("direction", direcrion);
        this.state.put("position",position);
        ///this.state.put("movement",movement);
    }

    public void setMovement(String instruction){
        String[] args =instruction.toLowerCase().trim().split(" ");
        int nrSteps = Integer.parseInt(args[1]);

        this.movement = new JSONObject();

        if(args[0].equals("forward")) {
            this.movement.put("forward", nrSteps);

        }else if(args[0].equals("back")){
            this.movement.put("back",nrSteps);
        }
    }
    public void setResponse(){
        this.response = new JSONObject();
        this.response.put("state",this.state);
        this.response.put("movement",this.movement);
    }
    public  Response( Robot robot){
        this.robot = robot;
        this.positionX = robot.getPosition().getX();
        this.positionY = robot.getPosition().getY();
        this.direcrion = robot.getCurrentDirection();
        this.position  = new int[]{positionX, positionY};

    }
    public JSONObject getStatus(){return this.response;}


}
