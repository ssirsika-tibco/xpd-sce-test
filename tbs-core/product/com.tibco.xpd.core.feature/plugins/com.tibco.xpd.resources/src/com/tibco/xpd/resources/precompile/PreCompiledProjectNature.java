/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.resources.precompile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Pre Compiled Project Nature. Adds this nature to the project when pre-compile
 * flag is selected/enabled for a project.
 * 
 * @author bharge
 * @since 23 Oct 2014
 */
public class PreCompiledProjectNature implements IProjectNature {

    IProject project;

    /**
     * @see org.eclipse.core.resources.IProjectNature#configure()
     * 
     * @throws CoreException
     */
    @Override
    public void configure() throws CoreException {

        // do nothing
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     * 
     * @throws CoreException
     */
    @Override
    public void deconfigure() throws CoreException {

        /*
         * We don't need this as we are not disabling modifications on the
         * project rather we treat the working copies as read only (so no need
         * to enable modifications if we are not disabling them in the first
         * place)
         */
        // ProjectUtil.enableModificationsOnProject(project);
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
