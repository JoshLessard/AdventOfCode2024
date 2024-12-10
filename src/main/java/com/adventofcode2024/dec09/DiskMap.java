package com.adventofcode2024.dec09;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class DiskMap {

    private final LinkedList<DiskBlock> blocks;

    DiskMap( List<DiskBlock> blocks ) {
        this.blocks = new LinkedList<>( blocks );
    }

    void compact() {
        trimTrailingFreeSpace();
        BlockInfo firstFreeSpaceBlockInfo;
        while ( ( firstFreeSpaceBlockInfo = findFirstFreeSpaceBlock() ) != null ) {
            DiskBlock firstFreeSpaceBlock = firstFreeSpaceBlockInfo.block();
            DiskBlock lastDiskBlock = blocks.getLast();
            DiskBlock fileBlockToInsertInto = findFileBlockToInsertInto( firstFreeSpaceBlockInfo.index(), lastDiskBlock.id() );
            while ( ! lastDiskBlock.isEmpty() && ! firstFreeSpaceBlock.isEmpty() ) {
                lastDiskBlock.decrement();
                fileBlockToInsertInto.increment();
                firstFreeSpaceBlock.decrement();
            }
            if ( firstFreeSpaceBlock.isEmpty() ) {
                blocks.remove( firstFreeSpaceBlock );
            }
            if ( lastDiskBlock.isEmpty() ) {
                blocks.removeLast();
                trimTrailingFreeSpace();
            }
        }
    }

    private void trimTrailingFreeSpace() {
        while ( ! blocks.isEmpty() && blocks.getLast().isFreeSpace() ) {
            blocks.removeLast();
        }
    }

    private BlockInfo findFirstFreeSpaceBlock() {
        int index = 0;
        for ( DiskBlock block : blocks ) {
            if ( block.isFreeSpace() ) {
                return new BlockInfo( index, block );
            }
            ++index;
        }
        return null;
    }

    private DiskBlock findFileBlockToInsertInto( int firstFreeSpaceIndex, int targetFileId ) {
        DiskBlock fileBlock = blocks.get( firstFreeSpaceIndex - 1 );
        if ( fileBlock.id() != targetFileId ) {
            fileBlock = new DiskBlock( DiskBlock.Type.FILE, targetFileId, 0 );
            blocks.add( firstFreeSpaceIndex, fileBlock );
        }
        return fileBlock;
    }

    long checksum() {
        long checksum = 0L;
        int index = 0;
        for ( DiskBlock block : blocks ) {
            if ( block.isFile() ) {
                checksum += block.checksum( index );
            }
            index += block.size();
        }

        return checksum;
    }

    void compactWholeFiles() {
        trimTrailingFreeSpace();
        for ( int id = blocks.getLast().id(); id >= 0; --id ) {
            BlockInfo fileBlockInfo = findFileBlockWithId( id );
            findFirstFreeSpaceBlockWithSize( fileBlockInfo.block().size(), fileBlockInfo.index() )
                .ifPresent( freeSpaceBlockInfo -> {
                    DiskBlock newEmptySpace = new DiskBlock( DiskBlock.Type.FREE_SPACE, Integer.MIN_VALUE, fileBlockInfo.block().size() );
                    blocks.add( fileBlockInfo.index(), newEmptySpace );
                    blocks.remove( fileBlockInfo.block() );
                    mergeFreeSpaceBlocks( fileBlockInfo.index() );
                    blocks.add( freeSpaceBlockInfo.index(), fileBlockInfo.block() );
                    freeSpaceBlockInfo.block().decrement( fileBlockInfo.block().size() );
                    if ( freeSpaceBlockInfo.block().isEmpty() ) {
                        blocks.remove( freeSpaceBlockInfo.block() );
                    }
                } );
        }
    }

    private Optional<BlockInfo> findFirstFreeSpaceBlockWithSize( int size, int thresholdIndex ) {
        int index = 0;
        for ( DiskBlock block : blocks ) {
            if ( index == thresholdIndex ) {
                break;
            }
            if ( block.isFreeSpace() && block.size() >= size ) {
                return Optional.of( new BlockInfo( index, block ) );
            }
            ++index;
        }

        return Optional.empty();
    }

    private BlockInfo findFileBlockWithId( int id ) {
        int index = blocks.size() - 1;
        Iterator<DiskBlock> iterator = blocks.descendingIterator();
        while ( iterator.hasNext() ) {
            DiskBlock block = iterator.next();
            if ( block.isFile() && block.id() == id ) {
                return new BlockInfo( index, block );
            }
            --index;
        }
        throw new IllegalStateException( "No file block with id " + id );
    }

    private void mergeFreeSpaceBlocks( int index ) {
        DiskBlock freeSpaceBlock = blocks.get( index );
        DiskBlock previousBlock = index > 0 ? blocks.get( index - 1 ) : null;
        DiskBlock nextBlock = index < blocks.size() - 1 ? blocks.get( index + 1 ) : null;

        if ( previousBlock != null && previousBlock.isFreeSpace() && nextBlock != null && nextBlock.isFreeSpace() ) {
            previousBlock.increment( freeSpaceBlock.size() + nextBlock.size() );
            blocks.remove( freeSpaceBlock );
            blocks.remove( nextBlock );
        } else if ( previousBlock != null && previousBlock.isFreeSpace() ) {
            previousBlock.increment( freeSpaceBlock.size() );
            blocks.remove( freeSpaceBlock );
        } else if ( nextBlock != null && nextBlock.isFreeSpace() ) {
            freeSpaceBlock.increment( nextBlock.size() );
            blocks.remove( nextBlock );
        }
    }

    private record BlockInfo( int index, DiskBlock block ) {
    }
}
