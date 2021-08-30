package za.co.wethinkcode;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class LaunchRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientTwo = new RobotWorldJsonClient();
    private final RobotWorldClient serverClientThree = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientTwo.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClientThree.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
        serverClientTwo.disconnect();
        serverClientThree.disconnect();
    }

    @Test
    void validLaunchShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    @Test
    void invalidLaunchShouldFail() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }

    @Test
    void notEnoughSpaceForTheRobot() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        assertTrue(serverClientTwo.isConnected());
        assertTrue(serverClientThree.isConnected());

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode response = serverClient.sendRequest(request);

        String requestTwo = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode responseTwo = serverClientTwo.sendRequest(requestTwo);

        String requestThree = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        JsonNode responseThree = serverClientThree.sendRequest(requestThree);

        // Then I should get an error response
        assertNotNull(responseThree.get("result"));
        assertEquals("ERROR", responseThree.get("result").asText());

        // Then I should get a response from the server that 'Too many users are currently connected, Please wait for an opening.'"
        assertNotNull(responseThree.get("data"));
        assertNotNull(responseThree.get("data").get("message"));
        assertFalse(responseThree.get("data").get("message").asText().contains("Too many users are currently connected, Please wait for an opening."));
    }

    @Test
    void nameAlreadyExists(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        assertTrue(serverClientTwo.isConnected());

        // When I am requested to name the robot
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"tank\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // And I enter a robot with the same name
        String requestTwo = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"sniper\",\"3\",\"5\"]" +
                "}";
        JsonNode responseTwo = serverClientTwo.sendRequest(requestTwo);

        System.out.println(responseTwo);

        // Then I should get an error response
        assertNotNull(responseTwo.get("result"));
        assertEquals("ERROR", responseTwo.get("result").asText());

        // And the message "Too many of you in this world"
        assertNotNull(responseTwo.get("data"));
        assertNotNull(responseTwo.get("data").get("message"));
        assertTrue(responseTwo.get("data").get("message").asText().contains("Too many of you in this world"));
    }

    @Test
    void robotState(){
        //Given that I am connected to the Robot World server
        assertTrue(serverClient.isConnected());

        //When I send a valid launch request to the server
        //And I want to check the current state of my robot
        String request = "{" +
             "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"tank\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        //Then I should get a response saying the current state of my robot
        assertNotNull(response.get("state"));
    }
  @Test
  void robotInvalidState(){
      //When I send a valid launch request to the server
      String request = "{" +
        "  \"robot\": \"HAL\"," +
        "  \"command\": \"luanch\"," +
        "  \"arguments\": [\"tank\",\"5\",\"5\"]" +
        "}";
      JsonNode response = serverClient.sendRequest(request);
      //And I want to check the current state of my robot
      assertNotNull(response.get("state"));

  //      And the Robot is not connected to the Robot World server
  //      Then I should get and an "ERROR" message saying the robot is not connected to the world.
      assertNotNull(response.get("result"));
      assertEquals("ERROR", response.get("result").asText());
  }
}
