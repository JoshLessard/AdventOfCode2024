package com.adventofcode2024.dec03;

class MultiplicationInstruction implements Instruction {

    private final long leftOperand;
    private final long rightOperand;

    MultiplicationInstruction( long leftOperand, long rightOperand ) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public ComputerState nextState(ComputerState currentState) {
        return currentState;
    }

    @Override
    public long returnValue() {
        return leftOperand * rightOperand;
    }
}
