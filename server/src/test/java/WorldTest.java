import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest {
    @Test
    public void testConfig() throws IOException {
        World world = new World();
        assertEquals(200, world.getWidth());
        assertEquals(200, world.getHeight());
        assertEquals(20, world.getVisibility());
        assertEquals(3, world.getReloadSpeed());
        assertEquals(3, world.getRepairSpeed());
        assertEquals(3, world.getMineSpeed());

    }

    @Test
    public void testWorldHasObstructions() throws IOException {
        World world = new World();
        assertTrue(world.getObstacleList().size() >= 1);
        assertTrue(world.getPitList().size() >= 1);

    }
}
