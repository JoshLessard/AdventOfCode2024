package com.adventofcode2024.dec05;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class PageOrderingRulesBuilder {

    private final Map<Integer, Set<Integer>> orderingRules = new HashMap<>();

    void addOrderingRule( int before, int after ) {
        orderingRules.computeIfAbsent( before, k -> new HashSet<>() ).add( after );
    }

    PageOrderingRules build() {
        return new PageOrderingRules( orderingRules );
    }
}
