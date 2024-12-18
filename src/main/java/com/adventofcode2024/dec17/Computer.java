package com.adventofcode2024.dec17;

import java.util.ArrayList;
import java.util.List;

class Computer {

    private final Register registerA;
    private final Register registerB;
    private final Register registerC;
    private final List<Byte> instructions;
    private int instructionPointer;
    private final List<Byte> output;

    Computer( Register registerA, Register registerB, Register registerC, List<Byte> instructions ) {
        this.registerA = registerA;
        this.registerB = registerB;
        this.registerC = registerC;
        this.instructions = List.copyOf( instructions );
        this.instructionPointer = 0;
        this.output = new ArrayList<>();
    }

    List<Byte> run() {
        while ( instructionPointer < instructions.size() ) {
            byte opCode = instructions.get( instructionPointer );
            byte operand = instructions.get( instructionPointer + 1 );
            switch ( opCode ) {
                case 0 -> instructionPointer = adv( operand ).jump( instructionPointer );
                case 1 -> instructionPointer = bxl( operand ).jump( instructionPointer );
                case 2 -> instructionPointer = bst( operand ).jump( instructionPointer );
                case 3 -> instructionPointer = jnz( operand ).jump( instructionPointer );
                case 4 -> instructionPointer = bxc( operand ).jump( instructionPointer );
                case 5 -> instructionPointer = out( operand ).jump( instructionPointer );
                case 6 -> instructionPointer = bdv( operand ).jump( instructionPointer );
                case 7 -> instructionPointer = cdv( operand ).jump( instructionPointer );
                default -> throw new IllegalArgumentException( "Invalid opcode: " + opCode );
            }
        }
        return output;
    }

    private InstructionPointerJump adv( byte comboOperand ) {
        long numerator = registerA.value();
        long denominator = 1L << comboOperandValue( comboOperand );
        long quotient = numerator / denominator;
        registerA.setValue( quotient );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump bxl( byte literalOperand ) {
        long leftSide = registerB.value();
        long rightSide = literalOperand;
        long result = leftSide ^ rightSide;
        registerB.setValue( result );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump bst( byte comboOperand ) {
        long value = comboOperandValue( comboOperand );
        long result = value % 8;
        registerB.setValue( result );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump jnz( byte literalOperand ) {
        long value = registerA.value();
        if ( value == 0L ) {
            return pointer -> pointer + 2;
        } else {
            return pointer -> literalOperand;
        }
    }

    private InstructionPointerJump bxc( byte ignoredOperand ) {
        long leftSide = registerB.value();
        long rightSide = registerC.value();
        long result = leftSide ^ rightSide;
        registerB.setValue( result );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump out( byte comboOperand ) {
        long comboOperandValue = comboOperandValue( comboOperand );
        long result = comboOperandValue % 8;
        output.add( (byte) result );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump bdv( byte comboOperand ) {
        long numerator = registerA.value();
        long denominator = 1L << comboOperandValue( comboOperand );
        long quotient = numerator / denominator;
        registerB.setValue( quotient );
        return pointer -> pointer + 2;
    }

    private InstructionPointerJump cdv( byte comboOperand ) {
        long numerator = registerA.value();
        long denominator = 1L << comboOperandValue( comboOperand );
        long quotient = numerator / denominator;
        registerC.setValue( quotient );
        return pointer -> pointer + 2;
    }

    private long comboOperandValue( byte comboOperand ) {
        return switch ( comboOperand ) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> registerA.value();
            case 5 -> registerB.value();
            case 6 -> registerC.value();
            default -> throw new IllegalArgumentException( "Invalid combo operand: " + comboOperand );
        };
    }

    List<Byte> instructions() {
        return instructions;
    }

    @FunctionalInterface
    private interface InstructionPointerJump {

        int jump( int currentInstructionPointer );
    }
}
