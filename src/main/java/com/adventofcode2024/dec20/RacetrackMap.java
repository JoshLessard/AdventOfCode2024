package com.adventofcode2024.dec20;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

class RacetrackMap {

    private final int width;
    private final int height;
    private final Point startPosition;
    private final Point endPosition;
    private final Set<Point> wallPositions;

    RacetrackMap( int width, int height, Point startPosition, Point endingPosition, Set<Point> wallPositions ) {
        this.width = width;
        this.height = height;
        this.startPosition = startPosition;
        this.endPosition = endingPosition;
        this.wallPositions = Set.copyOf( wallPositions );
    }

    int numberOfCheatsThatSaveAtLeast( int numberOfPicosecondsToSave, int maxCheatPicoseconds ) {
        List<Point> racePath = getRacePath();
        Set<Cheat> validCheats = new HashSet<>();
        for ( int startIndex = 0; startIndex < racePath.size() - 1; ++startIndex ) {
            for ( int endIndex = startIndex + numberOfPicosecondsToSave; endIndex < racePath.size(); ++endIndex ) {
                Cheat cheat = new Cheat( racePath.get( startIndex ), racePath.get( endIndex ) );
                int pathLength = endIndex - startIndex;
                int cheatLength = cheat.length();
                if ( cheatLength <= maxCheatPicoseconds && pathLength - cheatLength >= numberOfPicosecondsToSave ) {
                    validCheats.add( cheat );
                }
            }
        }
        return validCheats.size();
    }

    private List<Point> getRacePath() {
        Set<Point> racePath = new LinkedHashSet<>();
        Point currentPosition = startPosition;
        while ( ! currentPosition.equals( endPosition ) ) {
            racePath.add( currentPosition );
            List<Point> nextPathPosition = pathNeighboursOf( currentPosition )
                .filter( not( racePath::contains ) )
                .toList();
            if ( nextPathPosition.size() != 1 ) {
                throw new IllegalStateException();
            }
            currentPosition = nextPathPosition.get( 0 );
        }
        racePath.add( endPosition );

        return new ArrayList<>( racePath );
    }

    private Stream<Point> pathNeighboursOf( Point position ) {
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
