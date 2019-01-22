/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author Miguel Torres
 */
public class IntermediateEventParametersRule extends
        FlowContainerValidationRule {

    /** Invalid output parameters mapped issue ID. */
    private static final String INVALID_OUTPUT_PARAMETERS_MAPPED =
            "bpmn.interEventInvalidOutputParametersMapped"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        for (Object next : process.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                // Only check parameters for intermediate events
                // of trigger type none or message
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                TriggerType trigger = intermediateEvent.getTrigger();
                if (trigger != null) {
                    if (TriggerType.MESSAGE_LITERAL.equals(trigger)) {
                        checkMappedParameters(activity);
                    }
                }
            }
        }
    }

    private void checkMappedParameters(Activity activity) {
        if (activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_AssociatedParameters().getName()) != null) {
            AssociatedParameters associatedParameters =
                    (AssociatedParameters) activity
                            .getOtherElement(XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_AssociatedParameters()
                                    .getName());
            if (associatedParameters != null) {
                EList parametersList =
                        associatedParameters.getAssociatedParameter();
                if (parametersList != null) {
                    // Get a list of the formal parameters for the process
                    Process process = Xpdl2ModelUtil.getProcess(activity);
                    if (process != null) {
                        for (Iterator iterator = parametersList.iterator(); iterator
                                .hasNext();) {
                            Object obj = (Object) iterator.next();
                            if (obj instanceof AssociatedParameter) {
                                AssociatedParameter parameter =
                                        (AssociatedParameter) obj;
                                if (parameter != null) {
                                    ModeType mode =
                                            ProcessInterfaceUtil
                                                    .getAssocParamModeType(parameter);
                                    if (mode != null
                                            && mode.getName() != null
                                            && mode
                                                    .getName()
                                                    .equals(ModeType.OUT_LITERAL
                                                            .getName())) {
                                        addIssue(INVALID_OUTPUT_PARAMETERS_MAPPED,
                                                activity);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
