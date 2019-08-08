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
            return oldIdentMap.containsKey(aToken.getText());
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
}
