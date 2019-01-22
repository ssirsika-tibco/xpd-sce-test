/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to set the Org Model version to be the same as its
 * project version.
 * 
 * @author njpatel
 * 
 */
public class SetToProjectVersionResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof BaseOrgModel) {
            IResource resource = marker.getResource();
            if (resource != null && resource.getProject() != null) {
                return SetCommand.create(editingDomain,
                        target,
                        OMPackage.eINSTANCE.getBaseOrgModel_Version(),
                        ProjectUtil.getProjectVersion(resource.getProject()));
            }
        }
        return null;
    }

}
