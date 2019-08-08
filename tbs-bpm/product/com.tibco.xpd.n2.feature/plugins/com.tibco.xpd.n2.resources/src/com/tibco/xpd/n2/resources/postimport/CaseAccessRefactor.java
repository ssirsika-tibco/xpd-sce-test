/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Refactors the case-access classes to the new "bpm.caseData" methods.
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class CaseAccessRefactor implements ScriptRefactorRule {
    // original findAll method
    private static final String FIND_ALL_METHOD = "findAll"; //$NON-NLS-1$

    // original findByCriteria method
    private static final String FIND_BY_METHOD = "findByCriteria"; //$NON-NLS-1$

    // original read method
    private static final String READ_METHOD = "read"; //$NON-NLS-1$

    // map of BOM classes keyed on their case-access names
    private final Map<String, Class> mappings;

    public CaseAccessRefactor(DataFieldResolver aResolver) {
        Collection<Class> bomClasses = aResolver.getBOMDataTypes(Class.class);

        mappings = new HashMap<>();
        StringBuilder classRef = new StringBuilder();
        for (Class clazz : bomClasses) {
            String modelName = clazz.getModel().getName();

            classRef.setLength(0);
            classRef.append("cac_") //$NON-NLS-1$
                    .append(modelName.replace('.', '_')).append('_')
                    .append(clazz.getName());

            mappings.put(classRef.toString(), clazz);
        }
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (!mappings.containsKey(aToken.getText())) {
            return false;
        }

        // if not followed by a method
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);
        if ((dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                || (methodToken == null) || (methodToken.getType() != JScriptTokenTypes.IDENT) //
                || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
            return false;
        }

        String method = methodToken.getText();

        // find all instances
        // find all instances - paginated
        if (CaseAccessRefactor.FIND_ALL_METHOD.equals(method)) {
            // allows no parameters OR two numeric parameters
            int parameterCount = getParameterCount(aParser, aIndex + 3);
            return (parameterCount == 0) || (parameterCount == 2);
        }

        // find all by DQL
        // find all that match criteria
        if (CaseAccessRefactor.FIND_BY_METHOD.equals(method)) {
            Token paramToken = aParser.LT(aIndex + 4);
            // only support string literal parameter
            if ((paramToken == null) || (paramToken.getType() != JScriptTokenTypes.STRING_LITERAL)) {
                return false;
            }

            return true;
        }

        // read for all given case references in array
        if (CaseAccessRefactor.READ_METHOD.equals(method)) {
            return true;
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
        Class bomClass = mappings.get(aToken.getText());
        
        // construct a string containing full path reference to bom class
        // this is used by most of the new methods
        String fullPath = new StringBuilder().append('"').append(bomClass.getModel().getName()).append('.')
                .append(bomClass.getName()).append('"').toString();

        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);

        String method = methodToken.getText();

        Collection<ScriptItemReplacementRef> result = new ArrayList<>();
        result.add(new ScriptItemReplacementRef(aToken, "bpm.caseData")); //$NON-NLS-1$

        // find all instances
        // find all instances - paginated
        if (CaseAccessRefactor.FIND_ALL_METHOD.equals(method)) {
            // add bom class name to parameter list
            int parameterCount = getParameterCount(aParser, aIndex + 3);
            String replacement = "(" + fullPath; //$NON-NLS-1$
            if (parameterCount > 0) {
                replacement += ','; // put comma-delimter after new parameter
            }
            result.add(new ScriptItemReplacementRef(openParen, replacement));
        }

        // find all by DQL
        // find all that match criteria
        else if (CaseAccessRefactor.FIND_BY_METHOD.equals(method)) {
            Token paramToken = aParser.LT(aIndex + 4);
            String replacement = paramToken.getText() + ',' + fullPath;
            result.add(new ScriptItemReplacementRef(paramToken, replacement));
        }

        // read for all given case references in array
        else if (CaseAccessRefactor.READ_METHOD.equals(method)) {
            result.add(new ScriptItemReplacementRef(methodToken, "readAll")); //$NON-NLS-1$
        }

        return result;
    }
}
