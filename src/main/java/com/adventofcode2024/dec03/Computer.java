package com.adventofcode2024.dec03;

import java.util.List;

class Computer {

    private final List<Instruction> instructions;

    Computer( List<Instruction> instructions ) {
        this.instructions = instructions;
    }

    long executeInstructionsWithoutStateChanges() {
        return instructions
            .stream()
            .mapToLong( Instruction::returnValue )
            .sum();
    }

    long executeInstructions() {
        ComputerState state = ComputerState.ENABLED;
        long sum = 0;
        for ( Instruction instruction : instructions ) {
            state = instruction.nextState( state );
            if ( state == ComputerState.ENABLED ) {
                sum += instruction.returnValue();
            }
        }

        return sum;
    }
}
