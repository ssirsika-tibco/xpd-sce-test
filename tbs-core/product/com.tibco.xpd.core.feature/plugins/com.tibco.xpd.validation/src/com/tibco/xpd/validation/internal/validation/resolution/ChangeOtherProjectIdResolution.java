/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.validation.ProjectLifecycleValidator;

/**
 * Quick-fix resolution for the duplicate project id issue. This resolution will
 * allow user to change the id of the project that has the same id as the
 * project the problem is reported on.
 * 
 * @author njpatel
 * 
 */
public class ChangeOtherProjectIdResolution extends ChangeProjectIdResolution {

    @Override
    protected String getProjectLocation(IMarker marker) throws CoreException {
        Object attr =
                marker
                        .getAttribute(ProjectLifecycleValidator.ATT_DUPLICATE_PROJECT);
        return attr instanceof String ? (String) attr : null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        try {
            IProject project = getProject(getProjectLocation(marker));
            setProject(project);
            if (project != null) {
                return String.format(propertiesLabel, project.getName());
            }
        } catch (CoreException e) {
            // Do nothing
        }
        return Messages.ChangeOtherProjectIdResolution_changeOtherProject_resolution_label;
    }
}
