package com.adventofcode2024.dec06;

import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

class Guard {

    private final OrientedPosition originalOrientedPosition;
    private OrientedPosition currentOrientedPosition;

    Guard( OrientedPosition startingPosition ) {
        this.originalOrientedPosition = this.currentOrientedPosition = startingPosition;
    }

    Point currentPosition() {
        return currentOrientedPosition.position();
    }

    OrientedPosition currentOrientedPosition() {
        return currentOrientedPosition;
    }

    Point nextPosition() {
        return currentOrientedPosition.nextOrientedPosition().position();
    }

    void turnRight() {
        currentOrientedPosition = currentOrientedPosition.right90Degrees();
    }

    void moveForward() {
        currentOrientedPosition = currentOrientedPosition.nextOrientedPosition();
    }

    Guard atOriginalOrientedPosition() {
        return new Guard( originalOrientedPosition );
    }
}
