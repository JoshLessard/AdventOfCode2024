package com.adventofcode2024.dec24;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class Wire {

    private final String name;
    private final Set<LogicGate> outputGates = new HashSet<>();
    private Boolean value;

    Wire( String name ) {
        this.name = name;
    }

    boolean isInput() {
        return name.startsWith( "x" ) || name.startsWith( "y" );
    }

    boolean isOutput() {
        return name.startsWith( "z" );
    }

    String name() {
        return name;
    }

    boolean hasValue() {
        return value != null;
    }

    boolean value() {
        return value;
    }

    void setValue( boolean value ) {
        this.value = value;
    }

    void addOutputGate( LogicGate outputGate ) {
        outputGates.add( outputGate );
    }

    Set<Wire> transmitValue() {
        outputGates.forEach( g -> g.receiveInput( value ) );
        return outputGates
            .stream()
            .map( LogicGate::outputWire )
            .filter( Wire::hasValue )
            .collect( toSet() );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wire)) return false;
        Wire wire = (Wire) o;
        return name.equals(wire.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
