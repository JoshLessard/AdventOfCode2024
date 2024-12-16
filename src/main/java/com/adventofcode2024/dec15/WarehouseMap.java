package com.adventofcode2024.dec15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class WarehouseMap {

    private final Set<Point> wallPositions;
    private final Set<Point> boxPositions;
    private Point robotPosition;

    WarehouseMap( Set<Point> wallPositions, Set<Point> boxPositions, Point robotPosition ) {
        this.wallPositions = Set.copyOf( wallPositions );
        this.boxPositions = new HashSet<>( boxPositions );
        this.robotPosition = robotPosition;
    }

    void moveRobot( Direction direction ) {
        Point desiredNextRobotPosition = robotPosition.nextPoint( direction );
        if ( isUnoccupied( desiredNextRobotPosition ) ) {
            robotPosition = desiredNextRobotPosition;
        } else if ( containsBox( desiredNextRobotPosition ) ) {
            List<Point> boxPositionsToMove = new ArrayList<>();
            Point currentPosition = desiredNextRobotPosition;
            while ( containsBox( currentPosition ) ) {
                boxPositionsToMove.add( currentPosition );
                currentPosition = currentPosition.nextPoint( direction );
            }
            if ( ! containsWall( currentPosition ) ) {
                robotPosition = robotPosition.nextPoint( direction );
                int numberOfBoxesToMove = boxPositionsToMove.size();
                Point firstBoxPositionToMove = boxPositionsToMove.get( 0 );
                boxPositions.remove( firstBoxPositionToMove );
                for ( int i = 0; i < numberOfBoxesToMove; ++i ) {
                    firstBoxPositionToMove = firstBoxPositionToMove.nextPoint( direction );
                }
                boxPositions.add( firstBoxPositionToMove );
            }
        }
    }

    private boolean isUnoccupied( Point position ) {
        return ! containsWall( position )
            && ! containsBox( position )
            && ! containsRobot( position );
    }

    private boolean containsWall( Point position ) {
        return wallPositions.contains( position );
    }

    private boolean containsBox( Point position ) {
        return boxPositions.contains( position );
    }

    private boolean containsRobot( Point position ) {
        return robotPosition.equals(position);
    }

    int gpsCoordinateSums() {
        return boxPositions
            .stream()
            .mapToInt( position -> 100 * position.y() + position.x() )
            .sum();
    }
}
