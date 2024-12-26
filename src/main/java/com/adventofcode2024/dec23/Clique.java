package com.adventofcode2024.dec23;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

record Clique( Set<String> vertices ) {

    boolean hasVertexStartingWith( char character ) {
        return vertices
            .stream()
            .anyMatch( v -> v.charAt( 0 ) == character );
    }

    Clique mergedWith( String vertex ) {
        return new Clique(
            Stream.concat( vertices.stream(), Stream.of( vertex ) ).collect( toSet() )
        );
    }

    String password() {
        return vertices
            .stream()
            .sorted()
            .collect( joining( "," ) );
    }
}
