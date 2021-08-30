package za.co.wethinkcode;

import org.json.JSONArray;

public class LookRequest extends Request {
    /**
     * Constructor for the look command
     * @param name
     */
    public LookRequest(String name) {
        super(name, "look", new JSONArray("[]"));
    }
}
