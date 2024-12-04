package com.adventofcode2024.dec03;

class DoInstruction implements Instruction {

    @Override
    public ComputerState nextState( ComputerState currentState ) {
        return ComputerState.ENABLED;
    }

    @Override
    public long returnValue() {
        return 0;
    }
}
