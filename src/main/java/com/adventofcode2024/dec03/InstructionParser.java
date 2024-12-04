package com.adventofcode2024.dec03;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InstructionParser {

    private static final Pattern MULTIPLICATION_INSTRUCTION_PATTERN = Pattern.compile( "mul\\((\\d+),(\\d+)\\)" );
    private static final Pattern DO_INSTRUCTION_PATTERN = Pattern.compile( "do\\(\\)" );
    private static final Pattern DONT_INSTRUCTION_PATTERN = Pattern.compile( "don't\\(\\)" );
    private static final Pattern ALL_INSTRUCTIONS_PATTERN = Pattern.compile( "(" + MULTIPLICATION_INSTRUCTION_PATTERN + "|" + DO_INSTRUCTION_PATTERN + "|" + DONT_INSTRUCTION_PATTERN + ")" );

    List<Instruction> parseInstructions( String inputLine ) {
        List<Instruction> instructions = new ArrayList<>();
        Matcher matcher = ALL_INSTRUCTIONS_PATTERN.matcher( inputLine );
        while ( matcher.find() ) {
            String instruction = matcher.group( 0 );
            Matcher multiplicationInstructionMatcher = MULTIPLICATION_INSTRUCTION_PATTERN.matcher( instruction );
            if ( multiplicationInstructionMatcher.matches() ) {
                instructions.add(
                    new MultiplicationInstruction(
                        Long.parseLong( multiplicationInstructionMatcher.group( 1 ) ),
                        Long.parseLong( multiplicationInstructionMatcher.group( 2 ) )
                    )
                );
            }
            Matcher doInstructionMatcher = DO_INSTRUCTION_PATTERN.matcher( instruction );
            if ( doInstructionMatcher.matches() ) {
                instructions.add( new DoInstruction() );
            }
            Matcher dontInstructionMatcher = DONT_INSTRUCTION_PATTERN.matcher( instruction );
            if ( dontInstructionMatcher.matches() ) {
                instructions.add( new DontInstruction() );
            }
        }

        return instructions;
    }
}
