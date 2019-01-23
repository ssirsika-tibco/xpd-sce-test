/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Checks to see if all sub process references to other processes are still
 * available and returns an error if the reference process has been removed.
 * 
 * @author glewis
 */
public class UnresolvedProcessRule extends ProcessValidationRule {

    /** The issue ID. */
    public static final String UNRESOLVED_ID = "bpmn.unresolvedProcess"; //$NON-NLS-1$

    private static final String DEFAULT_SUBPROCID = "-unknown-"; //$NON-NLS-1$

    private static final String UNDEFINED = "UNDEFINED"; //$NON-NLS-1$

    private static final String THE_SAME_PACKAGE = "THE_SAME_PACKAGE"; //$NON-NLS-1$

    private static final String ID = "bpmn.nonExistentInterfaceFormalParam"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity act : activities) {
            Implementation implementation = act.getImplementation();
            if (implementation instanceof SubFlow) {
                String subProcId = getSubProcessId(act);
                if (subProcId != null && subProcId.length() > 0) {
                    //
                    // A sub-process is defined in the model.
                    //
                    EObject subproc =
                            TaskObjectUtil.getSubProcessOrInterface(act);

                    if (subproc == null) {
                        // Cannot access the referenced sub-process.
                        addIssue(UNRESOLVED_ID, act);
                    }
                }
            }
        }
        validate(process);
    }

    @Override
    public void validate(Process process) {
        if (ProcessInterfaceUtil
                .isUnresolvedImplementedProcessInterface(process)) {
            // Cannot access the referenced process interface.
            addIssue(UNRESOLVED_ID, process);
            // MR 37536 implementation
            // getting all the activities in a process
            Collection<Activity> activityList =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            // for each activity in the process getting all the associated
            // parameters
            for (Activity activity : activityList) {
                List<AssociatedParameter> activityAssociatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                // for each associated parameter, checking if it is resolved for
                // the deleted interface
                for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                    ProcessRelevantData processRelevantDataFromAssociatedParam =
                            ProcessInterfaceUtil
                                    .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                    if (null == processRelevantDataFromAssociatedParam && null != associatedParameter) {
                        // add an issue if it is not resolved
                        List<String> messages = new ArrayList<String>();
                        messages.add(associatedParameter.getFormalParam());
                        addIssue(ID, associatedParameter,messages);
                    }
                }
            }
        }
    }

    /**
     * @param act
     * @return
     */
    private String getSubProcessId(Activity act) {
        String processId = null;
        if (act != null) {
            Implementation implementation = act.getImplementation();
            if (implementation instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) implementation;
                processId = subFlow.getProcessId();
                if (processId != null && processId.equals(DEFAULT_SUBPROCID)) {
                    processId = null;
                }
            }
        }
        return processId;
    }

}
