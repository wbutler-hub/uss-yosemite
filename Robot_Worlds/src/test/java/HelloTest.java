import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloTest {
    @Test
    void testObstacleDimensions() {
        HelloWorld helloWorld = new HelloWorld();
        assertEquals("Hello World", helloWorld.hello());
    }
}
