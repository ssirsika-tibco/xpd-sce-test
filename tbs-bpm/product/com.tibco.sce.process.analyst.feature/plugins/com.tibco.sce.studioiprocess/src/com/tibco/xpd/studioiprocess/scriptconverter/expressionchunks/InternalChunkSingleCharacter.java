package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk that is a single character
 * 
 * @author aallway
 * @since 19 May 2011
 */
abstract class InternalChunkSingleCharacter extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    InternalChunkSingleCharacter(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }

    /**
     * @see com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk#calculateChunkText(java.lang.String,
     *      int, java.lang.StringBuffer)
     * 
     * @param line
     * @param startOfChunkIdx
     * @param retString
     * @return
     */
    @Override
    protected int calculateChunkText(String line, int startOfChunkIdx,
            StringBuffer buff) {
        /*
         * We should always have just been passed a string with appropriate
         * char as first in string.
         */
        buff.append(line.substring(startOfChunkIdx, startOfChunkIdx + 1));
        return startOfChunkIdx + 1;
    }
}