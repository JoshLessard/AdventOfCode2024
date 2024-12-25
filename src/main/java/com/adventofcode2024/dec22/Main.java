package com.adventofcode2024.dec22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Exception {
        SecretNumberBuilder builder = new SecretNumberBuilder( 2000 );
        List<SecretNumber> secretNumbers = parseInput()
            .stream()
            .map( builder::build )
            .toList();
        SecretNumberCalculator calculator = new SecretNumberCalculator( secretNumbers );

        System.out.println( "Part one: " + calculator.sum() );
        System.out.println( "Part two: " + calculator.mostAvailableBananas() );
    }

    private static List<Long> parseInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec22/input.txt" ) ) ) {
            return reader
                .lines()
                .map( Long::valueOf )
                .toList();
        }
    }
}
