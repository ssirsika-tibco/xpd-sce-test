/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.daa;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.daa.builder.CleanBpmFolderBuilder;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 *
 */
public class CleanBpmFolderNature implements IProjectNature {

    /** Id of this nature. */
    public static final String NATURE_ID = Activator.PLUGIN_ID + ".cleanBpmFolderNature"; //$NON-NLS-1$

    private static final String CLEAN_BPM_FOLDER_BUILDER_ID = CleanBpmFolderBuilder.BUILDER_ID;

    /** The context project. */
    private IProject project;

    /**
     * Configures the nature. Invoked when nature is added to project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        ProjectUtil.addBuilderToProject(project, CLEAN_BPM_FOLDER_BUILDER_ID);
    }

    /**
     * Deconfigures the nature. Invoked when nature is removed from project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project, CLEAN_BPM_FOLDER_BUILDER_ID);
    }

    /**
     * Returns the context project.
     * 
     * @return the context project.
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Sets the context project.
     * 
     * @param project
     *            context project.
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
        this.project = project;
    }


}
