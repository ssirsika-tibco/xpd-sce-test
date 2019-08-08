/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Identifies uses of the date/time factory DateTimeUtil and replaces them with "new Date()"
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class DateConstructorRefactor implements ScriptRefactorRule {
    private static final String DATE_FACTORY = "DateTimeUtil"; //$NON-NLS-1$

    private static String CREATE_DATE = "createDate"; //$NON-NLS-1$

    private static String CREATE_DATETIME = "createDatetime"; //$NON-NLS-1$

    private static String CREATE_TIME = "createTime"; //$NON-NLS-1$

    private static String CREATE_DATETIMETZ = "createDatetimetz"; //$NON-NLS-1$

    private static final String[] FACTORY_METHODS = { CREATE_DATE, CREATE_DATETIME, CREATE_TIME, CREATE_DATETIMETZ };

    private static final String DATE_CONSTRUCTOR = "new Date"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (aIndex > 2) {
            // ensure that the token is for the old method name and is preceeded by the field name and a dot
            Token factoryNameToken = aParser.LT(aIndex - 2);
            Token dotToken = aParser.LT(aIndex - 1);
            Token openParen = aParser.LT(aIndex + 1);

            if ((factoryNameToken == null) || (factoryNameToken.getType() != JScriptTokenTypes.IDENT)
                    || (dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                    || (aToken == null) || (aToken.getType() != JScriptTokenTypes.IDENT) //
                    || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
                return false;
            }

            // check the factory name is "DateTimeUtil"
            if (!DateConstructorRefactor.DATE_FACTORY.equals(factoryNameToken.getText())) {
                return false;
            }

            // look for a matching method name
            for (String method : DateConstructorRefactor.FACTORY_METHODS) {
                if (method.equals(aToken.getText())) {
                    return true;
                }
            }
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
        Token factoryNameToken = aParser.LT(aIndex - 2);
        Token dotToken = aParser.LT(aIndex - 1);

        // we will be replacing all these tokens
        Collection<ScriptItemReplacementRef> result = new ArrayList<>();
        result.add(new ScriptItemReplacementRef(factoryNameToken, null));
        result.add(new ScriptItemReplacementRef(dotToken, null));
        result.add(new ScriptItemReplacementRef(aToken, DateConstructorRefactor.DATE_CONSTRUCTOR));

        // special case for createTime with time-unit parameters
        if (DateConstructorRefactor.CREATE_TIME.equals(aToken.getText())) {
            if (getParameterCount(aParser, aIndex + 1) == 4) {
                Token openParen = aParser.LT(aIndex + 1);
                result.add(new ScriptItemReplacementRef(openParen, "(0, 0, 0, ")); //$NON-NLS-1$
            }
        }

        return result;
    }
}
