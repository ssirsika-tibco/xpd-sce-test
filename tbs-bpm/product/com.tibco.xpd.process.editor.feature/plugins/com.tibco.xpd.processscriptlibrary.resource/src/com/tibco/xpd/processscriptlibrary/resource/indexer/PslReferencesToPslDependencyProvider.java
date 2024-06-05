/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.indexer;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;

/**
 * Working copy dependency provider for PSL file references from other PSL files.
 *
 * Sid ACE-8238
 * 
 * @author aallway
 * @since 22 May 2024
 */
public class PslReferencesToPslDependencyProvider extends AbstractReferencesToPslDependencyProvider
{

	/**
	 * @see com.tibco.xpd.processscriptlibrary.resource.indexer.AbstractReferencesToPslDependencyProvider#getFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getFileExtension()
	{
		return ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;
	}

}
