/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunction;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibrary;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryProject;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;

/**
 * ACE-7368 Utility class to get all BPM Process Script Library Projects in the workspace
 *
 * @author nkelkar
 * @since Feb 23, 2024
 */
public class ProcessScriptLibraryUtil
{

	/**
	 * Return a list of BPM Process Script Library projects. But there is following special scenario: if passed
	 * aIndexType is either 'PSL_WM_PARAM_REF' or 'PSL_PE_PARAM_REF' then return {@link ProcessScriptLibraryProject} for
	 * indexType 'PSL_ALL_PARAM_REF' as well.
	 * 
	 * @param aIndexType
	 *            type of an index based on the target environment of a function
	 * @return List of ProcessScriptLibraryProject for passed index type.
	 */
	public static List<ProcessScriptLibraryProject> getProcessScriptProjects(IndexType aIndexType)
	{
		// Create a filter on the index to filter records for a specific target environment
		IndexerItemImpl criteria = new IndexerItemImpl(null, aIndexType.getType(), null, null);

		// Read from index
		Collection<IndexerItem> query = XpdResourcesPlugin.getDefault().getIndexerService()
				.query(ProcessScriptLibraryIndexProvider.PSL_INDEXER_ID, criteria);

		/*
		 * If passed aIndexType is either of type 'PSL_WM_PARAM_REF' or 'PSL_PE_PARAM_REF' then return {@link
		 * ProcessScriptLibraryProject} for indexType 'PSL_ALL_PARAM_REF' as well.
		 */
		if (IndexType.PSL_PE_PARAM_REF.equals(aIndexType) || IndexType.PSL_WM_PARAM_REF.equals(aIndexType))
		{
			criteria = new IndexerItemImpl(null, IndexType.PSL_ALL_PARAM_REF.getType(), null, null);
			query.addAll(XpdResourcesPlugin.getDefault().getIndexerService()
					.query(ProcessScriptLibraryIndexProvider.PSL_INDEXER_ID, criteria));
		}

		HashMap<String, ProcessScriptLibraryProject> pslProjectsMap = new HashMap<>();
		HashMap<String, ProcessScriptFunction> pslFunctionsMap = new HashMap<>();

		for (IndexerItem indexerItem : query)
		{
			String projectName = indexerItem.get(ProcessScriptLibraryIndexProvider.PSL_PROJECT_NAME);
			String libraryName = indexerItem.get(ProcessScriptLibraryIndexProvider.PSL_LIBRARY_NAME);
			String functionName = indexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_NAME);
			String paramName = indexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_NAME);
			
			String functionMapKey = projectName + "." + libraryName + "." + functionName;
			
			ProcessScriptFunction functionEntryInMap = pslFunctionsMap.get(functionMapKey);
			// Check if the combination of "<project>.<library>.<function>" has already been read
			// If yes, then just add the param to the existing function(in map)
			if (functionEntryInMap != null)
			{
				functionEntryInMap.addChild(paramName, indexerItem);

			} else {
				ProcessScriptLibraryProject pslProject = pslProjectsMap.get(projectName);
				if (pslProject == null)
				{
					pslProject = new ProcessScriptLibraryProject(projectName);
					pslProjectsMap.put(projectName, pslProject);
				}
				
				ProcessScriptLibrary scriptLibrary = (ProcessScriptLibrary) pslProject.getOrCreateChild(libraryName,
						indexerItem);

				ProcessScriptFunction pslFunction = (ProcessScriptFunction) scriptLibrary.getOrCreateChild(functionName,
						indexerItem);

				// Add a param to function and also create a new entry
				// for the function(using functionMapKey) in map
				pslFunction.addChild(paramName, indexerItem);
				pslFunctionsMap.put(functionMapKey, pslFunction);
			}

		}

		ArrayList<ProcessScriptLibraryProject> scriptProjects = new ArrayList<ProcessScriptLibraryProject>(
				pslProjectsMap.values());

		return scriptProjects;
	}
}
