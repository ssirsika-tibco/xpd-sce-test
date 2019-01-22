/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.projectconfig.util.SetProjectDestinationCommand;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.bpmn.rules.PackageDestSubsetOfProjectDestRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * AddProjectDestinationResolution
 * <p>
 * Resolution to add a given global destination (expected to be in
 * 
 * @author aallway
 * @since 3.3 (21 Oct 2009)
 */
public class AddProjectDestinationResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        IProject project = WorkingCopyUtil.getProjectFor(target);
        if (project != null) {
            String badDestName = getBadDestinationName(marker);

            if (badDestName != null && badDestName.length() > 0) {

                return new SetProjectDestinationCommand(project, badDestName);
            }
        }
        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String badDestName = getBadDestinationName(marker);
        if (badDestName != null && badDestName.length() > 0) {
            return String.format(propertiesLabel, badDestName);
        }
        return super.getResolutionLabel(propertiesLabel, marker);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        String badDestName = getBadDestinationName(marker);
        if (badDestName != null && badDestName.length() > 0) {
            return String.format(propertiesDescription, badDestName);
        }
        return super.getResolutionLabel(propertiesDescription, marker);
    }

    /**
     * @param marker
     * @return
     */
    private String getBadDestinationName(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            return addInfo
                    .getProperty(PackageDestSubsetOfProjectDestRule.BAD_PKGDEST_ADDINFOKEY);
        }
        return null;
    }

}
