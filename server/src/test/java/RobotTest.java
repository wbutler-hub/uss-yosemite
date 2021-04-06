import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RobotTest {

    @Test
    void forward() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        Position expectedPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY()+10);
        ForwardCommand command = new ForwardCommand("10");
        assertTrue(robot.handleCommand(command));
        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void back() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        Position expectedPosition = new Position(robot.getPosition().getX(), robot.getPosition().getY()-10);
        BackCommand command = new BackCommand("10");
        assertTrue(robot.handleCommand(command));
        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
        assertEquals("Moved back by 10 steps.", robot.getStatus());
    }

    @Test
    void left() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        Position expectedPosition = new Position(robot.getPosition().getX()-10, robot.getPosition().getY());
        LeftCommand command = new LeftCommand();
        ForwardCommand command1 = new ForwardCommand("10");
        assertTrue(robot.handleCommand(command));
        assertTrue(robot.handleCommand(command1));
        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void right() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        Position expectedPosition = new Position(robot.getPosition().getX()+10, robot.getPosition().getY());
        RightCommand command = new RightCommand();
        ForwardCommand command1 = new ForwardCommand("10");
        assertTrue(robot.handleCommand(command));
        assertTrue(robot.handleCommand(command1));
        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }
}
