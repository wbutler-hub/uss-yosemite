package za.co.wethinkcode;

import org.json.JSONArray;

public class RepairRequest extends Request {
    /**
     * Constructor for the repair commands
     * @param name
     */
    public RepairRequest(String name) {
        super(name, "repair", new JSONArray("[]"));
    }
}
