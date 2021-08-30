package za.co.wethinkcode;

import org.json.JSONArray;

public class StateRequest extends Request {
    /**
     * Constructor for the state command
     * @param name
     */
    public StateRequest(String name) {
        super(name, "state", new JSONArray("[]"));
    }
}
