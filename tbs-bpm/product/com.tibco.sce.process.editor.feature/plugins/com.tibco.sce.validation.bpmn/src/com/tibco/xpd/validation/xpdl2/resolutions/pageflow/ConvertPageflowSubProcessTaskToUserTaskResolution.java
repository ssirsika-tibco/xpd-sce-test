/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions.pageflow;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ConvertPageflowSubProcessTaskToUserTaskResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (16 Nov 2009)
 */
public class ConvertPageflowSubProcessTaskToUserTaskResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            EObject procOrIfc =
                    TaskObjectUtil.getSubProcessOrInterface(activity);
            if (procOrIfc instanceof Process
                    && Xpdl2ModelUtil.isPageflow((Process) procOrIfc)) {
                Process pageflowProcess = (Process) procOrIfc;

                ConvertPageflowTaskToUserTaskCommand cmd =
                        new ConvertPageflowTaskToUserTaskCommand(editingDomain,
                                activity, pageflowProcess);

                return cmd;
            }
        }

        return null;
    }

    private static class ConvertPageflowTaskToUserTaskCommand extends
            CompoundCommand {
        private EditingDomain editingDomain;

        private Process pageflowProcess;

        private Activity activity;

        /**
         * @param editingDomain
         * @param activity
         * @param pageflowProcess
         */
        public ConvertPageflowTaskToUserTaskCommand(
                EditingDomain editingDomain, Activity activity,
                Process pageflowProcess) {
            super();
            this.editingDomain = editingDomain;
            this.activity = activity;
            this.pageflowProcess = pageflowProcess;

            setLabel(Messages.ConvertPageflowSubProcessTaskToUserTaskResolution_ConvertToPageflowUserTask_menu);
        }

        @Override
        public boolean canExecute() {
            return true;
        }

        @Override
        public void execute() {
            appendAndExecute(TaskObjectUtil
                    .getSetTaskTypeCommandEx(editingDomain,
                            activity,
                            TaskType.USER_LITERAL,
                            activity.getProcess(),
                            true,
                            true,
                            true));

            appendAndExecute(TaskObjectUtil
                    .getUserTaskSetPageflowProcessCommand(editingDomain,
                            activity,
                            pageflowProcess));

        }

    }

}
