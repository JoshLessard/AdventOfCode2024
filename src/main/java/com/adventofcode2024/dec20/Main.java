package com.adventofcode2024.dec20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        RacetrackMap map = parseRacetrackMap();
        System.out.println( "Shortest path to the end: " + map.shortestPath() );
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
