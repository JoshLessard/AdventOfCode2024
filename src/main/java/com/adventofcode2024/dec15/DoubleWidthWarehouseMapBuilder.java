package com.adventofcode2024.dec15;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class DoubleWidthWarehouseMapBuilder {

    private int height = 0;
    private final Set<Point> wallPositions = new HashSet<>();
    private final Set<DoubleWidthBox> boxes = new HashSet<>();
    private Point robotPosition;

    void addLine( String inputLine ) {
        for ( int x = 0; x < inputLine.length(); ++x ) {
            switch( inputLine.charAt( x ) ) {
                case '#' -> {
                    Point leftWallPosition = new Point( x * 2, height );
                    Point rightWallPosition = leftWallPosition.nextPoint( Direction.RIGHT );
                    wallPositions.add( leftWallPosition );
                    wallPositions.add( rightWallPosition );
                }
                case 'O' -> boxes.add( new DoubleWidthBox( new Point( x * 2, height ) ) );
                case '@' -> robotPosition = new Point( x * 2, height );
                case '.' -> {}
                default -> throw new IllegalArgumentException( "Unrecognized character " + inputLine.charAt( x ) );
            }
        }
        ++height;
    }

    DoubleWidthWarehouseMap build() {
        return new DoubleWidthWarehouseMap( wallPositions, boxes, robotPosition );
    }
}
