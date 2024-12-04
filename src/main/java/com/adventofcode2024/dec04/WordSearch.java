package com.adventofcode2024.dec04;

import java.util.Arrays;

class WordSearch {

    private final char[][] board;
    private final int width;
    private final int height;

    WordSearch( char[][] board ) {
        this.board = board;
        this.width = board[0].length;
        this.height = board.length;
    }

    int numberOfOccurrencesOfWord( String word ) {
        int numberOfOccurrences = 0;
        for ( int y = 0; y < height; ++y ) {
            for ( int x = 0; x < width; ++x ) {
                numberOfOccurrences += numberOfOccurrencesOfWord( word, new Point( x, y ) );
            }
        }
        return numberOfOccurrences;
    }

    private int numberOfOccurrencesOfWord( String word, Point startingPoint ) {
        return (int) Arrays.stream( Direction.values() )
            .filter( direction -> containsWord( word, startingPoint, direction ) )
            .count();
    }

    private boolean containsWord( String word, Point point, Direction direction ) {
        for ( int i = 0; i < word.length(); ++i ) {
            if ( ! containsCharacter(word.charAt(i), point) ) {
                return false;
            }
            point = point.nextPoint( direction );
        }
        return true;
    }

    private boolean containsCharacter( char character, Point point ) {
        return isInBounds( point ) && board[point.y()][point.x()] == character;
    }

    private boolean isInBounds( Point point ) {
        return point.x() >= 0 && point.x() < width && point.y() >= 0 && point.y() < height;
    }

    int numberOfOccurrencesOfXmas() {
        int numberOfOccurrences = 0;
        for ( int y = 0; y < height; ++y ) {
            for ( int x = 0; x < width; ++x ) {
                Point currentPoint = new Point( x, y );
                Point topLeft = currentPoint.nextPoint( Direction.UP_LEFT );
                Point topRight = currentPoint.nextPoint( Direction.UP_RIGHT );
                Point bottomLeft = currentPoint.nextPoint( Direction.DOWN_LEFT );
                Point bottomRight = currentPoint.nextPoint( Direction.DOWN_RIGHT );
                if ( containsCharacter( 'A', currentPoint )
                    && (
                        ( containsCharacter( 'M', topLeft ) && containsCharacter( 'S', bottomRight ) )
                        ||
                        (containsCharacter( 'S', topLeft ) && containsCharacter( 'M', bottomRight ) )
                    )
                    && (
                        ( containsCharacter( 'M', topRight ) && containsCharacter( 'S', bottomLeft ) )
                        ||
                        ( containsCharacter( 'S', topRight ) && containsCharacter( 'M', bottomLeft ) )
                    )
                ) {
                    ++numberOfOccurrences;
                }
            }
        }

        return numberOfOccurrences;
    }
}
