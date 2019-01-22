/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * @author NWilson
 * 
 */
public class Xpdl2DeveloperFileFilter implements IFileFilter {

    /**
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
     * 
     * @param file
     * @return
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            if ("xpdl".equals(file.getFileExtension())) { //$NON-NLS-1$
                IProject project = file.getProject();
                ProjectConfig config =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(project);
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
        }
        return ok;
    }

}
