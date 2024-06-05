/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider;
import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Class representing a Process Script Library Function in a Script Library. A Script function has a name, return param
 * and a list of params.
 *
 * @author nkelkar
 * @since Feb 23, 2024
 */
public class ProcessScriptFunction extends AbstractProcessScriptLibraryElement
{
	private static final String					DOT_STR				= ".";				//$NON-NLS-1$

	private List<ProcessScriptFunctionParam>	params				= new ArrayList<>();

	/** Sid ACE-8226 Tracks whether params list has changed since it was last sorted. */
	private boolean								paramsListUnsorted	= false;

	private String								description;

	private ProcessScriptLibrary				processScriptLibrary;


	/**
	 * 
	 * @param aName
	 * @param aIndexerItem
	 * @param aProcessScriptLibrary
	 */
	public ProcessScriptFunction(String aName, IndexerItem aIndexerItem, ProcessScriptLibrary aProcessScriptLibrary)
	{
		this.name = aName;
		this.processScriptLibrary = aProcessScriptLibrary;
		this.description = aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_DESCRIPTION);
	}
	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.AbstractProcessScriptLibraryElement#getChildren()
	 *
	 * @return
	 */
	@Override
	public List<ProcessScriptFunctionParam> getChildren()
	{
		/*
		 * Sid ACE-8226 We must sort the parameter list by index because the order in which they are read from indexer
		 * DB is not guaranteed to be the same as they were written.
		 * 
		 * But don't do it every time, just if params list has changed since we sorted.
		 */
		if (paramsListUnsorted)
		{
			params.sort(new ParamSortComparator());
			paramsListUnsorted = false;
		}

		return params;
	}

	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryElement#accept(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor)
	 *
	 * @param aVisitor
	 */
	@Override
	public void accept(ProcessScriptLibraryVisitor aVisitor, Object aContext)
	{
		Object result = aVisitor.visit(this, aContext);

		for (ProcessScriptFunctionParam processScriptFunctionParam : getChildren())
		{
			processScriptFunctionParam.accept(aVisitor, result);
		}
	}

	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.AbstractProcessScriptLibraryElement#addChild(java.lang.String,
	 *      com.tibco.xpd.resources.indexer.IndexerItem)
	 *
	 * @param aChildName
	 * @param aIndexerItem
	 * @return
	 */
	@Override
	public ProcessScriptFunctionParam addChild(String aChildName, IndexerItem aIndexerItem)
	{
		ProcessScriptFunctionParam newPslFunctionParam = new ProcessScriptFunctionParam(aChildName, aIndexerItem);

		/*
		 * Sid ACE-8226 Add to params field directly because getChildren() will sort params and don't want to
		 * continually do that whilst building the list of params.
		 */
		params.add(newPslFunctionParam);

		/*
		 * Tag the list as unsorted, so next getChildren() will sort the parms by index position (and don't keep
		 * separate list of paramsWithoutReturn, we can just sort that out when building the syntax description).
		 */
		paramsListUnsorted = true;

		return newPslFunctionParam;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Return function syntax.
	 * 
	 * @return {@link String}
	 */
	public String getSyntax()
	{
		StringBuilder syntax = new StringBuilder().append(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME)
				.append(DOT_STR).append(processScriptLibrary.getProcessScriptLibraryProject().getName()).append(DOT_STR)
				.append(processScriptLibrary.getNameWithoutExtension()).append(DOT_STR).append(name).append("("); //$NON-NLS-1$

		/*
		 * Sid ACE-8226 Use getChildren() (full params list) and allow for $RETURN param, rather than separately
		 * maintained paramsWithoutReturn list. This makes it much easier to maintain a sorted list of params without
		 * having to worry about 2 separate list sort status.
		 */
		boolean doneFirstParam = false;

		for (ProcessScriptFunctionParam param : getChildren())
		{
			if (!param.isReturnParam())
			{
				if (doneFirstParam)
				{
					syntax.append(", "); //$NON-NLS-1$
				}
				else
				{
					doneFirstParam = true;
				}

				syntax.append(param.getName());
			}
		}
		syntax.append(")"); //$NON-NLS-1$
		return syntax.toString();
	}

	/**
	 * @return the processScriptLibrary
	 */
	public ProcessScriptLibrary getProcessScriptLibrary()
	{
		return processScriptLibrary;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return "ProcessScriptFunction : " + name; //$NON-NLS-1$
	}

	/**
	 * Sid ACE-8226 PSL Function parameter list sorter. Sorts params by index (which is taken from the order they appear
	 * in function datafields definition).
	 *
	 * @author aallway
	 * @since 20 May 2024
	 */
	private final class ParamSortComparator implements Comparator<ProcessScriptFunctionParam>
	{
		@Override
		public int compare(ProcessScriptFunctionParam o1, ProcessScriptFunctionParam o2)
		{
			return o1.getIndex() - o2.getIndex();
		}
	}

}
