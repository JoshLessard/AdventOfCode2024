package com.adventofcode2024.dec20;

import static java.util.Comparator.comparingInt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.adventofcode2024.common.Point;

class ShortestPathStatistics {

    private final Map<Point, Integer> shortestPathByPosition = new HashMap<>();
    private final Set<Point> visitedPositions = new HashSet<>();
    private final PriorityQueue<Point> unprocessedPositionsOrderedByShortestPath = new PriorityQueue<>(
        comparingInt( this::shortestPathLengthTo )
    );

    ShortestPathStatistics( Point startingPosition ) {
        shortestPathByPosition.put( startingPosition, 0 );
        unprocessedPositionsOrderedByShortestPath.offer( startingPosition );
    }

    int shortestPathLengthTo( Point position ) {
        return shortestPathByPosition.getOrDefault( position, Integer.MAX_VALUE );
    }

    boolean hasUnprocessedPositions() {
        return ! unprocessedPositionsOrderedByShortestPath.isEmpty();
    }

    Path shortestUnprocessedPath() {
        Point position = unprocessedPositionsOrderedByShortestPath.poll();
        return new Path( position, shortestPathLengthTo( position ) );
    }

    void offer( Point position, int pathLength ) {
        if ( ! visitedPositions.contains( position ) ) {
            int currentShortestPathLength = shortestPathLengthTo( position );
            if ( pathLength < currentShortestPathLength ) {
                shortestPathByPosition.put( position, pathLength );
                unprocessedPositionsOrderedByShortestPath.remove( position );
                unprocessedPositionsOrderedByShortestPath.offer( position );
            }
        }
    }

    void markVisited( Point position ) {
        visitedPositions.add( position );
    }
}
