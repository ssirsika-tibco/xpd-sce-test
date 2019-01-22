/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;

/**
 * WMNature asset configurator.
 * <p>
 * <i>Created: 6 Oct 2008</i>
 * 
 * @author glewis
 * 
 */
public class N2PEN2NatureAssetConfigurator implements IAssetConfigurator {

    /**
     * Adds omNature to the project.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator#configure(org.eclipse.core.resources.IProject,
     *      java.lang.Object)
     */
    public void configure(IProject project, Object configuration)
            throws CoreException {
        // ProjectUtil.addNature(project, DAANature.NATURE_ID);
    }

}
