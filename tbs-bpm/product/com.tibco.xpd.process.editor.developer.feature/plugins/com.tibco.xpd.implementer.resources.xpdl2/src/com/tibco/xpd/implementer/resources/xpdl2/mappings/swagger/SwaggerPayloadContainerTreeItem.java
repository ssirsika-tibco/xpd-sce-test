/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;

/**
 * Mapper tree item representing Swagger payload container.
 *
 * @author nkelkar
 * @since Sep 17, 2024
 */
@SuppressWarnings("nls")
public final class SwaggerPayloadContainerTreeItem extends SwaggerContainerTreeItem
{

	/**
	 * The application/json content type identifier
	 */
	public static final String		JSON_CONTENT_TYPE	= "application/json";

	public static final String		PLAIN_TEXT_CONTENT_TYPE	= "text/plain";

	private final Activity			activity;

	private final String			label;

	private final MappingDirection	direction;

	private String					pathPrefix;

	private SwaggerMapperTreeItem	parent;

	private final Content			content;

	private final boolean			isRequired;

	/**
	 * 
	 * @param activity
	 * @param label
	 * @param direction
	 * @param content
	 *            The Swagger payload content item
	 */
	public SwaggerPayloadContainerTreeItem(SwaggerMapperTreeItem parent, Activity activity, String label,
			MappingDirection direction, String pathPrefix, Content content, boolean isRequired)
	{
		this.parent = parent;
		this.activity = activity;
		this.label = label;
		this.direction = direction;
		this.pathPrefix = pathPrefix;

		this.content = content;
		this.isRequired = isRequired;
	}

	/**
	 * Get the payload Swagger API 'Content' object.
	 * 
	 * @param activity
	 * 
	 * @return the payload Swagger API 'Content' object.
	 */
	public Content getContent(Activity activity)
	{
		return content;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SwaggerMapperTreeItem getParent()
	{
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SwaggerMapperTreeItem> createChildren()
	{
		if (activity != null)
		{
			List<SwaggerMapperTreeItem> payloadChildren = new ArrayList<>();
			Content payloadContent = getContent(activity);
			if (payloadContent != null && !payloadContent.isEmpty())
			{
				String mediaTypeName = null;

				MediaType defaultMediaType = payloadContent.get(JSON_CONTENT_TYPE);

				if (defaultMediaType != null)
				{
					mediaTypeName = JSON_CONTENT_TYPE;
				}
				else
				{
					defaultMediaType = payloadContent.get(PLAIN_TEXT_CONTENT_TYPE);
					if (defaultMediaType != null)
					{
						mediaTypeName = PLAIN_TEXT_CONTENT_TYPE;
					}
				}

				if (defaultMediaType != null)
				{
					Schema schema = defaultMediaType.getSchema();

					if (schema != null)
					{
						boolean isRequired = isPayloadRequired();

						SwaggerPayloadTreeItem treeItem = new SwaggerPayloadRootTreeItem(this, activity, pathPrefix,
								pathPrefix, mediaTypeName, schema, isRequired);

						payloadChildren.add(treeItem);
					}
				}
				else
				{
					/*
					 * Unsupported media type.
					 * 
					 * Sid ACE-8885 Simplified use of UnsupportedSwaggerTreeItem
					 */
					if (MappingDirection.IN.equals(direction))
					{
						payloadChildren.add(UnsupportedSwaggerTreeItem.createUnsupportedRequestItem(activity,
								Messages.SwaggerPayloadContainerTreeItem_MediaTypeUnsupported, this, pathPrefix));
					}
					else
					{
						payloadChildren.add(UnsupportedSwaggerTreeItem.createUnsupportedRequestItem(activity,
								Messages.SwaggerPayloadContainerTreeItem_MediaTypeUnsupported, this, pathPrefix));
					}
				}
			}
			return payloadChildren;
		}
		return Collections.emptyList();
	}

	/**
	 * 
	 * @return <code>true</code> the (required fields in the) payload must be mapped
	 */
	public boolean isPayloadRequired()
	{
		return isRequired;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText()
	{
		return label;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Activity getActivity()
	{
		return activity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath()
	{
		return null;
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
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((getParent() == null) ? 0 : getParent().hashCode());
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
		SwaggerPayloadContainerTreeItem other = (SwaggerPayloadContainerTreeItem) obj;

		if (!XpdUtil.safeEquals(activity, other.activity)) return false;
		if (!XpdUtil.safeEquals(direction, other.direction)) return false;
		if (!XpdUtil.safeEquals(getParent(), other.getParent())) return false;

		return true;
	}

}
