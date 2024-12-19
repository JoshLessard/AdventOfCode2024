package com.adventofcode2024.dec18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode2024.common.Point;

public class Main {

    public static void main( String[] args ) throws Exception {
        MemorySpace memorySpace = new MemorySpace( 70 );
        List<Point> bytePositions = parseBytePositions();
        for ( int i = 0; i < 1024; ++i ) {
            memorySpace.corruptPosition( bytePositions.get( i ) );
        }
        System.out.println( "Minimum steps to exit: " + memorySpace.minimumStepsToReachExit() );

        for ( int i = 1024; i < bytePositions.size(); ++i ) {
            Point bytePosition = bytePositions.get( i );
            memorySpace.corruptPosition( bytePosition );
            if ( memorySpace.minimumStepsToReachExit() == Integer.MAX_VALUE ) {
                System.out.println( "Byte position that blocks exit: " + bytePosition.x() + "," + bytePosition.y() );
                break;
            }
        }
    }

    private static List<Point> parseBytePositions() throws IOException {
        Pattern bytePositionPattern = Pattern.compile( "^(\\d+),(\\d+)$" );
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec18/input.txt" ) ) ) {
            return reader
                .lines()
                .map( bytePositionPattern::matcher )
                .filter( Matcher::matches )
                .map( matcher -> new Point( Integer.parseInt( matcher.group( 1 ) ), Integer.parseInt( matcher.group( 2 ) ) ) )
                .toList();
        }
    }
}
