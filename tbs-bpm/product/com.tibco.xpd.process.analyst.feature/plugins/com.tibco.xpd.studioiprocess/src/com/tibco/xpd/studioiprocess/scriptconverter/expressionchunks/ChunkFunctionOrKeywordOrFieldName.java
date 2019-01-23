package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * A chunk of an expression representing an alphanumeric keyword, a data name or
 * a function call name.
 * 
 * 
 * @author aallway
 * @since 20 May 2011
 */
public class ChunkFunctionOrKeywordOrFieldName extends IProcessExpressionChunk {

    private boolean isFunctionName = false;

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkFunctionOrKeywordOrFieldName(String line, int startOfChunkIdx) {
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
        int idx = startOfChunkIdx;

        while (idx < length) {
            int codePointChr = line.codePointAt(idx);

            if (Character.isLetterOrDigit(codePointChr) || codePointChr == '_') {
                buff.appendCodePoint(codePointChr);

            } else if (codePointChr == ':' && idx != startOfChunkIdx
                    && (idx + 1) < length
                    && Character.isLetterOrDigit(line.codePointAt(idx + 1))) {
                /*
                 * : is valid in the middle of an identifier AS LONG as it's not
                 * start of chunk AND it's followed by another character.
                 * 
                 * This allows for things lioke SW_STARTER:DESCRTIPTION and so
                 * on.
                 */
                buff.appendCodePoint(codePointChr);

            } else {
                /* End of chunk at first on alphanum. */
                break;
            }

            idx++;
        }

        return idx;
    }

    /**
     * This is called by the converter after it has created the keyword chunk if
     * it then subsequently discovers that the keyword is actually a function
     * call name.
     * 
     * @param isFunctionName
     */
    public final void setIsFunctionName(boolean isFunctionName) {
        this.isFunctionName = isFunctionName;
    }

    /**
     * @return <code>true</code> if the keyword is a function name.
     */
    public boolean isFunctionName() {
        return isFunctionName;
    }
}