package com.adventofcode2024.dec24;

class LogicGate {

    private final Wire inputWire1;
    private final Wire inputWire2;
    private final LogicOperation operation;
    private final Wire outputWire;
    private final boolean[] inputValues = new boolean[2];
    private int numberOfInputValues = 0;

    LogicGate( Wire inputWire1, Wire inputWire2, LogicOperation operation, Wire outputWire ) {
        this.inputWire1 = inputWire1;
        this.inputWire2 = inputWire2;
        this.operation = operation;
        this.outputWire = outputWire;
    }

    void receiveInput( boolean value ) {
        inputValues[numberOfInputValues++] = value;
        if ( numberOfInputValues == 2 ) {
            boolean outputValue = operation.apply( inputValues[0], inputValues[1] );
            outputWire.setValue( outputValue );
        }
    }

    boolean isOr() {
        return operation.equals( LogicOperation.OR );
    }

    boolean isAnd() {
        return operation.equals( LogicOperation.AND );
    }

    boolean isXor() {
        return operation.equals( LogicOperation.XOR );
    }

    Wire outputWire() {
        return outputWire;
    }

    boolean isInput() {
        return
            inputWire1.isInput()
            && inputWire2.isInput()
            && ! outputWire.isOutput();
    }

    boolean isFirstInput() {
        return inputWire1.name().equals( "x00" )
            || inputWire2.name().equals( "x00" )
            || inputWire1.name().equals( "y00" )
            || inputWire2.name().equals( "y00" );
    }

    boolean isOutput() {
        return outputWire.isOutput();
    }

    boolean isLastOutput() {
        return outputWire.name().equals( "z45" );
    }

    boolean isIntermediate() {
        return
            ! inputWire1.isInput()
            && ! inputWire2.isInput()
            && ! outputWire.isOutput();
    }

    boolean hasInputWire( Wire wire ) {
        return inputWire1.equals( wire ) || inputWire2.equals( wire );
    }
}
