package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk that is a comment (leading iprocess comment character is removed.
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkComment extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkComment(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }

    /**
     * @param line
     * @param startOfChunkIdx
     * @return <code>true</code> if the text at start location is start of a
     *         comment.
     */
    public static boolean isStartOfCommentChunk(String line, int startOfChunkIdx) {
        String chunkStr = line.substring(startOfChunkIdx);

        if (chunkStr.startsWith(";")) { //$NON-NLS-1$
            return true;
        }

        if (chunkStr.startsWith("//")) { //$NON-NLS-1$
            return true;
        }

        return false;
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
        /* Everything after the ';' character to end of line is a comment. */

        /* Skip initial ; or // */
        if (line.codePointAt(startOfChunkIdx) == ';') {
            startOfChunkIdx++;

        } else if (line.codePointAt(startOfChunkIdx) == '/') {
            startOfChunkIdx++;

            if (startOfChunkIdx < line.length()
                    && line.codePointAt(startOfChunkIdx) == '/') {
                startOfChunkIdx++;
            }
        }

        buff.append(line.substring(startOfChunkIdx).trim());
        return line.length();
    }
}