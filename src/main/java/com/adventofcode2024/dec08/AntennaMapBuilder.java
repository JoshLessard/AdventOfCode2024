package com.adventofcode2024.dec08;

import com.adventofcode2024.common.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class AntennaMapBuilder {

    private int width = 0;
    private int height = 0;
    private final Map<Character, Set<Point>> antennaPositionsByFrequency = new HashMap<>();


    void addRow( String inputLine ) {
        width = Math.max( width, inputLine.length() );
        for ( int x = 0; x < inputLine.length(); ++x ) {
            char character = inputLine.charAt( x );
            if ( character != '.' ) {
                antennaPositionsByFrequency.computeIfAbsent( character, c -> new HashSet<>() ).add( new Point( x, height ) );
            }
        }
        ++height;
    }

    public AntennaMap build() {
        return new AntennaMap( width, height, antennaPositionsByFrequency );
    }
}
