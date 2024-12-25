package com.adventofcode2024.dec22;

import java.util.HashMap;
import java.util.Map;

class SecretNumberBuilder {

    private final int numberOfIterations;

    SecretNumberBuilder( int numberOfIterations ) {
        this.numberOfIterations = numberOfIterations;
    }

    SecretNumber build( long value ) {
        Map<SecretNumber.Sequence, Integer> bananasBySequence = new HashMap<>();
        int lastPrice = (int) (value % 10);
        int[] differences = new int[numberOfIterations];
        for ( int i = 0; i < numberOfIterations; ++i ) {
            value = iterate( value );
            int currentPrice = (int) (value % 10);
            differences[i] = currentPrice - lastPrice;
            lastPrice = currentPrice;
            if ( i >= 3 ) {
                SecretNumber.Sequence sequence = new SecretNumber.Sequence( differences[i-3], differences[i-2], differences[i-1], differences[i] );
                bananasBySequence.putIfAbsent( sequence, currentPrice );
            }
        }

        return new SecretNumber( value, bananasBySequence );
    }

    private long iterate( long value ) {
        value = prune( mix( value, value * 64 ) );
        value = prune( mix( value, value / 32 ) );
        return prune( mix( value, value * 2048 ) );
    }

    private long mix( long value, long numberToBeMixed ) {
        return value ^ numberToBeMixed;
    }

    private long prune( long value ) {
        return value % 16777216;
    }
}
