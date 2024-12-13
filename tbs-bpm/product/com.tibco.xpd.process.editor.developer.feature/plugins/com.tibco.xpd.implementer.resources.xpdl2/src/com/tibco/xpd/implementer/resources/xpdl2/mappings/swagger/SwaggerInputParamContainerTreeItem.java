/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * Swagger service mapper tree container for input parameters (path/query/header)
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public final class SwaggerInputParamContainerTreeItem extends SwaggerParamContainerTreeItem
{

	private Collection<Parameter> inputParameters;

	/**
	 * Construct a swagger INPUT parameters container including all the items within it.
	 * 
	 * @param parent
	 * @param activity
	 * @param paramStyle
	 * @param direction
	 * @param inputParameters
	 */
	public SwaggerInputParamContainerTreeItem(SwaggerMapperTreeItem parent, Activity activity,
			ParameterStyle paramStyle, Collection<Parameter> inputParameters)
	{
		super(parent, activity, paramStyle, MappingDirection.IN);

		this.inputParameters = inputParameters;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#createChildren()
	 *
	 * @return
	 */
	@Override
	public List< ? > createChildren()
	{
		/*
		 * Create the children
		 */
		List<SwaggerInputParamTreeItem> children = new ArrayList<>();

		if (inputParameters != null)
		{
			for (io.swagger.v3.oas.models.parameters.Parameter param : inputParameters)
			{
				ParameterStyle paramStyle = getParamStyle();

				if (isParameterStyle(param, paramStyle))
				{
					children.add(new SwaggerInputParamTreeItem(this, param, getActivity(), paramStyle));
				}
			}
		}

		return children;
	}

	/**
	 * Check whether the given parameter is of the given parameter type.
	 * 
	 * @param param
	 * @param paramStyle
	 * 
	 * @return <code>true</code> if the parameter is of the given type.
	 */
	private boolean isParameterStyle(Parameter param, ParameterStyle paramStyle)
	{
		if ("path".equals(param.getIn()) && ParameterStyle.PATH.equals(paramStyle))
		{
			return true;
		}
		else if ("query".equals(param.getIn()) && ParameterStyle.QUERY.equals(paramStyle))
		{
			return true;
		}
		else if ("header".equals(param.getIn()) && ParameterStyle.HEADER.equals(paramStyle))
		{
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText()
	{
		ParameterStyle paramStyle = getParamStyle();

		switch (paramStyle)
		{
			case PATH:
				return Messages.RestMapperTreeItemFactory_PathParameters_label;

			case QUERY:
				return Messages.RestMapperTreeItemFactory_QueryParameters_label;

			case HEADER:
				return Messages.RestMapperTreeItemFactory_HeaderParameters_label;

		}

		assert false : String.format("Unsupported parameter style: '%1$s'.", paramStyle);

		return null;

	}

}
