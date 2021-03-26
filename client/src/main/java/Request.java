import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Request {
    JSONObject request;

    public Request(String name, String command, JSONArray arguments) {
        this.request = new JSONObject();
        request.put("name", name);
        request.put("command", command);
        request.put("arguments", arguments);
    }

    public String getRequest() {
        return request.toString();
    }

    public static Request create(String instruction, String name) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "launch":
//                return new LaunchRequest(name);
            case "repair":
                return new RepairRequest(name);
            case "forward":
                return new MovementRequest(name, true, Integer.parseInt(args[1]));
            case "back":
                return new MovementRequest(name, false, Integer.parseInt(args[1]));
            case "right":
                return new TurnRequest(name, true);
            case "left":
                return new TurnRequest(name, false);
            case "reload":
                return new ReloadRequest(name);
            case "look":
                return new LookRequest(name);
            case "mine":
                return new MineRequest(name);
            case "state":
                return new StateRequest(name);

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
