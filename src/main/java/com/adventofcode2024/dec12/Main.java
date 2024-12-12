package com.adventofcode2024.dec12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        GardenMap map = parseGardenMap();

        System.out.println( "Fence price: " + map.calculateFencePrice() );
        System.out.println( "Bulk discount fence price: " + map.calculateBulkDiscountFencePrice() );
    }

    private static GardenMap parseGardenMap() throws IOException {
        GardenMapBuilder builder = new GardenMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec12/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( builder::addLine );
        }

        return builder.build();
    }
}
