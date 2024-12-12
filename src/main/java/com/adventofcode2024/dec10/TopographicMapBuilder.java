package com.adventofcode2024.dec10;

import java.util.ArrayList;
import java.util.List;

class TopographicMapBuilder {

    private int width;
    private int height;
    private final List<byte[]> rows = new ArrayList<>();

    void addRow( String inputLine ) {
        rows.add( toHeights( inputLine ) );
        width = Math.max( width, inputLine.length() );
        ++height;
    }

    private byte[] toHeights(String inputLine) {
        byte[] row = new byte[inputLine.length()];
        for (int i = 0; i < inputLine.length(); ++i ) {
            row[i] = (byte) (inputLine.charAt( i ) - '0');
        }
        return row;
    }

    TopographicMap build() {
        byte[][] heights = new byte[height][];
        for ( int i = 0; i < height; ++i ) {
            heights[i] = rows.get( i );
        }

        return new TopographicMap( width, height, heights );
    }
}
