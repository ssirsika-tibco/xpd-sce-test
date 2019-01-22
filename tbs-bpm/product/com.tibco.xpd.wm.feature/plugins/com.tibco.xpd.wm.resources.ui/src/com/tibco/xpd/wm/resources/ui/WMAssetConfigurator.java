/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wm.resources.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * WMNature asset configurator.
 * <p>
 * <i>Created: 6 Oct 2008</i>
 * 
 * @author glewis
 * 
 */
public class WMAssetConfigurator extends SpecialFolderAssetConfigurator {

    /**
     * Adds omNature to the project.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator#configure(org.eclipse.core.resources.IProject,
     *      java.lang.Object)
     */
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);
    }

}
