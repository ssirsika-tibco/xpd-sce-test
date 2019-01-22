/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.validation.ProjectValidator;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to remove a project reference from a project that is either closed
 * or not present in the workspace.
 * 
 * @author njpatel
 * 
 */
public class RemoveProjectReferenceResolution implements IResolution {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        if (marker.getResource() instanceof IProject) {
            IProject project = (IProject) marker.getResource();
            try {
                // Get the referenced project from the marker
                IProject ref =
                        ProjectValidator
                                .getReferencedProjectStoredInMarker(marker);

                if (ref != null) {
                    IProjectDescription description = project.getDescription();
                    List<IProject> newRefList = new ArrayList<IProject>();

                    // Create a new reference list without the project
                    // that needs to be unreferenced
                    for (IProject pr : description.getReferencedProjects()) {
                        if (!ref.equals(pr)) {
                            newRefList.add(pr);
                        }
                    }

                    // Update the project description
                    description.setReferencedProjects(newRefList
                            .toArray(new IProject[newRefList.size()]));
                    project.setDescription(description,
                            new NullProgressMonitor());
                }
            } catch (CoreException e) {
                throw new ResolutionException(
                        String.format(Messages.RemoveProjectReferenceResolution_errorInRemovingReference_error_shortdesc,
                                project.getName()), e);
            }
        }
    }
}
