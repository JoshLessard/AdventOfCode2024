package com.adventofcode2024.dec12;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode2024.common.Direction;
import com.adventofcode2024.common.Point;

class GardenMap {

    private final int width;
    private final int height;
    private final char[][] plants;

    GardenMap( int width, int height, char[][] plants ) {
        this.width = width;
        this.height = height;
        this.plants = plants;
    }

    int calculateFencePrice() {
        return findRegions()
            .stream()
            .mapToInt( Region::calculateFencePrice )
            .sum();
    }

    int calculateBulkDiscountFencePrice() {
        return findRegions()
            .stream()
            .mapToInt( Region::calculateBulkDiscountFencePrice )
            .sum();
    }

    private List<Region> findRegions() {
        List<Region> regions = new ArrayList<>();
        Set<Point> visited = new HashSet<>();
        for ( int y = 0; y < height; ++y ) {
            for ( int x = 0; x < width; ++x ) {
                Point point = new Point(x, y);
                if ( ! visited.contains( point ) ) {
                    regions.add( findRegion( plantAt( point ), point, visited ) );
                }
            }
        }
        return regions;
    }

    private Region findRegion( char plant, Point startingPoint, Set<Point> pointsAssignedToRegion ) {
        Set<Point> regionPoints = new HashSet<>();
        Deque<Point> pointsToProcess = new LinkedList<>();
        pointsToProcess.add( startingPoint );
        while ( ! pointsToProcess.isEmpty() ) {
            Point point = pointsToProcess.removeFirst();
            if ( ! pointsAssignedToRegion.contains( point ) && plantAt( point ) == plant ) {
                regionPoints.add( point );
                pointsAssignedToRegion.add( point );
                pointsToProcess.addAll( getInboundNeighbours( point ) );
            }
        }
        return new Region( plant, regionPoints );
    }

    private char plantAt( Point point ) {
        return plants[point.y()][point.x()];
    }

    private Set<Point> getInboundNeighbours( Point point ) {
        return getNeighbours( point )
            .filter( this::isInBounds )
            .collect( toSet() );
    }

    private Stream<Point> getNeighbours( Point point ) {
        return Stream.of(
            point.nextPoint( Direction.UP ),
            point.nextPoint( Direction.RIGHT ),
            point.nextPoint( Direction.DOWN ),
            point.nextPoint( Direction.LEFT )
        );
    }

    private boolean isInBounds( Point point ) {
        return
            point.x() >= 0 && point.x() < width
            &&
            point.y() >= 0 && point.y() < height;
    }

    private class Region {

        private final char plant;
        private final Set<Point> locations;

        Region( char plant, Set<Point> locations ) {
            this.plant = plant;
            this.locations = locations;
        }

        int calculateFencePrice() {
            return perimeter() * area();
        }

        private int perimeter() {
            return getEdges().size();
        }

        private int area() {
            return locations.size();
        }

        private Set<Edge> getEdges() {
            Set<Edge> edges = new HashSet<>();
            for ( Point location : locations ) {
                for ( Direction direction : List.of( Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT ) ) {
                    if ( isOutsideRegion( location.nextPoint( direction ) ) ) {
                        edges.add( new Edge( location, direction ) );
                    }
                }
            }

            return edges;
        }

        private boolean isOutsideRegion( Point neighbour ) {
            return ! isInBounds( neighbour ) || plantAt( neighbour ) != plant;
        }

        int calculateBulkDiscountFencePrice() {
            return numberOfSides() * area();
        }

        private int numberOfSides() {
            Map<Direction, List<Edge>> edgesByDirection = getEdges()
                .stream()
                .collect( groupingBy( Edge::direction, toList() ) );

            return
                countHorizontalSides( edgesByDirection.get( Direction.UP ) )
                + countHorizontalSides( edgesByDirection.get( Direction.DOWN ) )
                + countVerticalSides( edgesByDirection.get( Direction.LEFT ) )
                + countVerticalSides( edgesByDirection.get( Direction.RIGHT ) );
        }

        private int countHorizontalSides( List<Edge> edges ) {
            int numberOfSides = 0;
            edges.sort( comparing( Edge::location, comparingInt( Point::y ).thenComparingInt( Point::x ) ) );
            while ( ! edges.isEmpty() ) {
                int lastIndexOfSide = 1;
                while ( lastIndexOfSide < edges.size() ) {
                    if ( edges.get( lastIndexOfSide ).isDirectlyToRightOf( edges.get( lastIndexOfSide - 1 ) ) ) {
                        ++lastIndexOfSide;
                    } else {
                        break;
                    }
                }
                edges = edges.subList( lastIndexOfSide, edges.size() );
                ++numberOfSides;
            }

            return numberOfSides;
        }

        private int countVerticalSides( List<Edge> edges ) {
            int numberOfSides = 0;
            edges.sort( comparing( Edge::location, comparingInt( Point::x ).thenComparingInt( Point::y ) ) );
            while ( ! edges.isEmpty() ) {
                int lastIndexOfSide = 1;
                while ( lastIndexOfSide < edges.size() ) {
                    if ( edges.get( lastIndexOfSide ).isDirectlyBelow( edges.get( lastIndexOfSide - 1 ) ) ) {
                        ++lastIndexOfSide;
                    } else {
                        break;
                    }
                }
                edges = edges.subList( lastIndexOfSide, edges.size() );
                ++numberOfSides;
            }

            return numberOfSides;
        }
    }
}
