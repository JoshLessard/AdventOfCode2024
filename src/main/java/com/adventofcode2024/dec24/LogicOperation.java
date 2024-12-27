package com.adventofcode2024.dec24;

enum LogicOperation {
    OR( (a, b) -> a | b ),
    AND( (a, b) -> a & b ),
    XOR( (a, b) -> a ^ b );

    private final BiBooleanFunction function;

    LogicOperation( BiBooleanFunction function ) {
        this.function = function;
    }

    boolean apply( boolean inputValue1, boolean inputValue2 ) {
        return function.apply( inputValue1, inputValue2 );
    }


    @FunctionalInterface
    private interface BiBooleanFunction {

        boolean apply( boolean inputValue1, boolean inputValue2 );
    }
}
