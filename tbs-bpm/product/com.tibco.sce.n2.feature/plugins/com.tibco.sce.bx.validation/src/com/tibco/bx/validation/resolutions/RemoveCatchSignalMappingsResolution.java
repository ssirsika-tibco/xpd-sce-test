/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick fix resolution to remove the data mappings from a catch signal event.
 * 
 * @author aallway
 * @since 15 May 2012
 */
public class RemoveCatchSignalMappingsResolution extends
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

            if (activity.getEvent() != null
                    && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {

                TriggerResultSignal trs =
                        (TriggerResultSignal) activity.getEvent()
                                .getEventTriggerTypeNode();

                SignalData signalData =
                        (SignalData) Xpdl2ModelUtil.getOtherElement(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());

                if (signalData != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.RemoveCatchSignalMappingsResolution_RemoveCatchSignalMappings_menu);

                    /*
                     * Side XPD-4021. There is now more than simply data
                     * mappings stored in xpdExt:SignalData (such as reschedule
                     * timer event settings) therefore remove only the data
                     * mappings themselves.
                     */
                    EList<DataMapping> dataMappings =
                            signalData.getDataMappings();

                    if (dataMappings.size() > 1) {
                        cmd.append(RemoveCommand.create(editingDomain,
                                dataMappings));

                        return cmd;
                    }
                }
            }
        }

        return null;
    }

}
