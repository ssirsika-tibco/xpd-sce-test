/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DecisionFlowFilterPicker.DecisionFlowPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.DecisionTaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;

/**
 * Selet decision flow for target decision service task.
 * 
 * @author aallway
 * @since 3 Aug 2011
 */
public class SelectDecisionFlowResolution extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            if (DecisionTaskObjectUtil.isDecisionServiceTask(activity)) {
                EObject decisionFlow = getDecisionFlowFromPicker(activity);
                if (decisionFlow != null) {
                    return DecisionTaskObjectUtil
                            .getSetDecFlowCommand(editingDomain,
                                    activity,
                                    decisionFlow);
                }
            }
        }
        return null;
    }

    /**
     * Get the process from a process picker displayed to user
     * 
     * @return
     */
    private EObject getDecisionFlowFromPicker(Activity activity) {
        EObject decisionFlow = null;

        if (activity != null) {
            DecisionFlowFilterPicker picker =
                    new DecisionFlowFilterPicker(Display.getDefault()
                            .getActiveShell(),
                            DecisionFlowPickerType.DECISIONFLOW, false);

            Object curSel = DecisionTaskObjectUtil.getDecisionFlow(activity);
            if (curSel != null) {
                picker.setInitialElementSelections(Collections
                        .singletonList(curSel));
            }

            decisionFlow =
                    ProcessUIUtil.getResultFromPicker(picker, Display
                            .getDefault().getActiveShell(), activity);
        }

        return decisionFlow;
    }
}
