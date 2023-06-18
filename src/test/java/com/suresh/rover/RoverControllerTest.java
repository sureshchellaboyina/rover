package com.suresh.rover;

import com.suresh.rover.controller.MarsRoverController;
import com.suresh.rover.model.Coordinate;
import com.suresh.rover.model.Direction;
import com.suresh.rover.model.Rover;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RoverControllerTest {
    @Mock
    private Map<String, Rover> rovers;

    @InjectMocks
    private MarsRoverController roverController;

    public RoverControllerTest() {
        MockitoAnnotations.openMocks(this);
    }


    // Create Rover
    @Test
    void createRoverPositiveResponse() {
        String coordinate = "3,4,N";
        when(rovers.size()).thenReturn(0);

        ResponseEntity<String> response = roverController.createRover(coordinate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createRoverBadRequest() {
        String coordinate = "3,4";
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Invalid coordinate format");

        ResponseEntity<String> response = roverController.createRover(coordinate);

        assertEquals(expectedResponse, response);
    }

    // Current Position
    @Test
    void getCurrentPositionPositiveResponse() {
        String roverName = "R1";
        Rover rover = new Rover(roverName, new Coordinate(3, 4), Direction.N);
        when(rovers.get(roverName)).thenReturn(rover);

        ResponseEntity<String> response = roverController.getCurrentPosition(roverName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getCurrentPositionNegativeResponse() {
        String roverName = "R1";
        when(rovers.get(roverName)).thenReturn(null);
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Rover not found: R1");

        ResponseEntity<String> response = roverController.getCurrentPosition(roverName);

        assertEquals(expectedResponse, response);
    }

    // Move Rover
    @Test
    void moveRoverPositiveResponse() {
        String roverName = "R1";
        Rover rover = mock(Rover.class);
        Coordinate coordinate = new Coordinate(3, 4); // Create a non-null Coordinate object
        Direction direction = Direction.N;
        when(rover.getCoordinate()).thenReturn(coordinate); // Set up mock behavior
        when(rovers.get(roverName)).thenReturn(rover);
        when(rover.getDirection()).thenReturn(direction);

        ResponseEntity<String> response = roverController.moveRover(roverName, "f");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(rover, times(1)).executeCommands("f");
    }


    @Test
    void moveRoverNegativeResponse() {
        String roverName = "R1";
        when(rovers.get(roverName)).thenReturn(null);
        ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("Rover not found: R1");

        ResponseEntity<String> response = roverController.moveRover(roverName, "f");

        assertEquals(expectedResponse, response);
    }
}

