/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * Object that represents (and wraps) a Swagger service input parameter, in other words a path/query/header parameter.
 *
 * 
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public final class SwaggerInputParamTreeItem extends SwaggerParamTreeItem
{
	private final Parameter param;

	/**
	 * 
	 * @param parent
	 * @param param
	 * @param name
	 * @param activity
	 * @param direction
	 * @param paramStyle
	 */
	public SwaggerInputParamTreeItem(SwaggerMapperTreeItem parent, Parameter param, Activity activity,
			ParameterStyle paramStyle)
	{
		super(parent, param.getName(), activity, MappingDirection.IN, paramStyle, param.getSchema());

		this.param = param;
	}

	/**
	 * @return the underlying Swagger API param object
	 */
	public Parameter getParam()
	{
		return param;
	}

	/**
	 * 
	 * @return <code>true</code> if the parameter is an array type
	 */
	@Override
	public boolean isRequired()
	{
		// Must allow for required property being null
		return Boolean.TRUE.equals(param.getRequired());
	}

	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem#getPath()
	 *
	 * @return
	 */
	@Override
	public String getPath()
	{
		String path = getParamPrefix();
		if (path != null)
		{
			path += param.getName();
		}
		else
		{
			// Default
			path = param.getName();
		}

		/**
		 * JS script variable names params need to have special characters removed
		 * 
		 * Also, just replacing hyphen with underscore is a little weak, so we will do that (as we always used to for
		 * header params) AND then remove anything that is not alphanumeric or underscore - that should cleanse
		 * everything else that will be an issue for JS script variables.
		 */
		path = path.replace('-', '_');

		path = NameUtil.getInternalName(path, true);

		return path;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem#getParamPrefix()
	 *
	 * @return
	 */
	@Override
	public String getParamPrefix()
	{
		return RestMappingPrefix.getForParamStyle(getParamStyle()).getPrefix();
	}
}
