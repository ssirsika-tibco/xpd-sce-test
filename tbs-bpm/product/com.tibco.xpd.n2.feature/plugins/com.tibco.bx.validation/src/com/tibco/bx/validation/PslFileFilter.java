/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.bx.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.validation.provider.IFileFilter;

/**
 * Process Script Library File Filter for validation, accepts ONLY PSL file.
 * 
 * @author aallway
 */
public class PslFileFilter implements IFileFilter {

    /**
     * @param file The file to check.
     * @return true if the file should be validated.
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(
     *      org.eclipse.core.resources.IFile)
     */
    @Override
	public boolean accept(IFile file) {
        boolean ok = false;
		if (ProcessScriptLibraryConstants.PSL_FILE_EXTENSION.equals(file.getFileExtension()))
		{
            IProject project = file.getProject();
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                SpecialFolders folders = config.getSpecialFolders();
                if (folders != null) {
                    SpecialFolder folder = folders.getFolderContainer(file);
                    if (folder != null
							&& ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND
                                    .equals(folder.getKind())) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

}
