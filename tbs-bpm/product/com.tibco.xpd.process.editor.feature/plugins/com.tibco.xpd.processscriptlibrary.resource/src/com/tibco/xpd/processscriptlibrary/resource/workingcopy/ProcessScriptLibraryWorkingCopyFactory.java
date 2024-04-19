/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.workingcopy;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.xpdl2.resources.AbstractXpdl2WorkingCopyFactory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;

/**
 * Working copy factory for Process Script Library resources.
 *
 * @author ssirsika
 * @since 07-Dec-2023
 */
public class ProcessScriptLibraryWorkingCopyFactory extends AbstractXpdl2WorkingCopyFactory
{

	/**
	 * @see com.tibco.xpd.xpdl2.resources.AbstractXpdl2WorkingCopyFactory#getFileExtension()
	 *
	 * @return
	 */
	@Override
	public String getFileExtension()
	{
		return ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;
	}

	/**
	 * @see com.tibco.xpd.xpdl2.resources.AbstractXpdl2WorkingCopyFactory#getXpdl2FileType()
	 *
	 * @return
	 */
	@Override
	public Xpdl2FileType getXpdl2FileType()
	{
		return Xpdl2FileType.SCRIPT_LIBRARY;
	}

	/**
	 * @see com.tibco.xpd.xpdl2.resources.AbstractXpdl2WorkingCopyFactory#getSpecialFolderKind()
	 *
	 * @return
	 */
	@Override
	public String getSpecialFolderKind()
	{
		return ProcessScriptLibraryConstants.PSL_SPECIAL_FOLDER_KIND;
	}
}
