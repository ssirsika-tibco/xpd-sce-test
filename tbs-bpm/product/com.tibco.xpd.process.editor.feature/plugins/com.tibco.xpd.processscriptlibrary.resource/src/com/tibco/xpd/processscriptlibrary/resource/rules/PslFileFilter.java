/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.rules;

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
 * @author nkelkar
 * @since Mar 13, 2024
 */
public class PslFileFilter implements IFileFilter
{

	/**
	 * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
	 * 
	 * @param file
	 * @return
	 */
	@Override
	public boolean accept(IFile file)
	{
		boolean ok = false;
		if (ProcessScriptLibraryConstants.PSL_FILE_EXTENSION.equals(file.getFileExtension())) // $NON-NLS-1$
		{
			IProject project = file.getProject();
			ProjectConfig config = XpdResourcesPlugin.getDefault().getProjectConfig(project);
			if (config != null)
			{
				SpecialFolders folders = config.getSpecialFolders();
				if (folders != null)
				{
					SpecialFolder folder = folders.getFolderContainer(file);
					if (folder != null
							&& ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND.equals(folder.getKind())) // $NON-NLS-1$
					{
						ok = true;
					}
				}
			}
		}
		return ok;
	}

}
