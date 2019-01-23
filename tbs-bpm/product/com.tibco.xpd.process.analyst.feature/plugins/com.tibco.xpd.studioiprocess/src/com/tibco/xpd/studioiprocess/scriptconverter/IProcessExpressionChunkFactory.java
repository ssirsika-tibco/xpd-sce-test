/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;

import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkBracket;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkComment;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkDateConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkDateTimeOffsetConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionOrKeywordOrFieldName;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionParameterSeparator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkOperator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkStringConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkTimeConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkUnhandledCharacter;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkWhiteSpace;

/**
 * Factory that creates {@link IProcessExpressionChunk}'s on behalf of
 * {@link AbstractIProcessToJavaScriptConverter} and
 * {@link IProcessScriptLineChunkList}
 * <p>
 * As the iProcessScript grammar script is parsed, each line is parsed into
 * chunks using this factory by passing it the first character on the line (and
 * subsequently the character following each chunk returned.
 * <p>
 * This factory can be overriddenif necessary so that the syub-class can return
 * their own SubClass implementations of the various
 * {@link IProcessExpressionChunk} sub-classes.
 * <p>
 * Please note that some chunk types have special significance to the standard
 * parsing behaviour ({@link ChunkStringConstant} and so on so care should be
 * taken to preserve these types - they can be sub-classed themselves rather
 * than supplying a complete alternative based on
 * {@link IProcessExpressionChunk}
 * <p>
 * If you directly sub-class {@link IProcessExpressionChunk} then the
 * {@link IProcessExpressionChunk#getChunkText()} will ultimately be called and
 * used for the output text in the order it appears in the chunk list.
 * 
 * @author aallway
 * @since 26 May 2011
 */
public class IProcessExpressionChunkFactory {

    /**
     * This method is pased the first character of a 'chunk' in the expression
     * line.
     * <p>
     * A chunk is either (a) the first character on the line or (b) the
     * character immediately after the end of previous chunk.
     * 
     * @param codePointChr
     * @return appropriate chunk type for given start of chunk character.
     */
    public IProcessExpressionChunk createChunkForStartCharacter(String line,
            int startOfChunkIdx) {
        int startChunkCodePointChr = line.codePointAt(startOfChunkIdx);

        if (startChunkCodePointChr == '"') {
            return createStringConstantChunk(line, startOfChunkIdx);

        } else if (startChunkCodePointChr == '!') {
            return createDateConstantChunk(line, startOfChunkIdx);

        } else if (startChunkCodePointChr == '#') {
            return createTimeConstantChunk(line, startOfChunkIdx);

        } else if (startChunkCodePointChr == '@') {
            return createDateTimeOffsetConstantChunk(line, startOfChunkIdx);

        } else if (ChunkComment.isStartOfCommentChunk(line, startOfChunkIdx)) {
            return createCommentChunk(line, startOfChunkIdx);

        } else if (Character.isWhitespace(startChunkCodePointChr)) {
            return createWhiteSpaceChunk(line, startOfChunkIdx);

        } else if (ChunkOperator.isOperatorCharacter(startChunkCodePointChr)) {
            return createOperatorChunk(line, startOfChunkIdx);

        } else if (startChunkCodePointChr == '('
                || startChunkCodePointChr == ')') {
            return createBracketChunk(line, startOfChunkIdx);

        } else if (startChunkCodePointChr == ',') {
            return createFunctionParameterSeparatorChunk(line, startOfChunkIdx);

        } else if (Character.isLetterOrDigit(startChunkCodePointChr)) {
            return createKeywordOrFieldNameChunk(line, startOfChunkIdx);

        }

        return createUnhandledCharaterChunk(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * 
     * @return Chunk for character that isn't handled as the start character of
     *         any other chunk
     */
    protected IProcessExpressionChunk createUnhandledCharaterChunk(String line,
            int startOfChunkIdx) {
        return new ChunkUnhandledCharacter(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a keyword or field name.
     */
    protected IProcessExpressionChunk createKeywordOrFieldNameChunk(
            String line, int startOfChunkIdx) {
        return new ChunkFunctionOrKeywordOrFieldName(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a function parameter separator ","
     */
    protected IProcessExpressionChunk createFunctionParameterSeparatorChunk(
            String line, int startOfChunkIdx) {
        return new ChunkFunctionParameterSeparator(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing an open or close bracket "(" or ")"
     */
    protected IProcessExpressionChunk createBracketChunk(String line,
            int startOfChunkIdx) {
        return new ChunkBracket(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing an operation (== >= + etc)
     */
    protected IProcessExpressionChunk createOperatorChunk(String line,
            int startOfChunkIdx) {
        return new ChunkOperator(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a whitespace
     */
    protected IProcessExpressionChunk createWhiteSpaceChunk(String line,
            int startOfChunkIdx) {
        return new ChunkWhiteSpace(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a comment
     */
    protected IProcessExpressionChunk createCommentChunk(String line,
            int startOfChunkIdx) {
        return new ChunkComment(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a date-time constant
     */
    protected IProcessExpressionChunk createDateTimeOffsetConstantChunk(
            String line, int startOfChunkIdx) {
        return new ChunkDateTimeOffsetConstant(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a time constant
     */
    protected IProcessExpressionChunk createTimeConstantChunk(String line,
            int startOfChunkIdx) {
        return new ChunkTimeConstant(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a date constant
     */
    protected IProcessExpressionChunk createDateConstantChunk(String line,
            int startOfChunkIdx) {
        return new ChunkDateConstant(line, startOfChunkIdx);
    }

    /**
     * @param line
     *            The whole expression line.
     * @param startOfChunkIdx
     *            The start character of this chunk.
     * @return Chunk representing a string constant.
     */
    protected IProcessExpressionChunk createStringConstantChunk(String line,
            int startOfChunkIdx) {
        return new ChunkStringConstant(line, startOfChunkIdx);
    }

}
