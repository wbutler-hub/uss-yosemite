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
    String status = new String();

    private int positionX ;
    private int positionY ;
    private Command Forward = new ForwardCommand("");
    private Command Back = new BackCommand("");
    private Command Mine = new MineCommand();
    private Command Repair = new RepairCommand();
    private int[] position ;

    public  Response( Robot robot){
        this.robot = robot;

    }


    public void setMovement(String instruction){
        String[] args =instruction.toLowerCase().trim().split(" ");


        this.movement = new JSONObject();
        this.Direction = new JSONObject();

        if(args[0].equals("forward")) {
            int nrSteps = Integer.parseInt(args[1]);
            this.movement.put("forward", nrSteps);
            System.out.println("updating forward");////////////////////////

        }else if(args[0].equals("back")){
            int nrSteps = Integer.parseInt(args[1]);
            this.movement.put("back",nrSteps);
        }
    }
    public void setData(){
        this.data = new JSONObject();

        System.out.println(this.message);
        this.data.put("message",this.message);
    }
    public void setStatus() {
        this.state = new JSONObject();
        this.positionX = robot.getPosition().getX();
        this.positionY = robot.getPosition().getY();
        this.position = new int[]{positionX, positionY};


        this.state.put("direction", robot.getCurrentDirection());
        this.state.put("position", position);
        this.state.put("movement", this.movement);
        if (Mine.mine == true || Repair.repair == true && Forward.forward == false && Back.back == false) {
            this.state.put("status", this.status);
        }
    }

    public void setResult(){
        this.result = new JSONObject();
        this.resp = new JSONArray();
        if(Mine.mine == false && Back.back == false && Forward.forward == true
                && Repair.repair == false){
            this.message = "Done";
            this.resp.put("OK!@");
            this.result.put("data",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);
            System.out.println(this.message);
            System.out.println("this is forward "+ this.resp);
        } else if( Mine.mine == false && Back.back == true && Forward.forward == false
                && Repair.repair == false){
            this.resp.put("OK");
            this.message = "Done2";
            this.result.put("data",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);
            System.out.println("this is back "+ this.resp);
        }else if(Mine.mine == true && Back.back == false && Forward.forward == false
                && Repair.repair == false){
            this.resp.put("OK");
            this.message = "Done3";
            this.status = "SETMINE";
            this.result.put("result",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);
            System.out.println("this is mine " + this.resp);
        }else if(Mine.mine == false && Back.back == false && Forward.forward == false
                && Repair.repair == true){
            this.resp.put("OK");
            this.message = "Done4";
            this.status = "REPAIR";
            this.result.put("result",this.data);
            this.resp.put(this.result);
            this.resp.put(this.state);

        }
        else{
            this.result.put("Error", "I fucked up");
        }
    }



    public void setResponse(){
        this.response = new JSONObject();
        this.response.put("result",this.resp);


        System.out.println(69);
        System.out.println(this.resp);

    }

    public JSONObject getStatus(){return this.response;}


}
