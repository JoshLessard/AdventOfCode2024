package com.adventofcode2024.dec12;

import java.util.ArrayList;
import java.util.List;

class GardenMapBuilder {

    private int width = 0;
    private int height = 0;
    private final List<char[]> rows = new ArrayList<>();

    void addLine( String inputLine ) {
        char[] row = new char[inputLine.length()];
        for ( int x = 0; x < inputLine.length(); ++x ) {
            row[x] = inputLine.charAt( x );
        }
        rows.add( row );
        width = Math.max( width, row.length );
        ++height;
    }

    public GardenMap build() {
        char[][] plots = new char[height][];
        for ( int y = 0; y < height; ++y ) {
            plots[y] = rows.get( y );
        }
        return new GardenMap( width, height, plots );
    }
}
