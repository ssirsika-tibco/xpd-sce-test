package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;


/**
 * Single character that is not recognised as any othe type of chunk.
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkUnhandledCharacter extends InternalChunkSingleCharacter {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkUnhandledCharacter(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }

}