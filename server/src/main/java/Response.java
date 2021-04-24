import org.json.JSONObject;

public class Response {

    Robot robot;
    public  Response( Robot robot){
        this.robot = robot;

    }

    public Response() {

    }

    public static JSONObject setResult(String instruction, Robot robot){


        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "forward":
                return new ForwardResponse(robot).executeRsponse(instruction);
            case "back":
                return new BackResponse(robot).executeRsponse(instruction);
            case "mine":
                return new MineResponse(robot).executeRsponse();
            case "repair":
                return new RepairResponse(robot).executeRsponse();
            case "fire":
                return new FireResponse(robot).executeRsponse();
        }
        return null;
    }





}
