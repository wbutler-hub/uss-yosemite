package za.co.wethinkcode;

import org.json.JSONArray;

public class TurnRequest extends Request {
    /**
     * Constructor for thr turn command (right, left)
     * @param name
     * @param right
     */
    public TurnRequest(String name, boolean right) {
        super(name, "turn", new JSONArray("[" + (right? "right" : "left") + "]"));
    }
}
