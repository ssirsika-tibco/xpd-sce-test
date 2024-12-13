/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger;

import java.util.Collections;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.rest.schema.ui.RestConstants;

/**
 * Factory for Swagger working copies.
 *
 * @author nkelkar
 * @since Jul 2, 2024
 */
public class SwaggerWorkingCopyFactory implements WorkingCopyFactory
{

	/**
	 * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
	 *
	 * @param resource
	 * @return
	 */
	@Override
	public WorkingCopy getWorkingCopy(IResource resource)
	{
		return new SwaggerWorkingCopy(Collections.singletonList(resource));
	}

	/**
	 * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
	 *
	 * @param resource
	 * @return
	 */
	@Override
	public boolean isFactoryFor(IResource resource)
	{
		String ext = resource.getFileExtension();
		if ((RestConstants.JSON_FILE_EXTENSION.equalsIgnoreCase(ext)
				|| RestConstants.YAML_FILE_EXTENSION.equalsIgnoreCase(ext)))
		{
			ProjectConfig config = XpdResourcesPlugin.getDefault().getProjectConfig(resource.getProject());
			if (config != null)
			{
				SpecialFolders folders = config.getSpecialFolders();
				if (folders != null)
				{
					SpecialFolder folder = folders.getFolderContainer(resource);
					return folder != null && RestConstants.REST_SPECIAL_FOLDER_KIND.equals(folder.getKind());
				}
			}
		}
        return false;
	}

}
