package com.adventofcode2024.dec17;

import java.util.List;

class ComputerBuilder {

    private Register registerA;
    private Register registerB;
    private Register registerC;
    private List<Byte> instructions;

    void setRegisterAValue( int value ) {
        this.registerA = new Register( value );
    }

    void setRegisterBValue( int value ) {
        this.registerB = new Register( value );
    }

    void setRegisterCValue( int value ) {
        this.registerC = new Register( value );
    }

    void setInstructions( List<Byte> instructions ) {
        this.instructions = List.copyOf( instructions );
    }

    Computer build() {
        return new Computer( registerA, registerB, registerC, instructions );
    }
}
