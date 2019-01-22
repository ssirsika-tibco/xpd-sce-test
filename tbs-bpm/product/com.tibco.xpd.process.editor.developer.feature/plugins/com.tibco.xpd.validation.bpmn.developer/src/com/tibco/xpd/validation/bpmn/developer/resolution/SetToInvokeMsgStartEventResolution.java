/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to Set the request activity for a send task. This resolution
 * should only be used when the referenced process contains Single Start Event.
 * 
 * 
 * @author aprasad
 * @since 14 June 2013
 */
public class SetToInvokeMsgStartEventResolution extends
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

        if (target instanceof Activity
                && TaskObjectUtil.getTaskTypeStrict((Activity) target) == TaskType.SEND_LITERAL) {
            CompoundCommand command = new CompoundCommand(Messages.SetToInvokeMsgStartEventResolution_menu);
            Activity sendTaskActivity = (Activity) target;
            // get list of start activities in the process.
            List<Activity> invokeBusinessProcessStartActivities =
                    TaskObjectUtil
                            .getInvokeBusinessProcessStartActivities(TaskObjectUtil
                                    .getInvokeBusinessProcess(sendTaskActivity));
            // if there is only one start event activity , set this activity.
            if (invokeBusinessProcessStartActivities != null
                    && invokeBusinessProcessStartActivities.size() == 1) {
                Activity requestActivity =
                        invokeBusinessProcessStartActivities.get(0);
                if (requestActivity != null
                        && requestActivity instanceof Activity) {
                    /*
                     * generate wsdl info for send task invoking a business
                     * process activity
                     */
                    ProcessDeveloperUtil.generateWsdlInfo(editingDomain,
                            command,
                            requestActivity.getProcess(),
                            sendTaskActivity,
                            requestActivity);
                    return command;

                }
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

}
