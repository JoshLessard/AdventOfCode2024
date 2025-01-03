package com.adventofcode2024.dec25;

class Key {

    private final char[][] heights;

    Key( char[][] heights ) {
        this.heights = heights;
    }

    int height( int column ) {
        int height = 0;
        for ( int h = 0; h < 5; ++h ) {
            if ( heights[h][column] == '#' ) {
                ++height;
            }
        }

        return height;
    }
}
