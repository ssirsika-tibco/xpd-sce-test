/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate issues surrounding data associated with activities in a pageflow.
 * 
 * @author aallway
 * @since 9 May 2011
 */
public class N2PageflowActivityDataAssociationRules extends ProcessValidationRule {

    private static final String ISSUE_EVENTHANDLER_PARAMMODE_INONLY =
            "wm.pageflow.eventHandlerParamModeInOnly"; //$NON-NLS-1$

    private static final String ISSUE_EVENTHANDLER_FORMALPARAMS_ONLY =
            "wm.pageflow.eventHandlerParamsOnly"; //$NON-NLS-1$

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

        for (Activity activity : activities) {

            if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {

                List<AssociatedParameter> activityAssociatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);

                if (!activityAssociatedParameters.isEmpty()) {
                    Map<String, ProcessRelevantData> dataMapForActivity =
                            ProcessInterfaceUtil
                                    .getAllAvailableRelevantDataMapForActivity(activity);

                    boolean hasAssociatedDataFields = false;
                    boolean hasBadModes = false;

                    for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                        ProcessRelevantData data =
                                dataMapForActivity.get(associatedParameter
                                        .getFormalParam());

                        if ((data instanceof DataField)) {
                            hasAssociatedDataFields = true;
                        }

                        ModeType mode = associatedParameter.getMode();
                        if (!ModeType.IN_LITERAL.equals(mode)
                                && !ModeType.INOUT_LITERAL.equals(mode)) {
                            hasBadModes = true;
                        }
                    }

                    if (hasAssociatedDataFields) {
                        addIssue(ISSUE_EVENTHANDLER_FORMALPARAMS_ONLY, activity);
                    }

                    if (hasBadModes) {
                        addIssue(ISSUE_EVENTHANDLER_PARAMMODE_INONLY, activity);
                    }
                }

            }
        }

    }

}
