package com.adventofcode2024.dec01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class LocationIds {

    private final List<Integer> left;
    private final List<Integer> right;

    LocationIds( List<Integer> left, List<Integer> right ) {
        this.left = List.copyOf( left );
        this.right = List.copyOf( right );
    }

    long distance() {
        List<Integer> left = new ArrayList<>( this.left );
        List<Integer> right = new ArrayList<>( this.right );
        left.sort( Integer::compareTo );
        right.sort( Integer::compareTo );
        long distance = 0L;
        for ( int i = 0; i < left.size(); ++i ) {
            distance += Math.abs( left.get( i ) - right.get( i ) );
        }

        return distance;
    }

    long similarityScore() {
        Map<Integer, AtomicInteger> rightCountsByValue = new HashMap<>();
        right
            .forEach( value -> rightCountsByValue.computeIfAbsent( value, k -> new AtomicInteger( 0 ) ).incrementAndGet() );

        return left
            .stream()
            .mapToLong( value -> (long) value * rightCountsByValue.getOrDefault( value, new AtomicInteger( 0 ) ).get() )
            .sum();
    }
}
