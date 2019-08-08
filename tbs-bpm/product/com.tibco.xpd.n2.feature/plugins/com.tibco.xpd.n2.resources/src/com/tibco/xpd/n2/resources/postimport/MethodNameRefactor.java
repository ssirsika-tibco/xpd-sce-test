/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 *
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class MethodNameRefactor implements ScriptRefactorRule {
    private final Map<String, String> methodReplacements;

    // optional - to provide additional filtering in the isMatch() method
    private final MethodFilter methodFilter;

    /**
     * A constructor that accepts an additional filter, applied during the isMatch() method, to further refine the
     * identification of the method.
     * 
     * @param aOldMethod
     *            the name of the method to be replaced.
     * @param aNewMethod
     *            the name of the new method to replace the old method.
     * @param aMethodFilter
     *            an optional filter to further confirm a match on the method.
     */
    public MethodNameRefactor(String aOldMethod, String aNewMethod, MethodFilter aMethodFilter) {
        methodReplacements = new HashMap<>();
        methodReplacements.put(aOldMethod, aNewMethod);
        methodFilter = aMethodFilter;
    }

    /**
     * Creates a MethodRefactorRule to replace the given old method name with the new method name.
     * 
     * @param aOldMethod
     *            the name of the method to be replaced.
     * @param aNewMethod
     *            the name of the new method to replace the old method.
     */
    public MethodNameRefactor(String aOldMethod, String aNewMethod) {
        this(aOldMethod, aNewMethod, null);
    }

    /**
     * Creates a MethodRefactorRule to replace multiple method names with a corresponding value.
     * 
     * @param aMethodMap
     *            the map of method names to be replaced. The key is the old method name.
     * @param aMethodFilter
     *            an optional filter to further confirm a match on the method.
     */
    public MethodNameRefactor(Map<String, String> aMethodMap, MethodFilter aMethodFilter) {
        methodReplacements = new HashMap<>(aMethodMap);
        methodFilter = aMethodFilter;
    }

    public MethodNameRefactor(Map<String, String> aMethodMap) {
        this(aMethodMap, null);
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (aIndex > 2) {
            // ensure that the token is for the old method name and is preceeded by the field name and a dot
            Token fieldNameToken = aParser.LT(aIndex - 2);
            Token prevToken = aParser.LT(aIndex - 1);
            Token nextToken = aParser.LT(aIndex + 1);

            if ((fieldNameToken == null)
                    || ((fieldNameToken.getType() != JScriptTokenTypes.IDENT)
                            && (fieldNameToken.getType() != JScriptTokenTypes.RPAREN))
                    || (prevToken == null) || (prevToken.getType() != JScriptTokenTypes.DOT) //
                    || (aToken == null) || (aToken.getType() != JScriptTokenTypes.IDENT) //
                    || (nextToken == null) || (nextToken.getType() != JScriptTokenTypes.LPAREN)) {
                return false;
            }

            if (!methodReplacements.containsKey(aToken.getText())) {
                return false;
            }

            // if a filter is supplied - as it for confirmation - otherwise it's a match
            return (methodFilter == null) || methodFilter.accept(aParser, aIndex);
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#getReplacements(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException {
        String newMethod = methodReplacements.get(aToken.getText());
        return Collections.singleton(new ScriptItemReplacementRef(aToken, newMethod));
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
