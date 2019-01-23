/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.wsdltransform.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author rsomayaj
 * 
 */
public class WsdlBomProjectNature implements IProjectNature {

    /** Id of this nature. */
    public static final String NATURE_ID =
            "com.tibco.xpd.wsdltobom.wsdlBomNature"; //$NON-NLS-1$

    public static final String BUILDER_ID =
            "com.tibco.xpd.wsdltobom.wsdlToBomBuilder"; //$NON-NLS-1$

    private IProject project;

    /**
     * @see org.eclipse.core.resources.IProjectNature#configure()
     * 
     * @throws CoreException
     */
    public void configure() throws CoreException {
        ProjectUtil.addBuilderToProject(project, BUILDER_ID);
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     * 
     * @throws CoreException
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project, BUILDER_ID);
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     * 
     * @return
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    public void setProject(IProject project) {
        this.project = project;
    }

}
