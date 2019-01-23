/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules for reusable sub-process tasks in pageflow processes.
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Nov 2009)
 */
public class PageflowSubProcessRule extends ProcessValidationRule {

    private static final String ISSUE_CANNOT_INVOKE_IFC_WITH_MESSAGEEVENTS =
            "pageflow.cannotInvokeInterfaceWithMessageEvents"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        if (Xpdl2ModelUtil.isPageflow(process)) {

            for (Activity activity : activities) {

                if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))) {
                    EObject subProcessOrInterface =
                            TaskObjectUtil.getSubProcessOrInterface(activity);

                    if (subProcessOrInterface instanceof ProcessInterface) {
                        /*
                         * Can only invoke process interface with no message
                         * events.
                         */
                        ProcessInterface processInterface =
                                (ProcessInterface) subProcessOrInterface;

                        if (hasMessageEvents(processInterface)) {
                            addIssue(ISSUE_CANNOT_INVOKE_IFC_WITH_MESSAGEEVENTS,
                                    activity);
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * @param processInterface
     * @return <code>true</code> if interface contains message events.
     */
    private boolean hasMessageEvents(ProcessInterface processInterface) {
        boolean hasMessageEvents = false;

        for (StartMethod startMethod : processInterface.getStartMethods()) {
            if (TriggerType.MESSAGE_LITERAL.equals(startMethod.getTrigger())) {
                hasMessageEvents = true;
                break;
            }
        }

        if (!hasMessageEvents) {
            for (IntermediateMethod intermediateMethod : processInterface
                    .getIntermediateMethods()) {
                if (TriggerType.MESSAGE_LITERAL.equals(intermediateMethod
                        .getTrigger())) {
                    hasMessageEvents = true;
                    break;
                }
            }
        }
        return hasMessageEvents;
    }
}
