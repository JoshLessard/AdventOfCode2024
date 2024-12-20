package com.adventofcode2024.dec20;

import com.adventofcode2024.common.Point;

record Cheat( Point startPosition, Point endPosition ) {

    int length() {
        return startPosition.manhattanDistanceTo( endPosition );
    }
}
