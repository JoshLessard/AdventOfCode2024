package com.adventofcode2024.dec16;

import static java.util.Collections.emptySet;
import static java.util.Comparator.comparingLong;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.adventofcode2024.common.OrientedPosition;
import com.adventofcode2024.common.Point;

class OrientedPositionDistances {

    private static final ShortestDistanceAndPredecessors DEFAULT_SHORTEST_DISTANCE_AND_PREDECESSORS = new ShortestDistanceAndPredecessors(
        Long.MAX_VALUE, emptySet()
    );

    private final Map<OrientedPosition, ShortestDistanceAndPredecessors> shortestDistancesAndPredecessorsByPosition = new HashMap<>();
    private final PriorityQueue<OrientedPosition> unvisitedPositionsSortedByShortestDistance = new PriorityQueue<>(
        comparingLong( position -> currentShortestDistanceAndPredecessors( position ).distance() )
    );

    void offerSourcePosition( OrientedPosition sourcePosition ) {
        shortestDistancesAndPredecessorsByPosition.put( sourcePosition, new ShortestDistanceAndPredecessors( 0, emptySet() ) );
        unvisitedPositionsSortedByShortestDistance.add( sourcePosition );
    }

    void offerShortestDistance( OrientedPosition predecessor, OrientedPosition position, long distance ) {
        ShortestDistanceAndPredecessors currentShortestDistanceAndPredecessors = currentShortestDistanceAndPredecessors( position );
        if ( distance == currentShortestDistanceAndPredecessors.distance() ) {
            shortestDistancesAndPredecessorsByPosition.put( position, currentShortestDistanceAndPredecessors.withPredecessor( predecessor ) );
        }
        if ( distance < currentShortestDistanceAndPredecessors.distance() ) {
            shortestDistancesAndPredecessorsByPosition.put( position, new ShortestDistanceAndPredecessors( distance, predecessor ) );
            unvisitedPositionsSortedByShortestDistance.remove( position );
            unvisitedPositionsSortedByShortestDistance.add( position );
        }
    }

    long currentShortestDistance( OrientedPosition position ) {
        return currentShortestDistanceAndPredecessors( position ).distance();
    }

    private ShortestDistanceAndPredecessors currentShortestDistanceAndPredecessors(OrientedPosition position ) {
        return shortestDistancesAndPredecessorsByPosition.getOrDefault( position, DEFAULT_SHORTEST_DISTANCE_AND_PREDECESSORS);
    }

    ShortestDistance removeShortestDistanceOfUnvisitedPositions() {
        OrientedPosition nextPosition = unvisitedPositionsSortedByShortestDistance.remove();
        return new ShortestDistance( nextPosition, shortestDistancesAndPredecessorsByPosition.get( nextPosition ).distance() );
    }

    boolean hasUnvisitedPositions() {
        return ! unvisitedPositionsSortedByShortestDistance.isEmpty();
    }

    int bestPathsTileCount( OrientedPosition endPosition ) {
        Set<Point> bestPathPositions = new HashSet<>();
        bestPathPositions.add( endPosition.position() );
        Deque<OrientedPosition> positionsToConsider = new LinkedList<>();
        positionsToConsider.add( endPosition );
        while ( ! positionsToConsider.isEmpty() ) {
            OrientedPosition currentPosition = positionsToConsider.remove();
            Set<OrientedPosition> predecessors = shortestDistancesAndPredecessorsByPosition
                .get( currentPosition )
                .predecessors();
            positionsToConsider.addAll( predecessors );
            predecessors
                .stream()
                .map( OrientedPosition::position )
                .forEach( bestPathPositions::add );
        }
        return bestPathPositions.size();
    }

    private record ShortestDistanceAndPredecessors( long distance, Set<OrientedPosition> predecessors ) {

        ShortestDistanceAndPredecessors( long distance, OrientedPosition predecessor ) {
            this( distance, Set.of( predecessor ) );
        }

        ShortestDistanceAndPredecessors withPredecessor( OrientedPosition predecessor ) {
            Set<OrientedPosition> withPredecessor = new HashSet<>( predecessors );
            withPredecessor.add( predecessor );
            return new ShortestDistanceAndPredecessors( distance, Set.copyOf( withPredecessor ) );
        }
    }
}
