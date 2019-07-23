package com.tibco.xpd.rasc.ui.governance;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Project nature class for projects that are Locked for Production.
 *
 * @author nwilson
 * @since 23 Jul 2019
 */
public class LockedForProductionNature implements IProjectNature {
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
     */
    @Override
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

}
