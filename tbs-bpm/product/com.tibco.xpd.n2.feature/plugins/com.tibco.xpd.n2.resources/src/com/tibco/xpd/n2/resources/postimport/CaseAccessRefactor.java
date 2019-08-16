/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    // original read by case-ref method
    public static final String READ_REF_METHOD = "readCaseData"; //$NON-NLS-1$

    // original navigate by case-ref and query - leading text
    public static final String NAVIGATE_METHOD = "navigateByCriteriaTo"; //$NON-NLS-1$

    // map of BOM classes keyed on their case-access names
    private final Map<String, Class> mappings;

    public CaseAccessRefactor(DataFieldResolver aResolver) {
        Collection<Class> bomClasses = aResolver.getBOMDataTypes(Class.class);

        // create mappings current case-access keywords to look for
        // and the classes that those case-accesses refer to
        // these will included non-case-data classes but it's easier to
        // include all classes than to filter out non-case-data
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
        return matchesStaticMethod(aToken, aParser, aIndex) || matchesCaseRefMethod(aToken, aParser, aIndex);
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#getReplacements(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public Collection<ScriptItemReplacementRef> getReplacements(Token aToken, JScriptParser aParser, int aIndex)
            throws TokenStreamException {

        if (matchesStaticMethod(aToken, aParser, aIndex)) {
            return getReplacementsForStaticMethods(aToken, aParser, aIndex);
        }

        if (matchesCaseRefMethod(aToken, aParser, aIndex)) {
            return getReplacementsForCaseRefMethods(aToken, aParser, aIndex);
        }

        return Collections.emptyList();
    }

    /**
     * Looks for a match with one of the supported static case-access methods.
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
    private boolean matchesStaticMethod(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (!mappings.containsKey(aToken.getText())) {
            return false; // not one of the case-access references
        }
    
        // if not followed by a method
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token openParen = aParser.LT(aIndex + 3);
        if ((dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                || (methodToken == null) || (methodToken.getType() != JScriptTokenTypes.IDENT) //
                || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
            return false; // doesn't look like a method call
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
     * Creates the replacement commands for the static case-access methods.
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
    private Collection<ScriptItemReplacementRef> getReplacementsForStaticMethods(Token aToken, JScriptParser aParser,
            int aIndex)
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

    /**
     * Looks for a match with one of the supported case reference methods.
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
    private boolean matchesCaseRefMethod(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (aIndex <= 2) {
            return false;
        }
    
        Token dotToken = aParser.LT(aIndex - 1);
        Token openParen = aParser.LT(aIndex + 1);
        if ((dotToken == null) || (dotToken.getType() != JScriptTokenTypes.DOT) //
                || (openParen == null) || (openParen.getType() != JScriptTokenTypes.LPAREN)) {
            return false; // doesn't look like a method call
        }
    
        String method = aToken.getText();
    
        // caseDataRef.navigateByCriteriaToOrderRef("attribute1 = 1");
        if (method.startsWith(CaseAccessRefactor.NAVIGATE_METHOD)) {
            Token paramToken = aParser.LT(aIndex + 2);
            Token closeParen = aParser.LT(aIndex + 3);
            // only support string literal parameter
            if ((paramToken == null) || (paramToken.getType() != JScriptTokenTypes.STRING_LITERAL)
                    || (closeParen == null) || (closeParen.getType() != JScriptTokenTypes.RPAREN)) {
                return false;
            }
    
            return true;
        }
    
        // caseDataRef.readCaseData();
        if (method.equals(CaseAccessRefactor.READ_REF_METHOD)) {
            Token closeParen = aParser.LT(aIndex + 2);
            if ((closeParen == null) || (closeParen.getType() != JScriptTokenTypes.RPAREN)) {
                return false;
            }
    
            return true;
        }
    
        // bpm.caseData.navigateAll(CaseReference,String,Number,Number)
        // bpm.caseData.navigateByCriteria(CaseReference,String,String,Number,Number)
        // bpm.caseData.navigateBySimpleSearch(CaseReference,String,String,Number,Number)
        // bpm.caseData.read(CaseReference)
        // bpm.caseData.readAll(CaseReference[])
    
        return false;
    }

    /**
     * Creates the replacement commands for the static case-access methods.
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
    private Collection<ScriptItemReplacementRef> getReplacementsForCaseRefMethods(Token aToken, JScriptParser aParser,
            int aIndex) throws TokenStreamException {

        Token caseRefField = aParser.LT(aIndex - 2);
        Token dotToken = aParser.LT(aIndex - 1);
        Token openParen = aParser.LT(aIndex + 1);

        Collection<ScriptItemReplacementRef> result = new ArrayList<>();
        result.add(new ScriptItemReplacementRef(caseRefField, null));
        result.add(new ScriptItemReplacementRef(dotToken, "bpm.caseData.")); //$NON-NLS-1$

        String method = aToken.getText();
        
        // caseDataRef.navigateByCriteriaToOrderRef("attribute1 = 1");
        if (method.startsWith(CaseAccessRefactor.NAVIGATE_METHOD)) {
            // get the case link name and remove "Ref" suffix
            String linkName = getCaseRefLinkName(method.substring(CaseAccessRefactor.NAVIGATE_METHOD.length()));
            
            // only support string literal parameter

            // bpm.caseData.navigateByCriteria(CaseReference,String,String,Number,Number)
            // will be missing the pagination parameters - so expect validation markers
            result.add(new ScriptItemReplacementRef(aToken, "navigateByCriteria(data.")); //$NON-NLS-1$
            result.add(new ScriptItemReplacementRef(openParen, caseRefField.getText() + ", \"" + linkName + "\", ")); //$NON-NLS-1$ //$NON-NLS-2$
        }
    
        // caseDataRef.readCaseData();
        else if (method.equals(CaseAccessRefactor.READ_REF_METHOD)) {
    
            // bpm.caseData.read(CaseReference)
            result.add(new ScriptItemReplacementRef(aToken, "read(data.")); //$NON-NLS-1$
            result.add(new ScriptItemReplacementRef(openParen, caseRefField.getText()));
        }
        
        return result;
    }

    /**
     * Takes the name of the navigation link name, as given in the original "navigateByCriteriaXxxx" call, and refactors
     * it to derive the actual link name.
     * 
     * @param aName
     *            the link name as given in original navigateBy method.
     * @return the actual link name.
     */
    private String getCaseRefLinkName(String aName) {
        StringBuilder result = new StringBuilder();

        result.append(Character.toLowerCase(aName.charAt(0)));

        if (aName.endsWith("Refs")) { //$NON-NLS-1$
            result.append(aName.substring(1, aName.length() - 4));
        } else if (aName.endsWith("Ref")) { //$NON-NLS-1$
            result.append(aName.substring(1, aName.length() - 3));
        } else {
            result.append(aName.substring(1));
        }

        return result.toString();
    }
}
