package com.adventofcode2024.common;

public record Slope( int deltaX, int deltaY ) {

    public Slope inverse() {
        return new Slope( -deltaX, -deltaY );
    }
}
