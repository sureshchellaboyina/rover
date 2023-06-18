package com.suresh.rover.model;

public enum Direction {
    N("North"),
    S("South"),
    E("East"),
    W("West");

    private final String fullName;

    Direction(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    //r – Rotate 90 degree clock-wise from current direction
    public Direction rotateClockwise() {
        switch (this) {
            case N:
                return E;
            case E:
                return S;
            case S:
                return W;
            case W:
                return N;
        }
        return this;
    }

    //l – Rotate 90 degree anti clock-wise from current direction
    public Direction rotateAntiClockwise() {
        switch (this) {
            case N:
                return W;
            case W:
                return S;
            case S:
                return E;
            case E:
                return N;
        }
        return this;
    }


}
