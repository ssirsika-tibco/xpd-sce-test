/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.wsdlgen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author kupadhya
 * 
 */
public class WsdlBuilderAssetConfigurator implements IAssetConfigurator {

    /**
     * 
     */
    private static final String WSDL_NATURE_ID = WsdlProjectNature.NATURE_ID;

    public void configure(IProject project, Object configuration)
            throws CoreException {
        ProjectUtil.addNature(project, WSDL_NATURE_ID);
    }
}
