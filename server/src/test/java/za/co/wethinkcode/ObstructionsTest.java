package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Obstacle;
import za.co.wethinkcode.Pit;
import za.co.wethinkcode.Position;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ObstructionsTest {
    @Test
    void testObstacleDimensions() {
        Position position1 = new Position(1,5);
        Position position2 = new Position(5,1);
        Obstacle obstacle = new Obstacle(position1,position2);
        Pit pit = new Pit(position1,position2);
        assertEquals(1, obstacle.getBottomLeftPosition().getX());
        assertEquals(1, obstacle.getBottomLeftPosition().getY());
        assertEquals(1, pit.getBottomLeftPosition().getX());
        assertEquals(1, pit.getBottomLeftPosition().getY());
        assertEquals(5, obstacle.getSize());
    }

    @Test
    void testBlockPosition(){
        Position position1 = new Position(1,5);
        Position position2 = new Position(5,1);
        Obstacle obstacle = new Obstacle(position1,position2);
        Pit pit = new Pit(position1,position2);
        assertTrue(obstacle.blocksPosition(new Position(1,1)));
        assertTrue(pit.blocksPosition(new Position(1,1)));
        assertTrue(obstacle.blocksPosition(new Position(5,1)));
        assertTrue(obstacle.blocksPosition(new Position(1,5)));
        assertFalse(obstacle.blocksPosition(new Position(0,5)));
        assertFalse(pit.blocksPosition(new Position(0,5)));
        assertFalse(obstacle.blocksPosition(new Position(6,5)));
    }

    @Test
    void testBlockPath(){
        Position position1 = new Position(1,5);
        Position position2 = new Position(5,1);
        Obstacle obstacle = new Obstacle(position1,position2);
        Pit pit = new Pit(position1,position2);
        assertTrue(obstacle.blocksPath(new Position(1,0), new Position(1,50)));
        assertTrue(pit.blocksPath(new Position(1,0), new Position(1,50)));
        assertTrue(obstacle.blocksPath(new Position(2,-10), new Position(2, 100)));
        assertTrue(obstacle.blocksPath(new Position(-10,5), new Position(20, 5)));
        assertFalse(obstacle.blocksPath(new Position(0,1), new Position(0, 100)));
        assertFalse(pit.blocksPath(new Position(0,1), new Position(0, 100)));
        assertFalse(obstacle.blocksPath(new Position(1,6), new Position(1, 100)));

    }

}
