package com.adventofcode2024.dec23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class GraphBuilder {

    private final Map<String, Set<String>> edges = new HashMap<>();

    void addEdge( String vertex1, String vertex2 ) {
        edges.computeIfAbsent( vertex1, l -> new HashSet<>() ).add( vertex2 );
        edges.computeIfAbsent( vertex2, r -> new HashSet<>() ).add( vertex1 );
    }

    Graph build() {
        return new Graph( edges );
    }
}
