package com.adventofcode2024.dec06;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

class LabMapBuilder {

    private int width = 0;
    private int height = 0;
    private Guard guard;
    private final Set<Point> obstaclePositions = new HashSet<>();

    void addRow( String inputLine ) {
        width = Math.max( width, inputLine.length() );
        int y = height++;
        for ( int x = 0; x < inputLine.length(); ++x ) {
            switch( inputLine.charAt( x ) ) {
                case '^' -> guard = new Guard( new OrientedPosition( new Point( x, y ), Direction.UP ) );
                case '#' -> obstaclePositions.add( new Point( x, y ) );
                case '.' -> {}
                default -> throw new IllegalArgumentException( "Unrecognized character in input: " + inputLine.charAt( x ) );
            }

        }
    }

    LabMap build() {
        if ( guard == null ) {
            throw new IllegalStateException( "Guard not set before creating map." );
        }
        return new LabMap( width, height, guard, obstaclePositions );
    }
}
