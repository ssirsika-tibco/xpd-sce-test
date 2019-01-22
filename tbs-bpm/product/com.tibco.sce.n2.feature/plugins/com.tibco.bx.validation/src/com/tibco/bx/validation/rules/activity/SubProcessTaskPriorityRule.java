/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate that sub-process has a validation rule set.
 * 
 * @author aallway
 * @since 26 Oct 2011
 */
public class SubProcessTaskPriorityRule extends ProcessValidationRule {

    private static final String ISSUE_PRIORITY_NOT_DEFINED =
            "bx.subProcesstaskPriorityNotDefined"; //$NON-NLS-1$

    private static final String ISSUE_UNRECOGNISED_PRIORITY =
            "bx.subProcessTaskUnrecognisedPriority"; //$NON-NLS-1$

    private static final String ISSUE_PRIORITY_FIELD_CANT_BE_INLINE =
            "bx.fieldOrParamPriorityCantBeInline"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        /* Priorities are only applicable to Business processes. */
        if (Xpdl2ModelUtil.isBusinessProcess(process)
                || ProcessInterfaceUtil.isProcessEngineServiceProcess(process)) {

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {

                validate(activity);
            }
        }
    }

    /**
     * Apply rules to activity
     * 
     * @param activity
     */
    private void validate(Activity activity) {
        if (TaskType.SUBPROCESS_LITERAL.equals(TaskObjectUtil
                .getTaskTypeStrict(activity))) {

            /*
             * Sid XPD-3445 - Only need to check if there is a priority set if
             * the start type is not start immediately.
             */
            SubFlow subFlow = (SubFlow) activity.getImplementation();

            if (!SubProcessStartStrategy.START_IMMEDIATELY
                    .equals(Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_StartStrategy()))) {

                /*
                 * Check priority set.
                 */
                Priority priority = activity.getPriority();
                if (priority == null) {
                    addIssue(ISSUE_PRIORITY_NOT_DEFINED, activity);

                } else {
                    /*
                     * Check it is one of the recognised settings.
                     */
                    String priorityValue = priority.getValue();
                    if (priorityValue != null && priorityValue.length() != 0) {

                        /*
                         * XPD-5475: Now we allow any value between minimum and
                         * maximum (100 - 400) or "" = server default
                         */
                        try {
                            int intPriority = Integer.parseInt(priorityValue);
                            if (intPriority < 100 || intPriority > 400) {
                                addIssue(ISSUE_UNRECOGNISED_PRIORITY, activity);
                            }

                        } catch (NumberFormatException e) {
                            /*
                             * If it's not a number then check for integer data
                             * field.
                             */
                            List<ProcessRelevantData> dataForActivity =
                                    ProcessInterfaceUtil
                                            .getAllAvailableRelevantDataForActivity(activity);

                            boolean isInt = false;
                            boolean found = false;

                            for (ProcessRelevantData data : dataForActivity) {
                                if (priorityValue.equals(data.getName())) {
                                    found = true;

                                    BasicType type =
                                            BasicTypeConverterFactory.INSTANCE
                                                    .getBasicType(data);

                                    if (type != null
                                            && BasicTypeType.INTEGER_LITERAL
                                                    .equals(type.getType())) {
                                        isInt = true;
                                    }

                                    break;
                                }

                            }

                            if (!found || !isInt) {
                                addIssue(ISSUE_UNRECOGNISED_PRIORITY, activity);
                            }

                            if (found) {
                                /*
                                 * One final check - the field name cannot be
                                 * "inline" as this is a reserved word in the
                                 * BPEL priority used for "start immediately".
                                 */
                                if ("inline".equalsIgnoreCase(priorityValue)) { //$NON-NLS-1$
                                    addIssue(ISSUE_PRIORITY_FIELD_CANT_BE_INLINE,
                                            activity);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

}
