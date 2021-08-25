import org.json.JSONArray;

public class MineRequest extends Request {
    /**
     * Constructor for the mine command
     * @param name
     */
    public MineRequest(String name) {
        super(name, "mine", new JSONArray("[]"));
    }
}
