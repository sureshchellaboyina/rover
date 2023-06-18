package com.suresh.rover.model;

public class Rover {
    private final String name;
    private final Coordinate coordinate;
    private Direction direction;

    public Rover(String name, Coordinate coordinate, Direction direction) {
        this.name = name;
        this.coordinate = coordinate;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }

    public void moveForward() {
        coordinate.moveForward(direction);
    }

    public void moveBackward() {
        coordinate.moveBackward(direction);
    }

    public void rotateClockwise() {
        direction = direction.rotateClockwise();
    }

    public void rotateAntiClockwise() {
        direction = direction.rotateAntiClockwise();
    }


    public void executeCommands(String commands) {
        for (char command : commands.toCharArray()) {
            switch (command) {
                case 'f':
                    moveForward();
                    break;
                case 'b':
                    moveBackward();
                    break;
                case 'r':
                    rotateClockwise();
                    break;
                case 'l':
                    rotateAntiClockwise();
                    break;
            }
        }
    }
}

