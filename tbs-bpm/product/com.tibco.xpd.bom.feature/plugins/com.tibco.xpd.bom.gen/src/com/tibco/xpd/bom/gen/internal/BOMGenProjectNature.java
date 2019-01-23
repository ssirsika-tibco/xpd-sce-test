/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * BOM generator project nature.
 * 
 * @author njpatel
 * 
 */
public class BOMGenProjectNature implements IProjectNature {

    public static final String ID = "com.tibco.xpd.bom.gen.bomGenNature"; //$NON-NLS-1$

    private IProject project;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        if (project != null) {
            ProjectUtil.addBuilderToProject(project, BOMGenProjectBuilder.ID);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        if (project != null) {
            ProjectUtil.removeBuilderFromProject(project,
                    BOMGenProjectBuilder.ID);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     */
    public IProject getProject() {
        return project;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core
     * .resources.IProject)
     */
    public void setProject(IProject project) {
        this.project = project;
    }

}
