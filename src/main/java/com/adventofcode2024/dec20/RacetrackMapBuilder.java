package com.adventofcode2024.dec20;

import com.adventofcode2024.common.Point;

import java.util.HashSet;
import java.util.Set;

class RacetrackMapBuilder {

    private int width = 0;
    private int height = 0;
    private Point startingPosition;
    private Point endingPosition;
    private final Set<Point> wallPositions = new HashSet<>();

    void addLine( String inputLine ) {
        for ( int x = 0; x < inputLine.length(); ++x ) {
            switch ( inputLine.charAt( x ) ) {
                case 'S' -> startingPosition = new Point( x, height );
                case 'E' -> endingPosition = new Point( x, height );
                case '#' -> wallPositions.add( new Point( x, height ) );
            }
        }
        width = Math.max( width, inputLine.length() );
        ++height;
    }

    RacetrackMap build() {
        return new RacetrackMap( width, height, startingPosition, endingPosition, wallPositions );
    }
}
