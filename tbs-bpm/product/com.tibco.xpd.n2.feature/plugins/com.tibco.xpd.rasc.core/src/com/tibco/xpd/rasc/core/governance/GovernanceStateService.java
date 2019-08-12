/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.governance;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Used for querying and setting the governance state of a project.
 *
 * @author nwilson
 * @since 18 Jul 2019
 */
public class GovernanceStateService {

    /**
     * Checks if a project is Locked for Production.
     * 
     * @param project
     *            The project to check.
     * @return true if it is locked.
     * @throws CoreException
     *             if the state could not be checked.
     */
    public boolean isLockedForProduction(IProject project) throws CoreException {
        if (!project.isAccessible()) {
            return false;
        }
        return project.hasNature(XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
    }

    /**
     * Sets a project as Locked for Production.
     * 
     * @param project
     *            The project to lock.
     * @throws CoreException
     *             if the project could not be locked.
     */
    public void lockForProduction(IProject project) throws CoreException {
        ProjectUtil.addNature(project, XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
    }

    /**
     * Creates a new draft of a project by unlocking it and incrementing the
     * minor version.
     * 
     * @param project
     *            The project to create a new draft for.
     * @throws CoreException
     *             if the project could not be unlocked.
     * @throws IOException
     */
    public void createNewDraft(IProject project) throws CoreException, IOException {
        ProjectUtil.removeNature(project, XpdConsts.LOCKED_FOR_PRODUCTION_NATURE);
        ProjectConfig projectConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (projectConfig != null) {
            ProjectDetails projectDetails = projectConfig.getProjectDetails();
            if (projectDetails != null) {
                String version = projectDetails.getVersion();
                if (version != null) {
                    // Increment minor version;
                    version = incrementVersion(version);
                    projectDetails.setVersion(version);
                    /* Save it. */
                    ProjectConfigWorkingCopy wc =
                            (ProjectConfigWorkingCopy) WorkingCopyUtil.getWorkingCopyFor(projectConfig);
                    wc.setDetails(projectDetails);
                }
            }
        }
    }

    /**
     * Increments the minor version of a project.
     * 
     * @param version
     *            The old version.
     * @return The new version.
     * @throws IOException
     */
    private String incrementVersion(String version) throws IOException {
        String newVersion = null;
        String[] parts = version.split("\\."); //$NON-NLS-1$
        if (parts.length > 1) {
            parts[1] = String.valueOf(Integer.parseInt(parts[1]) + 1);
            newVersion = String.join(".", parts); //$NON-NLS-1$
        }
        if (newVersion == null) {
            throw new IOException("Could not increment minor version for " + version); //$NON-NLS-1$
        }
        return newVersion;
    }
}
