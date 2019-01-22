/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.bpel.BpelBuilderActivator;
import com.tibco.xpd.n2.bpel.builder.BPELBuilder;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 * 
 */
public class BPELNature implements IProjectNature {

    /** Id of this nature. */
    public static final String NATURE_ID = BpelBuilderActivator.PLUGIN_ID
            + ".bpelNature"; //$NON-NLS-1$    

    private static final String BPEL_BUILDER_ID = BPELBuilder.BUILDER_ID;

    /** The context project. */
    private IProject project;

    /**
     * Configures the nature. Invoked when nature is added to project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        ProjectUtil.addBuilderToProject(project, BPEL_BUILDER_ID);
    }

    /**
     * Deconfigures the nature. Invoked when nature is removed from project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project, BPEL_BUILDER_ID);
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
