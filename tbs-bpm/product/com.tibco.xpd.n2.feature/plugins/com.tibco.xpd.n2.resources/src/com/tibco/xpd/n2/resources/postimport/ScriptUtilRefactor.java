/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Replace ScriptUtil methods with the equivalent bpm.scriptUtil methods.
 *
 * @author pwatson
 * @since 12 Aug 2019
 */
class ScriptUtilRefactor implements ScriptRefactorRule {
    private static final String OLD_UTIL = "ScriptUtil"; //$NON-NLS-1$

    private static final String NEW_UTIL = "bpm.scriptUtil"; //$NON-NLS-1$

    private final Map<String, String> methodReplacements;

    /**
     * Creates a MethodRefactorRule to replace multiple method names with a corresponding value.
     * 
     * @param aMethodMap
     *            the map of method names to be replaced. The key is the old method name.
     */
    public ScriptUtilRefactor(Map<String, String> aMethodMap) {
        methodReplacements = new HashMap<>(aMethodMap);
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if ((aToken.getType() != JScriptTokenTypes.IDENT) ||
                (!ScriptUtilRefactor.OLD_UTIL.equals(aToken.getText()))) {
            return false;
        }
        
        // ensure that the token is for the old method name and is preceeded by a dot
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);

        if ((dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                || (methodToken == null) || (methodToken.getType() != JScriptTokenTypes.IDENT) //
                || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
            return false;
        }

        if (!methodReplacements.containsKey(methodToken.getText())) {
            return false;
        }

        return true;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#getReplacements(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException {
        Token methodToken = aParser.LT(aIndex + 2);
        String newMethod = methodReplacements.get(methodToken.getText());
        return Arrays.asList(new ScriptItemReplacementRef(aToken, ScriptUtilRefactor.NEW_UTIL),
                new ScriptItemReplacementRef(methodToken, newMethod));
    }

    /**
     * Provides additional filtering to the {@link MethodNameRefactor}.
     */
    public static interface MethodFilter {
        /**
         * Returns <code>true</code> if the filter accepts the token(s) identified by the parser and index.
         * 
         * @param aParser
         *            the parser from which tokens are traversed.
         * @param aIndex
         *            the index of the field name token to be resolved.
         * @return <code>true</code> if filter accepts the token.
         */
        public boolean accept(JScriptParser aParser, int aIndex) throws TokenStreamException;
    }
}
