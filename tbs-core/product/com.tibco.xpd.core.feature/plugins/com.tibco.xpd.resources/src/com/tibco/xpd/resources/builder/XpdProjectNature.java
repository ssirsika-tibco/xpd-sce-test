/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * XPD Nature
 * 
 */
public class XpdProjectNature implements IProjectNature {

    /**
     * ID of this project nature
     */
    public static final String NATURE_ID = XpdConsts.PROJECT_NATURE_ID;

    public static final String VALIDATION_BUILDER_ID = "com.tibco.xpd.validation.validationBuilder"; //$NON-NLS-1$

    private IProject project;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#configure()
     */
    public void configure() throws CoreException {
        ProjectUtil.addBuilderToProject(project, VALIDATION_BUILDER_ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     */
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project, VALIDATION_BUILDER_ID);
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
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     */
    public void setProject(IProject project) {
        this.project = project;
    }

}
