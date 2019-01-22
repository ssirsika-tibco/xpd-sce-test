/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.daa;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 *
 */
public class CleanBpmFolderAssetConfigurator implements IAssetConfigurator {

    public void configure(IProject project, Object configuration)
            throws CoreException {
        ProjectUtil.addNature(project, CleanBpmFolderNature.NATURE_ID);
    }

}
