/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Collection;
import java.util.Collections;

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
class StaticRefRefactor implements ScriptRefactorRule {
    private final String oldIdent;

    private final String newIdent;

    public StaticRefRefactor(String aIdent, String aReplacement) {
        oldIdent = aIdent;
        newIdent = aReplacement;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);

        if (aToken != null && aToken.getType() == JScriptTokenTypes.IDENT) {
            if (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT) {
                // IDENT is either a symbol (data field / formal param) or a class property/method (such as
                // DateTime.Date)
                //
                // We only want to change data fields / params so we will ensure that previous token is not ".".
                // This is very crude, but at this point we do not have the ability to distinguish between them.

                return (aToken.getType() == JScriptTokenTypes.IDENT) && (oldIdent.equals(aToken.getText()))
                        && (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT);
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
        return Collections.singleton(new ScriptItemReplacementRef(aToken, newIdent));
    }
}
