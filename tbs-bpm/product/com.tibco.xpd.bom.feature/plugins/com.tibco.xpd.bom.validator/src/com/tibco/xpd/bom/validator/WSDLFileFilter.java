/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * 
 * Filters WSDL files that are validated
 * 
 * @author rsomayaj
 * @since 3.3 (11 Mar 2010)
 */
public class WSDLFileFilter implements IFileFilter {

    /**
     * 
     */
    private static final String WSDL = "wsdl";

    /**
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
     * 
     * @param file
     * @return
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        if (WSDL.equals(file.getFileExtension())) {
            IProject project = file.getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                SpecialFolders folders = config.getSpecialFolders();
                if (folders != null) {
                    SpecialFolder folder = folders.getFolderContainer(file);
                    if (folder != null && WSDL.equals(folder.getKind())) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

}
