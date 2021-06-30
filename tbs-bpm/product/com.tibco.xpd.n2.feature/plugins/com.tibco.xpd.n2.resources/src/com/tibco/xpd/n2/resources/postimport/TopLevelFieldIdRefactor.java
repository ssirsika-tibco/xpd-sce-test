/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;

import antlr.Token;
import antlr.TokenStreamException;

/**
 * Identifies top-level data field references by name and refactors them with new names.
 *
 * @author pwatson
 * @since 7 Aug 2019
 */
class TopLevelFieldIdRefactor implements ScriptRefactorRule {
    private final Map<String, String> oldIdentMap = new HashMap<>();

    // Sid ACE-4914 Keep a List of case ref fields names
    private final Set<String> caseRefFields;

    public TopLevelFieldIdRefactor(DataFieldResolver aResolver) {
        Collection<ProcessRelevantData> inScopeData = aResolver.getInScopeData();

        // for all the in-scope data fields to the old-name->new-name top level identifiers map
        for (ProcessRelevantData data : inScopeData) {
            String fieldName = data.getName();

            // rule for OldProcessFieldName to bpm.OldProcessFieldName
            oldIdentMap.put(fieldName,
                    ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR + fieldName);
        }

        /* Sid ACE-4914 Create a map of case ref field names to field defs. */
        caseRefFields = new HashSet<String>();

        for (ProcessRelevantData data : aResolver.getInScopeData()) {
            if (data.getDataType() instanceof RecordType) {
                caseRefFields.add(data.getName());
            }
        }
    }

    /**
     * @see com.tibco.xpd.n2.resources.postimport.ScriptRefactorRule#isMatch(antlr.Token,
     *      com.tibco.xpd.script.parser.antlr.JScriptParser, int)
     */
    @Override
    public boolean isMatch(Token aToken, JScriptParser aParser, int aIndex) throws TokenStreamException {
        if (oldIdentMap.isEmpty()) {
            return false;
        }

        Token prevToken = (aIndex <= 1) ? null : aParser.LT(aIndex - 1);

        // IDENT is either a symbol (data field / formal param) or a class property/method (such as
        // DateTime.Date)
        //
        // We only want to change data fields / params so we will ensure that previous token is not ".".
        // This is very crude, but at this point we do not have the ability to distinguish between them.

        if ((aToken != null) && (aToken.getType() == JScriptTokenTypes.IDENT)
                && (prevToken == null || prevToken.getType() != JScriptTokenTypes.DOT)) {
            if (oldIdentMap.containsKey(aToken.getText())) {

                // if this is NOT one of the case-data ref method invocations
                return !isCaseRefMethod(aParser, aIndex);
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
        return Collections.singleton(new ScriptItemReplacementRef(aToken, oldIdentMap.get(aToken.getText())));
    }

    /**
     * Tests if the field ID is that of a case-ref field that prefixes a call to a method that will be refactored by the
     * {@link CaseAccessRefactor}. In which case, the field reference will also be refactored by
     * {@link CaseAccessRefactor}, and need not be performed here.
     * <p>
     * Such method calls are, for example:
     * 
     * <pre>
     * caseRefField.readXXXX()
     * caseRefField.navigateByCriteriaToXxxxx()
     * </pre>
     */
    private boolean isCaseRefMethod(JScriptParser aParser, int aIndex) throws TokenStreamException {
        // look for the method name
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        Token methodParentObject = aParser.LT(aIndex);

        if ((dotToken != null) && (dotToken.getType() == JScriptTokenTypes.DOT) && (methodToken != null)
                && (methodToken.getType() == JScriptTokenTypes.IDENT) && (methodParentObject != null)
                && methodParentObject.getType() == JScriptTokenTypes.IDENT) {

            // is method name one that is replaced by CaseAccessRefactor
            // the readXXXX () method
            String methodName = methodToken.getText();

            /*
             * Sid ACE-4914 original impl' for CaseRef.readCaseDataType() calls didn't work as it used EQUALS methodname
             * not startsWith, but the method name would always be suffixed with case data type. So to be on the safe
             * side we also now check that it is some a readXXX on an actual case ref field.
             */
            if (caseRefFields.contains(methodParentObject.getText())) {

                if (methodName.startsWith(CaseAccessRefactor.READ_REF_METHOD_PREFIX)) {
                    return true;
                }

                // the navigateByCriteriaToXxxx - with string DQL expression
                if (methodName.startsWith(CaseAccessRefactor.NAVIGATE_METHOD_PREFIX)) {
                    Token openParen = aParser.LT(aIndex + 3);
                    Token paramToken = aParser.LT(aIndex + 4);
                    Token closeParen = aParser.LT(aIndex + 5);
                    // only if it's a string literal parameter
                    if ((openParen != null) && (openParen.getType() == JScriptTokenTypes.LPAREN) //
                            && (paramToken != null) && (paramToken.getType() == JScriptTokenTypes.STRING_LITERAL) //
                            && (closeParen != null) && (closeParen.getType() == JScriptTokenTypes.RPAREN)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
