/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to remove acitivity refs to activities that no longer exist from
 * Separation of Duty / Retain Familiar groups.
 * 
 * @author aallway
 * @since 18 Nov 2013
 */
public class RemoveDeletedTasksFromGroupResolution extends
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
        if (target instanceof SeparationOfDutiesActivities) {
            SeparationOfDutiesActivities separationOfDutiesActivities =
                    (SeparationOfDutiesActivities) target;

            return getRemoveUnknownRefsCommand(editingDomain,
                    separationOfDutiesActivities.getActivityRef());

        } else if (target instanceof RetainFamiliarActivities) {
            RetainFamiliarActivities retainFamiliarActivities =
                    (RetainFamiliarActivities) target;

            return getRemoveUnknownRefsCommand(editingDomain,
                    retainFamiliarActivities.getActivityRef());

        }

        return null;
    }

    /**
     * Remove acitivity refs to activities that no longer exist from Separation
     * of Duty / Retain Familiar groups.
     * 
     * @param editingDomain
     * @param process
     * @param activityRefs
     * @return
     */
    private Command getRemoveUnknownRefsCommand(EditingDomain editingDomain,
            final EList<ActivityRef> activityRefs) {

        RecordingCommand cmd =
                new RecordingCommand(
                        (TransactionalEditingDomain) editingDomain,
                        Messages.RemoveDeletedTasksFromGroupResolution_RemoveDeletedTasksFromTaskGroup_menu) {

                    @Override
                    protected void doExecute() {
                        for (Iterator iterator = activityRefs.iterator(); iterator
                                .hasNext();) {
                            ActivityRef activityRef =
                                    (ActivityRef) iterator.next();

                            Activity activity = activityRef.getActivity();

                            if (activity == null) {
                                iterator.remove();
                            }
                        }
                    }
                };

        return cmd;
    }

}
