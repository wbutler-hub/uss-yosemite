package za.co.wethinkcode;

import org.json.JSONArray;

public class FireRequest extends Request {
    /**
     * Constructor for fire command
     * @param name
     */
    public FireRequest(String name) {
        super(name, "fire", new JSONArray("[]"));
    }
}
