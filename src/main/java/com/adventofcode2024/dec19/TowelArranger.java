package com.adventofcode2024.dec19;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

class TowelArranger {

    private static final AtomicLong ZERO = new AtomicLong( 0L );

    private final Map<Character, List<String>> towelPatternsByFirstColour;
    private final Map<Integer, List<String>> towelPatternsByLength;

    TowelArranger( List<String> towelPatterns ) {
        this.towelPatternsByFirstColour = towelPatterns
            .stream()
            .collect( groupingBy( pattern -> pattern.charAt( 0 ) ) );
        this.towelPatternsByLength = towelPatterns
            .stream()
            .collect( groupingBy( String::length ) );
    }

    boolean isPossible( String design ) {
        for ( String pattern : towelPatternsByFirstColour.getOrDefault( design.charAt( 0 ), emptyList() ) ) {
            if (
                design.equals( pattern )
                ||
                design.startsWith( pattern ) && isPossible( design.substring( pattern.length() ) )
            ) {
                return true;
            }
        }

        return false;
    }

    long numberOfDifferentWaysToMake( String design ) {
        Map<String, AtomicLong> memo = new HashMap<>();
        memo.put( "", new AtomicLong( 1L ) );
        for ( int endIndex = design.length(); endIndex > 0; --endIndex ) {
            for ( int patternLength : towelPatternsByLength.keySet() ) {
                int startIndex = endIndex - patternLength;
                if ( startIndex >= 0 ) {
                    String designPortion = design.substring( startIndex, endIndex );
                    for ( String towelPattern : towelPatternsByLength.get( patternLength ) ) {
                        if ( towelPattern.equals( designPortion ) ) {
                            long numberOfWaysToMakeSuffixAfterDesignPortion = memo
                                .getOrDefault( design.substring( endIndex ), ZERO ).get();
                            memo.computeIfAbsent( design.substring( startIndex ), s -> new AtomicLong( 0L ) )
                                .addAndGet( numberOfWaysToMakeSuffixAfterDesignPortion );
                            break;
                        }
                    }
                }
            }
        }

        return memo.getOrDefault( design, ZERO ).get();
    }
}
