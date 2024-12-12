package com.adventofcode2024.dec11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main( String[] args ) throws Exception {
        Stones stones = parseInput();
        System.out.println( "Number of stones (part one): " + stones.blink( 25 ) );
        System.out.println( "Number of stones (part two): " + stones.blink( 75 ) );
    }

    private static Stones parseInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec11/input.txt" ) ) ) {
            String line = reader.readLine();
            return new Stones( parseLine( line ) );
        }
    }

    private static long[] parseLine( String inputLine ) {
        return Arrays.stream( inputLine.split( "\\s+" ) )
            .mapToLong( Long::parseLong )
            .toArray();
    }
}
