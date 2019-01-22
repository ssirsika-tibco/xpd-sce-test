/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BpmSubProcessRule - raise a problem when a business process invokes a
 * pageflow process from sub-proc task
 * 
 * @author aallway
 * @since 3.3 (16 Nov 2009)
 * @deprecated - The issue from this rule is being raised from
 *             CallSubProcessRule for Process Manager destination
 */
@Deprecated
public class BpmSubProcessRule extends ProcessValidationRule {

    private static final String ISSUE_CANNOT_INVOKE_PAGEFLOW_PROCESS =
            "bpmn.cannotInvokePageflowProcess"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        if (Xpdl2ModelUtil.isBusinessProcess(process)) {

            for (Activity activity : activities) {

                if (activity.getImplementation() instanceof SubFlow) {

                    EObject subProcessOrInterface =
                            TaskObjectUtil.getSubProcessOrInterface(activity);
                    if (subProcessOrInterface instanceof Process
                            && Xpdl2ModelUtil
                                    .isPageflow((Process) subProcessOrInterface)) {

                        addIssue(ISSUE_CANNOT_INVOKE_PAGEFLOW_PROCESS, activity);
                    }
                }
            }
        }
        return;
    }
}
