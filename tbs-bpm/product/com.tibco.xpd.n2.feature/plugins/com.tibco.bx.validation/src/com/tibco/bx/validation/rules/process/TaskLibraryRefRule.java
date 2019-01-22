/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Check if reference taks is referencing library task (not supported).
 * 
 * @author Jan Arciuchiewicz
 */
public class TaskLibraryRefRule extends ProcessValidationRule {

    private static final String TASK_LIBRARY_REFERENCE_NOT_SUPPORTED =
            "bx.taksLibraryReferenceNotSupported"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (ReferenceTaskUtil.isTaskLibraryTaskReference(activity)) {
                addIssue(TASK_LIBRARY_REFERENCE_NOT_SUPPORTED, activity);
            }
        }
    }
}
