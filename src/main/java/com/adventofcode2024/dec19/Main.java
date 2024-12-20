package com.adventofcode2024.dec19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Exception {
        Inputs inputs = parseInput();
        TowelArranger towelArranger = new TowelArranger( inputs.towelPatterns() );
        int numberOfPossiblePatterns = (int) inputs.desiredDesigns()
            .stream()
            .filter( towelArranger::isPossible )
            .count();
        System.out.println( "Number of possible patterns: " + numberOfPossiblePatterns );

        long numberOfDifferentWays = inputs.desiredDesigns()
            .stream()
            .mapToLong( towelArranger::numberOfDifferentWaysToMake )
            .sum();
        System.out.println( "Number of different ways patterns can be made: " + numberOfDifferentWays );
    }

    private static Inputs parseInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec19/input.txt" ) ) ) {
            String inputLine = reader.readLine();
            List<String> towelPatterns = Arrays.stream( inputLine.split( ", " ) )
                .toList();
            reader.readLine();
            List<String> desiredDesigns = new ArrayList<>();
            while ( ( inputLine = reader.readLine() ) != null ) {
                desiredDesigns.add( inputLine );
            }
            return new Inputs( towelPatterns, desiredDesigns );
        }
    }

    private record Inputs( List<String> towelPatterns, List<String> desiredDesigns ) {
    }
}
