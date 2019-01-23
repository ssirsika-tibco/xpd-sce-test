/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Quick fix to set signal event reschedule timers selection to None.
 * 
 * @author aallway
 * @since 14 Mar 2013
 */
public class DontRescheduleTimersResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    public DontRescheduleTimersResolution() {
    }

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
             * To set to 'don't reschedule timers, all we have to do is remove
             * the xpdlExt:SignalData/xpdExt:RescheduleTimers script.
             */
            SignalData signalData =
                    EventObjectUtil
                            .getSignalDataExtensionElement((Activity) target);

            if (signalData != null) {
                RescheduleTimers rescheduleTimers =
                        signalData.getRescheduleTimers();

                if (rescheduleTimers != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.DontRescheduleTimersResolution_UnsetRescheduleTimers_menu);

                    cmd.append(SetCommand.create(editingDomain,
                            signalData,
                            XpdExtensionPackage.eINSTANCE
                                    .getSignalData_RescheduleTimers(),
                            SetCommand.UNSET_VALUE));

                    return cmd;
                }
            }
        }
        return null;
    }

}
