/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.asset;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * The REST project nature used to identify projects that can contain REST
 * Service Descriptors.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public class RestServicesProjectNature implements IProjectNature {

    public static final String ID = "com.tibco.xpd.rest.nature"; //$NON-NLS-1$

    private IProject project;

    /**
     * @see org.eclipse.core.resources.IProjectNature#configure()
     * 
     * @throws CoreException
     */
    @Override
    public void configure() throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     * 
     * @throws CoreException
     */
    @Override
    public void deconfigure() throws CoreException {
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     * 
     * @return
     */
    @Override
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
