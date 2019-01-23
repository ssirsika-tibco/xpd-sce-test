/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import java.util.EnumSet;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wm.pageflow.internal.Messages;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Remove any task script from the target user task that is not supported
 * 
 * @author aallway
 * @since 6 Jul 2011
 */
public class RemoveInvalidUserTaskScriptsResolution extends
        AbstractWorkingCopyResolution {

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

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.RemoveInvalidUserTaskScriptsResolution_RemoveUnsupprotedUserTaskScripts_menu);

            Audit audit = TaskObjectUtil.getAudit(activity);

            if (audit != null) {
                final EnumSet<AuditEventType> permittedEvtTypes =
                    EnumSet.of(AuditEventType.COMPLETED_LITERAL,
                            AuditEventType.INITIATED_LITERAL);
                
                for (AuditEventType auditEventType : AuditEventType.values()) {
                    /*
                     * Currently, only Complete script is supported in pageflwo
                     * user task.
                     */
                    if (!permittedEvtTypes.contains(auditEventType)) {
                        AuditEvent auditEvent =
                                TaskObjectUtil.getAuditEvent(auditEventType,
                                        audit);

                        if (auditEvent != null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    auditEvent));
                        }
                    }
                }

                UserTaskScripts userTaskScripts =
                        TaskObjectUtil.getUserTaskScripts(activity);
                if (userTaskScripts != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    ((Task) activity.getImplementation())
                                            .getTaskUser(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_UserTaskScripts(),
                                    userTaskScripts));
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }

        }
        return null;
    }
}
