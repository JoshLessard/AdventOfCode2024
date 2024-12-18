package com.adventofcode2024.dec17;

class Register {

    private long value;

    Register( long value ) {
        this.value = value;
    }

    long value() {
        return value;
    }

    void setValue( long value ) {
        this.value = value;
    }
}
