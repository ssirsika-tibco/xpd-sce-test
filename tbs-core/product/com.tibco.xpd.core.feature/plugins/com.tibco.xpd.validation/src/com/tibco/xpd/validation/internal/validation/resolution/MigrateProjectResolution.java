/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to migrate the assets of a given Studio project.
 * 
 * @author njpatel
 * @since 3.5
 */
public class MigrateProjectResolution implements IResolution {

    public MigrateProjectResolution() {
    }

    public void run(IMarker marker) throws ResolutionException {
        if (marker != null && marker.exists()) {
            IResource resource = marker.getResource();
            if (resource != null) {
                try {
                    ProjectAssetMigrationManager.getInstance().migrate(resource
                            .getProject(),
                            new NullProgressMonitor());
                } catch (CoreException e) {
                    throw new ResolutionException(
                            String
                                    .format(Messages.MigrateProjectResolution_migrationProblems_error_shortdesc,
                                            resource.getProject().getName()), e);
                }
            }
        }
    }

}
