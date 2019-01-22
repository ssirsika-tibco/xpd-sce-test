/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.studioiprocess.scriptconverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.studioiprocess.internal.Messages;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkBracket;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkComment;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionOrKeywordOrFieldName;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkFunctionParameterSeparator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkOperator;
import com.tibco.xpd.studioiprocess.scriptconverter.expressionchunks.ChunkStringConstant;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class performs a rough conversion of iProcessiprocessScript grammar
 * iprocessScript to Studio JavaiprocessScript grammar.
 * <p>
 * Things not handled by the conversion are left 'as-is' for the user to fix as
 * required.
 * <p>
 * The conversion can be customised to some extent by overriding the methods
 * {@link #translateFunctionName(String)} and
 * {@link #translateKeywordOrFieldName(String)} methods, or by overriding
 * {@link #createChunkFactory()} method to provide a factory that uses extended
 * chunk types (a chunk being a single part within an expression line such as a
 * keyword or a constant string and so on).
 * 
 * @author aallway
 * @since 18 May 2011
 */
public abstract class AbstractIProcessToJavaScriptConverter {

    public static final String IPROCESSSCRIPT_GRAMMAR = "iProcessScript"; //$NON-NLS-1$

    /**
     * The source iProcess script.
     */
    private String sourceIProcessScript;

    /**
     * The Approximation of JavaScript for given iProcess script.
     */
    private String approximateJavaScript;

    private Expression expression;

    private Set<String> availableDataNames;

    /**
     * Create converter that can output a JavaScript approximation of the given
     * iProcessScript grammar script.
     * 
     * @param iprocessScript
     * @param The
     *            expression object for the script (may be <code>null</code> if
     *            not available!)
     */
    public AbstractIProcessToJavaScriptConverter(String sourceIProcessScript,
            Expression expression) {
        super();
        this.expression = expression;

        init();

        this.sourceIProcessScript = sourceIProcessScript;

        doConversion();
    }

    /**
     * Create converter that can output a JavaScript approximation of the given
     * iProcessScript grammar script.
     * 
     * Note: ABPM-675- We are modifying the existing internal API to be passed
     * the list of available-data-names rather than an expression and therefore
     * not require or use the EMF XPDL model at all.
     * 
     * @param iprocessScript
     * @param The
     *            list of available-data-names (may be <code>null</code> if not
     *            available!)
     */
    public AbstractIProcessToJavaScriptConverter(String sourceIProcessScript,
            Set<String> allData) {
        super();

        availableDataNames = new HashSet<String>();

        if (allData != null) {
            for (String data : allData) {
                availableDataNames.add(data);
            }
        }

        this.sourceIProcessScript = sourceIProcessScript;

        doConversion();
    }

    /**
     * 
     */
    private void doConversion() {
        IProcessExpressionChunkFactory chunkFactory = createChunkFactory();

        approximateJavaScript =
                convertIProcessScriptToJavaScript(this.sourceIProcessScript,
                        chunkFactory);
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Perform any initialisation required during construction.
     */
    protected void init() {
        /*
         * Cache the available data names so we can fix case of field names in
         * script.
         */
        availableDataNames = new HashSet<String>();

        expression = getExpression();
        if (expression != null) {
            List<ProcessRelevantData> allData = null;

            EObject activity =
                    Xpdl2ModelUtil.getAncestor(expression, Activity.class);
            if (activity != null) {
                allData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity((Activity) activity);
            } else {
                EObject process =
                        Xpdl2ModelUtil.getAncestor(expression, Process.class);
                if (process != null) {
                    allData =
                            ProcessInterfaceUtil
                                    .getAllProcessRelevantData((Process) process);
                }
            }

            if (allData != null) {
                for (ProcessRelevantData data : allData) {
                    availableDataNames.add(data.getName());
                }
            }
        }
        return;
    }

    /**
     * This method can be overridden to provide custom chunk factory - this can
     * return chunks that have special handling for different types of chunks.
     * <p>
     * For instance destination specific handling of keywords or function names
     * found in source script into specific output in target.
     * 
     * @return The factory for creating expression line chunks
     */
    protected IProcessExpressionChunkFactory createChunkFactory() {
        return new IProcessExpressionChunkFactory();
    }

    /**
     * Convert the source iProcess Script to approximation in JavaScript
     * 
     * @param script
     * @param chunkFactory
     *            factory for creating chunks representing parts of expression
     *            line
     * 
     * @return approximation in JavaScript
     */
    protected String convertIProcessScriptToJavaScript(String script,
            IProcessExpressionChunkFactory chunkFactory) {
        NestedLineList javaScriptLines = new NestedLineList(2);

        /*
         * Process the script into a line list...
         */
        IProcessScriptLineList iProcessScriptLines =
                new IProcessScriptLineList(script);

        for (String iProcessScriptLine : iProcessScriptLines) {
            /*
             * Process the line into chunks of various types.
             */
            IProcessScriptLineChunkList chunkList =
                    new IProcessScriptLineChunkList(iProcessScriptLine,
                            chunkFactory);

            /*
             * Go thru the chunks compiling them into a JAvaScript line
             * (approximation)
             */
            convertIProcessScriptLine(chunkList, javaScriptLines);

        }

        return javaScriptLines.toString();
    }

    /**
     * @param chunkList
     * @param javaScriptLines
     * @return The given line as a JavaScript approximation.
     */
    protected String convertIProcessScriptLine(
            IProcessScriptLineChunkList chunkList,
            NestedLineList javaScriptLines) {

        StringBuffer currentJavaScriptLine = new StringBuffer();

        boolean needEndConditionBracket = false;

        boolean isStartOfBlockStructure = isStartOfBlockStructure(chunkList);
        boolean isEndStructuredBlock = isEndStructuredBlock(chunkList);
        boolean endsInComment = false;

        /* Never need to prefix a space in front of first chunk in statement. */
        int ignorePrefixSpaceForChunkIdx = 0;

        for (int chunkIdx = 0; chunkIdx < chunkList.size(); chunkIdx++) {
            IProcessExpressionChunk chunk = chunkList.get(chunkIdx);

            String chunkText = chunk.getChunkText();

            IProcessExpressionChunk previousChunk =
                    (chunkIdx > 0) ? chunkList.get(chunkIdx - 1) : null;

            if (chunkIdx == 0 && isConditionLeader(chunkList)) {
                /*
                 * The first chunk needs to be processed specially for condition
                 * constructs.
                 * 
                 * XPD-4572: Used to be case sensitive check here,
                 * iProcessScript is case insenstive
                 */
                if (IProcessScriptKeywords.ELSEIF.equalsIgnoreCase(chunkText)) {
                    /*
                     * Finish block for previous if/elseif.
                     */
                    javaScriptLines.setIdentLevel(javaScriptLines
                            .getIdentLevel() - 1);
                    javaScriptLines.add("}"); //$NON-NLS-1$
                }
                currentJavaScriptLine.append(translateConditionWord(chunkText));
                currentJavaScriptLine.append(" ("); //$NON-NLS-1$
                needEndConditionBracket = true;
                ignorePrefixSpaceForChunkIdx = (chunkIdx + 1);

            } else if (chunkIdx == 0
                    && IProcessScriptKeywords.ELSE.equalsIgnoreCase(chunkText)) {
                /*
                 * Finish block for previous if/elseif/while.
                 */
                javaScriptLines
                        .setIdentLevel(javaScriptLines.getIdentLevel() - 1);
                javaScriptLines.add("}"); //$NON-NLS-1$
                currentJavaScriptLine.append(translateConditionWord(chunkText));

            } else if (chunkIdx == 0 && isEndStructuredBlock) {
                /*
                 * Finish block for previous if/elseif/while.
                 */
                javaScriptLines
                        .setIdentLevel(javaScriptLines.getIdentLevel() - 1);
                currentJavaScriptLine.append("}"); //$NON-NLS-1$

            } else if (chunk instanceof ChunkBracket) {
                boolean isStart = ((ChunkBracket) chunk).isStart();
                if (isStart) {
                    /*
                     * Don't append a space if we have been asked not to by
                     * previous chunk's processing.
                     */
                    if (!(ignorePrefixSpaceForChunkIdx == chunkIdx)) {
                        currentJavaScriptLine.append(' ');
                    }

                    ignorePrefixSpaceForChunkIdx = chunkIdx + 1;

                } else {
                    /* End bracket need never be prefixed with space */
                }

                currentJavaScriptLine.append(chunkText);

            } else if (chunk instanceof ChunkComment) {
                /*
                 * Comment would mark the end of condition so end the condition
                 * bracket if needed
                 */
                if (needEndConditionBracket) {
                    currentJavaScriptLine.append(") "); //$NON-NLS-1$
                    needEndConditionBracket = false;

                } else if (previousChunk != null) {
                    if (!isStartOfBlockStructure && !isEndStructuredBlock) {
                        /* Terminate normal statements with ; */
                        currentJavaScriptLine.append("; "); //$NON-NLS-1$
                    } else {
                        currentJavaScriptLine.append(' ');
                    }
                }

                currentJavaScriptLine.append(JavaScriptKeywords.COMMENT);
                currentJavaScriptLine.append(' ');
                currentJavaScriptLine.append(chunkText);
                endsInComment = true;

            } else if (chunk instanceof ChunkFunctionParameterSeparator) {
                /*
                 * No need to prefix space before "," separator in function
                 * parameter list.
                 */
                currentJavaScriptLine.append(chunkText);

            } else {

                /*
                 * Don't append a space if we have been asked not to by previous
                 * chunk's processing
                 */
                /*
                 * XPD-6654: Don't append a space before/after a '.' (DOT).
                 */
                if (!(ignorePrefixSpaceForChunkIdx == chunkIdx)
                        && !isCurrentOrPreviousCharacterDot(chunkText,
                                chunkList,
                                chunkIdx)) {

                    currentJavaScriptLine.append(' ');
                }

                if (chunk instanceof ChunkFunctionOrKeywordOrFieldName) {
                    if (IProcessScriptKeywords.NOT.equalsIgnoreCase(chunkText)) {
                        /*
                         * When translating a "NOT(xx)" function to "!(xx)" we
                         * do not want to prefix next bracket with space.
                         */
                        ignorePrefixSpaceForChunkIdx = chunkIdx + 1;

                        currentJavaScriptLine.append(JavaScriptKeywords.NOT);

                    } else if (((ChunkFunctionOrKeywordOrFieldName) chunk)
                            .isFunctionName()) {
                        /*
                         * When translating a "FUNCTION(xx)" function to
                         * "TRANSLATEDFUNC(xx)" we do not want to prefix next
                         * bracket with space.
                         */
                        ignorePrefixSpaceForChunkIdx = chunkIdx + 1;

                        currentJavaScriptLine
                                .append(translateFunctionName(chunkText));

                    } else if (IProcessScriptKeywords
                            .isConditionKeyword(chunkText)) {
                        currentJavaScriptLine
                                .append(translateConditionWord(chunkText));

                    } else {
                        currentJavaScriptLine
                                .append(translateKeywordOrFieldName(chunkText));
                    }

                } else if (chunk instanceof ChunkOperator) {
                    currentJavaScriptLine.append(translateOperator(chunkText));

                } else if (chunk instanceof ChunkStringConstant) {

                    /*
                     * Output string constant.
                     */
                    currentJavaScriptLine.append('\"');
                    currentJavaScriptLine.append(chunkText);
                    currentJavaScriptLine.append('\"');

                } else {
                    /*
                     * Anything else (unrecognised text chunk etc) just dump out
                     * as-is.
                     */
                    currentJavaScriptLine.append(chunkText);
                }
            }
        }

        /* Finish of bracketing of condition expression if necessary. */
        if (needEndConditionBracket) {
            currentJavaScriptLine.append(')');
        } else if (!isStartOfBlockStructure && !isEndStructuredBlock
                && chunkList.size() > 0 && !endsInComment) {
            /* Terminate normal statements with ; */
            currentJavaScriptLine.append(";"); //$NON-NLS-1$
        }

        javaScriptLines.add(currentJavaScriptLine.toString());

        /*
         * Add start of block on next line if required.
         */
        if (isStartOfBlockStructure) {
            javaScriptLines.add("{"); //$NON-NLS-1$
            javaScriptLines.setIdentLevel(javaScriptLines.getIdentLevel() + 1);
        }

        return currentJavaScriptLine.toString();
    }

    /**
     * Return <code>true</code> if the current chunk OR the exact previous chunk
     * is a '.', <code>false</code> otherwise.
     * 
     * @param chunkText
     *            Current chunk text.
     * @param chunkList
     *            List of chunks.
     * @param chunkIdx
     *            Current chunk index.
     * 
     * @return <code>true</code> if the current chunk OR the exact previous
     *         chunk is a '.', <code>false</code> otherwise.
     */
    private boolean isCurrentOrPreviousCharacterDot(String chunkText,
            IProcessScriptLineChunkList chunkList, int chunkIdx) {

        if (chunkIdx > 0) {
            return chunkText.equals(".") //$NON-NLS-1$
                    || chunkList.get(chunkIdx - 1).getChunkText().equals("."); //$NON-NLS-1$
        } else {
            return chunkText.equals("."); //$NON-NLS-1$
        }
    }

    /**
     * @param chunkText
     * @return translated keytword or original if no translation.
     */
    protected String translateConditionWord(String chunkText) {
        if (IProcessScriptKeywords.AND.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.AND;
        }
        if (IProcessScriptKeywords.OR.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.OR;
        }
        if (IProcessScriptKeywords.IF.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.IF;
        }
        if (IProcessScriptKeywords.ELSEIF.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.ELSEIF;
        }
        if (IProcessScriptKeywords.ELSE.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.ELSE;
        }
        if (IProcessScriptKeywords.WHILE.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.WHILE;
        }

        return chunkText;
    }

    /**
     * @param chunkText
     * @return
     */
    protected String translateOperator(String chunkText) {
        if (IProcessScriptKeywords.ASSIGNMENT_EQUAL.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.ASSIGNMENT_EQUAL;
        }
        if (IProcessScriptKeywords.CONDITION_EQUAL.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.CONDITION_EQUAL;
        }
        if (IProcessScriptKeywords.NOT_EQUAL.equalsIgnoreCase(chunkText)) {
            return JavaScriptKeywords.NOT_EQUAL;
        }
        return chunkText;
    }

    /**
     * @param chunkText
     * @return translated keyword.
     */
    protected String translateKeywordOrFieldName(String chunkText) {
        /*
         * If it's a real field name in scope of activity/transition then set
         * the case to match the definition (iProcess names are case insensitive
         * but javascript are not.
         */
        for (String dataName : availableDataNames) {
            if (dataName.equalsIgnoreCase(chunkText)) {
                return dataName;
            }
        }
        return chunkText;
    }

    /**
     * @param functionName
     * @return translated function name.
     */
    protected String translateFunctionName(String functionName) {
        /*
         * Studio JavaScript does not support sub-script invocation.
         */
        if ("CALL".equalsIgnoreCase(functionName) || "SCRIPT".equalsIgnoreCase(functionName)) { //$NON-NLS-1$ //$NON-NLS-2$
            return "/* " //$NON-NLS-1$
                    + Messages.AbstractIProcessToJavaScriptConverter_CallScriptNotSupportedComment_message
                    + " */" //$NON-NLS-1$
                    + functionName;
        }

        return functionName;
    }

    /**
     * A start of a block is an iProcess IF, ELSEIF, ELSE or WHILE statement.
     * 
     * @param chunkList
     * @return <code>true</code> if the iProcessScript line represented by the
     *         given chunk list is one that should start a new block
     */
    private boolean isStartOfBlockStructure(
            IProcessScriptLineChunkList chunkList) {
        if (!chunkList.isEmpty()) {
            IProcessExpressionChunk chunk = chunkList.get(0);
            if (chunk instanceof ChunkFunctionOrKeywordOrFieldName) {
                String chunkText = chunk.getChunkText();

                if (IProcessScriptKeywords.IF.equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.ELSEIF
                                .equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.ELSE
                                .equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.WHILE
                                .equalsIgnoreCase(chunkText)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The end of a block is an iProcess ENDIF or WEND statement.
     * 
     * @param chunkList
     * @return <code>true</code> if the iProcessScript line represented by the
     *         given chunk list is one that should start a new block
     */
    private boolean isEndStructuredBlock(IProcessScriptLineChunkList chunkList) {
        if (!chunkList.isEmpty()) {
            IProcessExpressionChunk chunk = chunkList.get(0);
            if (chunk instanceof ChunkFunctionOrKeywordOrFieldName) {
                String chunkText = chunk.getChunkText();

                if (IProcessScriptKeywords.ENDIF.equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.WEND
                                .equalsIgnoreCase(chunkText)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param chunkList
     * @return <code>true</code> if chunkList represents a condition or loop
     *         line in iProcessScript
     */
    private boolean isConditionLeader(IProcessScriptLineChunkList chunkList) {
        if (!chunkList.isEmpty()) {
            IProcessExpressionChunk chunk = chunkList.get(0);
            if (chunk instanceof ChunkFunctionOrKeywordOrFieldName) {
                String chunkText = chunk.getChunkText();

                if (IProcessScriptKeywords.IF.equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.ELSEIF
                                .equalsIgnoreCase(chunkText)
                        || IProcessScriptKeywords.WHILE
                                .equalsIgnoreCase(chunkText)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @return The JavaScript approximation of the given iProcessScript grammar
     *         script.
     */
    public String getJavaScriptApproximation() {
        return approximateJavaScript;
    }

    /**
     * @return The original source iProcessScript
     */
    public String getIProcessScript() {
        return sourceIProcessScript;
    }

}
