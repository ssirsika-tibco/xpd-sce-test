/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to remove the Ad-Hoc activity marker.
 * 
 * @author aallway
 * @since 4 Apr 2012
 */
public class RemoveActivityAdHocMarkerResolution extends
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

        // WHEN REINTEGRATING TO 3.6.0 Need to add activityMarker_adHoc.png to
        // icons folder.

        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            ActivitySet activitySet =
                    Xpdl2ModelUtil.getEmbeddedSubProcessActivitySet(activity);

            if (activitySet.isAdHoc()) {
                return SetCommand.create(editingDomain,
                        activitySet,
                        Xpdl2Package.eINSTANCE.getFlowContainer_AdHoc(),
                        Boolean.FALSE);
            }
        }

        return null;
    }

}
