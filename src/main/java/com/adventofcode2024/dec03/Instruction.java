package com.adventofcode2024.dec03;

interface Instruction {

    ComputerState nextState( ComputerState currentState );
    long returnValue();
}
