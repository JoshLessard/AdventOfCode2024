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
        List<String> sequences = sequencesFor( code );
        return lengthOfShortest( sequences ) * numericPart( code );
    }

    private List<String> sequencesFor( String code ) {
        List<String> sequences = numericKeypad.sequencesForCode( code );
        for ( int i = 0; i < numberOfDirectionalKeypads; ++i ) {
            sequences = sequences
                .stream()
                .map( directionKeypad::sequencesForCode )
                .flatMap( List::stream )
                .toList();
        }
        return sequences;
    }

    private long lengthOfShortest( List<String> sequences ) {
        return sequences
            .stream()
            .mapToInt( String::length )
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
