package com.adventofcode2024.dec12;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

record Edge( Point location, Direction direction ) {

    boolean isDirectlyToRightOf( Edge otherEdge ) {
        Point thisLocation = location;
        Point thatLocation = otherEdge.location;

        return thisLocation.y() == thatLocation.y()
                && thisLocation.x() - thatLocation.x() == 1;
    }

    boolean isDirectlyBelow( Edge otherEdge ) {
        Point thisLocation = location;
        Point thatLocation = otherEdge.location;

        return thisLocation.x() == thatLocation.x()
                && thisLocation.y() - thatLocation.y() == 1;
    }
}