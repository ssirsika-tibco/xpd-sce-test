/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.newproject;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Hidden GSD asset configurator. Even though we already have a GSD asset
 * (configured by GsdAssetConfigurator), we need this an hidden asset to add GSD
 * nature so as to specifically identify a Global Signal Definition project
 * (can't identify on the basis of GSD asset because that gets added even if we
 * add a GSD special folder to a non-GSD project).
 * 
 * @author sajain
 * @since Apr 25, 2015
 */
public class HiddenGsdAssetConfigurator implements IAssetConfigurator {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.
     * AbstractSpecialFolderAssetConfigurator
     * #configure(org.eclipse.core.resources.IProject, java.lang.Object)
     */
    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {

        /*
         * Apply the GSD project nature.
         */
        ProjectUtil.addNature(project, GsdProjectNature.ID);

    }

}