/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.n2.cds.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.process.js.model.AceScriptProcessDataWrapperFactory;
import com.tibco.xpd.process.js.model.BpmScriptWrapperFactory;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
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
			/*
			 * Sid ACE-8452 Need to do some extra furtling with script relevant data for PSL function parameters so
			 * created function to deal with it.
			 */
			List<IScriptRelevantData> scriptRelevantDataList = createPslParameterScriptRelevantData(processDataList);

			results.addAll(scriptRelevantDataList);
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
	 * Create tehe Script Relevant Data objects (representation of data fields in th script validation/content assist
	 * frameworks) for PSL function parameter.
	 * 
	 * Sid ACE-8452 Need to do some extra furtling with script relevant data for PSL function parameters so created
	 * function to deal with it.
	 * 
	 * @param processDataList
	 * @return
	 */
	private List<IScriptRelevantData> createPslParameterScriptRelevantData(List<ProcessRelevantData> processDataList)
	{
		/*
		 * Sid ACE-8452 Provide the additional ExitInfo that is usually provided for the type of UML attributes that
		 * are included under the "data" in process data.
		 * 
		 * Why? In 5.x we had already moved to 'all data fields are under the 'data' wrapper class BEFORE we changed
		 * to use JavaScript arrays instead of EMF lists).
		 * 
		 * So all of the Generic (T<>) type checking for array methods for example that was introduced for array
		 * fields in 5.x were based on ARRAY ATTRIBUTES under within the 'data' UML Class wrapper that wraps all
		 * process data. Much of this code is based on the presences of a 'DefaultJsAttribute' class, with lots of
		 * additional type info, being added to the ExtInfo of the script relevantdata object that represents the
		 * datafield in scripts.
		 * 
		 * So rather than trying to replicate all of that for tope level fields, we now...
		 * 
		 * Create a dummy data wrapper class for all of the PSL function params. This gives us the same kind
		 * DefaultjsAttribute objects that are created for process data fields.
		 * 
		 * Then we can grab the ExtInfo from each attribute within the dummy wrapper class and add it to the
		 * ScriptRelevant data created for the top level PSL parameter. This will then ensure that the appropriate
		 * extension info for the given param type and multiplicity etc is present for all of the script
		 * parsing/validation code to use in the same way as it does for process data.
		 */
		IScriptRelevantData dataWrapper = AceScriptProcessDataWrapperFactory.getDefault()
				.createProcessDataWrapper("$PSL_DUMMY_WRAPPER", processDataList); //$NON-NLS-1$

		JsClass jsClass = ((com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData) dataWrapper)
				.getJsClass();

		List<JsAttribute> attributeList = ((DefaultJsClass) jsClass).getAttributeList(true);

		List<IScriptRelevantData> scriptRelevantDataList = convertToScriptRelevantData(processDataList);

		for (IScriptRelevantData data : scriptRelevantDataList)
		{
			String name = data.getName();

			for (JsAttribute attribute : attributeList)
			{
				if (name.equals(attribute.getName()))
				{
					((ITypeResolution) data).setExtendedInfo(attribute);

					break;
				}
			}

		}
		return scriptRelevantDataList;
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
