/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import java.util.ArrayList;
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

	private List<ProcessScriptFunctionParam>	paramsWithoutReturn	= new ArrayList<>();

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
		getChildren().add(newPslFunctionParam);
		if (!newPslFunctionParam.isReturnParam())
		{
			paramsWithoutReturn.add(newPslFunctionParam);
		}
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

		for (int i = 0; i < paramsWithoutReturn.size(); i++)
		{

			ProcessScriptFunctionParam param = paramsWithoutReturn.get(i);
			syntax.append(param.getName());
			if (i < paramsWithoutReturn.size() - 1)
			{
				syntax.append(", "); //$NON-NLS-1$
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
}
