package com.adventofcode2024.dec05;

import static java.util.Collections.emptySet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class PageOrderingRules {

    private final Map<Integer, Set<Integer>> orderingRules;

    PageOrderingRules( Map<Integer, Set<Integer>> orderingRules ) {
        this.orderingRules = Map.copyOf( orderingRules );
    }

    boolean isInCorrectOrder( PageUpdate pageUpdate ) {
        List<Integer> pages = pageUpdate.pages();
        for ( int i = pages.size() - 1; i > 0; --i ) {
            Integer currentPage = pages.get( i );
            List<Integer> earlierPages = pages.subList( 0, i );
            if ( ! canAllBePlacedInOrderBefore( currentPage, earlierPages ) ) {
                return false;
            }
        }

        return true;
    }

    PageUpdate toCorrectOrder( PageUpdate pageUpdate ) {
        List<Integer> pagesRemaining = new LinkedList<>( pageUpdate.pages() );
        List<Integer> pagesInCorrectOrder = new LinkedList<>();
        while ( ! pagesRemaining.isEmpty() ) {
            int index = indexOfPageWithNoOrderViolations( pagesRemaining );
            pagesInCorrectOrder.add( 0, pagesRemaining.remove( index ) );
        }

        return new PageUpdate( pagesInCorrectOrder );
    }

    private int indexOfPageWithNoOrderViolations( List<Integer> pages ) {
        for ( int indexUnderConsideration = 0; indexUnderConsideration < pages.size(); ++indexUnderConsideration ) {
            Integer pageUnderConsideration = pages.get( indexUnderConsideration );
            List<Integer> otherPages = withoutPageAtIndex( indexUnderConsideration, pages );
            if ( canAllBePlacedInOrderBefore( pageUnderConsideration, otherPages ) ) {
                return indexUnderConsideration;
            }
        }

        throw new IllegalStateException( "Could not find an ordering for pages: " + pages );
    }

    private List<Integer> withoutPageAtIndex( int indexToOmit, List<Integer> pages ) {
        List<Integer> otherPages = new ArrayList<>( pages.size() - 1 );
        otherPages.addAll( pages.subList( 0, indexToOmit ) );
        otherPages.addAll( pages.subList( indexToOmit + 1, pages.size() ) );
        return otherPages;
    }

    private boolean canAllBePlacedInOrderBefore( Integer page, List<Integer> potentialEarlierPages ) {
        Set<Integer> pagesThatMustComeLater = orderingRules.getOrDefault( page, emptySet() );
        return potentialEarlierPages
            .stream()
            .noneMatch( pagesThatMustComeLater::contains );
    }
}
