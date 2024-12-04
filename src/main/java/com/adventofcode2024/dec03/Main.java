package com.adventofcode2024.dec03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args ) throws Exception {
        Computer computer = createComputer();
        System.out.println( "Sum of multiplication operations without state changes: " + computer.executeInstructionsWithoutStateChanges() );
        System.out.println( "Sum of multiplication operations with state changes: " + computer.executeInstructions() );
    }

    private static Computer createComputer() throws IOException {
        List<String> input = readInput();
        InstructionParser instructionParser = new InstructionParser();
        List<Instruction> instructions = input
            .stream()
            .flatMap( line -> instructionParser.parseInstructions( line ).stream() )
            .toList();
        return new Computer( instructions );
    }

    private static List<String> readInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec03/input.txt" ) ) ) {
            return reader
                .lines()
                .toList();
        }
    }
}
