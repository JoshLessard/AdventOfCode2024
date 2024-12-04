package com.adventofcode2024.dec02;

import java.util.ArrayList;
import java.util.List;

class Report {

    private enum Direction {
        INCREASING,
        DECREASING;

        private static Direction ofDifference( int difference ) {
            return difference > 0
                ? INCREASING
                : DECREASING;
        }
    }

    private final List<Integer> levels;

    Report( List<Integer> levels ) {
        this.levels = levels;
    }

    public boolean isSafe() {
        return isSafe( levels );
    }

    private boolean isSafe( List<Integer> levels ) {
        Direction direction = Direction.ofDifference( levels.get( 1 ) - levels.get( 0 ) );
        for (int i = 1; i < levels.size(); i++ ) {
            int difference = levels.get( i ) - levels.get( i - 1 );
            if ( ! isGradual( difference ) || Direction.ofDifference( difference ) != direction ) {
                return false;
            }
        }

        return true;
    }

    private boolean isGradual( int difference ) {
        int absoluteDifference = Math.abs( difference );
        return absoluteDifference >= 1 && absoluteDifference <= 3;
    }

    public boolean isTolerantlySafe() {
        for ( int i = 0; i < levels.size(); ++i ) {
            if ( isSafe( levelsWithoutElementAtIndex( i ) ) ) {
                return true;
            }
        }

        return false;
    }

    private List<Integer> levelsWithoutElementAtIndex( int indexToRemove ) {
        List<Integer> levelsWithElementRemoved = new ArrayList<>( levels.size() - 1 );
        levelsWithElementRemoved.addAll( levels.subList( 0, indexToRemove ) );
        levelsWithElementRemoved.addAll( levels.subList( indexToRemove + 1, levels.size() ) );
        return levelsWithElementRemoved;
    }
}
