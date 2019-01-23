/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import com.tibco.xpd.resources.projectconfig.ProjectDetails;

/**
 * The implementor of this interface will provide the project details set in the
 * new XPD project creation wizard during the life-cycle of the wizard. As an
 * example, wizard pages can use this provider to get information set in the
 * first page of the wizard.
 * 
 * @author njpatel
 * 
 * @since 3.2
 */
public interface IProjectDetailsProvider {

    /**
     * Get the name of the project being created.
     * 
     * @return project name, or <code>null</code> if no project name available.
     */
    String getProjectName();

    /**
     * get the details of the project being created.
     * 
     * @return {@link ProjectDetails} or <code>null</code> if no details
     *         available.
     */
    ProjectDetails getProjectDetails();

}
