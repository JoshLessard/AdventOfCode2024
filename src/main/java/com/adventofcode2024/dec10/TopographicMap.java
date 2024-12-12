package com.adventofcode2024.dec10;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class TopographicMap {

    private final int width;
    private final int height;
    private final byte[][] heights;

    TopographicMap( int width, int height, byte[][] heights ) {
        this.width = width;
        this.height = height;
        this.heights = heights;
    }

    int trailheadScore() {
        return allPositions()
            .stream()
            .mapToInt( this::calculateTrailheadScore )
            .sum();
    }

    private Set<Point> allPositions() {
        Set<Point> positions = new HashSet<>();
        for ( int y = 0; y < height; ++y ) {
            for ( int x = 0; x < width; ++x ) {
                positions.add( new Point( x, y ) );
            }
        }
        return positions;
    }

    private int calculateTrailheadScore( Point point ) {
        return calculateTrailheadScore( point, new HashSet<>(), 0 );
    }

    private int calculateTrailheadScore( Point point, Set<Point> visitedPoints, int height ) {
        if ( ! isInbounds( point ) || visitedPoints.contains( point ) || heightOf( point ) != height ) {
            return 0;
        }
        visitedPoints.add( point );
        return height == 9
            ? 1
            : calculateTrailheadScore( point.nextPoint( Direction.UP ), visitedPoints, height + 1 )
                + calculateTrailheadScore( point.nextPoint( Direction.DOWN ), visitedPoints, height + 1 )
                + calculateTrailheadScore( point.nextPoint( Direction.LEFT ), visitedPoints, height + 1 )
                + calculateTrailheadScore( point.nextPoint( Direction.RIGHT ), visitedPoints, height + 1 );
    }

    private boolean isInbounds( Point point ) {
        return point.x() >= 0 && point.x() < width
            && point.y() >= 0 && point.y() < height;
    }

    private int heightOf( Point point ) {
        return heights[point.y()][point.x()];
    }

    long trailheadRating() {
        return allPositions()
            .stream()
            .mapToLong( this::calculateTrailheadRating )
            .sum();
    }

    private long calculateTrailheadRating( Point point ) {
        return calculateTrailheadRating( point, 0 );
    }

    private long calculateTrailheadRating( Point point, int height ) {
        if ( ! isInbounds( point ) || heightOf( point ) != height ) {
            return 0;
        }
        return height == 9
            ? 1
            : calculateTrailheadRating( point.nextPoint( Direction.UP ), height + 1 )
                + calculateTrailheadRating( point.nextPoint( Direction.DOWN ), height + 1 )
                + calculateTrailheadRating( point.nextPoint( Direction.LEFT ), height + 1 )
                + calculateTrailheadRating( point.nextPoint( Direction.RIGHT ), height + 1 );
    }
}
