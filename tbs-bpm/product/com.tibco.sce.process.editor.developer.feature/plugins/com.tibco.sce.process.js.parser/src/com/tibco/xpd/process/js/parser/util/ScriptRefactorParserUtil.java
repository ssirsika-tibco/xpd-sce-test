/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;

import com.tibco.xpd.process.js.parser.util.ScriptParserUtil.ScriptDataRef;
import com.tibco.xpd.script.parser.antlr.JScriptLexer;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptRefactorParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.refactoring.IRefactoringStrategy;
import com.tibco.xpd.script.parser.internal.refactoring.RefactorResult;
import com.tibco.xpd.script.parser.internal.refactoring.RefactoringInfo;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.validator.ISymbolTable;

/**
 * 
 * 
 * @author mtorres
 * 
 */
public class ScriptRefactorParserUtil {

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

    public static JScriptRefactorParser refactorScript(String strScript,
            List<String> destinationList,
            List<IRefactoringStrategy> refactoringStrategyList,
            ISymbolTable symbolTable, IVarNameResolver varNameResolver,
            List<RefactoringInfo> refactoringInfoList, String scriptType,
            RefactoringInfo refactoringInfo) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }

        strScript = autoTerminateSingleLineStatement(strScript);

        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            strScript += "\n"; //$NON-NLS-1$
        }
        JScriptRefactorParser parser = getScriptParser(strScript);
        if (parser == null) {
            return null;
        }
        parser.setVarNameResolver(varNameResolver);
        parser.setSymbolTable(symbolTable);
        parser.setRefactoringInfo(refactoringInfo);
        parser.setRefactoringStrategyList(refactoringStrategyList);
        try {
            parser.compilationUnit();
        } catch (RecognitionException e) {
            return null;
        } catch (TokenStreamException e) {
            return null;
        }

        return parser;
    }

    private static JScriptRefactorParser getScriptParser(String strScript) {
        StringReader reader = new StringReader(strScript);
        JScriptLexer lexer = new JScriptLexer(reader);
        JScriptRefactorParser parser = new JScriptRefactorParser(lexer);
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
                parser = new JScriptRefactorParser(lexer);
                return parser;
            }

        } catch (RecognitionException e) {
            return null;
        } catch (TokenStreamException e) {
            return null;
        }
        return null;
    }

    public static List<RefactorResult> getRefactorResultList(
            JScriptRefactorParser parser) {
        if (parser != null) {
            List<IRefactoringStrategy> refactoringStrategyList =
                    parser.getRefactoringStrategyList();
            List<RefactorResult> refactorResultList =
                    new ArrayList<RefactorResult>();
            for (IRefactoringStrategy strategy : refactoringStrategyList) {
                if (strategy.getRefactorResultList() != null) {
                    refactorResultList.addAll(strategy.getRefactorResultList());
                }
            }
            return refactorResultList;
        }
        return Collections.emptyList();
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
            Map<String, RefactoringInfo> old2NewNameMap) {
        if (strScript == null || strScript.trim().length() == 0) {
            return strScript;
        }

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
                    // Check if it's in rename map and if so, store the
                    // reference info.
                    RefactoringInfo refactoringInfo =
                            old2NewNameMap.get(token.getText());
                    if (refactoringInfo != null) {
                        String newName = refactoringInfo.getNewValue();
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
                String line = ScriptParserUtil.nextScriptLine(inputScript);
                inputLineNum++;

                outScript.append(line);
                outScript.append(lineTermination);
            }

            // The input has caught up with the next line to replace reference
            // on.
            // Get the line to replace refs on and go thru refs making
            // replacements.
            String line = ScriptParserUtil.nextScriptLine(inputScript);
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
        String line = ScriptParserUtil.nextScriptLine(inputScript);
        while (line != null) {
            outScript.append(line);
            outScript.append(lineTermination);
            line = ScriptParserUtil.nextScriptLine(inputScript);
        }

        return outScript.toString();
    }

}
