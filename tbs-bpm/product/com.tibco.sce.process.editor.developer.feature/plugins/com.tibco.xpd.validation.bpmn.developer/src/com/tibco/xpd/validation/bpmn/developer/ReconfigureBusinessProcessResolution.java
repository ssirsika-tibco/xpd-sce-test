/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.WebServiceOperationUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This resolution is designed specifically to fix the xpdl2:WebServiceOperation
 * and xpdExt:PortTypeOperation so that they are as they should be for the VALID
 * existing business process selected in a business process service send task.
 * 
 * @author aallway
 * @since 3.3 (6 May 2010)
 */
public class ReconfigureBusinessProcessResolution extends
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
            Activity activity = (Activity) target;

            if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {

                if (WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(activity)) {

                    EObject p =
                            TaskObjectUtil.getSubProcessOrInterface(activity);
                    if (p instanceof Process) {
                        Process process = (Process) p;

                        BusinessProcess businessProcess =
                                WebServiceOperationUtil
                                        .getBusinessProcess(activity);

                        Activity reqActivity =
                                Xpdl2ModelUtil.getActivityById(process,
                                        businessProcess.getActivityId());

                        return new ReconfigureBusinessProcessService(
                                editingDomain, activity, reqActivity);

                    }
                }
            }

        }
        return null;
    }

    private static class ReconfigureBusinessProcessService extends
            CompoundCommand {
        private EditingDomain editingDomain;

        private Activity activity;

        private Activity reqActivity;

        /**
         * @param editingDomain
         * @param activity
         * @param bizProcess
         */
        public ReconfigureBusinessProcessService(EditingDomain editingDomain,
                Activity activity, Activity reqActivity) {
            super();
            this.editingDomain = editingDomain;
            this.activity = activity;
            this.reqActivity = reqActivity;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
         */
        @Override
        public boolean canExecute() {
            if (TaskType.SEND_LITERAL.equals(TaskObjectUtil
                    .getTaskTypeStrict(activity))) {

                if (WebServiceOperationUtil
                        .isInvokeBusinessProcessImplementationType(activity)) {

                    EObject p =
                            TaskObjectUtil.getSubProcessOrInterface(activity);
                    if (p instanceof Process) {

                        Process process = (Process) p;

                        BusinessProcess businessProcess =
                                WebServiceOperationUtil
                                        .getBusinessProcess(activity);

                        Activity reqActivity =
                                Xpdl2ModelUtil.getActivityById(process,
                                        businessProcess.getActivityId());

                        if (reqActivity != null) {

                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public void execute() {

            TaskSend taskSend =
                    ((Task) activity.getImplementation()).getTaskSend();
            Message message = taskSend.getMessage();
            EObject copyMessage = EcoreUtil.copy(message);

            appendAndExecute(ProcessDeveloperUtil
                    .getSetBusinessProcessCommand(editingDomain,
                            activity,
                            reqActivity));

            /*
             * Reget taskSend because setBusinessPorcssCommand may overwrite the
             * original.
             */
            taskSend = ((Task) activity.getImplementation()).getTaskSend();
            appendAndExecute(SetCommand.create(editingDomain,
                    taskSend,
                    Xpdl2Package.eINSTANCE.getTaskSend_Message(),
                    copyMessage));
            return;
        }
    }
}
