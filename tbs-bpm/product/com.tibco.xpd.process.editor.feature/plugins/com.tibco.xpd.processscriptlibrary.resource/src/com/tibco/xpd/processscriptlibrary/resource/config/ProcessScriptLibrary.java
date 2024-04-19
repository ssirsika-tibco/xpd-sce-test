/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.config;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Class representing a Process Script Library in a PSL Project. A Script Library has a name and a list of process
 * script functions.
 *
 * @author nkelkar
 * @since Feb 23, 2024
 */
public class ProcessScriptLibrary extends AbstractProcessScriptLibraryElement
{
	private List<ProcessScriptFunction>	scriptFunctions	= new ArrayList<>();

	private ProcessScriptLibraryProject	processScriptLibraryProject;

	private String						nameWithoutExt;

	/**
	 * @param aLibraryName
	 *            the aLibraryName to set
	 * @param aProcessScriptLibraryProject
	 *            Process script library project
	 */
	public ProcessScriptLibrary(String aLibraryName, ProcessScriptLibraryProject aProcessScriptLibraryProject)
	{
		this.name = aLibraryName;
		this.nameWithoutExt = removePSLExtension(name);
		this.processScriptLibraryProject = aProcessScriptLibraryProject;
	}

	/**
	 * 
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.AbstractProcessScriptLibraryElement#getChildren()
	 *
	 * @return
	 */
	@Override
	public List<ProcessScriptFunction> getChildren()
	{
		return scriptFunctions;
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

		for (ProcessScriptFunction processScriptFunction : getChildren())
		{
			processScriptFunction.accept(aVisitor, result);
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
	public ProcessScriptFunction addChild(String aChildName, IndexerItem aIndexerItem)
	{
		ProcessScriptFunction newPslFunction = new ProcessScriptFunction(aChildName, aIndexerItem, this);
		getChildren().add(newPslFunction);

		return newPslFunction;
	}

	/**
	 * @return the processScriptLibraryProject
	 */
	public ProcessScriptLibraryProject getProcessScriptLibraryProject()
	{
		return processScriptLibraryProject;
	}

	/**
	 * @return the nameWithoutExt
	 */
	public String getNameWithoutExtension()
	{
		return nameWithoutExt;
	}

	/**
	 * Removes the psl extension from the file name.
	 *
	 * @param fileName
	 *            The name of the file.
	 * @return The file name without the extension.
	 */
	private String removePSLExtension(String fileName)
	{
		return PslEditorUtil.removePSLExtension(fileName);
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return "ProcessScriptLibrary : " + name; //$NON-NLS-1$
	}
}
