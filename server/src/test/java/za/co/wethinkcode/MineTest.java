package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.MineCommand;
import za.co.wethinkcode.Position;
import za.co.wethinkcode.Robot;
import za.co.wethinkcode.World;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MineTest {

    @Test
    void mine() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        robot.setPosition(new Position(0,0));
        MineCommand command = new MineCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(world.getMineList().size(),1);
        robot.updatePosition(10);
        MineCommand command1 = new MineCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(world.getMineList().size(),2);
        assertEquals("SETMINE", robot.getStatus());
    }

    @Test
    void mineExplode() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        robot.setPosition(new Position(0,0));
        MineCommand command = new MineCommand();
        assertEquals(robot.getShield(),3);
        assertTrue(robot.handleCommand(command));
        assertEquals(world.getMineList().size(),1);
        robot.updatePosition(-1);
        assertEquals(world.getMineList().size(),0);
        assertEquals(robot.getShield(),0);
        assertEquals("SETMINE", robot.getStatus());
    }
}
