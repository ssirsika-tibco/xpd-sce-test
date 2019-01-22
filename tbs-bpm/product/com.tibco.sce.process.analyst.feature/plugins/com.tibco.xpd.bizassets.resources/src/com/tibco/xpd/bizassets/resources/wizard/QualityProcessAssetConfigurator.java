/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfigurator;

/**
 * @author nwilson
 */
public class QualityProcessAssetConfigurator extends
        SpecialFolderAssetConfigurator {

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);
        if (configuration instanceof QualityProcessAssetConfiguration) {
            QualityProcessAssetConfiguration config =
                    (QualityProcessAssetConfiguration) configuration;
            IProject qualityProject = config.getQualityProject();
            if (qualityProject != null) {
                String folderName = getSpecialFolderName();
                IFolder folder = project.getFolder(folderName);
                if (folder != null) {
                    try {
                        QualityArchiveUtil.copyQualityProject(folder,
                                qualityProject);
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
