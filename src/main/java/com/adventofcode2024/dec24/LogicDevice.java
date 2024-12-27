package com.adventofcode2024.dec24;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

class LogicDevice {

    private final Set<Wire> wires;
    private final Set<LogicGate> logicGates;

    LogicDevice( Collection<Wire> wires, Set<LogicGate> logicGates ) {
        this.wires = Set.copyOf( wires );
        this.logicGates = Set.copyOf( logicGates );
    }

    long runSimulation() {
        List<Wire> wiresToProcess = getWiresWithValues();
        while ( ! wiresToProcess.isEmpty() ) {
            Wire wire = wiresToProcess.remove( 0 );
            Set<Wire> wiresReceivingValue = wire.transmitValue();
            wiresToProcess.addAll( wiresReceivingValue );
        }

        return getOutputValue();
    }

    private long getOutputValue() {
        List<Wire> zWiresDescending = wires
            .stream()
            .filter( Wire::isOutput )
            .sorted( comparing( Wire::name ).reversed() )
            .toList();
        long value = 0L;
        for ( Wire zWire : zWiresDescending ) {
            value *= 2;
            value += zWire.value() ? 1 : 0;
        }
        return value;
    }

    private List<Wire> getWiresWithValues() {
        return wires
            .stream()
            .filter( Wire::hasValue )
            .collect( toList() );
    }
    
    Set<Wire> faultyOutputWires() {
        Set<Wire> faultyOutputWires = new HashSet<>();
        for ( LogicGate gate : logicGates ) {
            if ( gate.isOutput() && ! gate.isLastOutput() && ! gate.isXor() ) {
                faultyOutputWires.add( gate.outputWire() );
            } else if ( gate.isIntermediate() && gate.isXor() ) {
                faultyOutputWires.add( gate.outputWire() );
            } else if ( gate.isInput() && ! gate.isFirstInput() && gate.isXor() && ! hasXorGateWithInput( gate.outputWire() ) ) {
                faultyOutputWires.add( gate.outputWire() );
            } else if ( gate.isInput() && ! gate.isFirstInput() && gate.isAnd() && ! hasOrGateWithInput( gate.outputWire() ) ) {
                faultyOutputWires.add( gate.outputWire() );
            }
        }
        return faultyOutputWires;
    }

    private boolean hasXorGateWithInput( Wire wire ) {
        return logicGates
            .stream()
            .filter( LogicGate::isXor )
            .anyMatch( gate -> gate.hasInputWire( wire ) );
    }

    private boolean hasOrGateWithInput( Wire wire ) {
        return logicGates
            .stream()
            .filter( LogicGate::isOr )
            .anyMatch( gate -> gate.hasInputWire( wire ) );
    }
}
