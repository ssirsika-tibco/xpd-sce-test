/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.TriggerResultSignal;

/**
 * Resolution to set the type of catch signal event to 'Local Signal'.
 * 
 * @author sajain
 * @since Mar 20, 2015
 */
public class SwitchToLocalSignalTypeResolution extends
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

        /*
         * Target must be an activity.
         */
        if (target instanceof Activity) {

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.SwithToLocalSignalResolution_Command_Label);

            Activity activity = (Activity) target;

            Event event = activity.getEvent();

            /*
             * Activity must be an event.
             */
            if (event != null) {

                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

                /*
                 * Event must be a signal event.
                 */
                if (eventTriggerTypeNode instanceof TriggerResultSignal) {

                    TriggerResultSignal signal =
                            (TriggerResultSignal) eventTriggerTypeNode;

                    /*
                     * Command to set the signal type to local.
                     */
                    cmd.append(SetCommand.create(editingDomain,
                            signal,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SignalType(),
                            SignalType.LOCAL));

                    /*
                     * Check if signal name is not null.
                     */
                    if (signal.getName() != null) {

                        /*
                         * remove the signal name as the signal type is changed.
                         */
                        cmd.append(EventObjectUtil
                                .getSetSignalNameCommand(editingDomain,
                                        activity,
                                        null));
                    }
                    return cmd;
                }
            }
        }

        return null;
    }

}