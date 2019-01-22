/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * Process interface invoked from resuable sub-process must have start event
 * type none rule.
 * 
 * @author aallway
 * @since 3.2
 */
public class InvokedInterfaceStartEventRule extends ProcessValidationRule {

    private static final String INVOKED_PROCESSIFC_REQUIRES_SINGLE_STARTTYPE_NONE =
            "bpm.invokedInterfaceRequiresOneStartNone"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (activity.getImplementation() instanceof SubFlow) {
                EObject procOrIfc =
                        TaskObjectUtil.getSubProcessOrInterface(activity);
                if (procOrIfc instanceof ProcessInterface) {
                    EList<StartMethod> starts =
                            ((ProcessInterface) procOrIfc).getStartMethods();

                    int countStartNones = 0;

                    for (StartMethod method : starts) {
                        if (TriggerType.NONE_LITERAL
                                .equals(method.getTrigger())) {
                            countStartNones++;
                        }
                    }

                    if (countStartNones != 1) {
                        addIssue(INVOKED_PROCESSIFC_REQUIRES_SINGLE_STARTTYPE_NONE,
                                activity);
                    }
                }
            }
        }
        
        return;
    }
}
