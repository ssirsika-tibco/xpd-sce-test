/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wsdlgen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * WSDL project nature. This nature manages builders for creating modules for
 * WSDL.
 * <p>
 * <i>Created: 13 April 2009</i>
 * 
 * @author kupadhya
 * 
 */
public class WsdlProjectNature implements IProjectNature {

    /** Id of this nature. */
    public static final String NATURE_ID = WsdlgenPlugin.PLUGIN_ID
            + ".wsdlGenNature"; //$NON-NLS-1$

    /** The context project. */
    private IProject project;

    /**
     * Configures the nature. Invoked when nature is added to project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        ProjectUtil.addBuilderToProject(project,
                WsdlgenPlugin.BUILDER_ID);
    }

    /**
     * Deconfigures the nature. Invoked when nature is removed from project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project,
                WsdlgenPlugin.BUILDER_ID);
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
