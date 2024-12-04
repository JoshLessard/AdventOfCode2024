package com.adventofcode2024.dec04;

import java.util.ArrayList;
import java.util.List;

class WordSearchBuilder {

    private final List<char[]> rows = new ArrayList<>();

    void addRow( String row ) {
        rows.add( row.toCharArray() );
    }

    public WordSearch build() {
        char[][] board = new char[rows.size()][];
        for ( int i = 0; i < rows.size(); i++ ) {
            board[i] = rows.get( i );
        }
        return new WordSearch( board );
    }
}
