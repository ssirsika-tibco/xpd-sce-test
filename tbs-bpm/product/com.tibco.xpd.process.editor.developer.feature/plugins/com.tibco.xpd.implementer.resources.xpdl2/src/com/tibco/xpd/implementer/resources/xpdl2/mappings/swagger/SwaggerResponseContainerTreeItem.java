/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.responses.ApiResponse;

/**
 * Mapper tree item representing a Swagger Response container.
 *
 * @author nkelkar
 * @since Sep 23, 2024
 */
@SuppressWarnings("nls")
public final class SwaggerResponseContainerTreeItem extends SwaggerContainerTreeItem
{

	private final Activity			activity;

	private final String			statusCode;

	private ApiResponse		apiResponse;
	
	/**
	 * Construct a parent container for an individual service success response (there will be one of these for each
	 * success response code)
	 * 
	 * @param activity
	 * @param statusCode
	 * @param apiResponse
	 */
	public SwaggerResponseContainerTreeItem(Activity activity, String statusCode, ApiResponse apiResponse)
	{
		this.activity = activity;
		this.statusCode = statusCode;
		this.apiResponse = apiResponse;
	}

	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#createChildren()
	 *
	 * @return
	 */
	@Override
	public List<SwaggerMapperTreeItem> createChildren()
	{
		List<SwaggerMapperTreeItem> children = new ArrayList<SwaggerMapperTreeItem>();

		/*
		 * Create the response headers container.
		 */
		SwaggerResponseParamContainerTreeItem paramContainer = new SwaggerResponseParamContainerTreeItem(getInstance(),
				activity, statusCode, (apiResponse != null ? apiResponse.getHeaders() : null));

		children.add(paramContainer);

		/*
		 * Create the payload tree item for this response code.
		 */
		SwaggerPayloadContainerTreeItem payloadContainer = new SwaggerPayloadContainerTreeItem(getInstance(), activity,
				Messages.RestMapperTreeItemFactory_Payload_label, MappingDirection.OUT,
				RestMappingPrefix.PAYLOAD.addPrefix("_" + statusCode),
				(apiResponse != null ? apiResponse.getContent() : null), false);

		children.add(payloadContainer);

		return children;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getParent()
	 *
	 * @return
	 */
	@Override
	public SwaggerMapperTreeItem getParent()
	{
		return null;
	}

	public SwaggerResponseContainerTreeItem getInstance()
	{
		return this;
	}
	/**
	 * 
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem#getPath()
	 *
	 * @return
	 */
	@Override
	public String getPath()
	{
		return null;
	}

	public String getStatusCode()
	{
		return statusCode;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getText()
	 *
	 * @return
	 */
	@Override
	public String getText()
	{
		return Messages.RestMapperTreeItemFactory_ResponseCode_label + " " + statusCode;
	}

	/**
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem#getActivity()
	 *
	 * @return
	 */
	@Override
	public Activity getActivity()
	{
		return activity;
	}

	/**
	 * 
	 * @return <code>true</code> if this is the container of the 'default' response header params / payload.
	 */
	public boolean isDefaultResponse()
	{
		return "default".equalsIgnoreCase(statusCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!getClass().equals(obj.getClass())) return false;
		SwaggerResponseContainerTreeItem other = (SwaggerResponseContainerTreeItem) obj;

		if (!XpdUtil.safeEquals(activity, other.activity)) return false;
		if (!XpdUtil.safeEquals(statusCode, other.statusCode)) return false;
		return true;
	}
}
