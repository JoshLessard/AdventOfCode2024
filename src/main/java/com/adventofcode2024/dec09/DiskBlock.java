package com.adventofcode2024.dec09;

class DiskBlock {

    enum Type {
        FILE,
        FREE_SPACE;
    }

    private final Type type;

    private final int id;
    private int size;

    DiskBlock( Type type, int id, int size ) {
        this.type = type;
        this.id = id;
        this.size = size;
    }

    boolean isFreeSpace() {
        return type == Type.FREE_SPACE;
    }

    boolean isFile() {
        return type == Type.FILE;
    }

    boolean isEmpty() {
        return size == 0;
    }

    void increment() {
        ++size;
    }

    void increment( int amount ) {
        size += amount;
    }

    void decrement() {
        --size;
    }

    void decrement( int amount ) {
        if ( amount > size ) {
            throw new IllegalArgumentException( "Can't make block size negative." );
        }
        size -= amount;
    }

    int id() {
        return id;
    }

    int size() {
        return size;
    }

    long checksum( int startingIndex ) {
        long checksum = 0L;
        for ( int i = 0; i < size; ++i ) {
            checksum += (long) (startingIndex + i) * id;
        }
        return checksum;
    }
}
