/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.bizassets.resources.BusinessAssetsConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;

/**
 * @author nwilson
 */
public class QualityProcessAssetConfiguratorV3 implements IAssetConfigurator {

    /**
     * @param project
     *            The project.
     * @param configuration
     *            The configuraiton to use.
     * @throws CoreException
     *             If there was a problem.
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator
     *      #configure(org.eclipse.core.resources.IProject, java.lang.Object)
     */
    public void configure(IProject project, Object configuration)
            throws CoreException {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        config.addAssetTask(BusinessAssetsConstants.BUSINESS_ASSETS_V3);
    }

}
