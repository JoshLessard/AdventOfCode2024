package com.adventofcode2024.dec04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main( String[] args ) throws Exception {
        WordSearch wordSearch = parseWordSearch();
        System.out.println( "Number of occurrences of XMAS: " + wordSearch.numberOfOccurrencesOfWord( "XMAS" ) );
        System.out.println( "Number of occurrences of X-MAS: " + wordSearch.numberOfOccurrencesOfXmas() );
    }

    private static WordSearch parseWordSearch() throws IOException {
        WordSearchBuilder builder = new WordSearchBuilder();
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec04/input.txt" ) ) ) {
            reader
                .lines()
                .forEach( builder::addRow );
        }

        return builder.build();
    }
}
