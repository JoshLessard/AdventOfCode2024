package com.adventofcode2024.dec15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.adventofcode2024.common.Direction;

public class Main {

    public static void main( String[] args ) throws Exception {
        WarehouseMap map = parseMap();
        System.out.println( "GPS coordinate sums (part one): " + map.gpsCoordinateSums() );

        DoubleWidthWarehouseMap doubleWidthMap = parseDoubleWidthMap();
        System.out.println( "GPS coordinate sums (part two): " + doubleWidthMap.gpsCoordinateSums() );
    }

    private static WarehouseMap parseMap() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec15/input.txt" ) ) ) {
            WarehouseMapBuilder builder = new WarehouseMapBuilder();
            String inputLine;
            while ( ! ( inputLine = reader.readLine() ).isEmpty() ) {
                builder.addLine( inputLine );
            }
            WarehouseMap map = builder.build();
            while ( (inputLine = reader.readLine() ) != null ) {
                inputLine.chars()
                    .forEach( character -> {
                        switch ( character ) {
                            case '<' -> map.moveRobot( Direction.LEFT );
                            case '^' -> map.moveRobot( Direction.UP );
                            case '>' -> map.moveRobot( Direction.RIGHT );
                            case 'v' -> map.moveRobot( Direction.DOWN );
                            default -> throw new IllegalArgumentException( "Unrecognized character: " + character );
                        }
                    });
            }
            return map;
        }
    }

    private static DoubleWidthWarehouseMap parseDoubleWidthMap() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec15/input.txt" ) ) ) {
            DoubleWidthWarehouseMapBuilder builder = new DoubleWidthWarehouseMapBuilder();
            String inputLine;
            while ( ! ( inputLine = reader.readLine() ).isEmpty() ) {
                builder.addLine( inputLine );
            }
            DoubleWidthWarehouseMap map = builder.build();
            while ( (inputLine = reader.readLine() ) != null ) {
                inputLine.chars()
                    .forEach( character -> {
                        switch ( character ) {
                            case '<' -> map.moveRobot( Direction.LEFT );
                            case '^' -> map.moveRobot( Direction.UP );
                            case '>' -> map.moveRobot( Direction.RIGHT );
                            case 'v' -> map.moveRobot( Direction.DOWN );
                            default -> throw new IllegalArgumentException( "Unrecognized character: " + character );
                        }
                    });
            }
            return map;
        }
    }
}
