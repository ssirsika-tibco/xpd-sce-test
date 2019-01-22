/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deploy;

import java.util.List;

import org.eclipse.core.resources.IProject;

/**
 * This interface should be implemented by all servers with project based
 * deployment (you always deploy the whole project).
 * <p>
 * <i>Created: 15 Dec 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface IProjectBasedDeployWizard extends IDeployWizard {

    /**
     * Set the context project for the wizard.
     * 
     * @param projects
     *            the context project.
     */
    void setContextProjects(List<IProject> projects);
}
