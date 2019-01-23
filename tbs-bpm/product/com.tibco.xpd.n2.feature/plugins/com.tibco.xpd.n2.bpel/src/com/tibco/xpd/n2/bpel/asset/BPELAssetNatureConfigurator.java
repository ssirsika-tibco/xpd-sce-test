/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.bpel.asset;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 * 
 */
public class BPELAssetNatureConfigurator implements IAssetConfigurator {

    public void configure(IProject project, Object configuration)
            throws CoreException {
        // TODO we might need to add nature in the right order
        ProjectUtil.addNature(project, BPELN2Utils.BPEL_NATURE_ID);

    }

}
