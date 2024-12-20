package com.adventofcode2024.dec20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        RacetrackMap map = parseRacetrackMap();
        System.out.println( "Number of cheats that save at least 100 picoseconds (part one): " + map.numberOfCheatsThatSaveAtLeast( 100, 2 ) );
        System.out.println( "Number of cheats that save at least 100 picoseconds (part two): " + map.numberOfCheatsThatSaveAtLeast( 100, 20 ) );
    }

    private static RacetrackMap parseRacetrackMap() throws IOException {
        RacetrackMapBuilder builder = new RacetrackMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec20/input.txt" ) ) ) {
            reader.lines()
                .forEach( builder::addLine );
        }
        return builder.build();
    }
}
