package com.adventofcode2024.dec14;

import com.adventofcode2024.common.Point;
import com.adventofcode2024.common.Slope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class RobotMap {

    private final int width;
    private final int height;
    private final List<Robot> robots;

    RobotMap( int width, int height, List<Robot> robots ) {
        this.width = width;
        this.height = height;
        this.robots = List.copyOf( robots );
    }

    int safetyFactorAfter( int seconds ) {
        List<Point> robotEndingPositions = robots
            .stream()
            .map( robot -> positionAfter( seconds, robot ) )
            .toList();
        return positionsPerQuadrant( robotEndingPositions )
            .entrySet()
            .stream()
            .filter( e -> e.getKey() != Quadrant.NONE )
            .map( Map.Entry::getValue )
            .mapToInt( AtomicInteger::intValue )
            .reduce( 1, (a, b) -> a * b );
    }

    private Point positionAfter( int seconds, Robot robot ) {
        Point startingPosition = robot.position();
        Slope velocity = robot.velocity();
        return new Point(
            modulo( startingPosition.x() + seconds * velocity.deltaX(), width ),
            modulo( startingPosition.y() + seconds * velocity.deltaY(), height )
        );
    }

    private int modulo( int value, int operand ) {
        if ( value < 0 ) {
            int factor = Math.abs( -value / operand );
            value += operand * (factor + 1);
        }
        return value % operand;
    }

    private Map<Quadrant, AtomicInteger> positionsPerQuadrant( List<Point> positions ) {
        Map<Quadrant, AtomicInteger> positionsByQuadrant = new HashMap<>();
        positions
            .stream()
            .map( this::quadrantOf )
            .forEach( quadrant -> positionsByQuadrant.computeIfAbsent( quadrant, q -> new AtomicInteger( 0 ) ).incrementAndGet() );
        return positionsByQuadrant;
    }

    private Quadrant quadrantOf( Point position ) {
        if ( isTopHalf( position ) && isLeftSide( position ) ) {
            return Quadrant.TOP_LEFT;
        } else if ( isTopHalf( position )&& isRightSide( position ) ) {
            return Quadrant.TOP_RIGHT;
        } else if ( isBottomHalf( position ) && isLeftSide( position ) ) {
            return Quadrant.BOTTOM_LEFT;
        } else if ( isBottomHalf( position ) && isRightSide( position ) ) {
            return Quadrant.BOTTOM_RIGHT;
        } else {
            return Quadrant.NONE;
        }
    }

    private boolean isTopHalf( Point position ) {
        return position.y() >= 0 && position.y() < height / 2;
    }

    private boolean isBottomHalf( Point position ) {
        return position.y() >= height / 2 + 1 && position.y() < height;
    }

    private boolean isLeftSide( Point position ) {
        return position.x() >= 0 && position.x() < width / 2;
    }

    private boolean isRightSide( Point position ) {
        return position.x() >= width / 2 + 1 && position.x() < width;
    }

    SecondsFluent displayIfAtLeast( int percent ) {
        return seconds -> {
            List<Point> robotEndingPositions = robots
                .stream()
                .map( robot -> positionAfter( seconds, robot ) )
                .toList();
            Map<Quadrant, AtomicInteger> positionsPerQuadrant = positionsPerQuadrant( robotEndingPositions );
            if ( 100 * positionsPerQuadrant.get( Quadrant.NONE ).intValue() / robotEndingPositions.size() >= percent ) {
                StringBuilder builder = new StringBuilder();
                for ( int y = 0; y < height; ++y ) {
                    for ( int x = 0; x < width; ++x ) {
                        if ( robotEndingPositions.contains( new Point( x, y ) ) ) {
                            builder.append( '#' );
                        } else {
                            builder.append( '.' );
                        }
                    }
                    builder.append( '\n' );
                }
                return builder.toString();
            } else {
                return "";
            }
        };
    }

    interface SecondsFluent {

        String percentOfRobotsNotInAQuadrantAfterSeconds( int seconds );
    }
}
