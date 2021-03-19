import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Server {
    private int width;
    private int height;

    public Server() throws IOException{
        getConfig();
    }

    private void getConfig() throws IOException {
        Properties prop = new Properties();
        InputStream propertyStream = new FileInputStream("server/resources/config.properties");

        if(propertyStream != null){
            prop.load(propertyStream);
        }
        else {
            throw new FileNotFoundException("config.properties not found.");
        }
        this.width = Integer.parseInt(prop.getProperty("width"));
        this.height = Integer.parseInt(prop.getProperty("height"));
    }


    public static void main(String[] args) throws IOException {
        Server s = new Server();
        System.out.println(s.getHeight());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
