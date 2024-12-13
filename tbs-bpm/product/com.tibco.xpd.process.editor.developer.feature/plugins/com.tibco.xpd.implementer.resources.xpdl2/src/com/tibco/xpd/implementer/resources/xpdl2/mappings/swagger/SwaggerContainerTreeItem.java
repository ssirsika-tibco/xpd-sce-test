/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A base class in the Swagger...TreeItem class hierarchy for container items.
 * 
 * It, for instance, provides the ability to find all the contained items that represent actual items in the Swagger
 * input / output.
 *
 * @author aallway
 * @since 4 Oct 2024
 */
public abstract class SwaggerContainerTreeItem extends SwaggerMapperTreeItem
{
	/**
	 * Recursively look for the FIRST LEVEL of mappable content items and add them to the given list.
	 * 
	 * This considers any {@link SwaggerTypedTreeItem} to be mappable (because to be mappable, a tree item must have a
	 * type - unlike containers etc).
	 * 
	 * @return list of mappable (i.e. typed) tree items (or empty list if none found)
	 */
	public Collection<SwaggerTypedTreeItem> getMappableContentItems()
	{
		List<SwaggerTypedTreeItem> mappableContentItems = new ArrayList<>();

		List< ? > children = getChildren();

		if (children != null)
		{
			for (Object child : children)
			{
				if (child instanceof SwaggerTypedTreeItem)
				{
					/* It's a tree item with a type, so it must (effectively) be mappable. */
					mappableContentItems.add((SwaggerTypedTreeItem) child);
				}
				else if (child instanceof SwaggerContainerTreeItem)
				{
					/*
					 * We may have multiple levels of container, so can just ask next level for its mappable content
					 * until we get some.
					 */
					mappableContentItems.addAll(((SwaggerContainerTreeItem) child).getMappableContentItems());
				}
			}
		}

		return mappableContentItems;
	}

}
