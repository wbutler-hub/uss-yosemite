import org.json.JSONArray;

public class MovementRequest extends Request {
    /**
     * Constructor for the movement commands (forward, back)
     * @param name
     * @param forward
     * @param steps
     */
    public MovementRequest(String name, boolean forward, int steps) {
        super(name, forward? "forward" : "back", new JSONArray("[" + steps + "]"));
    }
}
