/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.provider;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * @author nwilson
 */
public class Xpdl2FileFilter implements IFileFilter {

    /**
     * @param file The file to check.
     * @return true if the file should be validated.
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(
     *      org.eclipse.core.resources.IFile)
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        if ("xpdl".equals(file.getFileExtension())) { //$NON-NLS-1$
            IProject project = file.getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                SpecialFolders folders = config.getSpecialFolders();
                if (folders != null) {
                    SpecialFolder folder = folders.getFolderContainer(file);
                    if (folder != null
                            && Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND
                                    .equals(folder.getKind())) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

}
