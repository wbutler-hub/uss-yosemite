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
}
