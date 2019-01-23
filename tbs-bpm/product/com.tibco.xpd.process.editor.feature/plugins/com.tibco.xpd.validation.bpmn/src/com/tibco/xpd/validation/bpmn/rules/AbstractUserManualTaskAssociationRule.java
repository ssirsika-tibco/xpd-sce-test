/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check that read only data associated with user / manual task has mode
 * set to IN (read only data cannot be OUTput from a user task form.
 * 
 * @author aallway
 * @since 3.2
 */
public abstract class AbstractUserManualTaskAssociationRule extends
        ProcessValidationRule {

    public static final String EXTRA_INFO_PARAMNAME = "EXTRA_INFO_PARAMNAME"; //$NON-NLS-1$

    private static final String FIELD_READONLY_ISSUE =
            "bpmn.readOnlyFieldIssue"; //$NON-NLS-1$

    /**
     * Add issue that given user/manual task activity has given read only data
     * associated with output mode.
     * 
     * @param activity
     * @param data
     * @param extraIssueInfo
     *            This data MUST be included in the added issue.
     */
    protected abstract void addReadOnlyDataMustBeModeInIssue(Activity activity,
            ProcessRelevantData data, Map<String, String> extraIssueInfo);

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Collection<Activity> activityList =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : activityList) {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (TaskType.USER_LITERAL.equals(taskType)
                    || TaskType.MANUAL_LITERAL.equals(taskType)) {
                List<AssociatedParameter> activityAssociatedParameters =
                        ProcessInterfaceUtil
                                .getActivityAssociatedParameters(activity);
                for (AssociatedParameter assocParam : activityAssociatedParameters) {
                    ProcessRelevantData processRelevantDataFromAssociatedParam =
                            ProcessInterfaceUtil
                                    .getProcessRelevantDataFromAssociatedParam(assocParam);
                    if (null != processRelevantDataFromAssociatedParam
                            && processRelevantDataFromAssociatedParam
                                    .isReadOnly()) {
                        if (!(ModeType.IN_LITERAL.equals(assocParam.getMode()))) {
                            Map<String, String> extraInfo =
                                    new HashMap<String, String>();
                            extraInfo.put(EXTRA_INFO_PARAMNAME,
                                    assocParam.getFormalParam());

                            addReadOnlyDataMustBeModeInIssue(activity,
                                    processRelevantDataFromAssociatedParam,
                                    extraInfo);
                        }
                    }
                }

                // XPD-819
                // if the implicitly associated parameter(s) is read only then
                // the mode must be IN else raise issue
                if (activityAssociatedParameters.size() == 0) {
                    /*
                     * Sid XPD-2087: Only use all data implicitly if implicit
                     * association has not been disabled.
                     */
                    if (!ProcessInterfaceUtil
                            .isImplicitAssociationDisabled(activity)) {
                        for (FormalParameter param : process
                                .getFormalParameters()) {
                            ModeType mode = param.getMode();
                            if (param.isReadOnly()) {
                                if (ModeType.INOUT_LITERAL.equals(mode)
                                        || ModeType.OUT_LITERAL.equals(mode)) {
                                    addIssue(FIELD_READONLY_ISSUE,
                                            param,
                                            Collections
                                                    .singletonList(Xpdl2ModelUtil
                                                            .getDisplayNameOrName(param)));
                                }
                            }
                        }
                    }
                }
                // XPD-819
            }
        }
    }

}
