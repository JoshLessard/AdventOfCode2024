package com.adventofcode2024.dec17;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        Computer computer = parseComputer();
        System.out.println( "Part one: " + toString( computer.run() ) );
        System.out.println( "Part two: " + smallestRegisterAValueThatProducesOutput( computer.instructions() ) );
    }

    private static String toString( List<Byte> bytes ) {
        return bytes
            .stream()
            .map( String::valueOf )
            .collect( joining( "," ) );
    }

    private static long smallestRegisterAValueThatProducesOutput( List<Byte> expectedOutput ) {
        Set<Long> possibleValues = new HashSet<>();
        for ( int i = 0; i < 8; ++i ) {
            depthFirstSearch( expectedOutput, i, possibleValues );
        }
        return possibleValues
            .stream()
            .mapToLong( Long::longValue )
            .min()
            .orElseThrow( () -> new IllegalStateException( "No possible solution!" ) );
    }

    private static void depthFirstSearch( List<Byte> expectedOutput, long currentRegisterAValue, Set<Long> possibleValues ) {
        Computer computer = buildComputer( currentRegisterAValue, expectedOutput );
        List<Byte> actualOutput = computer.run();
        if ( actualOutput.equals( expectedOutput ) ) {
            possibleValues.add( currentRegisterAValue );
        } else {
            List<Byte> expectedOutputSuffix = expectedOutput.subList( expectedOutput.size() - actualOutput.size(), expectedOutput.size() );
            if ( actualOutput.equals( expectedOutputSuffix ) ) {
                for ( int i = 0; i < 8; ++i ) {
                    depthFirstSearch( expectedOutput, (currentRegisterAValue << 3) + i, possibleValues );
                }
            }
        }
    }

    private static Computer buildComputer( long registerAValue, List<Byte> instructions ) {
        return new Computer(
            new Register( registerAValue ),
            new Register( 0 ),
            new Register( 0 ),
            instructions
        );
    }

    private static Computer parseComputer() throws IOException {
        Pattern registerAPattern = Pattern.compile( "^Register A: (\\d+)$" );
        Pattern registerBPattern = Pattern.compile( "^Register B: (\\d+)$" );
        Pattern registerCPattern = Pattern.compile( "^Register C: (\\d+)$" );
        Pattern programPattern = Pattern.compile( "^Program: (\\d+[,\\d+]+)$" );
        ComputerBuilder builder = new ComputerBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec17/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( line -> {
                  Matcher registerAMatcher = registerAPattern.matcher( line );
                  if ( registerAMatcher.matches() ) {
                      builder.setRegisterAValue( Integer.parseInt( registerAMatcher.group( 1 ) ) );
                  }
                  Matcher registerBMatcher = registerBPattern.matcher( line );
                  if ( registerBMatcher.matches() ) {
                      builder.setRegisterBValue( Integer.parseInt( registerBMatcher.group( 1 ) ) );
                  }
                  Matcher registerCMatcher = registerCPattern.matcher( line );
                  if ( registerCMatcher.matches() ) {
                      builder.setRegisterCValue( Integer.parseInt( registerCMatcher.group( 1 ) ) );
                  }
                  Matcher programMatcher = programPattern.matcher( line );
                  if ( programMatcher.matches() ) {
                      List<Byte> instructions = Arrays.stream( programMatcher.group( 1 ).split( "," ) )
                          .map( Byte::parseByte )
                          .toList();
                      builder.setInstructions( instructions );
                  }
                } );
        }

        return builder.build();
    }
}
