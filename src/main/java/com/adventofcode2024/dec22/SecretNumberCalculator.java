package com.adventofcode2024.dec22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SecretNumberCalculator {

    private final List<SecretNumber> secretNumbers;

    SecretNumberCalculator( List<SecretNumber> secretNumbers ) {
        this.secretNumbers = List.copyOf( secretNumbers );
    }

    long sum() {
        return secretNumbers
            .stream()
            .mapToLong( SecretNumber::value )
            .sum();
    }

    int mostAvailableBananas() {
        Map<SecretNumber.Sequence, Integer> bananasBySequence = new HashMap<>();
        secretNumbers
            .stream()
            .map( SecretNumber::bananasBySequence )
            .forEach( m ->
                m.forEach( (sequence, bananas) ->
                    bananasBySequence.put(
                        sequence,
                        bananas + bananasBySequence.getOrDefault( sequence, 0 )
                    )
                )
            );
        return bananasBySequence.values()
            .stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElseThrow();
    }
}
