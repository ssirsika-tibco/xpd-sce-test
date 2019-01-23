/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.ui.projectimport;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.resources.util.SubProgressMonitorEx;

/**
 * 
 * @author patkinso
 * @since 18 Apr 2013
 */
public abstract class ProjectImporter {

    private String name;

    protected IProjectDescription description;

    /**
     * Returns whether the given project description file path is in the default
     * location for a project
     * 
     * @param path
     *            The path to examine
     * @return Whether the given path is the default location for a project
     */
    protected boolean isProjectFileInDefaultLocation(IPath path) {
        // The project description file must at least be within the project,
        // which is within the workspace location

        if (path.segmentCount() >= 2) {
            return path.removeLastSegments(2).toFile()
                    .equals(Platform.getLocation().toFile());
        }
        return false;
    }

    /**
     * @return the projectName
     */
    public String getName() {
        return name;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    protected void setName(String projectName) {
        this.name = projectName;
    }

    /**
     * @return the description
     */
    /*
     * protected IProjectDescription getDescription() { return description; }
     */
    /**
     * @param description
     *            the description to set
     */
    protected void setDescription(IProjectDescription description) {
        this.description = description;
    }

    /**
     * @return
     * @see org.eclipse.core.resources.IProjectDescription#getReferencedProjects()
     */
    public IProject[] getReferencedProjects() {
        return description.getReferencedProjects();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ProjectImporter) {
            String objName = ((ProjectImporter) obj).getName();
            return getName().equals(objName);
        }
        return false;
    }

    /**
     * @return
     */
    public IProjectDescription getDescription() {
        return description;
    }

    /**
     * @param progressMonitor
     * @return
     */
    public abstract IProject importProject(SubProgressMonitorEx progressMonitor);

}
