package com.adventofcode2024.dec08;

import com.adventofcode2024.common.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Main {

    public static void main( String[] args ) throws Exception {
        AntennaMap antennaMap = parseAntennaMap();
        Set<Point> antinodePositions = antennaMap.getAntinodePositions();
        System.out.println( "Number of unique antinode positions: " + antinodePositions.size() );

        Set<Point> resonantHarmonicAntinodePositions = antennaMap.getResonantHarmonicAntinodePositions();
        System.out.println( "Number of unique resonant harmonic antinode positions: " + resonantHarmonicAntinodePositions.size() );
    }

    private static AntennaMap parseAntennaMap() throws IOException {
        AntennaMapBuilder builder = new AntennaMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec08/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( builder::addRow );
        }

        return builder.build();
    }
}
