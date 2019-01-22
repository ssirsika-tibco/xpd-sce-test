/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.exports.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * @author rsomayaj
 * 
 */
public class XsdBuilderAssetConfigurator implements IAssetConfigurator {

    /**
     * 
     */
    private static final String XSD_NATURE_ID = XsdProjectNature.NATURE_ID;

    public void configure(IProject project, Object configuration)
            throws CoreException {
        ProjectUtil.addNature(project, XSD_NATURE_ID);
    }
}
