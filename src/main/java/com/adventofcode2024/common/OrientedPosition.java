package com.adventofcode2024.common;

public record OrientedPosition( Point position, Direction direction ) {

    public OrientedPosition nextOrientedPosition() {
        return new OrientedPosition( position.nextPoint( direction ), direction );
    }

    public OrientedPosition right90Degrees() {
        // Directions rotate by 45 degrees at a time
        return new OrientedPosition( position, direction.turnRight().turnRight() );
    }
}
