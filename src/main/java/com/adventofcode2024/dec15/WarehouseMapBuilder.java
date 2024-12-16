package com.adventofcode2024.dec15;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode2024.common.Point;

class WarehouseMapBuilder {

    private int height = 0;
    private final Set<Point> wallPositions = new HashSet<>();
    private final Set<Point> boxPositions = new HashSet<>();
    private Point robotPosition;

    void addLine( String inputLine ) {
        for ( int x = 0; x < inputLine.length(); ++x ) {
            switch( inputLine.charAt( x ) ) {
                case '#' -> wallPositions.add( new Point( x, height ) );
                case 'O' -> boxPositions.add( new Point( x, height ) );
                case '@' -> robotPosition = new Point( x, height );
                case '.' -> {}
                default -> throw new IllegalArgumentException( "Unrecognized character " + inputLine.charAt( x ) );
            }
        }
        ++height;
    }

    WarehouseMap build() {
        return new WarehouseMap( wallPositions, boxPositions, robotPosition );
    }
}
