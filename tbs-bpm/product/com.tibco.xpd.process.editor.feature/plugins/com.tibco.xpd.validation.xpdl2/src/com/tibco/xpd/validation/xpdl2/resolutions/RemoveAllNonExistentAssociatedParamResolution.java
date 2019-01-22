/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class RemoveAllNonExistentAssociatedParamResolution extends
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
        if (target instanceof AssociatedParameter) {
            AssociatedParameter associatedParam = (AssociatedParameter) target;
            Activity activity = Xpdl2ModelUtil.getParentActivity(associatedParam);
            List<AssociatedParameter> parametersList =
                    new ArrayList<AssociatedParameter>();
            List<AssociatedParameter> activityAssociatedParameters =
                    ProcessInterfaceUtil
                            .getActivityAssociatedParameters(activity);
            for (AssociatedParameter associatedParameter : activityAssociatedParameters) {
                ProcessRelevantData processRelevantDataFromAssociatedParam =
                        ProcessInterfaceUtil
                                .getProcessRelevantDataFromAssociatedParam(associatedParameter);
                if (null == processRelevantDataFromAssociatedParam
                        && null != associatedParameter) {
                    parametersList.add(associatedParameter);
                }
            }
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.DeleteNonExistentAssociatedParameter);
            cmd.append(RemoveCommand.create(editingDomain, parametersList));
            return cmd;
        }
        return null;
    }
}
