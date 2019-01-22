/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.manager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Nature for special Business Studio Servers project. This project contains
 * only metadata information about servers configuration. It is only used for
 * marking purposes. It does not contain any associated builders.
 * <p>
 * <i>Created: 11 Dec 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class BSServersNature implements IProjectNature {

    /** Nature identifier. */
    public static final String NATURE_ID = "com.tibco.xpd.deploy.core.edit.bsServersNature"; //$NON-NLS-1$

    private IProject project;

    /**
     * Configure the nature.
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        // do nothing
    }

    /**
     * De configures the nature.
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        // do nothing
    }

    /**
     * Returns associated project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /**
     * Sets the project associated with nature.
     * 
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
        this.project = project;
    }
}
