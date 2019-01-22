package com.tibco.bx.emulation.ui.properties;

import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class PropertyTypeMapper implements ITypeMapper
{

	public PropertyTypeMapper()
	{
		
	}
	
	public Class mapType(Object obj)
	{
	    return obj.getClass();
	}
}
