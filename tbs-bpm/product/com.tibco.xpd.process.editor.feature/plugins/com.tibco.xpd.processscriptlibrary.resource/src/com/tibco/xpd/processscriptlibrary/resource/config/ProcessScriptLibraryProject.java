/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Class representing a PSL Project in workspace. A PSL Project has a name and a list of process script libraries.
 *
 * @author nkelkar
 * @since Feb 23, 2024
 */
public class ProcessScriptLibraryProject extends AbstractProcessScriptLibraryElement
{
	private List<ProcessScriptLibrary>	scriptLibraries	= new ArrayList<>();

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public ProcessScriptLibraryProject(String aProjectName)
	{
		this.name = aProjectName;
	}

	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.AbstractProcessScriptLibraryElement#getChildren()
	 *
	 * @return
	 */
	@Override
	public List<ProcessScriptLibrary> getChildren()
	{
		return scriptLibraries;
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

		for (ProcessScriptLibrary processScriptLibrary : getChildren())
		{
			processScriptLibrary.accept(aVisitor, result);
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
	public ProcessScriptLibrary addChild(String aChildName, IndexerItem aIndexerItem)
	{
		ProcessScriptLibrary newScriptLibrary = new ProcessScriptLibrary(aChildName, this);
		getChildren().add(newScriptLibrary);

		return newScriptLibrary;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return "ProcessScriptLibraryProject : " + name; //$NON-NLS-1$
	}

}
