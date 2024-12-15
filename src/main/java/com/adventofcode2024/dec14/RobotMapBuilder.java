package com.adventofcode2024.dec14;

import java.util.ArrayList;
import java.util.List;

class RobotMapBuilder {

    private final int width;
    private final int height;
    private final List<Robot> robots = new ArrayList<>();

    RobotMapBuilder( int width, int height ) {
        this.width = width;
        this.height = height;
    }


    void addRobot( Robot robot ) {
        robots.add( robot );
    }

    RobotMap build() {
        return new RobotMap( width, height, robots );
    }
}
