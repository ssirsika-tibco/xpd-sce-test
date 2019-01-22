package com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks;

import com.tibco.xpd.studioiprocess.scriptconverter.IProcessExpressionChunk;

/**
 * Chunk of contiguous operator characters.
 * 
 * 
 * @author aallway
 * @since 19 May 2011
 */
public class ChunkOperator extends IProcessExpressionChunk {

    /**
     * @param line
     * @param startOfChunkIdx
     */
    public ChunkOperator(String line, int startOfChunkIdx) {
        super(line, startOfChunkIdx);
    }

    public static boolean isOperatorCharacter(int codePointChr) {
        switch (codePointChr) {
        case ':':
        case '=':
        case '<':
        case '>':
        case '+':
        case '-':
        case '*':
        case '/':
        case '%':
            return true;

        default:
            return false;
        }
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

        /*
         * XPD-4572: Only certain operator combinations are allowed, so we
         * should not treat all groups of operators as contiguous sequences.
         * 
         * Valid are := <> >= <= **
         */
        if (startOfChunkIdx < length) {
            int codePointChr = line.codePointAt(startOfChunkIdx);
            buff.appendCodePoint(codePointChr);
            startOfChunkIdx++;

            if (startOfChunkIdx < length) {
                int nextChr = line.codePointAt(startOfChunkIdx);

                if (codePointChr == ':' && nextChr == '=') {
                    buff.appendCodePoint(nextChr);
                    startOfChunkIdx++;

                } else if (codePointChr == '<' && nextChr == '>') {
                    buff.appendCodePoint(nextChr);
                    startOfChunkIdx++;

                } else if (codePointChr == '<' && nextChr == '=') {
                    buff.appendCodePoint(nextChr);
                    startOfChunkIdx++;

                } else if (codePointChr == '>' && nextChr == '=') {
                    buff.appendCodePoint(nextChr);
                    startOfChunkIdx++;

                } else if (codePointChr == '*' && nextChr == '*') {
                    buff.appendCodePoint(nextChr);
                    startOfChunkIdx++;

                }
            }

        }

        // while (startOfChunkIdx < length) {
        // int codePointChr = line.codePointAt(startOfChunkIdx);
        //
        // if (isOperatorCharacter(codePointChr)) {
        // buff.appendCodePoint(codePointChr);
        // } else {
        // /* End of chunk. */
        // break;
        // }
        //
        // startOfChunkIdx++;
        // }

        return startOfChunkIdx;
    }
}