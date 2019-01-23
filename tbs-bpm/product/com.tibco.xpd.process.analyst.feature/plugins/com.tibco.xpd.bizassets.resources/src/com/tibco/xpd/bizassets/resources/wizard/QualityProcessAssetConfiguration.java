/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * @author nwilson
 */
public class QualityProcessAssetConfiguration extends
        SpecialFolderAssetConfiguration {

    private IProject project;

    /**
     * 
     */
    public QualityProcessAssetConfiguration() {
    }

    public IProject getQualityProject() {
        return project;
    }

    /**
     * @param selected
     */
    public void setQualityProject(IProject project) {
        this.project = project;
    }

}
