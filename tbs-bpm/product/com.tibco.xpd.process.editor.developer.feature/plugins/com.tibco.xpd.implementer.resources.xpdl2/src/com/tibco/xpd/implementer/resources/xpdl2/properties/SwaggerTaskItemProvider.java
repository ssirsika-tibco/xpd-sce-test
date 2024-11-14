/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerContainerTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.swagger.SwaggerResponseContainerTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ResultError;

import io.swagger.v3.oas.models.Operation;

/**
 * Swagger Service content provider.
 *
 * @author nkelkar
 * @since Sep 3, 2024
 */
public class SwaggerTaskItemProvider implements ITreeContentProvider
{

	private Object[]						topLevelChildren	= null;

	private final ConceptContentProvider	conceptContentProvider;

	private final MappingDirection			direction;

	/**
	 * If the input is a catch error event, this was the last response code we returned (and cached) return content for?
	 */
	private String							previousResponseCode	= null;

	/**
	 * A string containing <OperationId>-<ServiceFile>-<ActivityFile>
	 */
	private String							cachedServiceRef	= null;

	/**
	 * 
	 */
	public SwaggerTaskItemProvider(MappingDirection direction)
	{
		this.direction = direction;
		final boolean isSourceMapping = true;
		conceptContentProvider = new ConceptContentProvider(direction, isSourceMapping, true);
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 * 
	 * @param viewer
	 * @param oldInput
	 * @param newInput
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		topLevelChildren = null;
		cachedServiceRef = null;
		previousResponseCode = null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 * 
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement)
	{
		return getChildren(inputElement);
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 * 
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(Object parentElement)
	{
		if (parentElement instanceof Activity)
		{
			Activity activity = (Activity) parentElement;
			RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();

			/*
			 * Check if input activity or selected service has changed.
			 */
			String currentServiceRef = rsta.getServiceRefKey(activity);

			if (!XpdUtil.safeEquals(currentServiceRef, cachedServiceRef))
			{
				topLevelChildren = null;
				cachedServiceRef = currentServiceRef;
			}

			if (rsta.isCatchEvent(activity))
			{
				/*
				 * If the response code caught by catch error event has changed sinbce we previously cached content,
				 * then throw cache away.
				 */
				ResultError resultError = EventObjectUtil.getResultError(activity);

				if (resultError != null)
				{
					String latestResponseCode = resultError.getErrorCode();

					if (topLevelChildren != null && !Objects.equals(latestResponseCode, previousResponseCode))
					{
						topLevelChildren = null;
					}

					previousResponseCode = latestResponseCode;
				}
			}

			/* 
			 * Return the current cache if not already cached and caceh not reset above
			 */
			if (topLevelChildren != null)
			{
				return topLevelChildren;
			}
			
			if (rsta.isCatchEvent(activity))
			{
				// May be a Catch for a REST fault.
				Activity thrower = rsta.getThrowerActivity(activity);
				if (thrower != null)
				{
					ArrayList<SwaggerMapperTreeItem> children = getTopServiceCatchChildren(activity, thrower);

					topLevelChildren = children.toArray();

					return topLevelChildren;
				}
			}
			else
			{
				Operation rsoOperation = rsta.getRSOOperation(activity);
				if (rsoOperation != null)
				{

					Collection< ? > children = (direction == MappingDirection.IN)
							? getTopServiceInChildren(activity)
							: getTopServiceOutChildren(activity);

					topLevelChildren = children.toArray();

					return topLevelChildren;
				}
			}

		}
		else if (parentElement instanceof SwaggerMapperTreeItem)
		{
			return ((SwaggerMapperTreeItem) parentElement).getChildren().toArray();
		}
		return new Object[0];
	}

	private Collection<SwaggerContainerTreeItem> getTopServiceInChildren(Activity activity)
	{
		RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();

		return factory.createSwaggerRequestItems(activity);
	}

	private Collection<SwaggerResponseContainerTreeItem> getTopServiceOutChildren(Activity activity)
	{
		RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();

		// Create a Tree Item for each Response Code
		return factory.createSwaggerResponseItems(activity);
	}

	private ArrayList<SwaggerMapperTreeItem> getTopServiceCatchChildren(Activity activity, Activity thrower)
	{
		ArrayList<SwaggerMapperTreeItem> children = new ArrayList<>();

		RestMapperTreeItemFactory factory = RestMapperTreeItemFactory.getInstance();
		String code = factory.getCaughtErrorCode(activity);

		// Add Payload Container
		SwaggerMapperTreeItem item =
                factory.createSwaggerCatchPesponseContainerTreeItem(activity, thrower, code);
        if (item != null) {
            children.add(item);
        }

		return children;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 * 
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(Object element)
	{
		SwaggerMapperTreeItem parent = null;

		if (element instanceof SwaggerMapperTreeItem)
		{
			parent = ((SwaggerMapperTreeItem) element).getParent();
		}

		return parent;
	}

	/**
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(Object element)
	{
		if (element instanceof SwaggerMapperTreeItem)
		{
			return ((SwaggerMapperTreeItem) element).hasChildren();
		}
		return getChildren(element).length != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose()
	{
		// Do nothing!
	}


}
