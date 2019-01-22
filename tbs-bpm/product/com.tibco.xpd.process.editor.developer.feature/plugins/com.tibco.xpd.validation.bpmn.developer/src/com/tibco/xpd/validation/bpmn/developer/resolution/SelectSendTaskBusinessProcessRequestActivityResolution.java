/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityPickerDialog;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to Select a request activity for a send task
 * 
 * 
 * @author agondal
 * @since 24 Jan 2013
 */
public class SelectSendTaskBusinessProcessRequestActivityResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {
            CompoundCommand command = new CompoundCommand();
            Activity activity = (Activity) target;
            Activity activityFromPicker =
                    ActivityPickerDialog.getActivityFromPicker(Display
                            .getDefault().getActiveShell(),
                            activity,
                            TaskObjectUtil.getInvokeBusinessProcess(activity));

            if (activityFromPicker != null
                    && activityFromPicker instanceof Activity) {

                /*
                 * generate wsdl info for send task invoking a business process
                 * activity
                 */

                ProcessDeveloperUtil.generateWsdlInfo(editingDomain,
                        command,
                        activityFromPicker.getProcess(),
                        activity,
                        activityFromPicker);
                return command;

            }
        }
        return UnexecutableCommand.INSTANCE;
    }

}
