package com.adventofcode2024.dec24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class Main {

    public static void main( String[] args ) throws Exception {
        LogicDevice logicDevice = parseLogicDevice();
        System.out.println( "Part one: " + logicDevice.runSimulation() );

        Set<Wire> faultyWires = logicDevice.faultyOutputWires();
        System.out.println( "Part two: " + faultyWires.stream().map( Wire::name ).sorted().collect( joining( "," ) ) );
    }

    private static LogicDevice parseLogicDevice() throws IOException {
        Pattern wirePattern = Pattern.compile( "^([a-z]\\d\\d): ([0-1])$" );
        Pattern andPattern = Pattern.compile( "^([a-z0-9]+) AND ([a-z0-9]+) -> ([a-z0-9]+)$" );
        Pattern orPattern = Pattern.compile( "^([a-z0-9]+) OR ([a-z0-9]+) -> ([a-z0-9]+)$" );
        Pattern xorPattern = Pattern.compile( "^([a-z0-9]+) XOR ([a-z0-9]+) -> ([a-z0-9]+)$" );
        LogicDeviceBuilder builder = new LogicDeviceBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec24/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( inputLine -> {
                    Matcher wireMatcher = wirePattern.matcher( inputLine );
                    if ( wireMatcher.matches() ) {
                        String wire = wireMatcher.group( 1 );
                        boolean value = Integer.parseInt( wireMatcher.group( 2 ) ) == 1;
                        builder.addInitialWireValue( wire, value );
                    }

                    Matcher andMatcher = andPattern.matcher( inputLine );
                    if ( andMatcher.matches() ) {
                        String inputWire1 = andMatcher.group( 1 );
                        String inputWire2 = andMatcher.group( 2 );
                        String outputWire = andMatcher.group( 3 );
                        builder.addAndGate( inputWire1, inputWire2, outputWire );
                    }

                    Matcher orMatcher = orPattern.matcher( inputLine );
                    if ( orMatcher.matches() ) {
                        String inputWire1 = orMatcher.group( 1 );
                        String inputWire2 = orMatcher.group( 2 );
                        String outputWire = orMatcher.group( 3 );
                        builder.addOrGate( inputWire1, inputWire2, outputWire );
                    }

                    Matcher xorMatcher = xorPattern.matcher( inputLine );
                    if ( xorMatcher.matches() ) {
                        String inputWire1 = xorMatcher.group( 1 );
                        String inputWire2 = xorMatcher.group( 2 );
                        String outputWire = xorMatcher.group( 3 );
                        builder.addXorGate( inputWire1, inputWire2, outputWire );
                    }
                } );
        }

        return builder.build();
    }
}
