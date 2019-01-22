package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk of contiguous whitespace
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkWhiteSpace extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkWhiteSpace(String line, int startOfChunkIdx) {
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

        int length = line.length();

        while (startOfChunkIdx < length) {
            int codePointChr = line.codePointAt(startOfChunkIdx);

            if (Character.isWhitespace(codePointChr)) {
                buff.appendCodePoint(codePointChr);
            } else {
                /* End of chunk at first on alphanum. */
                break;
            }

            startOfChunkIdx++;
        }

        return startOfChunkIdx;
    }
}