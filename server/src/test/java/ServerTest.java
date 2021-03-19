import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    @Test
    public void testConfig() throws IOException {
        Server s= new Server();
        System.out.println(s.getHeight());
//        assertEquals(200, s.getWidth());
//        assertEquals(200, s.getHeight());
    }
}