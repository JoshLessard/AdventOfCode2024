package com.adventofcode2024.dec05;

import static java.util.function.Predicate.not;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        Inputs inputs = parseInput();
        PageOrderingRules orderingRules = inputs.orderingRules;
        List<PageUpdate> pageUpdates = inputs.pageUpdates;

        List<PageUpdate> updatesInCorrectOrder = pageUpdates
            .stream()
            .filter( orderingRules::isInCorrectOrder )
            .toList();
        List<PageUpdate> updatesThatNeededCorrecting = pageUpdates
            .stream()
            .filter( not( orderingRules::isInCorrectOrder ) )
            .map( orderingRules::toCorrectOrder )
            .toList();
        System.out.println( "Sum of middle pages of update in correct order: " + updatesInCorrectOrder.stream().mapToInt( PageUpdate::middlePage ).sum() );
        System.out.println( "Sum of middle pages of updates that needed correcting: " + updatesThatNeededCorrecting.stream().mapToInt( PageUpdate::middlePage ).sum() );
    }

    private static Inputs parseInput() throws IOException {
        Pattern orderingRulesPattern = Pattern.compile("^(\\d+)\\|(\\d+)$");
        Pattern pageUpdateRulesPattern = Pattern.compile( "^\\d+(,\\d+)*$" );
        List<String> input = readInput();
        PageOrderingRulesBuilder builder = new PageOrderingRulesBuilder();
        List<PageUpdate> updates = new ArrayList<>();
        for ( String line : input ) {
            Matcher matcher = orderingRulesPattern.matcher( line );
            if ( matcher.matches() ) {
                builder.addOrderingRule( Integer.parseInt( matcher.group( 1 ) ), Integer.parseInt( matcher.group( 2 ) ) );
            }
            matcher = pageUpdateRulesPattern.matcher( line );
            if ( matcher.matches() ) {
                List<Integer> pages = Arrays.stream( line.split( "," ) )
                    .map( Integer::valueOf )
                    .toList();
                updates.add( new PageUpdate( pages ) );
            }
        }

        return new Inputs( builder.build(), updates );
    }

    private static List<String> readInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec05/input.txt" ) ) ) {
            return reader
                .lines()
                .toList();
        }
    }

    private record Inputs( PageOrderingRules orderingRules, List<PageUpdate> pageUpdates ) {
    }
}
