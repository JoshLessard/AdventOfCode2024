package com.adventofcode2024.dec15;

import java.util.Objects;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class DoubleWidthBox {

    private final Point leftPosition;
    private final Point rightPosition;

    DoubleWidthBox( Point leftPosition ) {
        this.leftPosition = leftPosition;
        this.rightPosition = leftPosition.nextPoint( Direction.RIGHT );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleWidthBox that)) return false;
        return leftPosition.equals(that.leftPosition) && rightPosition.equals(that.rightPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPosition, rightPosition);
    }

    Set<Point> positions() {
        return Set.of( leftPosition, rightPosition );
    }

    Point leftPosition() {
        return leftPosition;
    }

    Set<Point> nextPositions( Direction direction ) {
        return switch ( direction ) {
            case UP -> Set.of( leftPosition.nextPoint( Direction.UP ), rightPosition.nextPoint( Direction.UP ) );
            case RIGHT -> Set.of( rightPosition.nextPoint( Direction.RIGHT ) );
            case DOWN -> Set.of( leftPosition.nextPoint( Direction.DOWN ), rightPosition.nextPoint( Direction.DOWN ) );
            case LEFT -> Set.of( leftPosition.nextPoint( Direction.LEFT ) );
            default -> throw new IllegalArgumentException( "Unrecognized direction: " + direction );
        };
    }

    DoubleWidthBox moved( Direction direction ) {
        return new DoubleWidthBox( leftPosition.nextPoint( direction ) );
    }
}
