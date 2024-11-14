/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.rest.swagger;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexResourceMatcher;

/**
 * Implemented to dynamically check for SwaggerWorkingCopy resources to index
 * 
 *
 * @author nkelkar
 * @since Aug 6, 2024
 */
public class SwaggerIndexResourceMatcher implements IndexResourceMatcher
{
	/**
	 * 
	 * @see com.tibco.xpd.resources.indexer.IndexResourceMatcher#accept(com.tibco.xpd.resources.WorkingCopy)
	 *
	 * @param wc
	 * @return
	 */
	@Override
	public boolean accept(WorkingCopy wc)
	{
		if (wc != null && !wc.isInvalidFile())
		{
			if (wc instanceof SwaggerWorkingCopy)
			{
				return true;
			}
		}
		return false;
	}
}

