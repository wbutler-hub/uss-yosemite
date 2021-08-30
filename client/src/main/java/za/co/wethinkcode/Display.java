package za.co.wethinkcode;

import org.json.JSONArray;
import org.json.JSONObject;

public class Display {
    private final Turtle player;
    private final Turtle obstruction;
    private JSONArray position;

    public Display() {
        this.player = new Turtle();
        this.obstruction = new Turtle();
        player.hide();
        player.up();
        player.penColor("green");
        obstruction.hide();
        obstruction.up();
    }

    /**
     * Creates the obstacle in the robots world
     * @param list
     */
    public void drawObstruction(JSONArray list) {
        for (int i = 0; i < list.length(); i++) {
            JSONObject key = (JSONObject) list.get(i);
                String type = (String) key.get("type");
                int x = (int)this.position.get(0);
                int y = (int)this.position.get(1);
                String direction = (String) key.get("Direction");
                String distance = (String) key.get("Distance");
                if (type.equals("Robot")) {
                    this.obstruction.penColor("red");
                }
                else {
                    this.obstruction.penColor("yellow");
                }
                    if(direction.equals("North")) {
                        y = y + Integer.parseInt(distance);
                    }
                    if(direction.equals("East")) {
                        x = x + Integer.parseInt(distance);

                    }
                    if(direction.equals("West")) {
                        x = x - Integer.parseInt(distance);

                    }
                    if(direction.equals("South")) {
                        y = y - Integer.parseInt(distance);

                    }
                    this.obstruction.setPosition(x,y);
                    this.obstruction.dot();
                    this.obstruction.clear();
                }
        }

    /**
     * Creates the robot and positions it (0,0)
     * @param position
     */
    public void drawPlayer(JSONArray position) {
        this.position = position;
        this.player.setPosition((int)position.get(0),(int)position.get(1));
        this.player.dot();
        this.player.clear();
        this.obstruction.setPosition(0,0);
        this.obstruction.clear();
    }

//    public void resetPlayer() {
//        this.player.clear();
//    }


}
