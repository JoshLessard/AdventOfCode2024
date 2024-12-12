package com.adventofcode2024.dec10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        TopographicMap map = parseMap();

        System.out.println( "Trailhead score: " + map.trailheadScore() );
        System.out.println( "Trailhead rating: " + map.trailheadRating() );
    }

    private static TopographicMap parseMap() throws IOException {
        TopographicMapBuilder builder = new TopographicMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec10/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( builder::addRow );
        }
        return builder.build();
    }
}
