package com.adventofcode2024.dec22;

import java.util.Map;

class SecretNumber {

    private final long value;
    private final Map<Sequence, Integer> bananasBySequence;

    SecretNumber( long value, Map<Sequence, Integer> bananasBySequence ) {
        this.value = value;
        this.bananasBySequence = Map.copyOf( bananasBySequence );
    }

    long value() {
        return value;
    }

    Map<Sequence, Integer> bananasBySequence() {
        return bananasBySequence;
    }

    record Sequence( int first, int second, int third, int fourth ) {
    }
}
