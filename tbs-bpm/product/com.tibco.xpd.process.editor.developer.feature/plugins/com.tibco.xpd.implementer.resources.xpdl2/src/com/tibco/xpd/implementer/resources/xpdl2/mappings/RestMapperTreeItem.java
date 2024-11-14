/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.Collections;
import java.util.List;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.resources.util.XpdUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Abstract tree item used in mapping content providers representing rest service mapped elements.
 *
 * @author jarciuch
 * @since 25 Mar 2015
 */
@SuppressWarnings("nls")
public abstract class RestMapperTreeItem
{
	/**
	 * The cached child items - these are kept cached until a change of input activity or selected service
	 */
	private List< ? >	cachedChildren	= null;

	/**
	 * A string containing <OperationId>-<ServiceFile>-<ActivityFile>
	 */
	private String		cachedServiceRef	= null;

	/**
	 * @return the parent of the element or 'null' if it's a root element.
	 */
	public abstract RestMapperTreeItem getParent();

	/**
	 * Get the children of this tree item.
	 * 
	 * The first returned content is cached.
	 * 
	 * @return
	 */
	public final List< ? > getChildren()
	{
		/*
		 * Check if input activity or selected service has changed.
		 */
		String currentServiceRef = getServiceRef();

		if (!XpdUtil.safeEquals(currentServiceRef, cachedServiceRef))
		{
			cachedChildren = null;
			cachedServiceRef = currentServiceRef;
		}

		/*
		 * If we have cached children then return them.
		 */
		if (cachedChildren != null)
		{
			return cachedChildren;
		}

		/*
		 * Otherwise create the cache for the input activity.
		 */
		Activity activity = getActivity();

		if (activity != null)
		{
			cachedChildren = createChildren();

			/* Nicer all round if we always return a list of some kind. */
			if (cachedChildren == null)
			{
				cachedChildren = Collections.emptyList();
			}

			return cachedChildren;
		}

		/* IF activity is null then don't set empty list as cache, in case we get called again after activity set. */
		return Collections.emptyList();
	}

	/**
	 * Create the list of child items - these will be cached.
	 * 
	 * @return a list of children elements.
	 */
	protected abstract List< ? > createChildren();

	/**
	 * Sid ACE-8742  Pulled up hasChildrfen() as this can be done generically here.
	 * @return 'true' if element has children.
	 */
	public boolean hasChildren()
	{
		return !getChildren().isEmpty();
	}

	/**
	 * @return Name used for this element - if a non artifical object it is expected to be the same as the element name
	 *         used in the path.
	 */
	public abstract String getText();

	/**
	 * @return the context activity.
	 */
	public abstract Activity getActivity();

	/**
	 * Get a key to the current service reference in the format...
	 * 
	 * <OperationId>-<ServiceFile>-<ActivityFile>
	 * 
	 * @return a key to the current service reference (or null if no service selected).
	 */
	private String getServiceRef()
	{
		Activity activity = getActivity();

		if (activity != null)
		{
			RestServiceTaskAdapter serviceTaskAdapter = new RestServiceTaskAdapter();

			return serviceTaskAdapter.getServiceRefKey(activity);
		}

		return null;
	}

}
