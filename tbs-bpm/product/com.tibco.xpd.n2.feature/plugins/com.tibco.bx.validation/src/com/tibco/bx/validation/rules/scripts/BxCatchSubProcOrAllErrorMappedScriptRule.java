/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.bx.validation.rules.scripts;

import com.tibco.bx.validation.rules.mapping.BxBpmnCatchErrorJSOutputMappingRule;
import com.tibco.xpd.js.validation.rules.AbstractCatchSubProcOrAllErrorOrGlobalDataMappedScriptRule;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapped Mapping script validation for catch specific sub-process error or
 * catch-all error event
 * 
 * @author agondal
 * @since 18 Sep 2013
 */
public class BxCatchSubProcOrAllErrorMappedScriptRule extends
        AbstractCatchSubProcOrAllErrorOrGlobalDataMappedScriptRule {

    /** The issue id. */
    private static final String ERROR_ID = "bx.validateScriptTask"; //$NON-NLS-1$

    private static final String WARNING_ID = "bx.warning.validateScriptTask"; //$NON-NLS-1$

    @Override
    protected String getErrorId() {
        return ERROR_ID;
    }

    @Override
    protected String getWarningId() {
        return WARNING_ID;
    }

    /**
     * @see com.tibco.xpd.js.validation.rules.AbstractCatchSubProcOrAllErrorOrGlobalDataMappedScriptRule#isScriptMappingSupportedForBpmnCatchError(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     * @return
     */
    @Override
    protected boolean isScriptMappingSupportedForBpmnCatchError(
            Activity activity) {

        return BxBpmnCatchErrorJSOutputMappingRule
                .isScriptMappingSupportedForBpmnCatchError(activity);
    }

}
