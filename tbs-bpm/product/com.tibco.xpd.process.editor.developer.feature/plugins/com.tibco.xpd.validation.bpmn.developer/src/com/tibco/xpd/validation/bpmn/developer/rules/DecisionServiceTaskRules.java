/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * Variosu decision service task validation rules.
 * 
 * @author aallway
 * @since 3 Aug 2011
 */
public class DecisionServiceTaskRules extends ProcessActivitiesValidationRule {

    private static final String ISSUE_DECISIONFLOW_REFERENCE_NOT_DEFINED =
            "bpmn.dev.decisionServiceTaskMustReferenceDecisionFlow"; //$NON-NLS-1$

    private static final String ISSUE_DECISIONFLOW_REFERENCE_CANT_ACCESS =
            "bpmn.dev.referencedDecisionServiceDoesNotExist"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        if (DecisionTaskObjectUtil.isDecisionServiceTask(activity)) {

            /*
             * First confirm that the decision flow reference is defined in the
             * model.
             */
            boolean isDefined = false;
            SubFlow decisionFlowReference =
                    DecisionTaskObjectUtil.getDecisionFlowReference(activity);

            if (decisionFlowReference != null) {
                String processId = decisionFlowReference.getProcessId();

                if (processId != null && processId.length() > 0) {
                    isDefined = true;
                }
            }

            if (!isDefined) {
                addIssue(ISSUE_DECISIONFLOW_REFERENCE_NOT_DEFINED, activity);

            } else {
                /*
                 * Ok, it's defined so lets check the other stuff.
                 * 
                 * Like can we access the referenced decision flow.
                 */
                EObject decisionFlow =
                        DecisionTaskObjectUtil.getDecisionFlow(activity);

                if (decisionFlow == null) {
                    addIssue(ISSUE_DECISIONFLOW_REFERENCE_CANT_ACCESS, activity);
                }
            }

        }
    }
}
