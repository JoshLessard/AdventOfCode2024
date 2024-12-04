package com.adventofcode2024.dec02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws Exception {
        List<Report> reports = parseReports();
        int numberOfSafeReports = (int) reports
            .stream()
            .filter( Report::isSafe )
            .count();
        System.out.println( "Number of safe reports: " + numberOfSafeReports );

        int numberOfTolerantlySafeReports = (int) reports
            .stream()
            .filter( Report::isTolerantlySafe )
            .count();
        System.out.println( "Number of tolerantly safe reports: " + numberOfTolerantlySafeReports );
    }

    private static List<Report> parseReports() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec02/input.txt" ) ) ) {
            return reader
                .lines()
                .map( line -> line.split( "\\s+" ) )
                .map( line -> Arrays.stream( line ).map( Integer::valueOf ).toList() )
                .map( Report::new )
                .toList();
        }
    }
}
