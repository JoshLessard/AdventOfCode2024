package com.adventofcode2024.dec25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Exception {
        Inputs inputs = parseInput();
        List<Lock> locks = inputs.locks();
        List<Key> keys = inputs.keys();
        int numberOfFits = 0;
        for ( Lock lock : locks ) {
            for ( Key key : keys ) {
                if ( lock.accepts( key ) ) {
                    ++numberOfFits;
                }
            }
        }

        System.out.println( "Number of fits: " + numberOfFits );
    }

    private static Inputs parseInput() throws IOException {
        List<Lock> locks = new ArrayList<>();
        List<Key> keys = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec25/input.txt" ) ) ) {
            String line;
            while ( ( line = reader.readLine() ) != null ) {
                if ( line.equals( "#####" ) ) {
                    locks.add( parseLock( reader ) );
                } else if ( line.equals( "....." ) ) {
                    keys.add( parseKey( reader ) );
                }
            }
        }

        return new Inputs( locks, keys );
    }

    private static Lock parseLock( BufferedReader reader ) throws IOException {
        char[][] pins = new char[5][];
        for ( int i = 0; i < 5; ++i ) {
            pins[i] = reader.readLine().toCharArray();
        }
        reader.readLine();
        return new Lock( pins );
    }

    private static Key parseKey( BufferedReader reader ) throws IOException {
        char[][] heights = new char[5][];
        for ( int i = 0; i < 5; ++i ) {
            heights[i] = reader.readLine().toCharArray();
        }
        reader.readLine();
        return new Key( heights );
    }

    private record Inputs( List<Lock> locks, List<Key> keys ) {
    }
}
