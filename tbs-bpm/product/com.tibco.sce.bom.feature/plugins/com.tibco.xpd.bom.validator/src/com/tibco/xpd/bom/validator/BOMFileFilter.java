/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * @author nwilson
 */
public class BOMFileFilter implements IFileFilter {

    /**
     * @param file The file to check.
     * @return true if the file should be validated.
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(
     *      org.eclipse.core.resources.IFile)
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        if (BOMResourcesPlugin.BOM_FILE_EXTENSION
                .equals(file.getFileExtension())) {
            IProject project = file.getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                SpecialFolders folders = config.getSpecialFolders();
                if (folders != null) {
                    SpecialFolder folder = folders.getFolderContainer(file);
                    if (folder != null
                            && BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                                    .equals(folder.getKind())) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

}
