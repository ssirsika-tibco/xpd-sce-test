/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.headers.Header;

/**
 * Swagger service mapper tree container for response header params
 *
 * @author aallway
 * @since Oct 2024
 */
@SuppressWarnings("nls")
public final class SwaggerResponseParamContainerTreeItem extends SwaggerParamContainerTreeItem
{

	private Map<String, Header> responseHeaders;

	private String				statusCode;

	/**
	 * Construct a swagger OUT parameters container including all the items within it.
	 * 
	 * @param parent
	 * @param activity
	 * @param paramStyle
	 * @param direction
	 * @param inputParameters
	 */
	public SwaggerResponseParamContainerTreeItem(SwaggerMapperTreeItem parent, Activity activity, String statusCode,
			Map<String, Header> responseHeaders)
	{
		super(parent, activity, ParameterStyle.HEADER, MappingDirection.OUT);

		this.statusCode = statusCode;
		this.responseHeaders = responseHeaders;
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
		List<SwaggerResponseParamTreeItem> children = new ArrayList<>();

		if (responseHeaders != null)
		{
			responseHeaders.forEach((headerName, header) -> {
				children.add(new SwaggerResponseParamTreeItem(this, header, headerName, getActivity(), statusCode));
			});
		}

		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText()
	{
		return Messages.RestMapperTreeItemFactory_HeaderParameters_label;
	}

}
