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
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdl2.Activity;

/**
 * RemoveUserTaskScriptsResolution
 * 
 * 
 * @author aallway
 * @since 3.3 (12 Nov 2009)
 */
public class RemoveActivityScriptsResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            /*
             * Sid XPD-1930: RemvoeActivityScript should do what it says, not
             * just remove all if there are usertaskscripts defined.
             * 
             * (remove condition on whether there are user task scripts.
             */

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.RemoveActivityScriptsResolution_RemiveActScripts_menu);

            Audit audit = TaskObjectUtil.getAudit(activity);

            if (audit != null) {
                for (AuditEventType auditEventType : AuditEventType.values()) {
                    AuditEvent auditEvent =
                            TaskObjectUtil.getAuditEvent(auditEventType, audit);

                    if (auditEvent != null) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                auditEvent));
                    }
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }

        return null;
    }

}
