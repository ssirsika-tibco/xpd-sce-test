/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author bharge
 * 
 */
public class RemoveCompensationReference extends AbstractWorkingCopyResolution {

    /** Remove Compensation Reference command name */
    private static final String COMMAND = "removeCompensationReferenceCommand"; //$NON-NLS-1$

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
        CompoundCommand cCmd =
                new CompoundCommand(ResolutionMessages.getText(COMMAND));

        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            Event event = activity.getEvent();
            TriggerResultCompensation resultCompensation = null;
            if (event instanceof EndEvent) {
                resultCompensation =
                        ((EndEvent) event).getTriggerResultCompensation();
            } else if (event instanceof IntermediateEvent) {
                resultCompensation =
                        ((IntermediateEvent) event)
                                .getTriggerResultCompensation();
            }
            if (null != resultCompensation
                    && null != resultCompensation.getActivityId()) {
                cCmd.setLabel(Messages.SetCompensationEventName_label);
                cCmd.append(SetCommand.create(editingDomain,
                        resultCompensation,
                        Xpdl2Package.eINSTANCE
                                .getTriggerResultCompensation_ActivityId(),
                        SetCommand.UNSET_VALUE));
            }
        }

        return cCmd;
    }

}
