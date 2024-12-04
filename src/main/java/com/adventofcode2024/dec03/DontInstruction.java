package com.adventofcode2024.dec03;

class DontInstruction implements Instruction {

    @Override
    public ComputerState nextState( ComputerState currentState ) {
        return ComputerState.DISABLED;
    }

    @Override
    public long returnValue() {
        return 0;
    }
}
