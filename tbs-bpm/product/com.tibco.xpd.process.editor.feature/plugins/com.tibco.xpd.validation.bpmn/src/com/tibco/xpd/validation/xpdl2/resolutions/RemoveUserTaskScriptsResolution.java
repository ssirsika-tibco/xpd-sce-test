/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;

/**
 * RemoveUserTaskScriptsResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Nov 2009)
 */
public class RemoveUserTaskScriptsResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            UserTaskScripts userTaskScripts =
                    TaskObjectUtil.getUserTaskScripts(activity);
            if (userTaskScripts != null) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.RemoveUserTaskScriptsResolution_RemoveUserTaskScripts_menu);

                if (userTaskScripts.getOpenScript() != null) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            userTaskScripts.getOpenScript()));
                }

                if (userTaskScripts.getCloseScript() != null) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            userTaskScripts.getCloseScript()));
                }

                if (userTaskScripts.getSubmitScript() != null) {
                    cmd.append(RemoveCommand.create(editingDomain,
                            userTaskScripts.getSubmitScript()));
                }

                if (!cmd.isEmpty()) {
                    return cmd;
                }
            }
        }
        return null;
    }

}
