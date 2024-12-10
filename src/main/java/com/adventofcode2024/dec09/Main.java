package com.adventofcode2024.dec09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        DiskMap diskMap = parseDiskMap();
        diskMap.compact();
        System.out.println( "Checksum: " + diskMap.checksum() );

        diskMap = parseDiskMap();
        diskMap.compactWholeFiles();
        System.out.println( "Checksum: " + diskMap.checksum() );
    }

    private static DiskMap parseDiskMap() throws IOException {
        DiskMapBuilder builder = new DiskMapBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec09/input.txt" ) ) ) {
            reader
                .readLine()
                .chars()
                .map( c -> c - '0' )
                .forEach( builder::addBlock );
        }

        return builder.build();
    }
}
