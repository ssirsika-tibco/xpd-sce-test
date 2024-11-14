/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.swagger.datamapper;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMappingPrefix;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerInputParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPayloadTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerPropertyType;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseParamContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerTypedTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.UnsupportedSwaggerTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping rule info content provider for validation of Swagger mapping content.
 *
 * @author nkelkar
 * @since Sep 18, 2024
 */
@SuppressWarnings("nls")
public class SwaggerDataMapperInfoProvider extends AbstractDataMapperInfoProvider
{

	private MappingDirection direction;

	/**
	 * @param direction
	 *            The mapping direction.
	 * @param contentProvider
	 *            The content provider.
	 * @param labelProvider
	 *            The label provider.
	 * @param caching
	 *            true to enable caching, can be turned on for rules.
	 */
	public SwaggerDataMapperInfoProvider(MappingDirection direction, ITreeContentProvider contentProvider,
			ILabelProvider labelProvider, boolean caching)
	{
		super(contentProvider, labelProvider, caching);
		this.direction = direction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getObjectPath(Object o)
	{
		if (o instanceof SwaggerPayloadTreeItem)
		{
			return ((SwaggerPayloadTreeItem) o).getPath();
		}
		else if (o instanceof SwaggerParamTreeItem)
		{
			return ((SwaggerParamTreeItem) o).getPath();
		}
		/* Sid ACE-885 support path for unsupported items, so that error reporting works the same as any other content */ 
		else if (o instanceof UnsupportedSwaggerTreeItem)
		{
			return ((UnsupportedSwaggerTreeItem) o).getPath();
		}
		else if (o instanceof ScriptInformation)
		{
			return ((ScriptInformation) o).getName();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getObjectPathDescription(Object o)
	{
		if (o instanceof SwaggerMapperTreeItem)
		{
			SwaggerMapperTreeItem item = (SwaggerMapperTreeItem) o;
			SwaggerMapperTreeItem parent = item.getParent();

			String itemPath = (item instanceof SwaggerTypedTreeItem)
					? ((SwaggerTypedTreeItem) item).getLabelWithoutType()
					: item.getLabel();

			if (parent != null)
			{
				return getObjectPathDescription(parent) + "." + itemPath; //$NON-NLS-1$
			}
			else
			{
				return itemPath;
			}
		}
		else if (o instanceof ScriptInformation)
		{
			return ((ScriptInformation) o).getName();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isReadOnlyTarget(Object targetObjectInTree)
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isMultiInstance(Object objectInTree)
	{
		if (objectInTree instanceof SwaggerPayloadTreeItem)
		{
			SwaggerPayloadTreeItem item = (SwaggerPayloadTreeItem) objectInTree;
			return item.isArray();
		}
		else if (objectInTree instanceof SwaggerParamTreeItem)
		{
			SwaggerParamTreeItem item = (SwaggerParamTreeItem) objectInTree;
			return item.isArray();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimumInstances(Object objectInTree)
	{
		int minInstances = 0;

		if (objectInTree instanceof SwaggerInputParamTreeItem)
		{
			minInstances = ((SwaggerInputParamTreeItem) objectInTree).isRequired() ? 1 : 0;
		}
		else if (objectInTree instanceof SwaggerPayloadTreeItem)
		{
			minInstances = ((SwaggerPayloadTreeItem) objectInTree).isRequired() ? 1 : 0;
		}

		return minInstances;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaximumInstances(Object objectInTree)
	{
		if (objectInTree instanceof SwaggerPayloadTreeItem)
		{
			SwaggerPayloadTreeItem item = (SwaggerPayloadTreeItem) objectInTree;
			if (item.isArray())
			{
				return -1;
			}
		}
		else if (objectInTree instanceof SwaggerParamTreeItem)
		{
			SwaggerParamTreeItem item = (SwaggerParamTreeItem) objectInTree;
			if (item.isArray())
			{
				return -1;
			}
		}
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getInstanceIndex(Object objectInTree)
	{
		// no Instance index specified
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSimpleTypeContent(Object objectInTree)
	{
		if (objectInTree instanceof SwaggerTypedTreeItem)
		{
			return ((SwaggerTypedTreeItem) objectInTree).isSimpleType();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNullSimpleContentAllowed(Object objectInTree)
	{
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isArtificialObject(Object objectInTree)
	{
		if (objectInTree instanceof SwaggerPayloadTreeItem || objectInTree instanceof SwaggerParamTreeItem)
		{
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isChoiceObject(Object objectFromMappingOrContent)
	{
		return false;
	}

	@Override
	public boolean isValidForMapping(Object objectInTree) {
		// ACE-8787 : Unsupported swagger data can not participate in the mapping. So return false.
		if (objectInTree instanceof UnsupportedSwaggerTreeItem) {
			return false;
		}
		/*
		 * Sid ACE-8885 Handle content types that are unsupported - this will prevent mapping to / from unsupported
		 * items in the first place - so there is no need to val
		 */
		else if (objectInTree instanceof SwaggerTypedTreeItem)
		{
			SwaggerPropertyType type = ((SwaggerTypedTreeItem) objectInTree).getType();

			if (type.isUnsupported())
			{
				return false;
			}
		}
		return super.isValidForMapping(objectInTree);
	}
	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectForPath(java.lang.Object,
	 *      java.lang.Object)
	 * 
	 * @param objectPath
	 * @param mapperInput
	 * @return
	 */
	@Override
	public Object getObjectForPath(String objectPath, Object mapperInput)
	{
		Object o = null;

		if (mapperInput instanceof Activity)
		{
			Activity activity = (Activity) mapperInput;

			Object[] allChildren = this.getContentProvider().getChildren(activity);

			if (allChildren != null && allChildren.length > 0)
			{
				String[] pathElements = objectPath.split("\\.");

				SwaggerMapperTreeItem containerTreeItem = findContainerTreeItem(Arrays.asList(allChildren),
						pathElements);
				if (containerTreeItem != null && containerTreeItem.hasChildren())
				{
					o = findChildByPath(containerTreeItem, pathElements, 0, "      ");
				}				
			}

		}

		return o;
	}

	/**
	 * The top level 'folders' arent included in the dot-separated path. So this function resolves first part of the
	 * path to a container where the object will be found.
	 * 
	 * @param allContainers
	 * @param pathElements
	 * 
	 * @return the container of the rest
	 */
	private SwaggerMapperTreeItem findContainerTreeItem(List< ? > allContainers, String[] pathElements)
	{
		SwaggerMapperTreeItem returnItem = null;

		String firstElement = pathElements[0];

		for (Object aContainer : allContainers)
		{
			if (aContainer instanceof SwaggerParamContainerTreeItem)
			{
				SwaggerParamContainerTreeItem containerItem = (SwaggerParamContainerTreeItem) aContainer;
				boolean isPathParamContainer = RestMappingPrefix.PATH_PARAM.startsWithPrefix(firstElement)
						&& ParameterStyle.PATH.equals(containerItem.getParamStyle());

				boolean isQueryParamContainer = RestMappingPrefix.QUERY_PARAM.startsWithPrefix(firstElement)
						&& ParameterStyle.QUERY.equals(containerItem.getParamStyle());

				boolean isHeaderParamContainer = RestMappingPrefix.HEADER_PARAM.startsWithPrefix(firstElement)
						&& ParameterStyle.HEADER.equals(containerItem.getParamStyle());

				if (isPathParamContainer || isQueryParamContainer || isHeaderParamContainer)
				{
					returnItem = containerItem;
					break;
				}
			}
			else if (aContainer instanceof SwaggerPayloadContainerTreeItem)
			{
				SwaggerPayloadContainerTreeItem containerItem = (SwaggerPayloadContainerTreeItem) aContainer;
				if (RestMappingPrefix.PAYLOAD.startsWithPrefix(firstElement))
				{
					returnItem = containerItem;
					break;
				}
			}
			else if (aContainer instanceof SwaggerResponseContainerTreeItem)
			{
				SwaggerResponseContainerTreeItem containerItem = (SwaggerResponseContainerTreeItem) aContainer;
				String statusCode = containerItem.getStatusCode();

				/*
				 * For Response items, need to return the Header Parameters or Payload container under the top level
				 * response code container.
				 */
				if (firstElement.startsWith(RestMappingPrefix.HEADER_PARAM.addPrefix(statusCode)))
				{
					for (Object child : containerItem.getChildren())
					{
						if (child instanceof SwaggerResponseParamContainerTreeItem)
						{
							returnItem = (SwaggerResponseParamContainerTreeItem) child;
							break;
						}
					}
				}
				else if (firstElement.startsWith(RestMappingPrefix.PAYLOAD.addPrefix("_" + statusCode)))
				{
					for (Object child : containerItem.getChildren())
					{
						if (child instanceof SwaggerPayloadContainerTreeItem)
						{
							returnItem = (SwaggerPayloadContainerTreeItem) child;
							break;
						}
					}
				}

				if (returnItem != null)
				{
					break;
				}
			}
		}

		return returnItem;
	}
	
	/**
	 * Find a child object given a list of children for a particular container
	 * 
	 * @param list
	 * @param pathElements
	 * 
	 * @return child item or null if no such child under this parent.
	 */
	private SwaggerMapperTreeItem findChildByPath(SwaggerMapperTreeItem parent, String[] pathElements, int index,
			String indent)
	{
		String lookingFor = pathElements[index];

		SwaggerMapperTreeItem foundChild = parent.findChild(lookingFor);

		if (foundChild != null)
		{
			/* If this is the leaf-node then we're done, return the item up thru recursion stack */
			if (index == (pathElements.length - 1))
			{
				return foundChild;
			}
			else
			{
				/* Recurse to find next path element within the children of this item. */
				return findChildByPath(foundChild, pathElements, index + 1, indent + "  ");
			}
		}

		return null;
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectName(java.lang.Object)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public String getObjectName(Object object)
	{
		if (object instanceof SwaggerMapperTreeItem)
		{
			return ((SwaggerMapperTreeItem) object).getText();
		}
		return getLabelProvider().getText(object);
	}

	/**
	 * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectType(java.lang.Object)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public String getObjectType(Object object)
	{
		if (object instanceof SwaggerTypedTreeItem)
		{
			//
			// TODO: This should probably be qualified by something related to the schema in in case we have colliding
			// BOM/swagger type names.
			return ((SwaggerTypedTreeItem) object).getTypeName();
		}
		return null;
	}

}
