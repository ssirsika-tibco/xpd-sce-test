/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule for event that implements a proc ifc event. Test case
 * scenario
 * 
 * Create a proc ifc, with start msg event. create a proc implementing the ifc.
 * rename the start msg event in the ifc
 * 
 * 
 * The proc event port type doesn't get updated which is validated in this rule.
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (6 Apr 2010)
 */
public class ImplementingEventValidatePortType extends ProcessValidationRule {

    private static final String INVALID_PORTTYPE =
            "bpmn.dev.invalidPorttypeConfigured"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            if (ProcessInterfaceUtil.isEventImplemented(activity)
                    && ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                InterfaceMethod implementedMethod =
                        ProcessInterfaceUtil.getImplementedMethod(activity);
                if (null != implementedMethod) {
                    ProcessInterface processInterface =
                            ProcessInterfaceUtil
                                    .getProcessInterface(implementedMethod);
                    PortTypeOperation portTypeOperation =
                            getPortTypeOperation(activity);
                    if (null != portTypeOperation) {
                        if (!(portTypeOperation.getPortTypeName()
                                .equals(processInterface.getName()))) {
                            // add issue that the web service operation
                            // configured is invalid.
                            addIssue(INVALID_PORTTYPE, activity);
                        } else if (!(portTypeOperation.getOperationName()
                                .equals(implementedMethod.getName()))) {
                            // add issue that the operation name is configured
                            addIssue(INVALID_PORTTYPE, activity);
                        }
                    }
                }
            }
        }

    }

    /**
     * @param activity
     * @return
     */
    private PortTypeOperation getPortTypeOperation(Activity activity) {

        TriggerResultMessage triggerResultMessage =
                EventObjectUtil.getTriggerResultMessage(activity);
        if (null != triggerResultMessage) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
            if (otherElement instanceof PortTypeOperation) {
                return (PortTypeOperation) otherElement;
            }
        }
        return null;
    }

}
