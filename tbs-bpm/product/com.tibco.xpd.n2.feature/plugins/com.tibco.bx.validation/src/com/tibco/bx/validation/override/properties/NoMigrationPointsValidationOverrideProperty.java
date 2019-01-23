/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.override.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.rules.process.ControlFlowValidationRule;
import com.tibco.xpd.n2.resources.util.MigrationPointUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty;
import com.tibco.xpd.xpdl2.Process;

/**
 * Process advanced property for overriding the
 * "There are no migration point activities in the process" validation.
 * 
 * @author aallway
 * @since 17 Jul 2012
 */
public class NoMigrationPointsValidationOverrideProperty extends
        AbstractValidationIssueOverrideProperty {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.advanced.AbstractValidationIssueOverrideProperty#getValidationIssueId()
     * 
     * @return
     */
    @Override
    protected String getValidationIssueId() {
        return ControlFlowValidationRule.ISSUE_NO_MIGRATION_POINTS;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.extensions.AbstractContributedAdvancedProperty#isApplicable(org.eclipse.emf.ecore.EObject)
     * 
     * @param input
     * @return
     */
    @Override
    public boolean isApplicable(EObject input) {
        return (input instanceof Process)
                && MigrationPointUtil.doMigrationPointsApply((Process) input);
    }

}
