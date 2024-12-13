/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.List;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Container tree item used for grouping Swagger Parameters tree items.
 *
 * @author nkelkar
 * @since Sep 3, 2024
 */
@SuppressWarnings("nls")
public abstract class SwaggerParamContainerTreeItem extends SwaggerContainerTreeItem
{

	private final Activity			activity;

	private final ParameterStyle	paramStyle;

	private final MappingDirection	direction;

	private SwaggerMapperTreeItem	parent;

	/**
	 * Construct a swagger parameters container
	 * 
	 * @param parent
	 * @param activity
	 * @param paramStyle
	 * @param direction
	 */
	public SwaggerParamContainerTreeItem(SwaggerMapperTreeItem parent, Activity activity,
			ParameterStyle paramStyle,
			MappingDirection direction)
	{
		this.parent = parent;
		this.activity = activity;
		this.paramStyle = paramStyle;
		this.direction = direction;
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
	public String getPath()
	{
		return null;
	}

	public ParameterStyle getParamStyle()
	{
		return paramStyle;
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
	 * @see com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem#findChild(java.lang.String)
	 *
	 * @param name
	 * @return
	 */
	@Override
	public SwaggerMapperTreeItem findChild(String name)
	{
		List< ? > children = getChildren();

		if (children != null)
		{
			for (Object child : children)
			{
				if (child instanceof SwaggerMapperTreeItem)
				{
					SwaggerMapperTreeItem treeItem = (SwaggerMapperTreeItem) child;

					if (name.equals(treeItem.getPath()))
					{
						return treeItem;
					}
				}
			}
		}

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
		result = prime * result + ((paramStyle == null) ? 0 : paramStyle.hashCode());
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
		if (getClass() != obj.getClass()) return false;
		SwaggerParamContainerTreeItem other = (SwaggerParamContainerTreeItem) obj;

		if (!XpdUtil.safeEquals(activity, other.activity)) return false;
		if (!XpdUtil.safeEquals(direction, other.direction)) return false;
		if (!XpdUtil.safeEquals(paramStyle, other.paramStyle)) return false;
		if (!XpdUtil.safeEquals(getParent(), other.getParent())) return false;

		return true;
	}

}
