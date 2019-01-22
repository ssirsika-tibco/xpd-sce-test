/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bx.validation.rules.scripts;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.js.validation.rules.AbstractPerformerFieldScriptRule;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.script.parser.validator.ErrorMessage;

/**
 * @author bharge
 *
 */
public class BxJsScriptPerformerRule extends AbstractPerformerFieldScriptRule {
    /** The issue id. */
    private static final String ERROR_ID = "bx.validateScriptTask"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.validateScriptTask"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractProcessRelevantDataScriptRule#getErrorId()
     *
     * @return
     */
    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }
    
    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractProcessRelevantDataScriptRule#getWarningId()
     *
     * @return
     */
    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractProcessRelevantDataScriptRule#getScriptGrammar()
     *
     * @return
     */
    @Override
    protected String getScriptGrammar() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }


    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractProcessRelevantDataScriptRule#getSubstitutionList(com.tibco.xpd.script.parser.validator.ErrorMessage)
     *
     * @param errorMessage
     * @return
     */
    @Override
    protected List<String> getSubstitutionList(ErrorMessage errorMessage) {
        List<String> tempMsgList = new ArrayList<String>();
        tempMsgList.add(Integer.toString(errorMessage.getLineNumber()));
        tempMsgList.add(Integer.toString(errorMessage.getColumnNumber()));
        tempMsgList.add(errorMessage.getErrorMessage());
        return tempMsgList;
    }

}
