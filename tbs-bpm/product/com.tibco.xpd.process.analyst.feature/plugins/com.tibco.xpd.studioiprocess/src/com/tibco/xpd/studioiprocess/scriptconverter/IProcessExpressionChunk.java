/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;


/**
 * Things representing tokenised chunks on an iProcess script expression line.
 * 
 * @author aallway
 * @since 18 May 2011
 */
public abstract class IProcessExpressionChunk {

    private String chunkText;

    private int startOfNextChunkIdx;

    /**
     * @param chunkText
     */
    protected IProcessExpressionChunk(String line, int startOfChunkIdx) {
        super();
        StringBuffer buff = new StringBuffer();
        startOfNextChunkIdx = calculateChunkText(line, startOfChunkIdx, buff);
        chunkText = buff.toString();
    }

    /**
     * 
     * @param line
     * @param startOfChunkIdx
     * @return The start offset in source line of the next chunk after this one
     *         and The text from the given start point offset in string in the
     *         passed buffer.
     */
    protected abstract int calculateChunkText(String line, int startOfChunkIdx,
            StringBuffer retString);

    /**
     * Get the text for the chunk.
     * 
     * @return
     */
    public String getChunkText() {
        return chunkText;
    }

    /**
     * @return The start offset in source line of the next chunk after this one
     */
    public int getNextStartOfNextChunkOffset() {
        return startOfNextChunkIdx;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return getChunkText();
    }

}
