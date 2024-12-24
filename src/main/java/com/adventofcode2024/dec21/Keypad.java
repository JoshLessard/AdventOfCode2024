package com.adventofcode2024.dec21;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;
import com.adventofcode2024.common.Slope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Keypad {

    private final Map<PathEndpoints, List<String>> shortestSequencesBetweenKeys = new HashMap<>();

    private final Map<Character, Point> positionsByKey;
    private final Point gapPosition;
    private Point currentPosition;

    Keypad( Map<Character, Point> positionsByKey, Point startPosition ) {
        this.positionsByKey = Map.copyOf( positionsByKey );
        this.gapPosition = positionsByKey.get( 'G' );
        this.currentPosition = startPosition;
    }

    List<String> sequencesForCode( String code ) {
        List<Point> codePositions = code
            .chars()
            .mapToObj( key -> positionsByKey.get( (char) key ) )
            .toList();
        List<String> sequences = sequencesForCodePositions( codePositions );
        currentPosition = positionsByKey.get( code.charAt( code.length() - 1 ) );
        return sequences;
    }

    private List<String> sequencesForCodePositions( List<Point> codePositions ) {
        List<String> sequences = new ArrayList<>();
        sequences.add( "" );
        Point currentPosition = this.currentPosition;
        for ( Point nextPosition : codePositions ) {
            List<String> sequencesBetweenPositions = sequencesBetweenPositions( currentPosition, nextPosition );
            List<String> newSequences = new ArrayList<>( sequences.size() * sequencesBetweenPositions.size() );
            for ( String prefix : sequences ) {
                for ( String suffix : sequencesBetweenPositions ) {
                    newSequences.add( prefix + suffix );
                }
            }
            sequences = newSequences;
            currentPosition = nextPosition;
        }
        return sequences;
    }

    private List<String> sequencesBetweenPositions( Point source, Point destination ) {
        PathEndpoints pathEndpoints = new PathEndpoints( source, destination );
        if ( shortestSequencesBetweenKeys.containsKey( pathEndpoints ) ) {
            return shortestSequencesBetweenKeys.get( pathEndpoints );
        }
        List<String> sequences = new ArrayList<>();
        depthFirstSearch( source, destination, "", sequences );
        shortestSequencesBetweenKeys.put( pathEndpoints, sequences );
        return sequences;
    }

    private void depthFirstSearch( Point source, Point destination, String currentSequence, List<String> sequences ) {
        if ( source.equals( gapPosition ) ) {
            return;
        }
        if ( source.equals( destination ) ) {
            sequences.add( currentSequence + 'A' );
        }
        Slope slope = source.slopeTo( destination );
        if ( slope.deltaX() > 0 ) {
            depthFirstSearch( source.nextPoint( Direction.RIGHT ), destination, currentSequence + '>', sequences );
        }
        if ( slope.deltaX() < 0 ) {
            depthFirstSearch( source.nextPoint( Direction.LEFT ), destination, currentSequence + '<', sequences );
        }
        if ( slope.deltaY() > 0 ) {
            depthFirstSearch( source.nextPoint( Direction.DOWN ), destination, currentSequence + 'v', sequences );
        }
        if ( slope.deltaY() < 0 ) {
            depthFirstSearch( source.nextPoint( Direction.UP ), destination, currentSequence + '^', sequences );
        }
    }

    private record PathEndpoints( Point source, Point destination ) {
    }
}
