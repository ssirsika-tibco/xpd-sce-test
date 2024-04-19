/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.indexer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunctionParamCategories;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer Provider which indexes all Process Script Library Function Parameters by name and also stores other param
 * attributes
 *
 * @author nkelkar
 * @since Feb 26, 2024
 */
@SuppressWarnings("nls")
public class ProcessScriptLibraryIndexProvider extends AbstractXpdl2ResourceIndexProvider
{

	/**
	 * Enum to determine the type of an index based on the 
	 * target environment of a function
	 * 
	 */
	public enum IndexType
	{
		PSL_PE_PARAM_REF("PSL_PE_PARAM_REF"),
		PSL_WM_PARAM_REF("PSL_WM_PARAM_REF"),
		PSL_ALL_PARAM_REF("PSL_ALL_PARAM_REF"),
		PSL_DEFAULT_PARAM_REF("PSL_DEFAULT_PARAM_REF");

		private String type;

		/**
		 * @param string
		 */
		IndexType(String aType)
		{
			this.type = aType;
		}

		public String getType()
		{
			return type;
		}
	}

	// Indexer ID
	public static final String	PSL_INDEXER_ID						= "com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider";

	// Indexer columns
	public static final String	PSL_PROJECT_NAME					= "project_name";

	public static final String	PSL_LIBRARY_NAME					= "library_name";

	public static final String	PSL_FUNCTION_NAME					= "function_name";

	public static final String	PSL_FUNCTION_DESCRIPTION			= "function_description";

	public static final String	PSL_FUNCTION_PARAM_NAME				= "param_name";

	public static final String	PSL_FUNCTION_PARAM_DESCRIPTION		= "param_description";

	public static final String	PSL_FUNCTION_PARAM_INDEX			= "param_index";

	public static final String	PSL_FUNCTION_PARAM_IS_ARRAY			= "param_is_array";

	public static final String	PSL_FUNCTION_PARAM_LENGTH			= "param_length";

	public static final String	PSL_FUNCTION_PARAM_DECIMALS			= "param_decimals";

	public static final String	PSL_FUNCTION_PARAM_TYPE_ID			= "param_type_id";

	public static final String	PSL_FUNCTION_PARAM_TYPE_CATEGORY	= "param_type_category";
	
	private static final String	RETURN_PARAM_TYPE_NAME				= "$RETURN";
	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#addIndexedItemsForPackage(java.util.ArrayList,
	 *      java.lang.String, java.lang.String, com.tibco.xpd.xpdl2.Package)
	 *
	 * @param indexedItems
	 * @param projectName
	 * @param xpdlPath
	 * @param pkg
	 */
	@Override
	protected void addIndexedItemsForPackage(ArrayList<IndexerItem> indexedItems, String projectName, String pslPath,
			Package pkg)
	{
		IFile library = WorkingCopyUtil.getFile(pkg);
		String libraryName = library.getName();

		EList<Process> processes = pkg.getProcesses();

		for (Process process : processes)
		{
			Collection<Activity> allActivitiesInProc = Xpdl2ModelUtil.getAllActivitiesInProc(process);
			for (Activity activity : allActivitiesInProc)
			{
				EList<DataField> functionParams = activity.getDataFields();
				int index = 0;
				for (DataField dataField : functionParams)
				{
					// For a return type field, save the index as -1;
					// and increment index for params only, not return type
					int paramIndex = RETURN_PARAM_TYPE_NAME.equals(dataField.getName()) ? -1 : index++;
					IndexerItem item = indexPSLFunctionParam(projectName, libraryName,
							activity, dataField, paramIndex);
					indexedItems.add(item);
				}

			}
		}

	}

	/**
	 * Creates an index record for every parameter in a PSL function
	 * 
	 * @param projectName
	 * @param libraryName
	 * @param functionName
	 * @param dataField
	 * @param paramIndex
	 * @return
	 */
	private IndexerItem indexPSLFunctionParam(String projectName, String libraryName, Activity activity,
			DataField dataField, int paramIndex)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		DataType dataType = dataField.getDataType();
		String paramName = dataField.getName();

		map.put(PSL_PROJECT_NAME, truncateString(projectName, 100));
		map.put(PSL_LIBRARY_NAME, truncateString(libraryName, 100));
		map.put(PSL_FUNCTION_NAME, truncateString(activity.getName(), 100));

		// Only add the function description for the $RETURN param in the function
		if (RETURN_PARAM_TYPE_NAME.equals(paramName))
		{
			map.put(PSL_FUNCTION_DESCRIPTION, truncateString(activity.getDescription().getValue(), 1024));
		}

		map.put(PSL_FUNCTION_PARAM_NAME, truncateString(paramName, 100));
		map.put(PSL_FUNCTION_PARAM_DESCRIPTION,
				truncateString(dataField.getDescription() != null ? dataField.getDescription().getValue() : "", 1024));
		map.put(PSL_FUNCTION_PARAM_INDEX, String.valueOf(paramIndex));
		map.put(PSL_FUNCTION_PARAM_IS_ARRAY, String.valueOf(dataField.isIsArray()));
		
		Object baseType = BasicTypeConverterFactory.INSTANCE.getBaseType(dataType, true);

		map.put(PSL_FUNCTION_PARAM_TYPE_CATEGORY, getTypeCategoryString(dataType, baseType));
		if (baseType instanceof BasicType)
		{
			BasicType basicType = (BasicType) baseType;
			map.put(PSL_FUNCTION_PARAM_LENGTH,
					basicType.getLength() != null ? basicType.getLength().getValue() : null);
			map.put(PSL_FUNCTION_PARAM_DECIMALS,
					basicType.getScale() != null ? String.valueOf(basicType.getScale().getValue()) : null);
		}
		else if (baseType instanceof Class)
		{
			// For an external Class reference, store the url ref to the class as Type ID
			Class baseClass = (Class) baseType;
			map.put(PSL_FUNCTION_PARAM_TYPE_ID, ProcessUIUtil.getURIString(baseClass, true));
		}

		return new IndexerItemImpl(paramName, getIndexType(activity),
				ProcessUIUtil.getURIString(dataField, true), map);
	}

	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#shouldReIndexForObject(java.lang.Object,
	 *      org.eclipse.emf.common.notify.Notification)
	 *
	 * @param o
	 * @param notification
	 * @return
	 */
	@Override
	protected boolean shouldReIndexForObject(Object o, Notification notification)
	{
		boolean shouldReIndex = false;
		Object feature = notification.getFeature();

		if (o instanceof EObject)
		{
			EObject dataFieldAncestor = Xpdl2ModelUtil.getAncestor((EObject) o, DataField.class);

			// If a new Function param (DataField) is added, removed
			// or changed (type, name, isArray, length, decimals)
			if (dataFieldAncestor != null)
			{
				shouldReIndex = true;
			}
		}

		if (isAddActivity(notification) || isRemoveActivity(notification))
		{
			// If a new Function is added/removed
			shouldReIndex = true;
		}
		else if (feature instanceof EAttribute)
		{
			// If the Name, Description or Target environment properties of a function have been updated
			EAttribute att = (EAttribute) feature;
			if (att == Xpdl2Package.eINSTANCE.getNamedElement_Name()
					|| att == Xpdl2Package.eINSTANCE.getDescription_Value()
					|| att == XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn())
			{
				shouldReIndex = true;
			}
		}
		return shouldReIndex;
	}

	/**
	 * Determines if the passed notification is raised after an activity is added to a PSL file
	 * 
	 * @param notification
	 * @return The activity that was added by change the notification or <code>null</code> if no activity was affected
	 */
	private boolean isAddActivity(Notification notification)
	{
		if (Notification.ADD == notification.getEventType()
				&& Xpdl2Package.eINSTANCE.getFlowContainer_Activities().equals(notification.getFeature()))
		{
			if (notification.getNewValue() instanceof Activity)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if the passed notification is raised after an activity is removed from a PSL file
	 * 
	 * @param notification
	 * @return The activity that was removed by the change notification or <code>null</code> if no activity was affected
	 */
	private boolean isRemoveActivity(Notification notification)
	{
		if (Notification.REMOVE == notification.getEventType()
				&& Xpdl2Package.eINSTANCE.getFlowContainer_Activities().equals(notification.getFeature()))
		{
			if (notification.getOldValue() instanceof Activity)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Truncates a given string to the specified length
	 * 
	 * @param text
	 * @param maxLength
	 * @return
	 */
	private String truncateString(String text, int maxLength)
	{
		if (text.length() <= maxLength)
		{
			return text;
		}
		else
		{
			return text.substring(0, maxLength);
		}
	}

	/**
	 * Returns the string representation for data type name.
	 * 
	 * @param dataField
	 * @param baseType
	 * @return string representation for data type name.
	 */
	private String getTypeCategoryString(DataType dataType, Object baseType)
	{
		return ProcessScriptFunctionParamCategories.fromType(dataType, baseType).toConstantString();
	}

	/**
	 * Returns a string representing an Index Type based on the 
	 * target environment of the referencing function(activity)
	 * 
	 * @param aActivity
	 * @return
	 */
	private String getIndexType(Activity aActivity)
	{
		if (aActivity != null)
		{
			LibraryFunctionUseIn useIn = (LibraryFunctionUseIn) Xpdl2ModelUtil.getOtherAttribute(aActivity,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn());

			if (LibraryFunctionUseIn.PROCESS_MANAGER.equals(useIn))
			{
				return IndexType.PSL_PE_PARAM_REF.getType();
			}
			else if (LibraryFunctionUseIn.WORK_MANAGER.equals(useIn))
			{
				return IndexType.PSL_WM_PARAM_REF.getType();
			}
			else if (LibraryFunctionUseIn.ALL.equals(useIn))
			{
				return IndexType.PSL_ALL_PARAM_REF.getType();
			}
		}
		return IndexType.PSL_DEFAULT_PARAM_REF.getType();

	}
}
