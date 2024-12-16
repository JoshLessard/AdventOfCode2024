package com.adventofcode2024.dec15;

import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class DoubleWidthWarehouseMap {

    private final Set<Point> wallPositions;
    private final Set<DoubleWidthBox> boxes;
    private final Map<Point, DoubleWidthBox> boxesByPosition = new HashMap<>();
    private Point robotPosition;

    DoubleWidthWarehouseMap( Set<Point> wallPositions, Set<DoubleWidthBox> boxes, Point robotPosition ) {
        this.wallPositions = Set.copyOf( wallPositions );
        this.boxes = new HashSet<>( boxes );
        mapBoxPositions( boxes );
        this.robotPosition = robotPosition;
    }

    private void mapBoxPositions( Set<DoubleWidthBox> boxes ) {
        boxes.forEach( box ->
            box.positions().forEach( position ->
                boxesByPosition.put( position, box )
            )
        );
    }

    void moveRobot( Direction direction ) {
        Point desiredNextRobotPosition = robotPosition.nextPoint( direction );
        if ( isUnoccupied( desiredNextRobotPosition ) ) {
            robotPosition = desiredNextRobotPosition;
        } else if ( containsBox( desiredNextRobotPosition ) ) {
            Set<DoubleWidthBox> boxesToMove = new HashSet<>();
            Set<DoubleWidthBox> nextBoxesToConsider = Set.of( boxesByPosition.get( desiredNextRobotPosition ) );
            while ( ! nextBoxesToConsider.isEmpty() ) {
                Set<Point> nextPositions = nextBoxesToConsider
                    .stream()
                    .flatMap( box -> box.nextPositions( direction ).stream() )
                    .collect( toSet() );
                if ( nextPositions.stream().anyMatch( this::containsWall ) ) {
                    // can't move
                    return;
                } else {
                    boxesToMove.addAll( nextBoxesToConsider );
                    nextBoxesToConsider = nextPositions
                        .stream()
                        .filter( this::containsBox )
                        .map( boxesByPosition::get )
                        .collect( toSet() );
                }
            }
            moveBoxes( boxesToMove, direction );
            robotPosition = desiredNextRobotPosition;
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
        return boxesByPosition.containsKey( position );
    }

    private boolean containsRobot( Point position ) {
        return robotPosition.equals( position );
    }

    private void moveBoxes( Set<DoubleWidthBox> boxes, Direction direction ) {
        removeBoxes( boxes );
        boxes
            .stream()
            .map( box -> box.moved( direction ) )
            .forEach( this::addBox );
    }

    private void removeBoxes( Set<DoubleWidthBox> boxes ) {
        boxes
            .stream()
            .flatMap( box -> box.positions().stream() )
            .forEach( boxesByPosition::remove );
        this.boxes.removeAll( boxes );
    }

    private void addBox( DoubleWidthBox box ) {
        box.positions()
            .forEach( position -> boxesByPosition.put( position, box ) );
        boxes.add( box );
    }

    int gpsCoordinateSums() {
        return boxes
            .stream()
            .map( DoubleWidthBox::leftPosition )
            .mapToInt( leftPosition -> 100 * leftPosition.y() + leftPosition.x() )
            .sum();
    }
}
