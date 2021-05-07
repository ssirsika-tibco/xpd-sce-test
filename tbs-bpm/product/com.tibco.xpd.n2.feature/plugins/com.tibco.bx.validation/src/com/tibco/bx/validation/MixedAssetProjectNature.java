/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * The project nature for Mixed-asset projects. Nominally in SCE (BPME Studio) we validate against the use of certain
 * assets in combination with other assets. Adding this nature to a project switches off that validation so that
 * projects with multiple assets (such as process, data and org) can be used and have RASC generated as a single project
 * app.
 *
 * Sid ACE-5185 New nature for switching off validations against mixed asset projects.
 *
 * @author aallway
 * @since 6 May 2021
 */
public class MixedAssetProjectNature implements IProjectNature {

    /** Id of this nature. */
    public static final String NATURE_ID = BxValidationPlugin.PLUGIN_ID
            + ".mixedAssetProjectNature"; //$NON-NLS-1$

    /** The context project. */
    private IProject project;

    /**
     * Configures the nature. Invoked when nature is added to project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    @Override
    public void configure() throws CoreException {
        /* Nothing to do, there are no special behaviours other than control in validation rules. */
    }

    /**
     * Deconfigures the nature. Invoked when nature is removed from project.
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    @Override
    public void deconfigure() throws CoreException {
    }

    /**
     * Returns the context project.
     * 
     * @return the context project.
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    @Override
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
    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
