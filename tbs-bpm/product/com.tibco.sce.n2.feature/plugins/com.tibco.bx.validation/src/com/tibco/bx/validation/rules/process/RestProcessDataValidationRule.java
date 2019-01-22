/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;
import java.util.Collections;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.bpmn.developer.preprocessors.RestPreProcessor;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Validate process data names for processes containing REST invocation
 * activities.
 * 
 * @author nwilson
 * @since 21 May 2015
 */
public class RestProcessDataValidationRule extends ProcessValidationRule {
    private static final String ISSUE_DATANAME_CANNOT_HAVE_REST_PREFIX =
            "bx.dataNameCannotHaveRestPrefix"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        RestPreProcessor tool = getTool(RestPreProcessor.class, process);
        boolean hasRest = tool.hasRestServiceInvocation();
        if (hasRest) {
            Collection<ProcessRelevantData> allData =
                    ProcessInterfaceUtil.getAllDataDefinedInProcess(process);
            for (ProcessRelevantData data : allData) {
                String dataName = data.getName();
                if (isRestKeywordPrefix(dataName)) {
                    addIssue(ISSUE_DATANAME_CANNOT_HAVE_REST_PREFIX,
                            data,
                            Collections.singletonList(dataName));
                }
            }
        }

    }

    /**
     * @param dataName
     *            The data name.
     * @return true if it starts with "REST_".
     */
    private boolean isRestKeywordPrefix(String dataName) {
        return dataName.startsWith("REST_"); //$NON-NLS-1$
    }

}
