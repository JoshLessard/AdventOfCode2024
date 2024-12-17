package com.adventofcode2024.dec16;

import java.util.Set;

import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

class ReindeerMap {

    private final OrientedPosition reindeerStartPosition;
    private final OrientedPosition endPosition;
    private final Set<Point> wallPositions;

    ReindeerMap( OrientedPosition reindeerStartPosition, OrientedPosition endPosition, Set<Point> wallPositions ) {
        this.reindeerStartPosition = reindeerStartPosition;
        this.endPosition = endPosition;
        this.wallPositions = Set.copyOf( wallPositions );
    }

    ShortestDistanceAndBestPathPositionCount lowestScoreAndBestPathTileCount() {
        OrientedPositionDistances shortestDistances = new OrientedPositionDistances();
        shortestDistances.offerSourcePosition( reindeerStartPosition );
        while ( shortestDistances.hasUnvisitedPositions() ) {
            ShortestDistance currentPositionShortestDistance = shortestDistances.removeShortestDistanceOfUnvisitedPositions();
            OrientedPosition currentPosition = currentPositionShortestDistance.position();
            long currentDistance = currentPositionShortestDistance.shortestDistance();
            shortestDistances.offerShortestDistance( currentPosition, currentPosition.left90Degrees(), currentDistance + 1000 );
            shortestDistances.offerShortestDistance( currentPosition, currentPosition.right90Degrees(), currentDistance + 1000 );
            if ( ! isWall( currentPosition.nextOrientedPosition().position() ) ) {
                shortestDistances.offerShortestDistance( currentPosition, currentPosition.nextOrientedPosition(), currentDistance + 1 );
            }
        }

        return new ShortestDistanceAndBestPathPositionCount(
            shortestDistances.currentShortestDistance( endPosition ),
            shortestDistances.bestPathsTileCount( endPosition )
        );
    }

    private boolean isWall( Point position ) {
        return wallPositions.contains( position );
    }
}
