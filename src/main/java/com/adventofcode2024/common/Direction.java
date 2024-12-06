package com.adventofcode2024.common;

public enum Direction {
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    UP_LEFT;

    public Direction turnRight() {
        return switch ( this ) {
            case UP -> UP_RIGHT;
            case UP_RIGHT -> RIGHT;
            case RIGHT -> DOWN_RIGHT;
            case DOWN_RIGHT -> DOWN;
            case DOWN -> DOWN_LEFT;
            case DOWN_LEFT -> LEFT;
            case LEFT -> UP_LEFT;
            case UP_LEFT -> UP;
        };
    }
}
