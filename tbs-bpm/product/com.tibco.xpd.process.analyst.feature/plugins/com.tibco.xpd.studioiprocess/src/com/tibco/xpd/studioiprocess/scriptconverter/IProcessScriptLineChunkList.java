/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;

import java.util.ArrayList;

import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkBracket;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkComment;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionOrKeywordOrFieldName;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionParameterSeparator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkOperator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkStringConstant;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkUnhandledCharacter;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkWhiteSpace;

/**
 * Tokenises an individual line from an iProcessScript grammar script into
 * individual chunks.
 * <p>
 * Each chunk will either be...
 * <li>{@link ChunkFunctionOrKeywordOrFieldName}: any chunk starting with
 * {@link Character#isLetterOrDigit(int codePoint)} i.e. a field name or script
 * keyword or function name</li>
 * <li>{@link ChunkOperator}: any contiguous chunk of special operator
 * characters in iProcess script.</li>
 * <li>{@link ChunkComment}: A comment (nominally comment is always last thing
 * on line when present.</li>
 * <li>{@link ChunkStringConstant}: The content of a constant string.</li>
 * <li>{@link ChunkBracket}: An individual opening or closing bracket "(" or ")"
 * </li>
 * <li>{@link ChunkFunctionParameterSeparator}: a function parameter separator
 * (a comma)</li>
 * <li>{@link ChunkWhiteSpace}: a chunk of contiguous white space.</li>
 * <li> {@link ChunkUnhandledCharacter}: any chunk that starts with a character
 * that does not fit any of the above criteria (this should not be possible but
 * is included just in case the original script had unexpected things in
 * unexpected places.).</li>
 * 
 * 
 * @author aallway
 * @since 18 May 2011
 */
public class IProcessScriptLineChunkList extends
        ArrayList<IProcessExpressionChunk> {

    public IProcessScriptLineChunkList(String line,
            IProcessExpressionChunkFactory chunkFactory) {
        super();
        tokeniseToChunkList(line, chunkFactory);
    }

    private void tokeniseToChunkList(String line,
            IProcessExpressionChunkFactory chunkFactory) {

        if (line != null) {

            int startChunk = 0;

            ChunkFunctionOrKeywordOrFieldName previousChunkIfKeyword = null;

            while (startChunk < line.length()) {
                /*
                 * Creat appropriate chunk type for the character(s) at this
                 * position.
                 */
                IProcessExpressionChunk chunk =
                        chunkFactory.createChunkForStartCharacter(line,
                                startChunk);

                int nextChunk = chunk.getNextStartOfNextChunkOffset();
                if (nextChunk <= startChunk) {
                    throw new RuntimeException(
                            "Unexpected Error parsing chunks from line(Pos:" + startChunk + ": '" //$NON-NLS-1$ //$NON-NLS-2$
                                    + line + "'"); //$NON-NLS-1$
                }

                if (!(chunk instanceof ChunkWhiteSpace)) {
                    /*
                     * Look out for key word chunks, if the next non-whitespace
                     * chunk is an open bracket then convert it to a function
                     * name chunk.
                     */
                    if (chunk instanceof ChunkFunctionOrKeywordOrFieldName) {
                        previousChunkIfKeyword =
                                (ChunkFunctionOrKeywordOrFieldName) chunk;

                    } else if (previousChunkIfKeyword != null) {

                        if (chunk instanceof ChunkBracket) {
                            if (((ChunkBracket) chunk).isStart()) {
                                /*
                                 * Set the keyword chunk to be a function chunk.
                                 */
                                String chunkText =
                                        previousChunkIfKeyword.getChunkText();
                                if (!IProcessScriptKeywords
                                        .isConditionKeyword(chunkText)) {
                                    previousChunkIfKeyword
                                            .setIsFunctionName(true);
                                }
                            }
                        }

                        previousChunkIfKeyword = null;
                    }

                    this.add(chunk);
                }

                /* Move to start of next chunk. */
                startChunk = nextChunk;
            }
        }

        return;
    }

    /**
     * ================================================================
     * 
     * Testing:
     * 
     * ================================================================
     */
    public static void main(String[] args) {
        TestLine[] tests =
                {
                        /* Blank line. */
                        new TestLine("", new String[0], new Class[0]), //$NON-NLS-1$
                        /* Comment line */
                        new TestLine("; A comment", //$NON-NLS-1$
                                new String[] { "A comment" }, //$NON-NLS-1$
                                new Class[] { ChunkComment.class }),
                        /* assignment */
                        new TestLine("A := B", //$NON-NLS-1$
                                new String[] { "A", ":=", "B" }, new Class[] { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
                                ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class }),
                        /* assignment2 */
                        new TestLine("ABC := BCD + 2", //$NON-NLS-1$
                                new String[] { "ABC", ":=", "BCD", "+", "2" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class }),
                        /* assignment3 */
                        new TestLine("  ABC:=BCD+2", //$NON-NLS-1$
                                new String[] { "ABC", ":=", "BCD", "+", "2" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class }),
                        /* End Comment line */
                        new TestLine("A:=B ; A comment", //$NON-NLS-1$
                                new String[] { "A", ":=", "B", "A comment" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkComment.class }),
                        /* End Comment line2 */
                        new TestLine("A:=B;A comment", //$NON-NLS-1$
                                new String[] { "A", ":=", "B", "A comment" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkComment.class }),

                        /* Condition */
                        new TestLine("IF ABC == DEF", new String[] { "IF", //$NON-NLS-1$ //$NON-NLS-2$
                                "ABC", "==", "DEF" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class }),
                        /* Condition2 */
                        new TestLine(
                                "\t\tIF (ABC == DEF) AND (XYZ <> WWW)", new String[] { "IF", //$NON-NLS-1$ //$NON-NLS-2$
                                        "(", "ABC", "==", "DEF", ")", "AND", "(", "XYZ", "<>", "WWW", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class }),
                        /* Condition3 */
                        new TestLine(
                                "IF(ABC==DEF)AND(XYZ<>WWW) ; Comment", new String[] { "IF", //$NON-NLS-1$ //$NON-NLS-2$
                                        "(", "ABC", "==", "DEF", ")", "AND", "(", "XYZ", "<>", "WWW", ")", "Comment" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class, ChunkComment.class }),

                        /* function call */
                        new TestLine(
                                "FUNC()", //$NON-NLS-1$
                                new String[] { "FUNC", "(", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class, ChunkBracket.class }),
                        /* function call 2 */
                        new TestLine("FUNC(param1)", //$NON-NLS-1$
                                new String[] { "FUNC", "(", "param1", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class }),
                        /* function call 3 */
                        new TestLine(
                                "FUNC(param1, param2)", //$NON-NLS-1$
                                new String[] {
                                        "FUNC", "(", "param1", ",", "param2", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionParameterSeparator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class }),
                        /* function call 4 */
                        new TestLine(
                                "FUNC(param1, param2, param3)", //$NON-NLS-1$
                                new String[] {
                                        "FUNC", "(", "param1", ",", "param2", ",", "param3", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionParameterSeparator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionParameterSeparator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class }),
                        /* function call 4 */
                        new TestLine(
                                "ABC:=FUNC (param1,param2,param3)", //$NON-NLS-1$
                                new String[] {
                                        "ABC", ":=", "FUNC", "(", "param1", ",", "param2", ",", "param3", ")" }, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ 
                                new Class[] { ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionParameterSeparator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkFunctionParameterSeparator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkBracket.class }),

                        /* Foreign chars */
                        new TestLine(
                                "AÈ…”/ÈÛB", //$NON-NLS-1$
                                new String[] { "AÈ…”", "/", "ÈÛB" }, new Class[] { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                ChunkFunctionOrKeywordOrFieldName.class,
                                        ChunkOperator.class,
                                        ChunkFunctionOrKeywordOrFieldName.class }),

                };

        for (int testIdx = 0; testIdx < tests.length; testIdx++) {
            TestLine test = tests[testIdx];

            if (test.expected.length != test.expectedClasses.length) {
                System.out
                        .println(testIdx
                                + ": BAD TEST: Invalid test - diff number expected and expectedClasses"); //$NON-NLS-1$
                return;
            }

            IProcessScriptLineChunkList chunks =
                    new IProcessScriptLineChunkList(test.line,
                            new IProcessExpressionChunkFactory());
            if (chunks.size() != test.expectedClasses.length) {
                System.out.println(String
                        .format("%d: BAD TEST: expected %d chunks got %d", //$NON-NLS-1$
                                testIdx,
                                test.expectedClasses.length,
                                chunks.size()));
                return;
            }

            for (int chunkIdx = 0; chunkIdx < chunks.size(); chunkIdx++) {
                IProcessExpressionChunk chunk = chunks.get(chunkIdx);

                if (!test.expected[chunkIdx].equals(chunk.getChunkText())) {
                    System.out
                            .println(String
                                    .format("%d: BAD TEST: expected chunk %d text to be '%s' got '%s'", //$NON-NLS-1$
                                            testIdx,
                                            chunkIdx,
                                            test.expected[chunkIdx],
                                            chunk.getChunkText()));

                    return;
                }
                if (!test.expectedClasses[chunkIdx].equals(chunk.getClass())) {
                    System.out
                            .println(String
                                    .format("%d: BAD TEST: expected chunk %d class to be '%s' got '%s'", //$NON-NLS-1$
                                            testIdx,
                                            chunkIdx,
                                            test.expectedClasses[chunkIdx]
                                                    .getSimpleName(),
                                            chunk.getClass().getSimpleName()));

                    return;
                }

            }
            System.out.println(String.format("%d: SUCCESS: '%s'", //$NON-NLS-1$
                    testIdx,
                    test.line));

        }

    }

    private static class TestLine {
        String line;

        String[] expected;

        Class[] expectedClasses;

        /**
         * @param line
         * @param expected
         * @param expectedClasses
         */
        public TestLine(String line, String[] expected, Class[] expectedClasses) {
            super();
            this.line = line;
            this.expected = expected;
            this.expectedClasses = expectedClasses;
        }

    }

}
