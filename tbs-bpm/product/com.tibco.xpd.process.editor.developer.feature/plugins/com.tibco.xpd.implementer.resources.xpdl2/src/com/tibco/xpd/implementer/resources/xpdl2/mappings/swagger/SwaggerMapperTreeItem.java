/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.List;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;

/**
 * Abstract tree item used in mapping content providers representing rest service and Swagger mapped elements.
 *
 * @author nkelkar
 * @since Sep 23, 2024
 */
public abstract class SwaggerMapperTreeItem extends RestMapperTreeItem
{
	/**
	 * @return the path of the element
	 */
	public abstract String getPath();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract SwaggerMapperTreeItem getParent();

	/**
	 * Get the full label for the item (name : type)
	 * 
	 * @return Default implementation returns the same as {@link #getText()}
	 */
	public String getLabel()
	{
		return getText();
	}

	/**
	 * Find a child with the given name (not label).
	 * 
	 * Default implementation compares the given name with the value returned by the getText() function of the child
	 * 
	 * @param name
	 * 
	 * @return {@link SwaggerMapperTreeItem} or <code>null</code> if nno child with given name exists.
	 */
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

					if (name.equals(treeItem.getText()))
					{
						return treeItem;
					}
				}
			}
		}

		return null;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return getLabel();
	}
}
