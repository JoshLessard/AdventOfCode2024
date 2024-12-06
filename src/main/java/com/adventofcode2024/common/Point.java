package com.adventofcode2024.common;

public record Point(int x, int y ) {

    public Point nextPoint( Direction direction ) {
        return switch ( direction ) {
            case UP -> new Point( x, y - 1 );
            case UP_RIGHT -> new Point( x + 1, y - 1 );
            case RIGHT -> new Point( x + 1, y );
            case DOWN_RIGHT -> new Point( x + 1, y + 1 );
            case DOWN -> new Point( x, y + 1 );
            case DOWN_LEFT -> new Point( x - 1, y + 1 );
            case LEFT -> new Point( x - 1, y );
            case UP_LEFT -> new Point( x - 1, y - 1 );
        };
    }
}
