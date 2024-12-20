package com.adventofcode2024.dec20;

import static java.util.function.Predicate.not;

import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class RacetrackMap {

    private final int width;
    private final int height;
    private final Point startingPosition;
    private final Point endingPosition;
    private final Set<Point> wallPositions;

    RacetrackMap( int width, int height, Point startingPosition, Point endingPosition, Set<Point> wallPositions ) {
        this.width = width;
        this.height = height;
        this.startingPosition = startingPosition;
        this.endingPosition = endingPosition;
        this.wallPositions = Set.copyOf( wallPositions );
    }

    int shortestPath() {
        ShortestPathStatistics statistics = new ShortestPathStatistics( startingPosition );
        while ( statistics.hasUnprocessedPositions() ) {
            Path currentPath = statistics.shortestUnprocessedPath();
            neighboursOf( currentPath.position() )
                .forEach( neighbour -> statistics.offer( neighbour, currentPath.pathLength() + 1 ) );
            statistics.markVisited( currentPath.position() );
        }

        return statistics.shortestPathLengthTo( endingPosition );
    }

    private Stream<Point> neighboursOf( Point position ) {
        return Stream.of(
            position.nextPoint( Direction.UP ),
            position.nextPoint( Direction.RIGHT ),
            position.nextPoint( Direction.DOWN ),
            position.nextPoint( Direction.LEFT )
        )
        .filter( this::isInbounds )
        .filter( not( this::isWall ) );
    }

    private boolean isWall( Point position ) {
        return wallPositions.contains( position );
    }

    private boolean isInbounds( Point point ) {
        return
            point.x() >= 0 && point.x() < width
            && point.y() >= 0 && point.y() < height;
    }
}
