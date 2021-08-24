import org.json.JSONObject;

public class Response {
    Robot robot;

    /**
     * Response constructor, sets the robot to this robot.
     * @param robot: pass throught the robot
     * */
    public Response(Robot robot){
        this.robot = robot;
    }

    /**
     * Empty default constructor?
     * */
    public Response() {

    }

    /**
     * It checks if the instruction matches a command that the robot can do <br/>
     * If it does, then it will execute the response for that command, else if it matches nothing it returns null
     * @param instruction: String instruction (which is the command from server(user input)).
     * @param robot: the robot object:
     * @return: either null, or the new object call if the instruction[0] matches a command
     * */
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
            case "look":
                return new LookResponse(robot).executeRsponse();
            case "reload":
                return new ReloadResponse(robot).executeRsponse();
            case "turn":
                if (args[1].equals("right")) {
                    return new LeftResponse(robot).executeRsponse();
                }
                if (args[1].equals("left")) {
                    return new LeftResponse(robot).executeRsponse();
                }
        }

        return null;
    }
}
