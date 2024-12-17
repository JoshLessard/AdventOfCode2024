package com.adventofcode2024.dec16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        ReindeerMap map = parseReindeerMap();
        ShortestDistanceAndBestPathPositionCount lowestScoreAndBestPathTileCount = map.lowestScoreAndBestPathTileCount();
        System.out.println( lowestScoreAndBestPathTileCount.distance() );
        System.out.println( lowestScoreAndBestPathTileCount.positionCount() );
    }

    private static ReindeerMap parseReindeerMap() throws IOException {
        ReindeerMapBuilder builder = new ReindeerMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec16/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( builder::addLine );
        }
        return builder.build();
    }
}
