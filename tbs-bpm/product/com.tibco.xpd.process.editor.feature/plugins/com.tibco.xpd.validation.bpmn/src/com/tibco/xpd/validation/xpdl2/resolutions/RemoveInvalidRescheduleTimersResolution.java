/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;

/**
 * Quick fix resolution to remove timer events explicitly selected for
 * reschedule in a signal event that no longer exist or are not timer events
 * attached to same task.
 * 
 * @author aallway
 * @since 14 Mar 2013
 */
public class RemoveInvalidRescheduleTimersResolution extends
        AbstractWorkingCopyResolution implements IResolution {

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
            /*
             * Get the reschedule timers element.
             */
            Activity signalEvent = (Activity) target;

            SignalData signalData =
                    EventObjectUtil.getSignalDataExtensionElement(signalEvent);

            if (signalData != null) {
                RescheduleTimers rescheduleTimers =
                        signalData.getRescheduleTimers();

                if (rescheduleTimers != null) {
                    CompoundCommand cmd = new LateExecuteCompoundCommand();

                    cmd.setLabel(Messages.DontRescheduleTimersResolution_UnsetRescheduleTimers_menu);

                    /* Check for invalid reschedule timer events. */
                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(signalEvent);

                    for (ActivityRef timerEventRef : rescheduleTimers
                            .getTimerEvents()) {
                        Activity timerEvent = timerEventRef.getActivity();

                        /* must still exist... */
                        if (timerEvent == null) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    timerEventRef));
                        }
                        /* Must still be a timer trigger */
                        else if (!EventTriggerType.EVENT_TIMER_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(timerEvent))) {
                            cmd.append(RemoveCommand.create(editingDomain,
                                    timerEventRef));
                        }
                        /* Must still be attached to same task as signal */
                        else if (taskAttachedTo != null) {
                            if (!taskAttachedTo.equals(EventObjectUtil
                                    .getTaskAttachedTo(timerEvent))) {
                                cmd.append(RemoveCommand.create(editingDomain,
                                        timerEventRef));
                            }
                        }
                    }

                    if (!cmd.isEmpty()) {
                        return cmd;
                    }
                }
            }
        }
        return null;
    }

}
