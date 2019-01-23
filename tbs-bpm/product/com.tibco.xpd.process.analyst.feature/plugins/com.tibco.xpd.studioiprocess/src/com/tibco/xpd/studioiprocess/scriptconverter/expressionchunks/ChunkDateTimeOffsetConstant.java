package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk that is a constant datetime offset (in iProcess this is "@d/m/y/h/m@"
 * (or something liek that!.
 * <p>
 * The leading and trailing # is NOT removed.
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkDateTimeOffsetConstant extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkDateTimeOffsetConstant(String line, int startOfChunkIdx) {
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
            StringBuffer retString) {

        retString.append('"');
        int length = line.length();

        int nextIdx = startOfChunkIdx + 1; /* Skip leading @ */

        while (nextIdx < length) {
            int codePointChr = line.codePointAt(nextIdx);

            /*
             * Everything is included in String until we find terminating @
             */

            if (codePointChr == '@' && nextIdx != startOfChunkIdx) {
                nextIdx++;
                break;
            }

            retString.appendCodePoint(codePointChr);

            nextIdx++;
        }
        retString.append('"');

        return nextIdx;

    }
}