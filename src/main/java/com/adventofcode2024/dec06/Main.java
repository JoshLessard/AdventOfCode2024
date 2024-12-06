package com.adventofcode2024.dec06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        LabMap labMap = parseMap();

        LabMap.Statistics statistics = labMap.doGuardPatrol();
        System.out.println( "Number of unique positions visited: " + statistics.uniqueGuardPositionCount() );
        System.out.println( "Number of new obstruction positions: " + statistics.newObstructionPositionCount() );
    }

    private static LabMap parseMap() throws IOException {
        LabMapBuilder mapBuilder = new LabMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec06/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( mapBuilder::addRow );
        }
        return mapBuilder.build();
    }
}
