package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;


/**
 * Chunk representing a function parameter separator ( , )
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkFunctionParameterSeparator extends
        InternalChunkSingleCharacter {
    public ChunkFunctionParameterSeparator(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }
}