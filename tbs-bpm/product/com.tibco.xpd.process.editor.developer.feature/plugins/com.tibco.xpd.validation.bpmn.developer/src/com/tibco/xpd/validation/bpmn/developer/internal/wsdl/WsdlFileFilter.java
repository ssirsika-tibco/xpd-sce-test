/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.wsdl.ui.WsdlUIPlugin;

/**
 * File filter for the WSDL validation destination.
 * 
 * @author njpatel
 * 
 */
public class WsdlFileFilter implements IFileFilter {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
     */
    public boolean accept(IFile file) {
        boolean ok = false;
        if ("wsdl".equals(file.getFileExtension())) { //$NON-NLS-1$
            IProject project = file.getProject();
            ProjectConfig config = XpdResourcesPlugin.getDefault()
                    .getProjectConfig(project);
            if (config != null) {
                SpecialFolders folders = config.getSpecialFolders();
                if (folders != null) {
                    SpecialFolder folder = folders.getFolderContainer(file);
                    if (folder != null
                            && WsdlUIPlugin.WSDL_SPECIALFOLDER_KIND
                                    .equals(folder.getKind())) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

}
