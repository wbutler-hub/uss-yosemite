package za.co.wethinkcode;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotTest {

//    @Test
//    void forward() throws IOException {
//        za.co.wethinkcode.Robot robot;
//        za.co.wethinkcode.World world = new za.co.wethinkcode.World();
//        world.setObstructionsEmpty();
//        robot = za.co.wethinkcode.Robot.create("Dummy","test", world );
//        robot.setPosition(new za.co.wethinkcode.Position(0,0));
//        za.co.wethinkcode.Position expectedPosition = new za.co.wethinkcode.Position(0, 10);
//        za.co.wethinkcode.ForwardCommand command = new za.co.wethinkcode.ForwardCommand("10");
//        assertTrue(robot.handleCommand(command));
//        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
//        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
//        assertEquals("Moved forward by 10 steps.", robot.getStatus());
//    }
//
//    @Test
//    void back() throws IOException {
//        za.co.wethinkcode.Robot robot;
//        za.co.wethinkcode.World world = new za.co.wethinkcode.World();
//        world.setObstructionsEmpty();
//        robot = za.co.wethinkcode.Robot.create("Dummy","test", world );
//        robot.setPosition(new za.co.wethinkcode.Position(0,0));
//        za.co.wethinkcode.Position expectedPosition = new za.co.wethinkcode.Position(0, -10);
//        za.co.wethinkcode.BackCommand command = new za.co.wethinkcode.BackCommand("10");
//        assertTrue(robot.handleCommand(command));
//        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
//        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
//        assertEquals("Moved back by 10 steps.", robot.getStatus());
//    }

//    @Test
//    void left() throws IOException {
//        za.co.wethinkcode.Robot robot;
//        za.co.wethinkcode.World world = new za.co.wethinkcode.World();
//        world.setObstructionsEmpty();
//        robot = za.co.wethinkcode.Robot.create("Dummy","test", world );
//        robot.setPosition(new za.co.wethinkcode.Position(0,0));
//        za.co.wethinkcode.Position expectedPosition = new za.co.wethinkcode.Position(-10, 0);
//        za.co.wethinkcode.LeftCommand command = new za.co.wethinkcode.LeftCommand();
//        za.co.wethinkcode.ForwardCommand command1 = new za.co.wethinkcode.ForwardCommand("10");
//        assertTrue(robot.handleCommand(command));
//        assertTrue(robot.handleCommand(command1));
//        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
//        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
//        assertEquals("Moved forward by 10 steps.", robot.getStatus());
//    }

//    @Test
//    void right() throws IOException {
//        za.co.wethinkcode.Robot robot;
//        za.co.wethinkcode.World world = new za.co.wethinkcode.World();
//        world.setObstructionsEmpty();
//        robot = za.co.wethinkcode.Robot.create("Dummy","test", world );
//        robot.setPosition(new za.co.wethinkcode.Position(0,0));
//        za.co.wethinkcode.Position expectedPosition = new za.co.wethinkcode.Position(10, 0);
//        za.co.wethinkcode.RightCommand command = new za.co.wethinkcode.RightCommand();
//        za.co.wethinkcode.ForwardCommand command1 = new za.co.wethinkcode.ForwardCommand("10");
//        assertTrue(robot.handleCommand(command));
//        assertTrue(robot.handleCommand(command1));
//        assertEquals(expectedPosition.getX(), robot.getPosition().getX());
//        assertEquals(expectedPosition.getY(), robot.getPosition().getY());
//        assertEquals("Moved forward by 10 steps.", robot.getStatus());
//    }

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
    void reload() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        assertEquals(3,robot.getShots());
        int expectedShots = 3;
        robot.updateShots("shoot");
        robot.updateShots("shoot");
        assertEquals(1,robot.getShots());
        ReloadCommand command = new ReloadCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals(expectedShots,robot.getShots());
        assertEquals("RELOAD", robot.getStatus());
    }

    @Test
    void outOfAmmo() throws IOException {
        Robot robot;
        World world = new World();
        world.setObstructionsEmpty();
        robot = Robot.create("Dummy","test", world );
        assertEquals(3,robot.getShots());
        int expectedShots = 0;
        robot.updateShots("shoot");
        robot.updateShots("shoot");
        robot.updateShots("shoot");
        assertEquals(expectedShots,robot.getShots());
        Command command = new FireCommand();
        assertTrue(robot.handleCommand(command));
        assertEquals("Out Of Ammo", robot.getStatus());
    }
}
