/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Identifies uses of the array accessor ".size()" and replaces them with the property accessor ".length".
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class ArraySizeRefactor implements ScriptRefactorRule {
    private static final String SIZE_METHOD = "size"; //$NON-NLS-1$

    private static final String LENGTH_PROPERTY = "length"; //$NON-NLS-1$

    // used to look-up fields in order to check data types
    private final DataFieldResolver resolver;

    public ArraySizeRefactor(DataFieldResolver aFieldResolver) {
        resolver = aFieldResolver;
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
            Token dotToken = aParser.LT(aIndex - 1);
            Token openParen = aParser.LT(aIndex + 1);
            Token closeParen = aParser.LT(aIndex + 2);

            if ((fieldNameToken == null) || (fieldNameToken.getType() != JScriptTokenTypes.IDENT) || (dotToken == null)
                    || (dotToken.getType() != JScriptTokenTypes.DOT) //
                    || (aToken == null) || (aToken.getType() != JScriptTokenTypes.IDENT) //
                    || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN) //
                    || (closeParen == null) || (closeParen.getType() != JScriptTokenTypes.RPAREN)) {
                return false;
            }

            // check the method name is "size"
            if (!ArraySizeRefactor.SIZE_METHOD.equals(aToken.getText())) {
                return false;
            }

            // check whether the identified field is an array
            ConceptPath conceptPath = resolver.resolve(aParser, aIndex - 2);
            return (conceptPath != null) && (conceptPath.isArray());
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
        Token openParen = aParser.LT(aIndex + 1);
        Token closeParen = aParser.LT(aIndex + 2);

        // we will be replacing all these tokens
        Collection<ScriptItemReplacementRef> result = new ArrayList<>();
        result.add(new ScriptItemReplacementRef(aToken, ArraySizeRefactor.LENGTH_PROPERTY));
        result.add(new ScriptItemReplacementRef(openParen, " ")); //$NON-NLS-1$
        result.add(new ScriptItemReplacementRef(closeParen, null)); // $NON-NLS-1$

        return result;
    }
}
