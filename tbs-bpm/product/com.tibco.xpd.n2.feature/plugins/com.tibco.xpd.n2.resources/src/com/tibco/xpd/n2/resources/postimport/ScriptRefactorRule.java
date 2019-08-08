/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * ScriptRefactorRules define patterns that identify elements of the JavaScript that is to be replaced with new values.
 * For example; a method may have been refactored with a new name. In which case, a rule would identify uses of the
 * original method name and create {@link ScriptItemReplacementRef}s to replace them.
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
interface ScriptRefactorRule {
    /**
     * Tests whether the given token is a match for this rule. The rule may access additional tokens from the given
     * parser in order to confirm a match.
     * 
     * @param aToken
     *            the token currently being parsed, and for which a match is to be tested.
     * @param aParser
     *            the parser from which the token was read.
     * @param aIndex
     *            the index of the given token within the parser's token collection.
     * @return <code>true</code> if the given token matches this rule.
     * @throws TokenStreamException
     */
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException;

    /**
     * Called when the rule determines that the given token is match. The rule will build a collection of
     * {@link ScriptItemReplacementRef script replacement commands} that will be used to replace the given token (and
     * any other that the rule deems necessary) with the refactored script code. The rule may access additional tokens
     * from the given parser in order build a collection of replacement commands.
     * 
     * @param aToken
     *            the token currently being parsed, and for which a match is to be tested.
     * @param aParser
     *            the parser from which the token was read.
     * @param aIndex
     *            the index of the given token within the parser's token collection.
     * @return the collection of script replacement commands.
     * @throws TokenStreamException
     */
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException;

    /**
     * A utility method for ScriptRefactorRule. Returns tokens within the opening parenthesis, located at the given
     * index within the given parser, and its matching closing parenthesis.
     * 
     * @param aParser
     *            the parser whose tokens are to be traversed.
     * @param aIndex
     *            the index of the opening parenthesis token.
     * @return collection of tokens within the opening and closing parenthesis.
     * @throws TokenStreamException
     *             if the parser throws an errors.
     */
    public default List<Token> getParameters(JScriptParser aParser, int aIndex) throws TokenStreamException {
        Token nextToken = aParser.LT(aIndex);
        if (nextToken.getType() != JScriptTokenTypes.LPAREN) {
            throw new IllegalArgumentException("Start index must reference an opening parenthesis."); //$NON-NLS-1$
        }

        List<Token> result = new ArrayList<>();

        int openCount = 0;
        while (nextToken != null) {
            int nextType = nextToken.getType();

            // we should always hit one - at the start index
            if (nextType == JScriptTokenTypes.LPAREN) {
                if (openCount > 0) {
                    result.add(nextToken);
                }
                openCount++;
            }

            else if (nextType == JScriptTokenTypes.RPAREN) {
                // are we back to the initial opening bracket
                if (--openCount == 0) {
                    return result;
                }
                result.add(nextToken);
            } else {
                result.add(nextToken);
            }

            nextToken = aParser.LT(++aIndex);
        }

        return Collections.emptyList();
    }

    /**
     * A utility method for ScriptRefactorRule. Returns the closing parenthesis for the opening parenthesis located at
     * the given index within the given parser.
     * 
     * @param aParser
     *            the parser whose tokens are to be traversed.
     * @param aIndex
     *            the index of the opening parenthesis token.
     * @return the closing parenthesis token, or <code>null</code> if not found.
     * @throws TokenStreamException
     *             if the parser throws an errors.
     */
    public default Token findClosingParen(JScriptParser aParser, int aIndex) throws TokenStreamException {
        List<Token> parameters = getParameters(aParser, aIndex);
        return aParser.LT(aIndex + parameters.size() + 1);
    }

    /**
     * A utility method for ScriptRefactorRule. Count the number of parameters in the upcoming parser tokens. The given
     * index should reference the opening parenthesis, and the count will continue until the matching closing
     * parenthesis.
     * 
     * @param aParser
     *            the parser from which the tokens are to be obtained.
     * @param aIndex
     *            the index of the opening parenthesis.
     * @return the number of parameters within the indexed parenthesis
     * @throws TokenStreamException
     */
    public default int getParameterCount(JScriptParser aParser, int aIndex) throws TokenStreamException {
        Token token = aParser.LT(aIndex + 1);
        if ((token == null) || (token.getType() == JScriptTokenTypes.RPAREN)) {
            return 0;
        }

        int result = 1;
        int nestedCount = 0;
        while (true) {
            Token nextToken = aParser.LT(aIndex++);
            int type = nextToken.getType();
            if (type == JScriptTokenTypes.RPAREN) {
                --nestedCount;
                if (nestedCount == 0) {
                    break;
                }
            }

            else if (type == JScriptTokenTypes.LPAREN) {
                nestedCount++;
            }

            // starting another parameter
            else if ((nestedCount == 1) && (type == JScriptTokenTypes.COMMA)) {
                result++;
            }
        }

        return result;
    }
}
