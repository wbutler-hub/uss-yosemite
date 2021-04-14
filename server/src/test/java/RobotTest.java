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

    @Test
    void repair() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        int expectedShield = 3;
        robot.updateShield("shot");
        robot.updateShield("shot");
        assertEquals(1,robot.getShield());
        RepairCommand command = new RepairCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(expectedShield,robot.getShield());
        assertEquals("REPAIR", robot.getStatus());
    }

    @Test
    void mine() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        MineCommand command = new MineCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(world.getMineList().size(),1);
        robot.updatePosition(10);
        MineCommand command1 = new MineCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(world.getMineList().size(),2);
        assertEquals("SETMINE", robot.getStatus());
    }
}
