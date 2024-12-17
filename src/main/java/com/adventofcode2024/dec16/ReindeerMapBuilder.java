package com.adventofcode2024.dec16;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

import java.util.HashSet;
import java.util.Set;

class ReindeerMapBuilder {

    private int height;
    private OrientedPosition startPosition;
    private OrientedPosition endPosition;
    private final Set<Point> wallPositions = new HashSet<>();

    void addLine( String inputLine ) {
        for ( int x = 0; x < inputLine.length(); ++x ) {
            switch ( inputLine.charAt( x ) ) {
                case '.' -> {}
                case 'S' -> startPosition = new OrientedPosition( new Point( x, height ), Direction.RIGHT );
                case 'E' -> endPosition = new OrientedPosition( new Point( x, height ), Direction.UP );
                case '#' -> wallPositions.add( new Point( x, height ) );
                default -> throw new IllegalArgumentException( "Unrecognized character: " + inputLine.charAt( x ) );
            }
        }
        ++height;
    }

    ReindeerMap build() {
        return new ReindeerMap( startPosition, endPosition, wallPositions );
    }
}
