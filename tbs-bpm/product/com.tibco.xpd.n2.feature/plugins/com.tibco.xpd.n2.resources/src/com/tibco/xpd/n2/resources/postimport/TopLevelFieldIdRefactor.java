/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.parser.antlr.JScriptParser;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

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

    public TopLevelFieldIdRefactor(DataFieldResolver aResolver) {
        Collection<ProcessRelevantData> inScopeData = aResolver.getInScopeData();

        // for all the in-scope data fields to the old-name->new-name top level identifiers map
        for (ProcessRelevantData data : inScopeData) {
            String fieldName = data.getName();

            // rule for OldProcessFieldName to bpm.OldProcessFieldName
            oldIdentMap.put(fieldName,
                    ReservedWords.PROCESS_DATA_WRAPPER_OBJECT_NAME + ConceptPath.CONCEPTPATH_SEPARATOR + fieldName);
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
     * caseRefField.read(xxx)
     * caseRefField.navigateByCriteriaToXxxxx()
     * </pre>
     */
    private boolean isCaseRefMethod(JScriptParser aParser, int aIndex) throws TokenStreamException {
        // look for the method name
        Token dotToken = aParser.LT(aIndex + 1);
        Token methodToken = aParser.LT(aIndex + 2);
        if ((dotToken != null) && (dotToken.getType() == JScriptTokenTypes.DOT) && (methodToken != null)
                && (methodToken.getType() == JScriptTokenTypes.IDENT)) {

            // is method name one that is replaced by CaseAccessRefactor
            String methodName = methodToken.getText();
            if ((methodName.equals(CaseAccessRefactor.READ_REF_METHOD))
                    || (methodName.startsWith(CaseAccessRefactor.NAVIGATE_METHOD))) {
                return true;
            }
        }

        return false;
    }
}
