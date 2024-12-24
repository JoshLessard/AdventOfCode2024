package com.adventofcode2024.dec21;

import java.util.List;

class Starship {

    private final Keypad numericKeypad;
    private final Keypad directionKeypad;
    private final int numberOfDirectionalKeypads;

    Starship(
        Keypad numericKeypad,
        Keypad directionKeypad,
        int numberOfDirectionalKeypads
    ) {
        this.numericKeypad = numericKeypad;
        this.directionKeypad = directionKeypad;
        this.numberOfDirectionalKeypads = numberOfDirectionalKeypads;
    }

    long complexityOf( String code ) {
        return lengthOfShortestSequence( code ) * numericPart( code );
    }

    private long lengthOfShortestSequence( String code ) {
        List<String> sequencesOnNumericKeypad = numericKeypad.sequencesForCode( code );
        return sequencesOnNumericKeypad
            .stream()
            .mapToLong( sequence -> directionKeypad.lengthOfShortestSequence( sequence, numberOfDirectionalKeypads ) )
            .min()
            .orElseThrow();
    }

    private int numericPart( String code ) {
        int numericPart = 0;
        for ( char character : code.toCharArray() ) {
            if ( Character.isDigit( character ) ) {
                numericPart *= 10;
                numericPart += character - '0';
            }
        }
        return numericPart;
    }
}
