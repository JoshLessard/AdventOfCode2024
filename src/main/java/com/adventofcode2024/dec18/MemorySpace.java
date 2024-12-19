package com.adventofcode2024.dec18;

import static java.util.function.Predicate.not;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class MemorySpace {

    private final int width;
    private final int height;
    private final Set<Point> corruptedPositions;

    MemorySpace( int maxPositionDimension ) {
        this.width = this.height = maxPositionDimension + 1;
        this.corruptedPositions = new HashSet<>();
    }

    void corruptPosition( Point position ) {
        corruptedPositions.add( position );
    }

    int minimumStepsToReachExit() {
        ShortestPathStatistics statistics = new ShortestPathStatistics( new Point( 0, 0 ) );
        while ( statistics.hasPathToProcess() ) {
            Path path = statistics.removeShortestUnprocessedPath();
            neighboursOf( path.destination() )
                .forEach( neighbour -> statistics.offer( neighbour, path.length() + 1 ) );
            statistics.markVisited( path.destination() );
        }
        return statistics.shortestPathTo( new Point( width - 1, height - 1 ) );
    }

    private Stream<Point> neighboursOf( Point position ) {
        return Stream.of(
            position.nextPoint( Direction.UP ),
            position.nextPoint( Direction.RIGHT ),
            position.nextPoint( Direction.DOWN ),
            position.nextPoint( Direction.LEFT )
        )
        .filter( this::isInbounds )
        .filter( not( this::isCorrupted ) );
    }

    private boolean isCorrupted( Point position ) {
        return corruptedPositions.contains( position );
    }

    private boolean isInbounds( Point point ) {
        return
            point.x() >= 0 && point.x() < width &&
            point.y() >= 0 && point.y() < height;
    }
}
