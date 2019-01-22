/*
 * Copyright (c) TIBCO Software Inc. 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation.resolution;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution to create all missing Special Folders of a project.
 * 
 * @author njpatel
 */
public class CreateMissingSpecialFolderResolution implements IResolution {

    public CreateMissingSpecialFolderResolution() {
    }

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {
        IResource resource = marker.getResource();
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;

            try {
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
                /*
                 * Create all missing special folders
                 */
                if (config != null && config.getSpecialFolders() != null) {
                    for (SpecialFolder sf : config.getSpecialFolders()
                            .getFolders()) {
                        if (sf.getFolder() == null || !sf.getFolder().exists()) {
                            if (sf.getLocation() != null) {
                                createFolder(project, sf.getLocation());
                            }
                        }
                    }
                }
            } catch (CoreException e) {
                throw new ResolutionException(e);
            }
        }
    }

    /**
     * Create the folder at the location given in the project. This will create
     * all missing parent folders.
     * 
     * @param project
     * @param folderPath
     *            project-relative path of the folder
     * @throws CoreException
     */
    private IFolder createFolder(IProject project, String folderPath)
            throws CoreException {
        IFolder folder = project.getFolder(folderPath);
        ProjectUtil.createFolder(folder, false, null);

        return folder;
    }

}
