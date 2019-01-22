/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to set a cancelling timer event as the activity deadline
 * of the Activity it's attached on.
 * 
 * @author njpatel
 */
public class SetDeadlineActivityResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof Activity) {
            Activity cancellingEvent = getCancellingEvent((Activity) target);
            if (cancellingEvent != null) {
                cmd =
                        Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        (Activity) target,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ActivityDeadlineEventId(),
                                        cancellingEvent.getId());
            }
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionLabel(java.lang.String,
     *      org.eclipse.core.resources.IMarker)
     * 
     * @param propertiesLabel
     * @param marker
     * @return
     */
    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String label = super.getResolutionLabel(propertiesLabel, marker);
        if (marker.exists()) {
            try {
                EObject target = getTarget(marker);

                if (target instanceof Activity) {
                    Activity cancellingEvent =
                            getCancellingEvent((Activity) target);
                    if (cancellingEvent != null) {
                        label =
                                String.format(label, Xpdl2ModelUtil
                                        .getDisplayName(cancellingEvent));
                    }
                }
            } catch (CoreException e) {
                // Do nothing
            }
        }
        return label;
    }

    /**
     * Get the first cancelling event attached to the given Task.
     * 
     * @param task
     * @return
     */
    private Activity getCancellingEvent(Activity task) {

        for (Activity attEvent : Xpdl2ModelUtil.getAttachedEvents(task)) {
            if (!EventObjectUtil.isNonCancellingEvent(attEvent)) {
                return attEvent;
            }
        }

        return null;
    }

}
