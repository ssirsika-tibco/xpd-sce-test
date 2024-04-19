/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import java.util.Collection;

import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Abstract class that represents a Process Script Library element
 *
 * @author nkelkar
 * @since Feb 29, 2024
 */
public abstract class AbstractProcessScriptLibraryElement implements ProcessScriptLibraryElement
{
	/**
	 * Every PSL Element has a name
	 */
	protected String name;

	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryElement#getName()
	 *
	 * @return
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * Gets a child from a PSL Element, if not found it creates a new child (of a specific type) by name and returns it
	 * 
	 * @param aChildName
	 * @param aIndexerItem
	 * @return
	 */
	public Object getOrCreateChild(String aChildName, IndexerItem aIndexerItem)
	{
		for (Object aChild : getChildren())
		{
			ProcessScriptLibraryElement pslElement = (ProcessScriptLibraryElement) aChild;
			if (pslElement.getName().equals(aChildName))
			{
				// Return if found
				return pslElement;
			}
		}
		// else create and return a new child
		return addChild(aChildName, aIndexerItem);
	}

	/**
	 * Returns the children of a PSL element
	 * 
	 * @return
	 */
	public abstract Collection<?> getChildren();
	
	/**
	 * Adds and returns a child of a specific type to a PSL element
	 * 
	 * @param aChildName
	 * @param aIndexerItem
	 * @return
	 */
	public abstract ProcessScriptLibraryElement addChild(String aChildName, IndexerItem aIndexerItem);

}
