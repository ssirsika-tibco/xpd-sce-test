/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wm.pageflow.internal.Messages;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Validations for task scripts in pageflow.
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Nov 2009)
 */
public class N2PageflowActivityScriptsRules extends ProcessValidationRule {

    private static final String ISSUE_USERTASKSCRIPTS_NOT_SUPPORTED =
            "wm.pageflow.userTaskSupportsOnlyCertainScripts"; //$NON-NLS-1$    

    private Map<Integer, String> auditEventTypeLabelsLookup = null;

    public N2PageflowActivityScriptsRules() {
        super();
        auditEventTypeLabelsLookup = assignAuditEventTypeLabels();
    }

    /**
     * @return
     */
    private Map<Integer, String> assignAuditEventTypeLabels() {

        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(AuditEventType.INITIATED_LITERAL.getValue(),
                Messages.N2PageflowActivityScriptsRules_UserTaskTypeValueInitiated);
        map.put(AuditEventType.COMPLETED_LITERAL.getValue(),
                Messages.N2PageflowActivityScriptsRules_UserTaskTypeValueCompleted);
        map.put(AuditEventType.DEADLINE_EXPIRED_LITERAL.getValue(),
                Messages.N2PageflowActivityScriptsRules_UserTaskTypeValueDeadlineExpired);
        map.put(AuditEventType.CANCELLED_LITERAL.getValue(),
                Messages.N2PageflowActivityScriptsRules_UserTaskTypeValueCancelled);

        return map;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {

            /*
             * SID XPD-1930: All pagelfow task scripts are supported for all
             * activities EXCEPT user-task which supports only Complete script.
             */
            if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {

                final EnumSet<AuditEventType> permittedEvtTypes =
                        EnumSet.of(AuditEventType.COMPLETED_LITERAL,
                                AuditEventType.INITIATED_LITERAL);
                if (isProcessScriptDisallowedForAnyEventType(activity,
                        permittedEvtTypes) || hasUserTaskScripts(activity)) {

                    // Construct arg for issue msg
                    StringBuilder sb = new StringBuilder();
                    final String SEPARATOR = " / "; //$NON-NLS-1$

                    for (AuditEventType permittedType : permittedEvtTypes) {
                        String auditEventTypeLabel =
                                auditEventTypeLabelsLookup.get(permittedType
                                        .getValue());
                        sb.append('\'').append(auditEventTypeLabel)
                                .append('\'').append(SEPARATOR);
                    }
                    sb.setLength(sb.lastIndexOf(SEPARATOR));
                    List<String> args = Arrays.asList(sb.toString());

                    this.addIssue(ISSUE_USERTASKSCRIPTS_NOT_SUPPORTED,
                            activity,
                            args);
                }

            }
        }

        return;
    }

    /**
     * @param activity
     * 
     * @return true if the activity has process flow scripts (other than
     *         Complete script).
     */
    private boolean isProcessScriptDisallowedForAnyEventType(Activity activity,
            EnumSet<AuditEventType> permittedEvtTypes) {

        Audit audit = TaskObjectUtil.getAudit(activity);

        if (audit != null) {

            for (AuditEventType auditEventType : EnumSet
                    .complementOf(permittedEvtTypes)) {
                Expression script =
                        TaskObjectUtil.getAuditScriptExpression(auditEventType,
                                audit);
                if (script != null) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * @param activity
     * 
     * @return true if the user task activity has user task scripts.
     */
    private boolean hasUserTaskScripts(Activity activity) {
        UserTaskScripts userTaskScripts =
                TaskObjectUtil.getUserTaskScripts(activity);

        if (userTaskScripts != null) {
            if (userTaskScripts.getOpenScript() != null
                    || userTaskScripts.getCloseScript() != null
                    || userTaskScripts.getSubmitScript() != null
                    || userTaskScripts.getScheduleScript() != null
                    || userTaskScripts.getRescheduleScript() != null) {
                return true;
            }
        }
        return false;
    }

}
