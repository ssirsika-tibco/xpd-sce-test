/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.decisions.actions;

import org.eclipse.jface.action.IAction;

import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * Action to open the decision flow for the selected decisaion service task in
 * BPM process.
 * 
 * @author aallway
 * @since 11 Aug 2011
 */
public class OpenDecisionFlowAction extends AbstractDecisionServiceTaskAction {

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {
        if (getSelectedDecisionServiceTask() != null) {
            DecisionTaskObjectUtil
                    .openDecisionFlowEditor(getSelectedDecisionServiceTask());
        }
    }

    /**
     * @see com.tibco.xpd.implementer.nativeservices.decisions.internal.actions.AbstractDecisionServiceTaskAction#shouldEnable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param selectedDecisionServiceTask
     * @return
     */
    @Override
    protected boolean shouldEnable(Activity selectedDecisionServiceTask) {
        /*
         * Only enable open if the decision flow reference is set.
         */
        SubFlow decisionServiceExt =
                DecisionFlowUtil
                        .getDecisionServiceExt(selectedDecisionServiceTask);

        if (decisionServiceExt != null) {
            String id = decisionServiceExt.getProcessId();

            if (id != null && id.length() > 0) {
                return true;
            }
        }

        return false;
    }

}
