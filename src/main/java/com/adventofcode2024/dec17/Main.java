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

    private static String toString( List<Byte> bytes ) {
        return bytes
            .stream()
            .map( String::valueOf )
            .collect( joining( "," ) );
    }
}
