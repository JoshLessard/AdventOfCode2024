package com.adventofcode2024.dec11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

class Stones {

    private final long[] stones;

    Stones( long[] stones ) {
        this.stones = Arrays.copyOf( stones, stones.length );
    }

    long blink( int numberOfIterations ) {
        Map<Arguments, Long> resultsMemo = new HashMap<>();
        return Arrays.stream( stones )
            .map( stone -> countStones( stone, numberOfIterations, resultsMemo ) )
            .sum();
    }

    private long countStones( long stone, int iterationsRemaining, Map<Arguments, Long> resultsMemo ) {
        Arguments arguments = new Arguments( stone, iterationsRemaining );
        if ( resultsMemo.containsKey( arguments ) ) {
            return resultsMemo.get( arguments );
        } else {
            if ( iterationsRemaining == 0 ) {
                return 1L;
            } else {
                long numberOfStones = nextStones( stone )
                    .map( nextStone -> countStones( nextStone, iterationsRemaining - 1, resultsMemo ) )
                    .sum();
                resultsMemo.put( arguments, numberOfStones );
                return numberOfStones;
            }
        }
    }

    private LongStream nextStones( long stone ) {
        if ( stone == 0L ) {
            return LongStream.of( 1L );
        } else if ( hasEvenNumberOfDigits( stone ) ) {
            int numberOfDigits = countNumberOfDigits( stone );
            long leftStone = stone / (long) Math.pow( 10, numberOfDigits / 2 );
            long rightStones = stone % (long) Math.pow( 10, numberOfDigits / 2 );
            return LongStream.of( leftStone, rightStones );
        } else {
            return LongStream.of( stone * 2024 );
        }
    }

    private boolean hasEvenNumberOfDigits( long stone ) {
        return countNumberOfDigits( stone ) % 2 == 0;
    }

    private int countNumberOfDigits( long stone ) {
        int numberOfDigits = 0;
        while ( stone > 0 ) {
            stone /= 10;
            ++numberOfDigits;
        }
        return numberOfDigits;
    }

    record Arguments( long stone, int numberOfIterations ) {
    }
}
