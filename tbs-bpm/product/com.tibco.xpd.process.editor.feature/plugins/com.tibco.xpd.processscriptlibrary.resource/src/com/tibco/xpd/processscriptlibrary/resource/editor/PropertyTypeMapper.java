/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.editor;

import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

/**
 * A property type mapper implementation to be used with the properties contributor extension.
 *
 * @author cbabar
 * @since Jan 9, 2024
 */
public class PropertyTypeMapper implements ITypeMapper
{

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITypeMapper#mapType(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public Class mapType(Object object)
	{
		return object.getClass();
	}

}
