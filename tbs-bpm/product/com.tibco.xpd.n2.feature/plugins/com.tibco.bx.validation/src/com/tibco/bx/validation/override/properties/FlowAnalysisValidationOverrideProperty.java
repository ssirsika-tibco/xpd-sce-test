/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.override.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.process.ControlFlowValidationRule;
import com.tibco.xpd.n2.resources.util.MigrationPointUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Advanced property to all suppression of process flow analyzer based
 * validations raised by {@link ControlFlowValidationRule}
 * 
 * @author aallway
 * @since 25 May 2013
 */
public class FlowAnalysisValidationOverrideProperty extends
        AbstractValidationIssueOverrideProperty {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty#getValidationIssueId()
     * 
     * @return
     */
    @Override
    protected String getValidationIssueId() {
        return ControlFlowValidationRule.BX_FLOW_ANALYSER_SUPPRESSOR_ID;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        /* Allow setting at process level or at package level. */
        if (input instanceof Process) {
            return MigrationPointUtil.doMigrationPointsApply((Process) input);

        } else if (input instanceof Package) {
            for (Process process : ((Package) input).getProcesses()) {
                if (MigrationPointUtil.doMigrationPointsApply(process)) {
                    return true;
                }
            }
        }

        return false;
    }

}
