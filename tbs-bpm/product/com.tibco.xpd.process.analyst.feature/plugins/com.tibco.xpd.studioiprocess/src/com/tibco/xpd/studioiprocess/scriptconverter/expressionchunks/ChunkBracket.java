package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;


/**
 * Chunk representing a single ( or ) bracket
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkBracket extends InternalChunkSingleCharacter {
    public ChunkBracket(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }

    public boolean isStart() {
        return getChunkText().equals("("); //$NON-NLS-1$
    }

    public boolean isEnd() {
        return getChunkText().equals(")"); //$NON-NLS-1$
    }

}