package com.adventofcode2024.dec07;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

record Equation( long testValue, List<Long> operands ) {

    boolean canBeSolved( Operator... allowedOperators ) {
        return
            possibleValues( operands, asList( allowedOperators ) ).contains( testValue );
    }

    private Set<Long> possibleValues( List<Long> operands, List<Operator> allowedOperators ) {
        Set<Long> possibleValues = new HashSet<>();
        possibleValues( operands.subList( 1, operands.size() ), allowedOperators, operands.get( 0 ), possibleValues );
        possibleValues( operands.subList( 1, operands.size() ), allowedOperators, operands.get( 0 ), possibleValues );

        return possibleValues;
    }

    private void possibleValues( List<Long> operands, List<Operator> allowedOperators, long currentValue, Set<Long> possibleValues ) {
        if ( operands.isEmpty() ) {
            possibleValues.add( currentValue );
        } else {
            allowedOperators
                .forEach( operator -> possibleValues( operands.subList( 1, operands.size() ), allowedOperators, operator.applyTo( currentValue, operands.get( 0 ) ), possibleValues ) );
        }
    }
}
