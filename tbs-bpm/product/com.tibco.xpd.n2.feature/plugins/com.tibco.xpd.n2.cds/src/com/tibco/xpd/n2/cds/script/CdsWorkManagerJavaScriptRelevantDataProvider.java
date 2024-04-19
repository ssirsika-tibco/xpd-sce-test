/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.n2.cds.script;

import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;

/**
 * Extends {@link CdsDefaultJavaScriptRelevantDataProvider} to provide specific process script libraries supported
 * in the work manager scripts (in UserTask).
 *
 * @author ssirsika
 * @since 12-Mar-2024
 */
public class CdsWorkManagerJavaScriptRelevantDataProvider extends CdsDefaultJavaScriptRelevantDataProvider
{

	/**
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getTargetProcessScriptLibraryIndexType()
	 *
	 * @return
	 */
	@Override
	protected IndexType getTargetProcessScriptLibraryIndexType()
	{
		// Always return work manager related Enum to provide process script libraries used in work manager.
		return IndexType.PSL_WM_PARAM_REF;
	}
}
