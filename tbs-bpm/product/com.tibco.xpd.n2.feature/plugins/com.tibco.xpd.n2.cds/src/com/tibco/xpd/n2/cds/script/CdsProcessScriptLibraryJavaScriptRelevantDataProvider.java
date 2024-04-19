/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.process.js.model.BpmScriptWrapperFactory;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.ui.internal.AbstractScriptRelevantDataProvider;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Default implementation for javascript of the Abstract class for Process Script Library functions
 * {@link AbstractScriptRelevantDataProvider}.
 *
 * @author nkelkar
 * @since Feb 1, 2024
 */
public class CdsProcessScriptLibraryJavaScriptRelevantDataProvider extends CdsDefaultJavaScriptRelevantDataProvider
{
	private static final String RETURN_DATA_TYPE_NAME = "$RETURN";
	/**
	 * 
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
	 *
	 * @return
	 */
	@Override
	public List<IScriptRelevantData> getScriptRelevantDataList()
	{
		List<IScriptRelevantData> results = new ArrayList<IScriptRelevantData>();

		List<ProcessRelevantData> processDataList = getAssociatedProcessRelevantData();
		if (!processDataList.isEmpty())
		{
			results.addAll(convertToScriptRelevantData(processDataList));
		}

		// Find the index type from UseIn value
		LibraryFunctionUseIn useIn = (LibraryFunctionUseIn) Xpdl2ModelUtil.getOtherAttribute(getActivity(),
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn());

		IndexType indexType = IndexType.PSL_ALL_PARAM_REF;

		if (LibraryFunctionUseIn.PROCESS_MANAGER.equals(useIn))
		{
			indexType = IndexType.PSL_PE_PARAM_REF;
		}
		else if (LibraryFunctionUseIn.WORK_MANAGER.equals(useIn))
		{
			indexType = IndexType.PSL_WM_PARAM_REF;
		}

		// ACE-7400 : Add 'bpmScript' specific relevant data.
		results.add(BpmScriptWrapperFactory.getDefault()
				.createBpmScriptWrapper(ReservedWords.PROCESS_SCRIPT_LIBRARY_WRAPPER_NAME, indexType));

		return results;
	}

	/**
	 * 
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getAssociatedProcessRelevantData()
	 *
	 * @return
	 */
	@Override
	protected List<ProcessRelevantData> getAssociatedProcessRelevantData()
	{
		Collection<DataField> activityDataList = new ArrayList<DataField>();
		List<ProcessRelevantData> processDataList = new ArrayList<ProcessRelevantData>();

		Activity activity = getActivity();
		if (activity != null)
		{
			// ACE-7398 Make only the Process Script Function parameters available
			// in the function script content assist.
			// Ensure that the data object is not shown
			activityDataList = activity.getDataFields();

			for (Iterator iterator = activityDataList.iterator(); iterator.hasNext();)
			{
				ProcessRelevantData processRelevantData = (ProcessRelevantData) iterator.next();
				// ACE-7398 Filter out the $RETURN field from content assist
				if (!RETURN_DATA_TYPE_NAME.equals(processRelevantData.getName()))
				{
					processDataList.add(processRelevantData);
				}
			}
		}
		return processDataList;
	}
}
