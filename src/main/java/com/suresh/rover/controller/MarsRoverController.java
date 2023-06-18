package com.suresh.rover.controller;

import com.suresh.rover.model.Coordinate;
import com.suresh.rover.model.Direction;
import com.suresh.rover.model.Rover;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
public class MarsRoverController {
    private Map<String, Rover> rovers = new HashMap<>();

    @PostMapping("/rover")
    public ResponseEntity<String> createRover(@RequestBody String coordinate) {
        String[] parts = coordinate.split(",");
        if (parts.length != 3) {
            return ResponseEntity.badRequest().body("Invalid coordinate format");
        }

        int x, y;
        try {
            x = Integer.parseInt(parts[0].trim());
            y = Integer.parseInt(parts[1].trim());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid coordinate values");
        }

        String directionStr = parts[2].trim().toUpperCase();
        Direction direction;
        try {
            direction = Direction.valueOf(directionStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid direction: " + directionStr);
        }

        String roverName = "R" + (rovers.size() + 1);
        Rover rover = new Rover(roverName, new Coordinate(x, y), direction);
        rovers.put(roverName, rover);

        return ResponseEntity.ok("New Rover: " + rover.getName() + ", Coordinate: " +
                rover.getCoordinate().getX() + "," + rover.getCoordinate().getY() +
                ", Direction: " + rover.getDirection().getFullName());
    }

    @GetMapping("/rover/{roverName}/position")
    public ResponseEntity<String> getCurrentPosition(@PathVariable String roverName) {
        Rover rover = rovers.get(roverName);
        if (rover == null) {
            return ResponseEntity.badRequest().body("Rover not found: " + roverName);
        }

        return ResponseEntity.ok("Rover: " + rover.getName() + ", Current Position: " +
                "Coordinate: " + rover.getCoordinate().getX() + "," + rover.getCoordinate().getY() +
                ", Direction: " + rover.getDirection().getFullName());
    }

    @PostMapping("/rover/{roverName}/move")
    public ResponseEntity<String> moveRover(@PathVariable String roverName, @RequestBody String commands) {
        Rover rover = rovers.get(roverName);
        if (rover == null) {
            return ResponseEntity.badRequest().body("Rover not found: " + roverName);
        }

        rover.executeCommands(commands);

        return ResponseEntity.ok("Rover: " + rover.getName() + ", Final Position: " +
                "Coordinate: " + rover.getCoordinate().getX() + "," + rover.getCoordinate().getY() +
                ", Direction: " + rover.getDirection().getFullName());
    }
}
