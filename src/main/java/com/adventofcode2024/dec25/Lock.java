package com.adventofcode2024.dec25;

class Lock {

    private final char[][] pins;

    Lock( char[][] pins ) {
        this.pins = pins;
    }

    boolean accepts( Key key ) {
        for ( int pin = 0; pin < 5; ++pin ) {
            if ( pinHeight( pin ) + key.height( pin ) > 5 ) {
                return false;
            }
        }

        return true;
    }

    private int pinHeight( int pin ) {
        int height = 0;
        for ( int h = 0; h < 5; ++h ) {
            if ( pins[h][pin] == '#' ) {
                ++height;
            }
        }

        return height;
    }
}
