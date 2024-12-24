package com.adventofcode2024.dec21;

import com.adventofcode2024.common.Point;

import java.util.HashMap;
import java.util.Map;

class KeypadBuilder {

    Keypad buildNumericKeypad() {
        Map<Character, Point> keyPositions = new HashMap<>();
        keyPositions.put( '7', new Point( 0, 0 ) );
        keyPositions.put( '8', new Point( 1, 0 ) );
        keyPositions.put( '9', new Point( 2, 0 ) );
        keyPositions.put( '4', new Point( 0, 1 ) );
        keyPositions.put( '5', new Point( 1, 1 ) );
        keyPositions.put( '6', new Point( 2, 1 ) );
        keyPositions.put( '1', new Point( 0, 2 ) );
        keyPositions.put( '2', new Point( 1, 2 ) );
        keyPositions.put( '3', new Point( 2, 2 ) );
        keyPositions.put( 'G', new Point( 0, 3 ) );
        keyPositions.put( '0', new Point( 1, 3 ) );
        keyPositions.put( 'A', new Point( 2, 3 ) );
        return new Keypad( keyPositions, keyPositions.get( 'A' ) );
    }

    Keypad buildDirectionalKeypad() {
        Map<Character, Point> buttonPositions = new HashMap<>();
        buttonPositions.put( 'G', new Point( 0, 0 ) );
        buttonPositions.put( '^', new Point( 1, 0 ) );
        buttonPositions.put( 'A', new Point( 2, 0 ) );
        buttonPositions.put( '<', new Point( 0, 1 ) );
        buttonPositions.put( 'v', new Point( 1, 1 ) );
        buttonPositions.put( '>', new Point( 2, 1 ) );
        return new Keypad( buttonPositions, buttonPositions.get( 'A' ) );
    }
}
