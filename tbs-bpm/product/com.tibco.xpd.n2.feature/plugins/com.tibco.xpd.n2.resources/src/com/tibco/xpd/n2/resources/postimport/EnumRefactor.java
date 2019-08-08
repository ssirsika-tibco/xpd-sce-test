/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Replaces all enumeration references of the form:
 * <ul>
 * <li>package_enum.literal</li>
 * <li>enum.literal</li>
 * </ul>
 * with the new form:
 * <ul>
 * <li>pkg.package.enum.literal</li>
 * </ul>
 * 
 * @author pwatson
 * @since 7 Aug 2019
 */
class EnumRefactor implements ScriptRefactorRule {
    // the original, optional accessor method
    private static final String ACCESSOR = "get"; //$NON-NLS-1$

    // the mappings of possible enumeration references and their replacements
    private final Map<String, String> mappings;

    public EnumRefactor(DataFieldResolver aResolver) {
        // construct a map of the enum references we will look for, and their replacements
        mappings = new HashMap<>();
        Collection<Enumeration> enums = aResolver.getBOMDataTypes(Enumeration.class);

        for (Enumeration enumType : enums) {
            // take the model package name and replace . with _
            String modelName = enumType.getModel().getName().replace('.', '_');

            String enumName = enumType.getName();
            String replacement = "pkg." + modelName + "." + enumName; //$NON-NLS-1$ //$NON-NLS-2$

            // each enum can have one of two forms of reference
            mappings.put(modelName + "_" + enumName, replacement); //$NON-NLS-1$
            mappings.put(enumName, replacement);
        }
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (mappings.isEmpty()) {
            return false; // no enumerations defined
        }

        // if the token within the enum mappings
        if (aToken.getType() == JScriptTokenTypes.IDENT) {
            return mappings.containsKey(aToken.getText());
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
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);

        // replace enum reference with new equivalence
        Collection<ScriptItemReplacementRef> result = new ArrayList<>();
        result.add(new ScriptItemReplacementRef(aToken, mappings.get(aToken.getText())));

        // if this is use of the .get(String) method
        // try to replace it with the enum literal
        if ((dotToken != null) && (dotToken.getType() == JScriptTokenTypes.DOT) //
                && (methodToken != null) && (methodToken.getType() == JScriptTokenTypes.IDENT)
                && (EnumRefactor.ACCESSOR.equals(methodToken.getText())) //
                && (openParen != null) && (openParen.getType() == JScriptTokenTypes.LPAREN)) {
            // get the parameters
            List<Token> parameterTokens = getParameters(aParser, aIndex + 3);

            // if more than one then this is a complex expression construct - which we can't handle
            // if none then this is not a valid match for the get() method
            if (parameterTokens.size() == 1) {
                Token param = parameterTokens.get(0);

                // can only replace string literals
                if (param.getType() == JScriptTokenTypes.STRING_LITERAL) {
                    // extract the string literal - without quotes
                    String enumLit = param.getText();
                    enumLit = enumLit.substring(1, enumLit.length() - 1);

                    Token closeParen = aParser.LT(aIndex + parameterTokens.size() + 4);

                    result.add(new ScriptItemReplacementRef(methodToken, null));
                    result.add(new ScriptItemReplacementRef(openParen, null));
                    result.add(new ScriptItemReplacementRef(param, enumLit));
                    result.add(new ScriptItemReplacementRef(closeParen, null));
                }
            }
        }

        return result;
    }
}
