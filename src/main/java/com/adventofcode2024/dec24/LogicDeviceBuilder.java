package com.adventofcode2024.dec24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class LogicDeviceBuilder {

    private final Map<String, Wire> wiresByName = new HashMap<>();
    private final Set<LogicGate> logicGates = new HashSet<>();

    void addInitialWireValue( String wireName, boolean value ) {
        Wire wire = wiresByName.computeIfAbsent( wireName, Wire::new );
        wire.setValue( value );
    }

    void addAndGate( String inputWireName1, String inputWireName2, String outputWireName ) {
        addGate( LogicOperation.AND, inputWireName1, inputWireName2, outputWireName );
    }

    void addOrGate( String inputWireName1, String inputWireName2, String outputWireName ) {
        addGate( LogicOperation.OR, inputWireName1, inputWireName2, outputWireName );
    }

    void addXorGate( String inputWireName1, String inputWireName2, String outputWireName ) {
        addGate( LogicOperation.XOR, inputWireName1, inputWireName2, outputWireName );
    }

    private void addGate( LogicOperation operation, String inputWireName1, String inputWireName2, String outputWireName ) {
        Wire inputWire1 = wiresByName.computeIfAbsent( inputWireName1, Wire::new );
        Wire inputWire2 = wiresByName.computeIfAbsent( inputWireName2, Wire::new );
        Wire outputWire = wiresByName.computeIfAbsent( outputWireName, Wire::new );
        LogicGate gate = new LogicGate( inputWire1, inputWire2, operation, outputWire );
        logicGates.add( gate );
        inputWire1.addOutputGate( gate );
        inputWire2.addOutputGate( gate );
    }

    LogicDevice build() {
        return new LogicDevice( wiresByName.values(), logicGates );
    }
}
