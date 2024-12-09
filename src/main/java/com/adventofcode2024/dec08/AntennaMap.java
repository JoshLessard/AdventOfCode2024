package com.adventofcode2024.dec08;

import com.adventofcode2024.common.Point;
import com.adventofcode2024.common.Slope;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

class AntennaMap {

    private final int width;
    private final int height;
    private final Map<Character, Set<Point>> antennaPositionsByFrequency;

    AntennaMap( int width, int height, Map<Character, Set<Point>> antennaPositionsByFrequency ) {
        this.width = width;
        this.height = height;
        this.antennaPositionsByFrequency = Map.copyOf( antennaPositionsByFrequency );
    }

    Set<Point> getAntinodePositions() {
        return antennaPositionsByFrequency.values()
            .stream()
            .map( antennaPositions -> getAntinodePositions( antennaPositions, this::getAntinodePositions ) )
            .flatMap( Set::stream )
            .collect( toSet() );
    }

    private Set<Point> getAntinodePositions( Set<Point> antennaLocations, BiFunction<Point, Point, Set<Point>> antinodeCalculator ) {
        List<Point> antennaLocationsList = new ArrayList<>( antennaLocations );
        Set<Point> antinodePositions = new HashSet<>();
        for ( int i = 0; i < antennaLocationsList.size() - 1; ++i ) {
            for ( int j = i + 1; j < antennaLocationsList.size(); ++j ) {
                Point antenna1 = antennaLocationsList.get( i );
                Point antenna2 = antennaLocationsList.get( j );
                antinodePositions.addAll( antinodeCalculator.apply( antenna1, antenna2 ) );
            }
        }

        return antinodePositions;
    }

    private Set<Point> getAntinodePositions( Point antennaPosition1, Point antennaPosition2 ) {
        Slope slope = antennaPosition1.slopeTo( antennaPosition2 );
        Point antinodePosition1 = antennaPosition1.shift( slope.inverse() );
        Point antinodePosition2 = antennaPosition2.shift( slope );
        return Stream.of( antinodePosition1, antinodePosition2 )
            .filter( this::isInbounds )
            .collect( toSet() );
    }

    private boolean isInbounds( Point position ) {
        return
            position.x() >= 0
            && position.x() < width
            && position.y() >= 0
            && position.y() < height;
    }

    Set<Point> getResonantHarmonicAntinodePositions() {
        return antennaPositionsByFrequency.values()
            .stream()
            .map( antennaPositions -> getAntinodePositions( antennaPositions, this::getResonantHarmonicAntinodePositions ) )
            .flatMap( Set::stream )
            .collect( toSet() );
    }

    private Set<Point> getResonantHarmonicAntinodePositions( Point antennaPosition1, Point antennaPosition2 ) {
        Set<Point> antinodePositions = new HashSet<>();
        Slope slope = antennaPosition1.slopeTo( antennaPosition2 );
        Point currentPoint = antennaPosition1;
        while ( isInbounds( currentPoint ) ) {
            antinodePositions.add( currentPoint );
            currentPoint = currentPoint.shift( slope );
        }

        slope = slope.inverse();
        currentPoint = antennaPosition1;
        while ( isInbounds( currentPoint ) ) {
            antinodePositions.add( currentPoint );
            currentPoint = currentPoint.shift( slope );
        }

        return antinodePositions;
    }
}
