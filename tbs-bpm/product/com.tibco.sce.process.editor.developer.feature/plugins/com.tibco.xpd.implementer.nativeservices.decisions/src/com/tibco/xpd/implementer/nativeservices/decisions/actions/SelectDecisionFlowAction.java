/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.decisions.actions;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;

import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Action to allow the user to select a decision flow for decision service task
 * 
 * @author aallway
 * @since 11 Aug 2011
 */
public class SelectDecisionFlowAction extends AbstractDecisionServiceTaskAction {

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {
        Activity selectedDecisionServiceTask = getSelectedDecisionServiceTask();

        if (selectedDecisionServiceTask != null) {

            EditingDomain editingDomain =
                    WorkingCopyUtil
                            .getEditingDomain(selectedDecisionServiceTask);

            if (editingDomain != null) {
                EObject decisionFlow =
                        DecisionTaskObjectUtil
                                .getDecisionFlowFromPicker(selectedDecisionServiceTask);

                if (decisionFlow != null) {
                    Command cmd =
                            DecisionTaskObjectUtil
                                    .getSetDecFlowCommand(editingDomain,
                                            selectedDecisionServiceTask,
                                            decisionFlow);

                    if (cmd != null && cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
            }
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
        /* Should always be able to reselect the decision flow. */
        return true;
    }

}
