package com.adventofcode2024.dec21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Exception {
        Starship starship2 = buildStarship( 2 );
        long sumOfComplexities = parseCodes()
            .stream()
            .mapToLong( starship2::complexityOf )
            .sum();
        System.out.println( "Part one: " + sumOfComplexities );

        Starship starship25 = buildStarship( 25 );
        sumOfComplexities = parseCodes()
            .stream()
            .mapToLong( starship25::complexityOf )
            .sum();
        System.out.println( "Part two: " + sumOfComplexities );
    }

    private static Starship buildStarship( int numberOfDirectionalKeypads ) {
        KeypadBuilder builder = new KeypadBuilder();
        return new Starship(
            builder.buildNumericKeypad(),
            builder.buildDirectionalKeypad(),
            numberOfDirectionalKeypads
        );
    }

    private static List<String> parseCodes() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec21/input.txt" ) ) ) {
            return reader
                .lines()
                .toList();
        }
    }
}
