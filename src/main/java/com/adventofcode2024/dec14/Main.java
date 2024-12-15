package com.adventofcode2024.dec14;

import com.adventofcode2024.common.Point;
import com.adventofcode2024.common.Slope;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main( String[] args ) throws Exception {
        RobotMap map = parseInput();
        System.out.println( "Safety factor: " + map.safetyFactorAfter( 100 ) );
        System.out.println();
        for ( int i = 0; ; ++i ) {
            String display = map.displayIfAtLeast( 7 ).percentOfRobotsNotInAQuadrantAfterSeconds( i );
            if ( ! display.isEmpty() ) {
                System.out.println( display );
                System.out.println( "After " + i + " seconds." );
                break;
            }
        }
    }

    private static RobotMap parseInput() throws Exception {
        RobotMapBuilder builder = new RobotMapBuilder( 101, 103 );
        Pattern robotPattern = Pattern.compile( "^p=(\\d+),(\\d+)\\s+v=(-?\\d+),(-?\\d+)$" );
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec14/input.txt" ) ) ) {
            reader
                .lines()
                .map( robotPattern::matcher )
                .filter( Matcher::matches )
                .map( matcher -> {
                    Point position = new Point(
                        Integer.parseInt( matcher.group( 1 ) ),
                        Integer.parseInt( matcher.group( 2 ) )
                    );
                    Slope velocity = new Slope(
                        Integer.parseInt( matcher.group( 3 ) ),
                        Integer.parseInt( matcher.group( 4 ) )
                    );
                    return new Robot( position, velocity );
                } )
                .forEach( builder::addRobot );
        }

        return builder.build();
    }
}
