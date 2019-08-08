/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Identifies uses of the array accessor ".get(int)" and replaces them with the bracketed accessor "[int]".
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class ArrayAccessorRefactor implements ScriptRefactorRule {
    private static final String ACCESSOR = "get"; //$NON-NLS-1$

    // used to look-up fields in order to check data types
    private final DataFieldResolver resolver;

    public ArrayAccessorRefactor(DataFieldResolver aResolver) {
        resolver = aResolver;
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (aIndex > 2) {
            Token fieldNameToken = aParser.LT(aIndex - 2);
            Token dotToken = aParser.LT(aIndex - 1);
            Token openParen = aParser.LT(aIndex + 1);

            if ((fieldNameToken == null) || (dotToken == null) || (aToken == null) || (openParen == null)) {
                return false;
            }

            // compares the current token for one that matches the pattern ".get("
            if ((fieldNameToken.getType() == JScriptTokenTypes.IDENT) && (dotToken.getType() == JScriptTokenTypes.DOT) //
                    && (aToken.getType() == JScriptTokenTypes.IDENT)
                    && (openParen.getType() == JScriptTokenTypes.LPAREN)
                    && (ArrayAccessorRefactor.ACCESSOR.equals(aToken.getText()))) {
                // check whether the identified field is an array
                ConceptPath conceptPath = resolver.resolve(aParser, aIndex - 2);
                return (conceptPath != null) && (conceptPath.isArray());
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
        // create replacement for accessor method (with leading dot and following parenthesis) with bracket
        Token dotToken = aParser.LT(aIndex - 1);
        Token openParen = aParser.LT(aIndex + 1);

        // find and replace the matching closing parenthesis with a bracket
        Token closingParen = findClosingParen(aParser, aIndex + 1);
        if ((closingParen == null) || (closingParen.getType() != JScriptTokenTypes.RPAREN)) {
            return Collections.emptyList(); // invalid construct
        }

        Collection<ScriptItemReplacementRef> result = new ArrayList<>();

        // we will be replacing all these tokens
        result.add(new ScriptItemReplacementRef(dotToken, null));
        result.add(new ScriptItemReplacementRef(aToken, "[")); //$NON-NLS-1$
        result.add(new ScriptItemReplacementRef(openParen, null));
        result.add(new ScriptItemReplacementRef(closingParen, "]")); //$NON-NLS-1$

        return result;
    }
}
