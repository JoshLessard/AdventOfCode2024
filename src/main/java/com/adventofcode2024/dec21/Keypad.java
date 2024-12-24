package com.adventofcode2024.dec21;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;
import com.adventofcode2024.common.Slope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Keypad {

    private final Map<Character, Point> positionsByKey;
    private final Point gapPosition;
    private final Point startPosition;

    Keypad( Map<Character, Point> positionsByKey, Point startPosition ) {
        this.positionsByKey = Map.copyOf( positionsByKey );
        this.gapPosition = positionsByKey.get( 'G' );
        this.startPosition = startPosition;
    }

    List<String> sequencesForCode( String code ) {
        List<Point> codePositions = code
            .chars()
            .mapToObj( key -> positionsByKey.get( (char) key ) )
            .toList();
        return sequencesForCodePositions( codePositions );
    }

    private List<String> sequencesForCodePositions( List<Point> codePositions ) {
        List<String> sequences = new ArrayList<>();
        sequences.add( "" );
        Point currentPosition = startPosition;
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
        List<String> sequences = new ArrayList<>();
        depthFirstSearch( source, destination, "", sequences );
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

    long lengthOfShortestSequence( String sequence, int numberOfKeypads ) {
        return lengthOfShortestSequence( sequence, numberOfKeypads, new HashMap<>() );
    }

    private long lengthOfShortestSequence( String sequence, int numberOfKeypads, Map<Arguments, Long> memo ) {
        Arguments arguments = new Arguments( sequence, numberOfKeypads );
        if ( memo.containsKey( arguments ) ) {
            return memo.get( arguments );
        }
        if ( sequence.isEmpty() || numberOfKeypads == 0 ) {
            memo.put( arguments, (long) sequence.length() );
            return sequence.length();
        }

        int indexOfA = sequence.indexOf( 'A' );
        String prefix = sequence.substring( 0, indexOfA + 1 );
        String suffix = sequence.substring( indexOfA + 1 );

        long prefixLength = sequencesForCode( prefix )
            .stream()
            .mapToLong( s -> lengthOfShortestSequence( s, numberOfKeypads - 1, memo ) )
            .min()
            .orElseThrow();
        long suffixLength = this.lengthOfShortestSequence( suffix, numberOfKeypads, memo );

        long sequenceLength = prefixLength + suffixLength;
        memo.put( arguments, sequenceLength );
        return sequenceLength;
    }

    private record Arguments( String sequence, int numberOfKeypads ) {
    }
}
