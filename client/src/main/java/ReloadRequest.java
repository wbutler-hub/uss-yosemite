import org.json.JSONArray;

public class ReloadRequest extends Request {
    /**
     * Constructor for the reload command
     * @param name
     */
    public ReloadRequest(String name) {
        super(name, "reload", new JSONArray("[]"));
    }
}
