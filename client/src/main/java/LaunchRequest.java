import org.json.JSONArray;

public class LaunchRequest extends Request{
    /**
     * Constructor for launch robot
     * @param name
     * @param kind
     * @param maxShieldStrength
     * @param maxShots
     */
    public LaunchRequest(String name, String kind, int maxShieldStrength, int maxShots) {
        super(name, "launch",
                new JSONArray("[" + kind +", " + maxShieldStrength + ", " + maxShots + "]") );
    }
}
