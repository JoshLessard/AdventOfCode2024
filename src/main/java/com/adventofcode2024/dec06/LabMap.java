package com.adventofcode2024.dec06;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

class LabMap {

    private final int width;
    private final int height;
    private final Guard guard;
    private final Set<Point> obstaclePositions;

    LabMap( int width, int height, Guard guard, Set<Point> obstaclePositions ) {
        this.width = width;
        this.height = height;
        this.guard = guard;
        this.obstaclePositions = Set.copyOf( obstaclePositions );
    }

    Statistics doGuardPatrol() {
        Set<Point> positionsVisited = new HashSet<>();
        Set<Point> newObstructionPositions = new HashSet<>();
        while ( isInbounds( guard.currentPosition() ) ) {
            positionsVisited.add( guard.currentPosition() );
            if ( addingObstacleCausesLoop( guard.nextPosition() ) ) {
                newObstructionPositions.add( guard.nextPosition() );
            }
            moveGuard();
        }

        return new Statistics( positionsVisited.size(), newObstructionPositions.size() );
    }

    private void moveGuard() {
        if ( isObstacleAhead() ) {
            guard.turnRight();
        } else {
            guard.moveForward();
        }
    }

    private boolean isInbounds( Point position ) {
        return
            position.x() >= 0 &&
            position.x() < width &&
            position.y() >= 0 &&
            position.y() < height;
    }

    private boolean addingObstacleCausesLoop( Point extraObstaclePosition ) {
        return copyOfMapWithExtraObstacle( extraObstaclePosition ).guardEntersLoop();
    }

    private LabMap copyOfMapWithExtraObstacle( Point extraObstaclePosition ) {
        HashSet<Point> obstaclePositions = new HashSet<>( this.obstaclePositions );
        obstaclePositions.add( extraObstaclePosition );
        return new LabMap(
            width,
            height,
            guard.atOriginalOrientedPosition(),
            obstaclePositions
        );
    }

    private boolean guardEntersLoop() {
        Set<OrientedPosition> orientedPositionsVisited = new HashSet<>();
        while ( isInbounds( guard.currentPosition() ) ) {
            if ( orientedPositionsVisited.contains( guard.currentOrientedPosition() ) ) {
                return true;
            }
            orientedPositionsVisited.add( guard.currentOrientedPosition() );
            moveGuard();
        }

        return false;
    }

    private boolean isObstacleAhead() {
        return obstaclePositions.contains( guard.nextPosition() );
    }

    record Statistics( int uniqueGuardPositionCount, int newObstructionPositionCount ) {
    }
}
