package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk that is a constant string.
 * <p>
 * The leading and traling " is removed.
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkStringConstant extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkStringConstant(String line, int startOfChunkIdx) {
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

        /* Skip initial " */
        if (line.codePointAt(startOfChunkIdx) == '"') {
            startOfChunkIdx++;
        }

        while (startOfChunkIdx < length) {
            int codePointChr = line.codePointAt(startOfChunkIdx);

            /*
             * Everything is included in String until we find terminating "
             * 
             * Note that
             * " can be placed within a string by escaping with a second ".
             */

            if (codePointChr == '"') {
                if (startOfChunkIdx < (length - 1)) {
                    int nextChr = line.codePointAt(startOfChunkIdx + 1);
                    if (nextChr == '"') {
                        /*
                         * replace escaped " with the equivalent javascript \"
                         */
                        buff.append("\\\""); //$NON-NLS-1$
                        startOfChunkIdx++; /* Skip extra char */
                    } else {
                        /*
                         * End of string - when we hit the end of string we want
                         * to step on one to idx next chunk (i.e. skip the final
                         * ")
                         */
                        startOfChunkIdx++;
                        break;
                    }
                }
            } else {
                if (codePointChr == '\\') {
                    /* Escape \ in string. */
                    buff.appendCodePoint('\\');
                }
                buff.appendCodePoint(codePointChr);
            }

            startOfChunkIdx++;
        }

        return startOfChunkIdx;
    }
}