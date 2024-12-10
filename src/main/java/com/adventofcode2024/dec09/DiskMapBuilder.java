package com.adventofcode2024.dec09;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class DiskMapBuilder {

    private DiskBlock.Type nextBlockType = DiskBlock.Type.FILE;
    private Map<DiskBlock.Type, AtomicInteger> nextIdByType = Map.of(
        DiskBlock.Type.FILE, new AtomicInteger( 0 ),
        DiskBlock.Type.FREE_SPACE, new AtomicInteger( 0 )
    );
    private final List<DiskBlock> blocks = new LinkedList<>();

    void addBlock( int size ) {
        if ( size > 0 ) {
            blocks.add( new DiskBlock( nextBlockType, nextIdByType.get( nextBlockType ).getAndIncrement(), size ) );
        }
        nextBlockType = nextBlockType == DiskBlock.Type.FILE
            ? DiskBlock.Type.FREE_SPACE
            : DiskBlock.Type.FILE;
    }

    DiskMap build() {
        return new DiskMap( blocks );
    }
}
