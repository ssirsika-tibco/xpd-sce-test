/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution for removing the activity scripts that are unsupported for a given
 * activity type.
 * <p>
 * <b>NOTE: </b>Currently this resolution considers ALL activity scripts to be
 * unsupported on ALL activity types. Eventually, these should be updated when
 * N2PE is implemented to match the following criteria...
 * 
 * <pre>
 * Task Script:
 * A task script is a script associated with a task (or event) as part of the task definition rather than as separate Script task. There are 4 types of scripts:
 * •   Initiated – Executed just before the task begins execution.
 * •   Completed – Executed just after the task completes normal execution.
 * •   Cancel – Executed in the event task execution is cancelled while in progress.
 * •   Timeout – This is a special case of cancel, executed in the event task execution is cancelled by the timeout of a timer event on the boundary of the task.
 * 
 * Script execution
 * Which type of task and event can execute which type of script:
 * •   Task – All 4 types of scripts can appear on all tasks. This provides uniformity for the UI, but in reality not all tasks are always capable of executing all script types. Some tasks can not execute a Cancel script as their operation is atomic and therefore can not be cancelled while in progress. When a task is serving as the process start event it can only execute the Competed script.
 * •   Start event - Start events can only execute a Completed script.
 * •   End event – End events can only execute an Initiated script
 * •   Intermediate boundary event – Boundary events, like start events, can only execute a Completed script.
 * •   Intermediate In-Flow Catch event – Intermediate catch events in the flow can execute Initiated, Completed and Cancel scripts.
 * •   Intermediate Throw event – Intermediate throw events can execute Initiated and Completed scripts.
 * 
 * Loops
 * A task may be configured with looping as well as with task scripts. In this case, the task scripts will be executed with each task instance. Task scripts in this case will need access to the loop index counter in order to really be useful, otherwise each script will end up doing exactly the same thing.
 * 
 * Faults
 * Should a task script throw a fault, it will be charged to the associated task
 * </pre>
 * 
 * @author aallway
 * @since 3.3 (14 Jan 2010)
 */
public abstract class AbstractRemoveUnsupportedActivityScriptsResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            CompoundCommand cmd = new LateExecuteCompoundCommand();
            cmd
                    .setLabel(Messages.AbstractRemoveUnsupportActivityScriptsResolution_RemoiveActScripts_menu);

            Collection<AuditEventType> scriptsToRemove =
                    getScriptsToRemove(activity);
            for (AuditEventType toRemove : scriptsToRemove) {
                appendRemoveScriptCommand(editingDomain,
                        activity,
                        toRemove,
                        cmd);
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }
        return null;
    }

    /**
     * Append command to remove script of given kind from activity.
     * 
     * @param editingDomain
     * @param activity
     * @param toRemove
     * @param cmd
     */
    private void appendRemoveScriptCommand(EditingDomain editingDomain,
            Activity activity, AuditEventType toRemove, CompoundCommand cmd) {
        Audit audit =
                (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Audit());
        if (audit != null) {
            for (AuditEvent auditEvent : audit.getAuditEvent()) {
                if (toRemove.equals(auditEvent.getType())) {
                    cmd.append(RemoveCommand.create(editingDomain, auditEvent));
                }
            }
        }

        return;
    }

    /**
     * @param activity
     * @return List of script types to remove.
     */
    protected abstract Collection<AuditEventType> getScriptsToRemove(
            Activity activity);

    public static class RemoveStartAndEventHandlerUnsupportedScriptsResolution extends
            AbstractRemoveUnsupportedActivityScriptsResolution {

        @Override
        protected Collection<AuditEventType> getScriptsToRemove(
                Activity activity) {
            Set<AuditEventType> types = new HashSet<AuditEventType>();
            types.add(AuditEventType.INITIATED_LITERAL);
            types.add(AuditEventType.CANCELLED_LITERAL);
            types.add(AuditEventType.DEADLINE_EXPIRED_LITERAL);

            return types;
        }

    }

    public static class RemoveActivityInitiateScriptResolution extends
            AbstractRemoveUnsupportedActivityScriptsResolution {
        @Override
        protected Collection<AuditEventType> getScriptsToRemove(
                Activity activity) {
            /* Currently only Initiated is supported for activites. */
            List<AuditEventType> types = new ArrayList<AuditEventType>();
            types.add(AuditEventType.INITIATED_LITERAL);
            return types;
        }

    }

    public static class RemoveActivityCompleteScriptResolution extends
            AbstractRemoveUnsupportedActivityScriptsResolution {
        @Override
        protected Collection<AuditEventType> getScriptsToRemove(
                Activity activity) {
            /* Currently only Initiated is supported for activites. */
            Set<AuditEventType> types = new HashSet<AuditEventType>();
            types.add(AuditEventType.COMPLETED_LITERAL);
            return types;
        }

    }

    public static class RemoveActivityCancelScriptResolution extends
            AbstractRemoveUnsupportedActivityScriptsResolution {
        @Override
        protected Collection<AuditEventType> getScriptsToRemove(
                Activity activity) {
            /* Currently only Initiated is supported for activites. */
            Set<AuditEventType> types = new HashSet<AuditEventType>();
            types.add(AuditEventType.CANCELLED_LITERAL);
            return types;
        }

    }

    public static class RemoveActivityTimeoutScriptResolution extends
            AbstractRemoveUnsupportedActivityScriptsResolution {
        @Override
        protected Collection<AuditEventType> getScriptsToRemove(
                Activity activity) {
            /* Currently only Initiated is supported for activites. */
            Set<AuditEventType> types = new HashSet<AuditEventType>();
            types.add(AuditEventType.DEADLINE_EXPIRED_LITERAL);
            return types;
        }

    }

}
