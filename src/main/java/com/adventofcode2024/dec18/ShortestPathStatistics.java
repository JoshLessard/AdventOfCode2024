package com.adventofcode2024.dec18;

import static java.util.Comparator.comparingInt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.adventofcode2024.common.Point;

class ShortestPathStatistics {

    private static final Integer MAX_PATH_LENGTH = Integer.MAX_VALUE;

    private final Set<Point> visitedPositions;
    private final Map<Point, Integer> shortestPathLengthsByPosition;
    private final PriorityQueue<Point> positionsToProcessByShortestDistance;

    ShortestPathStatistics( Point startingPosition ) {
        this.visitedPositions = new HashSet<>();
        this.shortestPathLengthsByPosition = new HashMap<>();
        this.shortestPathLengthsByPosition.put( startingPosition, 0 );
        this.positionsToProcessByShortestDistance = new PriorityQueue<>(
            comparingInt( this::shortestPathTo)
        );
        positionsToProcessByShortestDistance.offer( startingPosition );
    }

    void offer( Point position, int pathLength ) {
        if ( ! visitedPositions.contains( position ) ) {
            int currentShortestPath = shortestPathTo( position );
            if ( pathLength < currentShortestPath ) {
                shortestPathLengthsByPosition.put( position, pathLength );
                positionsToProcessByShortestDistance.remove( position );
                positionsToProcessByShortestDistance.offer( position );
            }
        }
    }

    int shortestPathTo( Point position ) {
        return shortestPathLengthsByPosition.getOrDefault( position, MAX_PATH_LENGTH );
    }

    boolean hasPathToProcess() {
        return ! positionsToProcessByShortestDistance.isEmpty();
    }

    Path removeShortestUnprocessedPath() {
        Point position = positionsToProcessByShortestDistance.poll();
        return new Path( position, shortestPathLengthsByPosition.get( position ) );
    }

    void markVisited( Point position ){
        visitedPositions.add( position );
    }
}
