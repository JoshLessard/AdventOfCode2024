package com.adventofcode2024.dec23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        Graph graph = parseGraph();
        Set<Clique> cliques = graph.get3Cliques();
        int numberOfTCliques = (int) cliques
            .stream()
            .filter( c -> c.hasVertexStartingWith( 't' ) )
            .count();
        System.out.println( "Part one: " + numberOfTCliques );

        System.out.println( "Part two: " + graph.largestClique().password() );
    }

    private static Graph parseGraph() throws IOException {
        Pattern edgePattern = Pattern.compile( "([a-z]+)-([a-z]+)" );
        GraphBuilder builder = new GraphBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec23/input.txt" ) ) ) {
            reader
                .lines()
                .map( edgePattern::matcher )
                .filter( Matcher::matches )
                .forEach( matcher -> builder.addEdge( matcher.group( 1 ), matcher.group( 2 ) ) );
        }
        return builder.build();
    }
}
