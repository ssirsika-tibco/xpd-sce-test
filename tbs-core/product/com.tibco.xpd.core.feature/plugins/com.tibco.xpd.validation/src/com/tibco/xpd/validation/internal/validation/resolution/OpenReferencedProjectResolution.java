/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.validation.ProjectValidator;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to open a referenced project.
 * 
 * @author njpatel
 */
public class OpenReferencedProjectResolution implements IResolution {

    public void run(IMarker marker) throws ResolutionException {
        if (marker.getResource() instanceof IProject) {
            IProject project = (IProject) marker.getResource();
            try {
                // Get the referenced project from the marker
                IProject ref =
                        ProjectValidator
                                .getReferencedProjectStoredInMarker(marker);
                if (ref != null && ref.exists()) {
                    ref.open(new NullProgressMonitor());
                } else {
                    throw new ResolutionException(
                            Messages.OpenReferencedProjectResolution_cannotOpenProject_error_shortdesc);
                }
            } catch (CoreException e) {
                throw new ResolutionException(
                        String.format(Messages.OpenReferencedProjectResolution_errorInOpenProjectResolution_error_shortdesc,
                                project.getName()), e);
            }
        }
    }
}
