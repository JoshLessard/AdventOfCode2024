package com.adventofcode2024.dec07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        List<Equation> equations = parseInput();
        long totalCalibrationResultPart1 = equations
            .stream()
            .filter( equation -> equation.canBeSolved( Operator.ADDITION, Operator.MULTIPLICATION ) )
            .mapToLong( Equation::testValue )
            .sum();
        System.out.println( "Total calibration result (part one): " + totalCalibrationResultPart1 );

        long totalCalibrationResultPart2 = equations
            .stream()
            .filter( equation -> equation.canBeSolved( Operator.ADDITION, Operator.MULTIPLICATION, Operator.CONCATENATION ) )
            .mapToLong( Equation::testValue )
            .sum();
        System.out.println( "Total calibration result (part two): " + totalCalibrationResultPart2 );
    }

    private static List<Equation> parseInput() throws IOException {
        Pattern equationPattern = Pattern.compile( "^(\\d+):\\s+([^\\n]+)" );
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec07/input.txt" ) ) ) {
            return reader
                .lines()
                .map( equationPattern::matcher )
                .filter( Matcher::matches )
                .map( matcher -> Main.toEquation( matcher.group( 1 ), matcher.group( 2 ) ) )
                .toList();
        }
    }

    private static Equation toEquation( String testValue, String operands ) {
        return new Equation(
            Long.parseLong( testValue ),
            Arrays.stream( operands.split( "\\s+" ) )
                .map( Long::valueOf )
                .toList()
        );
    }
}
