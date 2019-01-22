/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Project asset extension to the BOM asset that will add the BOM Generator
 * nature to the project.
 * 
 * @author njpatel
 */
public class BOMGenAssetConfigurator implements IAssetConfigurator {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator
     * #configure(org.eclipse.core.resources.IProject, java.lang.Object)
     */
    public void configure(IProject project, Object configuration)
            throws CoreException {
        if (project != null) {
            // Add the BOM Generator nature to this project
            ProjectUtil.addNature(project, BOMGenProjectNature.ID);
        }
    }

}
