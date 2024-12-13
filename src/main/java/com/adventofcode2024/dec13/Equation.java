package com.adventofcode2024.dec13;

record Equation( long aCoefficient, long bCoefficient, long answer ) {

    Equation multipliedBy( long value ) {
        return new Equation( aCoefficient * value, bCoefficient * value, answer * value );
    }

    Equation subtract( Equation that ) {
        Equation thisWithNormalizedACoefficient = this.multipliedBy( that.aCoefficient );
        Equation thatWithNormalizedACoefficient = that.multipliedBy( this.aCoefficient );
        return thisWithNormalizedACoefficient.minus( thatWithNormalizedACoefficient );
    }

    private Equation minus( Equation that ) {
        return new Equation( this.aCoefficient - that.aCoefficient, this.bCoefficient - that.bCoefficient, this.answer - that.answer );
    }

    public long solveForA( long bValue ) {
        return ( answer - bValue * bCoefficient ) / aCoefficient;
    }
}
