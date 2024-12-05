package com.adventofcode2024.dec05;

import java.util.List;

class PageUpdate {

    private final List<Integer> pages;

    PageUpdate( List<Integer> pages ) {
        this.pages = List.copyOf( pages );
    }

    int middlePage() {
        return pages.get( pages.size() / 2 );
    }

    List<Integer> pages() {
        return pages;
    }
}
