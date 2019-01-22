/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.builder.BuildJob;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;

/**
 * Resolution to clean and build the project of the problem marker's target
 * object.
 * 
 * @author aallway
 * @since 3.3 (20 Jan 2010)
 */
public class BuildProjectResolution extends AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        final IProject project = WorkingCopyUtil.getProjectFor(target);
        if (project != null) {

            BuildJob buildJob =
                    new BuildJob(
                            String
                                    .format(Messages.BuildProjectResolution_CleanAndBuildProjectResolution_message,
                                            project.getName()), project, true);

            buildJob.schedule();

        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionLabel(java.lang.String, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String projectName = ""; //$NON-NLS-1$

        try {
            EObject target = getTarget(marker);
            if (target != null) {
                IProject project = WorkingCopyUtil.getProjectFor(target);
                if (project != null) {
                    projectName = project.getName();
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return String.format(propertiesLabel, projectName);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionDescription(java.lang.String,
     * org.eclipse.core.resources.IMarker)
     */
    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        return getResolutionLabel(propertiesDescription, marker);
    }
}
