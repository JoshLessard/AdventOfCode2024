package com.adventofcode2024.dec01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        LocationIds locationIds = readInput();

        System.out.println( "Part A: " + locationIds.distance() );
        System.out.println( "Part B: " + locationIds.similarityScore() );
    }

    private static LocationIds readInput() throws IOException {
        Pattern pattern = Pattern.compile( "^(\\d+)\\s+(\\d+)$" );
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec01/input.txt" ) ) ) {
            reader
                .lines()
                .map( pattern::matcher )
                .filter( Matcher::matches )
                .forEach( m -> {
                    left.add( Integer.parseInt( m.group( 1 ) ) );
                    right.add( Integer.parseInt( m.group( 2 ) ) );
                } );
        }
        return new LocationIds( left, right );
    }
}
