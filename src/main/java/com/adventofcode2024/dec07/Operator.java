package com.adventofcode2024.dec07;

import java.util.function.LongBinaryOperator;

enum Operator {
    ADDITION( Long::sum ),
    MULTIPLICATION( (leftOperand, rightOperand) -> leftOperand * rightOperand ),
    CONCATENATION( (leftOperand, rightOperand) -> Long.parseLong( Long.toString( leftOperand ) + rightOperand ) );

    Operator( LongBinaryOperator operator ) {
        this.operator = operator;
    }

    private final LongBinaryOperator operator;

    long applyTo( long leftOperand, long rightOperand ) {
        return operator.applyAsLong( leftOperand, rightOperand );
    };
}
