package com.adventofcode2024.dec13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        List<ClawMachine> clawMachines = parseClawMachines();
        long tokensRequiredPart1 = clawMachines
            .stream()
            .map( ClawMachine::fewestTokensToWinPrize )
            .filter( OptionalLong::isPresent )
            .mapToLong( OptionalLong::getAsLong )
            .sum();
        System.out.println( "Total tokens required (part one): " + tokensRequiredPart1 );

        long tokensRequiredPart2 = clawMachines
            .stream()
            .map( clawMachine -> clawMachine.adjustPrizePositionBy( 10000000000000L ) )
            .map( ClawMachine::fewestTokensToWinPrize )
            .filter( OptionalLong::isPresent )
            .mapToLong( OptionalLong::getAsLong )
            .sum();
        System.out.println( "Total tokens required (part two): " + tokensRequiredPart2 );
    }

    private static List<ClawMachine> parseClawMachines() throws IOException {
        Pattern buttonAPattern = Pattern.compile( "^Button A: X\\+(\\d+), Y\\+(\\d+)$" );
        Pattern buttonBPattern = Pattern.compile( "^Button B: X\\+(\\d+), Y\\+(\\d+)$" );
        Pattern prizePattern = Pattern.compile( "^Prize: X=(\\d+), Y=(\\d+)$" );
        List<ClawMachine> clawMachines = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec13/input.txt" ) ) ) {
            String inputLine;
            while ( ( inputLine = reader.readLine() ) != null ) {
                Matcher buttonAMatcher = buttonAPattern.matcher( inputLine );
                if ( buttonAMatcher.matches() ) {
                    Matcher buttonBMatcher = buttonBPattern.matcher( reader.readLine() );
                    if ( buttonBMatcher.matches() ) {
                        Matcher prizeMatcher = prizePattern.matcher( reader.readLine() );
                        if ( prizeMatcher.matches() ) {
                            Button buttonA = new Button(
                                Integer.parseInt( buttonAMatcher.group( 1 ) ),
                                Integer.parseInt( buttonAMatcher.group( 2 ) ),
                                3
                            );
                            Button buttonB = new Button(
                                Integer.parseInt( buttonBMatcher.group( 1 ) ),
                                Integer.parseInt( buttonBMatcher.group( 2 ) ),
                                1
                            );
                            PrizePosition prizePosition = new PrizePosition(
                                Integer.parseInt( prizeMatcher.group( 1 ) ),
                                Integer.parseInt( prizeMatcher.group( 2 ) )
                            );
                            clawMachines.add( new ClawMachine( buttonA, buttonB, prizePosition ) );
                        }
                    }
                }
            }
        }

        return clawMachines;
    }
}
