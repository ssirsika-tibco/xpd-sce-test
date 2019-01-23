/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.resolutions;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wm.pageflow.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Set all data associated with targert activity to mode IN unless it is already
 * IN or INOUT.
 * 
 * @author aallway
 * @since 9 May 2011
 */
public class SetAssociatedDataToInModeResolution extends
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

            List<AssociatedParameter> activityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);

            Map<String, ProcessRelevantData> dataMapForActivity =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataMapForActivity(activity);

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.SetAssociatedDataToInModeResolution_SetAssocDataToInMode_menu);
            for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                ModeType mode = associatedParameter.getMode();
                if (!ModeType.IN_LITERAL.equals(mode)
                        && !ModeType.INOUT_LITERAL.equals(mode)) {
                    cmd.append(SetCommand.create(editingDomain,
                            associatedParameter,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParameter_Mode(),
                            ModeType.IN_LITERAL));
                }
            }

            if (!cmd.isEmpty()) {
                return cmd;
            }
        }
        return null;
    }

}
