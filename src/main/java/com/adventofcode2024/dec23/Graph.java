package com.adventofcode2024.dec23;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Graph {

    private final Map<String, Set<String>> edges;

    Graph( Map<String, Set<String>> edges ) {
        this.edges = Map.copyOf( edges );
    }

    Set<Clique> get3Cliques() {
        Set<Clique> cliques = new HashSet<>();
        List<String> vertices = List.copyOf( edges.keySet() );
        for ( int i = 0; i < vertices.size() - 2; ++i ) {
            for ( int j = i + 1; j < vertices.size() - 1; ++j ) {
                for ( int k = j + 1; k < vertices.size(); ++k ) {
                    String vertex1 = vertices.get( i );
                    String vertex2 = vertices.get( j );
                    String vertex3 = vertices.get( k );
                    if (
                        edges.get( vertex1 ).containsAll( List.of( vertex2, vertex3 ) )
                        && edges.get( vertex2 ).containsAll( List.of( vertex1, vertex3 ) )
                        && edges.get( vertex3 ).containsAll( List.of( vertex1, vertex2 ) )
                    ) {
                        cliques.add( new Clique( Set.of( vertex1, vertex2, vertex3 ) ) );
                    }
                }
            }
        }
        return cliques;
    }

    Clique largestClique() {
        Set<Clique> cliques = get3Cliques();
        while ( true ) {
            Set<Clique> mergedCliques = new HashSet<>();
            for ( Clique clique : cliques ) {
                for ( String vertex : edges.keySet() ) {
                    if ( canMerge( clique, vertex ) ) {
                        mergedCliques.add( clique.mergedWith( vertex ) );
                    }
                }
            }
            cliques = mergedCliques;
            if ( cliques.size() == 1 ) {
                return cliques.iterator().next();
            }
        }

    }

    private boolean canMerge( Clique clique, String vertex ) {
        return ! clique.vertices().contains( vertex )
            && edges.get( vertex ).containsAll( clique.vertices() );
    }
}
