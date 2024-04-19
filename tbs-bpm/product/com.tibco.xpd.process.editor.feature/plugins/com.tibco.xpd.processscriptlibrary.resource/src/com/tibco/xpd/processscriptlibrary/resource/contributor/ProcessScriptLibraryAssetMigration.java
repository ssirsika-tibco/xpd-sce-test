/*
* Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.contributor;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.util.AssetWorkingCopyMigration;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;

/**
 *
 * Asset migration for the Process Script Library asset.
 * 
 * @author ssirsika
 * @since 19-Dec-2023
 */
public class ProcessScriptLibraryAssetMigration extends AssetWorkingCopyMigration
		implements IProjectAssetVersionProvider
{

	/**
	 * 
	 */
	public ProcessScriptLibraryAssetMigration()
	{
		super(ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND,
				ProcessScriptLibraryConstants.PSL_FILE_EXTENSION);
	}

	/**
	 * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider#getVersion(org.eclipse.core.resources.IProject)
	 *
	 * @param project
	 * @return
	 */
	@Override
	public int getVersion(IProject project)
	{
		return Integer.parseInt(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
	}

}
