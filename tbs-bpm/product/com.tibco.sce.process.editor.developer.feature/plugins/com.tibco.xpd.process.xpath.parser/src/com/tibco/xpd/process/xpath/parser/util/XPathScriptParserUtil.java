/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.xpath.parser.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.wsdl.Part;
import org.jaxen.expr.Expr;

import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;
import antlr.collections.AST;

import com.tibco.xpd.process.js.model.Activator;
import com.tibco.xpd.process.js.parser.validator.IProcessValidationStrategy;
import com.tibco.xpd.process.xpath.model.ProcessXPathConsts;
import com.tibco.xpd.process.xpath.parser.antlr.XPathLexer;
import com.tibco.xpd.process.xpath.parser.antlr.XPathParser;
import com.tibco.xpd.process.xpath.parser.antlr.XPathTokenTypes;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.Messages;
import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;
import com.tibco.xpd.script.parser.validator.ISymbolTable;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

/**
 * 
 * 
 * <p>
 * <i>Created: 07 Feb 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public class XPathScriptParserUtil {

    public static String XPATH_DESTINATION = "xPath1.x"; //$NON-NLS-1$

    public static String WEBSERVICE_TASK = "WebServiceTask"; //$NON-NLS-1$

    public static String BWSERVICE_TASK = "BWServiceTask"; //$NON-NLS-1$

    public static final String XPATH_GRAMMAR = "XPath"; //$NON-NLS-1$

    public static XPathParser validateXPathScript(String strScript,
            List<String> destinationList, ISymbolTable symbolTable,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap,
            Map<String, List<ErrorMessage>> validationWarningMap,
            Part wsdlPart, boolean isWsdlSupported,
            IScriptRelevantData mappingType) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }

        XPathParser parser =
                getXPathScriptParser(strScript,
                        destinationList,
                        validationErrorMap);
        if (parser == null) {
            return null;
        }
        parser.setIsWsdlSupported(isWsdlSupported);
        parser.setMappingType(mappingType);
        parser.setWsdlPart(wsdlPart);
        parser.setSymbolTable(symbolTable);
        parser.setValidationStrategyList(validationStrategyList);
        try {
            parser.startValidation();
            if (!parser.getRecognitionExceptions().isEmpty()) {
                for (RecognitionException re : parser
                        .getRecognitionExceptions()) {
                    handleRecognitionException(re,
                            destinationList,
                            validationErrorMap);
                }
                return null;
            }
        } catch (RecognitionException e) {
            handleRecognitionException(e, destinationList, validationErrorMap);
            return null;
        } catch (TokenStreamException e) {
            // there could be errors thrown which we should handle
            handleTokenStreamException(e, destinationList, validationErrorMap);
            return null;
        }

        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (validationWarningMap == null) {
            validationWarningMap = new HashMap<String, List<ErrorMessage>>();
        }
        validationErrorMap.putAll(getErrorMap(parser));
        validationWarningMap.putAll(getWarningMap(parser));
        return parser;
    }

    private static void handleTokenStreamException(TokenStreamException e,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getMessage();
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg = new ErrorMessage(1, 1, errMsg, errorType);
        handleException(msg, destinationList, validationErrorMap);
    }

    private static void handleException(ErrorMessage errorMessage,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        List<ErrorMessage> msgList = new ArrayList<ErrorMessage>();
        msgList.add(errorMessage);
        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (destinationList != null) {
            for (String destinationName : destinationList) {
                validationErrorMap.put(destinationName, msgList);
            }
        }
    }

    private static XPathParser getXPathScriptParser(String strScript,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        StringReader reader = new StringReader(strScript);
        XPathLexer lexer = new XPathLexer(reader);
        XPathParser parser = new XPathParser(lexer);
        Token token;
        try {
            token = parser.LT(1);

            // check there to handle case when there is only comments in the
            // script
            if (token != null && token.getText() != null) {
                reader = new StringReader("(" + strScript + ")");//$NON-NLS-1$//$NON-NLS-2$
                lexer = new XPathLexer(reader);
                parser = new XPathParser(lexer);
                return parser;
            }

        } catch (TokenStreamException e) {
            // there could be errors thrown which we should handle
            handleTokenStreamException(e, destinationList, validationErrorMap);
        }
        return null;
    }

    private static void handleRecognitionException(RecognitionException e,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getMessage();
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg =
                ParseUtil.getANTLRFormattedMessage(e.getLine(),
                        e.getColumn(),
                        errMsg,
                        errorType);
        handleException(msg, destinationList, validationErrorMap);
    }

    public static void validateExpression(XPathParser parser,
            AST expressionAST, Token token, Expr jaxenExpression) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        for (IValidationStrategy strategy : validatorStrategyList) {
            strategy.validateExpression(expressionAST, token);
        }
    }

    public static List<IValidationStrategy> getValidatorStrategyList(
            XPathParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                parser.getValidatorStrategyList();
        return validatorStrategyList;
    }

    /**
     * Returns the list of errors in a Map. The key being the destination
     * environment and the value is the list of errors
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    private static Map<String, List<ErrorMessage>> getErrorMap(
            XPathParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> errorMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) strategy;
                errorMap.put(pStrategy.getDestinationName(), pStrategy
                        .getErrorList());
            }
        }
        return errorMap;
    }

    /**
     * Returns the list of warnings in a Map. The key being the destination
     * environment and the value is the list of warnings
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    private static Map<String, List<ErrorMessage>> getWarningMap(
            XPathParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> warningMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IProcessValidationStrategy) {
                IProcessValidationStrategy pStrategy =
                        (IProcessValidationStrategy) strategy;
                warningMap.put(pStrategy.getDestinationName(), pStrategy
                        .getWarningList());
            }

        }
        return warningMap;
    }

    public static List<JsClassDefinitionReader> getClassDefinitionReader(
            String destinationName, String scriptType) {
        Activator default1 = Activator.getDefault();
        if (default1 == null) {
            return null;
        }
        List<JsClassDefinitionReader> classDefinitionReader =
                default1
                        .getJsClassDefinitionReadersForDestination(destinationName,
                                scriptType,
                                ProcessXPathConsts.XPATH_GRAMMAR);
        if (classDefinitionReader == null) {
            throw new IllegalStateException(Messages.Utility_8
                    + destinationName);
        }
        return classDefinitionReader;
    }

    @SuppressWarnings("unchecked")
    public static List<JsClass> getSupportedJsClasses(String destination,
            String scriptType) {
        if (destination == null || destination.trim().length() < 1) {
            return Collections.EMPTY_LIST;
        }
        List<String> destList = new ArrayList<String>();
        destList.add(destination);
        return getSupportedJsClasses(destList, scriptType);
    }

    @SuppressWarnings("unchecked")
    public static List<JsClass> getSupportedJsClasses(
            List<String> destinationList, String scriptType) {
        List<JsClass> supportedJsClassList = new ArrayList<JsClass>();
        Activator default1 = Activator.getDefault();
        if (default1 != null) {
            List<JsClassDefinitionReader> jsClassDefinitionReader =
                    Collections.EMPTY_LIST;
            try {
                jsClassDefinitionReader =
                        ScriptGrammarContributionsUtil.INSTANCE
                                .getJsClassDefinitionReader(destinationList,
                                        scriptType,
                                        ProcessXPathConsts.XPATH_GRAMMAR,
                                        ProcessXPathConsts.XPATH_DESTINATION);
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
            if (jsClassDefinitionReader == null
                    || jsClassDefinitionReader.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
            for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
                List<JsClass> tempSupportedJsClassList =
                        reader.getSupportedClasses();
                supportedJsClassList.addAll(tempSupportedJsClassList);
            }
        }
        return supportedJsClassList;
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

        // Make sure we are newline terminated else get exceptions.
        String fixedScript;
        if (!strScript.endsWith("\n")) { //$NON-NLS-1$
            fixedScript = strScript + "\n"; //$NON-NLS-1$
        } else {
            fixedScript = strScript;
        }

        StringReader reader = new StringReader(fixedScript);
        XPathLexer lexer = new XPathLexer(reader);

        // THIS IS REALLY IMPORTANT - SET TAB SIZE TO ZERO - LExer counts tabs
        // as 8 chars by default when calculating the 'column' for a token.
        lexer.setTabSize(1);

        XPathParser scriptParser = new XPathParser(lexer);

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
                // Get next token from script
                token = scriptParser.LT(tokenIdx++);
                if (token == null || token.getType() == Token.EOF_TYPE) {
                    // End of script. That's it all done.
                    break;
                }

                if (token != null
                        && token.getType() == XPathTokenTypes.IDENTIFIER) {
                    // IDENT is either a symbol (data field / formal param) or a
                    // class property/method (such as DateTime.Date)
                    //
                    // We only want to change data fields / params so we will
                    // ensure that previous token is "$".
                    // This is very crude, but at this point we do not have the
                    // ability to distinguish between them.
                    if (prevToken != null
                            && prevToken.getType() == XPathTokenTypes.DOLLAR_SIGN) {

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

        // Sort references by ascending line number then descending column
        // number.
        Collections.sort(references);

        // The current reference idx
        int refIdx = 0;
        int numRefs = references.size();

        ScriptDataRef ref = references.get(refIdx);

        StringBuffer scriptBuff = new StringBuffer(strScript);

        // XPath parser does not recognise different lines
        // so line is always 1
        while (refIdx < numRefs) {
            int startIdx = ref.col - 1;
            scriptBuff.replace(startIdx,
                    startIdx + ref.oldName.length(),
                    ref.newName);

            // OK, that's this reference replaced, get next.
            refIdx++;
            if (refIdx >= numRefs) {
                break;
            }

            ref = references.get(refIdx);
        }
        return scriptBuff.toString();
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
    private static class ScriptDataRef implements Comparable<ScriptDataRef> {
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
