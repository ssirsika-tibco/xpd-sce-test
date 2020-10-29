/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.process.js.parser.validator.IProcessValidationStrategy;
import com.tibco.xpd.process.js.parser.validator.ProcessValidationUtil;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;

import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;

/**
 * 
 * 
 * <p>
 * <i>Created: 27 Mar 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class ScriptParserUtil {

    private static String regex = "(.*)(:\\s*null)"; //$NON-NLS-1$

    private static Pattern pattern = Pattern.compile(regex);

    /**
     * If the given script is single line and not terminated with a semi-colon,
     * then append a semi colon;
     * 
     * @param strScript
     * @return
     */
    private static String autoTerminateSingleLineStatement(String strScript) {
        if (!strScript.contains("\n")) { //$NON-NLS-1$
            String tmp = strScript.trim();

            if (!tmp.endsWith(";")) { //$NON-NLS-1$
                return strScript + ";"; //$NON-NLS-1$
            }
        }

        return strScript;
    }

    /**
     * This method returns true if the passed script is empty or just contain
     * comments. This method helps in situation when there is only comment
     * entered which the length check on script won't help.
     * 
     * @param strScript
     * @param grammar
     * @return <code>true</code> if script is empty or contains only comments.
     */
    @SuppressWarnings("unchecked")
    public static boolean isEmptyScript(String strScript, String scriptGrammar) {
        if (strScript == null || strScript.trim().length() < 1) {
            return true;
        }

        /*
         * XPD-3930: HAVE to protect parser from passing in a script with only
         * one token that is an unterminated string because that causes an
         * OutofMemoeryException in parser.LT(1).
         */
        if (isLastLineStartedWithUnterminatedString(strScript)) {
            /*
             * If the last starts with an unterminated string THEN (a) the
             * script is definitely not empty and (b) has the potential to crash
             * the parser.LT(1) if it is the only thing in script except
             * comments. So return here to save parser crashing.
             */
            return false;
        }

        /*
         * Sid XPD-2782: We used to attempt to do a FULL validateScript here,
         * the problem with that is that if there was a serious error with the
         * script, that would not be able to compile the script (and
         * validateScript() would return null) and we would return true.
         * 
         * so instead (and more efficiently) we will create a parser but NOT try
         * to compile it. This gives us a parser using which we can get the
         * first 'token' (a variable, operator, method and so on) and if that
         * returns an empty token we know that the script is empty or only
         * contains comments.
         * 
         * Later it would be better not to parse the script separately just for
         * the isEmptyScript test but that means changing the validation rule
         * and structre to prevent that.
         */
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptParser parser = new JScriptParser(lexer);

        try {
            Token token = parser.LT(1);
            // check there to handle case when there is only comments in the
            // script
            if (token != null && token.getText() != null) {
                return false;
            }
        } catch (Exception e) {
        } finally {
            reader.close();
        }
        return true;
    }

    /**
     * @param strScript
     * @return <code>true</code> if last line starts with an opening string but
     *         is never terminated
     */
    private static boolean isLastLineStartedWithUnterminatedString(
            String strScript) {
        String lastline = null;
        int lastIndexOf = strScript.lastIndexOf('\n');
        if (lastIndexOf >= 0) {
            lastline = strScript.substring(lastIndexOf + 1);
        } else {
            lastline = strScript;
        }
        lastline = lastline.trim();

        if (lastline.startsWith("\"")) { //$NON-NLS-1$
            if (lastline.lastIndexOf('\"') == 0) {
                /* unterminated string at end of script */
                return true;
            }

        } else if (lastline.startsWith("'")) { //$NON-NLS-1$
            if (lastline.lastIndexOf('\'') == 0) {
                /* unterminated string at end of script */
                return true;
            }

        }

        return false;
    }

    /**
     * This method returns true if the passed script is empty or just contain
     * comments. This method helps in situation when there is only comment
     * entered which the length check on script won't help.
     * 
     * @param strScript
     * @param scriptType
     * 
     * 
     * @return
     * 
     * @deprecated Use {@link #isEmptyScript(String, String)} instead,
     *             scriptType parameter is redundant.
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static boolean isEmptyScript(String strScript, String scriptType,
            String scriptGrammar) {
        return isEmptyScript(strScript, scriptGrammar);
    }

    public static JScriptParser validateScript(String strScript,
            List<String> destinationList,
            List<IValidationStrategy> validationStrategyList,
            ISymbolTable symbolTable, IVarNameResolver varNameResolver,
            Map<String, List<ErrorMessage>> validationErrorMap,
            Map<String, List<ErrorMessage>> validationWarningMap,
            String scriptType) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }

        strScript = autoTerminateSingleLineStatement(strScript);

        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            strScript += "\n"; //$NON-NLS-1$
        }
        JScriptParser parser =
                getScriptParser(strScript,
                        validationStrategyList,
                        validationErrorMap);
        if (parser == null) {
            return null;
        }
        parser.setValidationStrategyList(validationStrategyList);
        // parser.setScriptValidationStrategyList(Collections.singletonList((IScriptValidationStrategy)new
        // NewJScriptValidationStrategy()));
        parser.setVarNameResolver(varNameResolver);
        parser.setSymbolTable(symbolTable);
        try {
            parser.compilationUnit();
        } catch (RecognitionException e) {
            handleRecognitionException(e,
                    validationStrategyList,
                    validationErrorMap);
            return null;
        } catch (TokenStreamException e) {
            handleTokenStreamException(e,
                    validationStrategyList,
                    validationErrorMap);
            return null;
        }
        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (validationWarningMap == null) {
            validationWarningMap = new HashMap<String, List<ErrorMessage>>();
        }
        
        validationErrorMap.putAll(ProcessValidationUtil.getErrorMap(parser));
        validationWarningMap
                .putAll(ProcessValidationUtil.getWarningMap(parser));
        return parser;
    }

    private static void handleRecognitionException(RecognitionException e,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getMessage();
        Matcher matcher = pattern.matcher(errMsg);
        String newErrMsg = null;
        if (matcher.matches()) {
            int grpCount = matcher.groupCount();
            if (grpCount > 1) {
                newErrMsg = matcher.group(1);
                if (newErrMsg != null) {
                    errMsg = Messages.JsValidationStrategy_SemicolonExpected;
                }
            }
        }
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg =
                ParseUtil.getANTLRFormattedMessage(e.getLine(),
                        e.getColumn(),
                        errMsg,
                        errorType);
        handleException(msg, validationStrategyList, validationErrorMap);
    }

    private static void handleTokenStreamException(TokenStreamException e,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getMessage();
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg = new ErrorMessage(1, 1, errMsg, errorType);
        handleException(msg, validationStrategyList, validationErrorMap);
    }

    private static void handleException(ErrorMessage errorMessage,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        List<ErrorMessage> msgList = new ArrayList<ErrorMessage>();
        msgList.add(errorMessage);
        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (validationStrategyList != null) {
            for (IValidationStrategy strategy : validationStrategyList) {
                if (strategy instanceof IProcessValidationStrategy) {
                    IProcessValidationStrategy pStrategy =
                            (IProcessValidationStrategy) strategy;
                    List<ErrorMessage> oldErrorMessages =
                            validationErrorMap.get(pStrategy
                                    .getDestinationName());
                    if (oldErrorMessages == null) {
                        oldErrorMessages = new ArrayList<ErrorMessage>();
                    }
                    oldErrorMessages.addAll(msgList);
                    validationErrorMap.put(pStrategy.getDestinationName(),
                            oldErrorMessages);
                }
            }
        }
    }

    private static JScriptParser getScriptParser(String strScript,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptParser parser = new JScriptParser(lexer);
        Token token;
        try {
            token = parser.LT(1);
            // check there to handle case when there is only comments in the
            // script
            if (token != null && token.getText() != null) {
                // parsing script without validation strategy so that we can
                // ensure that we have a valid script and then in the second
                // pass we can use the validation strategy for destination
                // specific validations.
                parser.compilationUnit();
                reader = new StringReader(strScript);
                lexer = new JScriptLexer(reader);
                parser = new JScriptParser(lexer);
                return parser;
            }

        } catch (RecognitionException e) {
            handleRecognitionException(e,
                    validationStrategyList,
                    validationErrorMap);
            return null;
        } catch (TokenStreamException e) {
            // there could be errors thrown which we should handle
            handleTokenStreamException(e,
                    validationStrategyList,
                    validationErrorMap);
        }
        return null;
    }

    /**
     * Replace all occurrences of data field / parameter references in the given
     * script
     * 
     * @param scriptParser
     * @param strScript
     * @param old2NewNameMap
     * @return new script or null if error in token stream.
     */
    public static String replaceDataRefByName(String strScript,
            Map<String, String> old2NewNameMap) {
        if (strScript == null || strScript.trim().length() == 0) {
            return strScript;
        }

        JScriptParser scriptParser = prepareScriptParser(strScript);

        //
        // First of all, search for all occurrences of any field in the list to
        // replace - keeping track of the line and column number.
        List<ScriptDataRef> references = new ArrayList<ScriptDataRef>();

        Token token = null;
        Token prevToken = null;
        int tokenIdx = 1;
        while (true) {
            try {
                //
                // Get next token from sript
                token = scriptParser.LT(tokenIdx++);
                if (token == null || token.getType() == Token.EOF_TYPE) {
                    // End of script. That's it all done.
                    break;
                }

                if (token != null && token.getType() == JScriptTokenTypes.IDENT) {
                    // IDENT is either a symbol (data field / formal param) or a
                    // class property/method (such as DateTime.Date)
                    //
                    /*
                     * Sid ACE-2344 All data field / parameter references are now wrapped in the new 'data' object.
                     * So we're looking for data fields with a dot and 'data' ident in front.
                     */
                    if (isWrappedInDataObject(scriptParser,
                            tokenIdx - 1,
                            ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME)) {

                        // Check if it's in rename map and if so, store the
                        // reference info.
                        String newName = old2NewNameMap.get(token.getText());
                        if (newName != null) {
                            references
                                    .add(new ScriptDataRef(token.getLine(),
                                            token.getColumn(), token.getText(),
                                            newName));
                        }
                    }
                }

                prevToken = token;

            } catch (TokenStreamException e) {
                // ON exception return original - can't do it too man y problems
                // for lexer to cope with.
                return strScript;
            }

        }

        //
        // Recreate script (only if there were any references)...
        if (references.size() == 0) {
            return strScript;
        }

        // Attempt to preserve original line termination
        String lineTermination;
        if (strScript.contains("\r\n")) { //$NON-NLS-1$
            lineTermination = "\r\n"; //$NON-NLS-1$
        } else if (strScript.contains("\r")) { //$NON-NLS-1$
            lineTermination = "\r"; //$NON-NLS-1$
        } else {
            lineTermination = "\n"; //$NON-NLS-1$
        }

        // Go thru line by line.
        BufferedReader inputScript =
                new BufferedReader(new StringReader(strScript));

        // Sort references by ascending line number then descending column
        // number.
        Collections.sort(references);

        // The output script.
        StringBuffer outScript = new StringBuffer();

        // Track the input line number.
        int inputLineNum = 1;

        // The current reference idx
        int refIdx = 0;
        int numRefs = references.size();

        ScriptDataRef ref = references.get(refIdx);

        while (refIdx < numRefs) {
            // Wind on thru input script till we get to the given line.
            int currRefLine = ref.line;
            while (inputLineNum < currRefLine) {
                String line = nextScriptLine(inputScript);
                inputLineNum++;

                outScript.append(line);
                outScript.append(lineTermination);
            }

            // The input has caught up with the next line to replace reference
            // on.
            // Get the line to replace refs on and go thru refs making
            // replacements.
            String line = nextScriptLine(inputScript);
            inputLineNum++;

            StringBuffer lineBuff = new StringBuffer(line);

            //
            // Don't worry! Refs are from last on line to first on line order,
            // so simple iteration is ok.
            while (ref.line == currRefLine) {
                int startIdx = ref.col - 1;
                lineBuff.replace(startIdx,
                        startIdx + ref.oldName.length(),
                        ref.newName);

                // OK, that's this reference replaced, get next.
                refIdx++;
                if (refIdx >= numRefs) {
                    break;
                }

                ref = references.get(refIdx);
            }

            // That's all the refs finished in this line. output and continue.
            outScript.append(lineBuff);
            outScript.append(lineTermination);
        }

        // Output remainder of script.
        String line = nextScriptLine(inputScript);
        while (line != null) {
            outScript.append(line);
            outScript.append(lineTermination);
            line = nextScriptLine(inputScript);
        }

        return outScript.toString();
    }

    /**
     * Sid ACE-2344 Get all of objects referenced within the script under the given data field descriptor object name
     * {@link ReservedWords#PROCESS_DATA_WRAPPER_OBJECT_NAME} for the standard 'data' object
     * 
     * <b>NOTE:</b> That the references will incldue ANY identifier under the given object regardless of whether it is a
     * valid data field or not.
     * 
     * @param strScript
     * @param dataWrapperObjectName
     * @return The set of data references for data wrapped in the given named data field descriptor wrapper object
     */
    public static Collection<String> getProcessDataReferences(String strScript, String dataWrapperObjectName) {
        Set<String> dataReferences = new HashSet<String>();

        if (strScript == null || strScript.trim().length() == 0) {
            return dataReferences;
        }

        JScriptParser scriptParser = prepareScriptParser(strScript);

        Token token = null;
        Token prevToken = null;
        int tokenIdx = 1;
        while (true) {
            try {
                //
                // Get next token from sript
                token = scriptParser.LT(tokenIdx++);
                if (token == null || token.getType() == Token.EOF_TYPE) {
                    // End of script. That's it all done.
                    break;
                }

                if (token != null && token.getType() == JScriptTokenTypes.IDENT) {
                    // IDENT is either a symbol (data field / formal param) or a
                    // class property/method (such as DateTime.Date)
                    //
                    /*
                     * Sid ACE-2344 All data field / parameter references are now wrapped in the new 'data' object. So
                     * we're looking for data fields with a dot and 'data' ident in front.
                     */
                    if (isWrappedInDataObject(scriptParser, tokenIdx - 1, dataWrapperObjectName)) {
                        dataReferences.add(token.getText());
                    }
                }

            } catch (TokenStreamException e) {
                // ON exception return what we have so far.
                return dataReferences;
            }
        }

        return dataReferences;
    }

    /**
     * Prepare the script and create a parser for it.
     * 
     * @param strScript
     * @return The scrpt parser.
     */
    private static JScriptParser prepareScriptParser(String strScript) {
        // Make sure we are newline terminated else get exceptions.
        String fixedScript;
        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            fixedScript = strScript + "\n"; //$NON-NLS-1$
        } else {
            fixedScript = strScript;
        }

        StringReader reader = new StringReader(fixedScript);
        JScriptLexer lexer = new JScriptLexer(reader);

        // THIS IS REALLY IMPORTANT - SET TAB SIZE TO ZERO - LExer counts tabs
        // as 8 chars by default when calculating the 'column' for a token.
        lexer.setTabSize(1);

        JScriptParser scriptParser = new JScriptParser(lexer);
        return scriptParser;
    }

    /**
     * Given the token index of an identifier token, check if it is within the process "data" object
     * 
     * @param scriptParser
     * @param identTokenIdx
     *            1-based script parser token index of the identifier we're checking.
     * 
     * @return <code>true</code> if identifier is within the process "data" object
     * @throws TokenStreamException
     */
    private static boolean isWrappedInDataObject(JScriptParser scriptParser, int identTokenIdx,
            String dataWrapperObjectName)
            throws TokenStreamException {
        if (identTokenIdx >= 3) {
            Token prevToken = scriptParser.LT(identTokenIdx - 1);
            Token prevPrevToken = scriptParser.LT(identTokenIdx - 2);

            if (prevToken.getType() == JScriptTokenTypes.DOT && prevPrevToken.getType() == JScriptTokenTypes.IDENT
                    && dataWrapperObjectName.equals(prevPrevToken.getText())) {

                /* Musn't be anything before the data wrapper object. */
                if (identTokenIdx == 3 || scriptParser.LT(identTokenIdx - 3).getType() != JScriptTokenTypes.DOT) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get next line from buffer or null at end of stream.
     * 
     * @param inputScript
     * @return
     */
    public static String nextScriptLine(BufferedReader inputScript) {
        String line = ""; //$NON-NLS-1$
        try {
            line = inputScript.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            line = null;
        }

        return line;
    }

    /**
     * Data class to store individual field/param reference (and be able to sort
     * into line in ascending order, then column IN REVERSE ORDER!
     * <p>
     * This helps when doing replacements because we can always guarantee that
     * we don't shuffle text we're interested in away from it's original offset.
     * </p>
     * 
     * @author aallway
     * 
     */
    public static class ScriptDataRef implements Comparable<ScriptDataRef> {
        int line = 0;

        int col = 0;

        String oldName;

        String newName;

        public ScriptDataRef(int line, int col, String oldName, String newName) {
            this.line = line;
            this.col = col;
            this.oldName = oldName;
            this.newName = newName;
        }

        // Sort by line then column.
        @Override
        public int compareTo(ScriptDataRef o) {
            // Sort by ascending line number
            int ret = line - o.line;
            if (ret == 0) {
                // then descending (reverse) line number.
                ret = o.col - col;
            }
            return ret;
        }

    }

}
