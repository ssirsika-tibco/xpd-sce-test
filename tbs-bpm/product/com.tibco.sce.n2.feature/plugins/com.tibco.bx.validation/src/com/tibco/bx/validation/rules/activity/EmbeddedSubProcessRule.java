/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. Participants are not supported for embedded sub-processes.
 * 
 * 2. Transaction flag not supported for embedded sub-processes and re-usable
 * sub-processes
 * 
 * @author aallway
 * @since 3.3 (17 Mar 2010)
 */
public class EmbeddedSubProcessRule extends FlowContainerValidationRule {

    private static final String ISSUE_EMBEDDED_SUB_PROC_PARTICIPANT_NOT_SUPPORTED =
            "bx.embeddedSubProcParticipantNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_EMBEDDED_SUB_PROC_TRANSACTION_NOT_SUPPORTED =
            "bx.embeddedSubProcTransactionNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_REUSABLE_SUB_PROC_TRANSACTION_NOT_SUPPORTED =
            "bx.reusableSubProcTransactionNotSupported"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate
     * (com.tibco.xpd.xpdl2.FlowContainer)
     */
    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {
            if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {
                EObject[] activityPerformers =
                        TaskObjectUtil.getActivityPerformers(activity);
                if (activityPerformers.length > 0) {
                    addIssue(ISSUE_EMBEDDED_SUB_PROC_PARTICIPANT_NOT_SUPPORTED,
                            activity);
                }
                Object otherAttribute =
                        Xpdl2ModelUtil
                                .getOtherAttribute(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_RequireNewTransaction());
                if (Boolean.TRUE.equals(otherAttribute)) {
                    addIssue(ISSUE_EMBEDDED_SUB_PROC_TRANSACTION_NOT_SUPPORTED,
                            activity);
                }
            }
            if (activity.getImplementation() instanceof SubFlow) {
                if (Boolean.TRUE.equals(activity.isIsATransaction())) {
                    addIssue(ISSUE_REUSABLE_SUB_PROC_TRANSACTION_NOT_SUPPORTED,
                            activity);
                }
            }
        }
        return;
    }

}
