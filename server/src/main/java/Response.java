import org.json.JSONObject;

public class Response {
    JSONObject movement;
    Robot robot;
    public  Response( Robot robot){

        this.robot = robot;

    }

    public Response() {

    }

//    public void setMovement(String instruction) {
//        String[] args = instruction.toLowerCase().trim().split(" ");
//        this.movement = new JSONObject();
//
//        this.movement = new JSONObject();
//
//        if (args[0].equals("forward")) {
//            int nrSteps = Integer.parseInt(args[1]);
//            this.movement.put("forward", nrSteps);
//            System.out.println("updating forward");////////////////////////
//
//        }else if(args[0].equals("back")){
//            int nrSteps = Integer.parseInt(args[1]);
//            this.movement.put("back", nrSteps);
//            System.out.println("updating back");////////////////////////
//        }
//    }
    public JSONObject setResult(String instruction){


        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "forward":
                return new ForwardResponse(robot).executeRsponse();
            case "back":
                return new BackResponse().executeRsponse();
            case "mine":
                return new MineResponse().executeRsponse();
            case "repair":
                return new RepairResponse().executeRsponse();
        }
        return null;
    }





}
