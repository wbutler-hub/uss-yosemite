import org.json.JSONArray;

public class LaunchRequest extends Request{
    public LaunchRequest(String name, String kind, int maxShieldStrength, int maxShots) {
        super(name, "launch",
                new JSONArray("[" + kind +", " + maxShieldStrength + ", " + maxShots + "]") );
    }
}
