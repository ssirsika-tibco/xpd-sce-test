/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.config;

import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider;
import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Class representing a PSL Function Parameter in a Script Library. A Script function parameter has a name and other
 * attributes like length, isArray, decimals, etc
 *
 * @author nkelkar
 * @since Feb 23, 2024
 */
public class ProcessScriptFunctionParam implements ProcessScriptLibraryElement
{
	private String	name;

	private String	description;

	/**
	 * Represents the index (position) of the param in a PSL function
	 */
	private int										index;

	private String	length;

	private boolean	isArray;

	private String	decimals;

	/**
	 * URI string representing the path to a Class 
	 * when the data type of a param is an external reference
	 */
	private String	typeId;

	/**
	 * Function parameter type category
	 */
	private ProcessScriptFunctionParamCategories	typeCategory;

	public ProcessScriptFunctionParam(String aName)
	{
		this.name = aName;
	}

	/**
	 * 
	 * @param aName
	 * @param aIndexerItem
	 */
	public ProcessScriptFunctionParam(String aName, IndexerItem aIndexerItem)
	{
		this(aName);

		this.description = aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_DESCRIPTION);

		/* Sid ACE-8226 Should tgreat index as an integer as we only use it to sort parameters into correct order. */
		this.index = Integer.parseInt(aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_INDEX));

		this.isArray = Boolean
				.parseBoolean(aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_IS_ARRAY));
		this.length = aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_LENGTH);
		this.decimals = aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_DECIMALS);
		this.typeId = aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_TYPE_ID);
		this.typeCategory = ProcessScriptFunctionParamCategories
				.valueOfConstant(aIndexerItem.get(ProcessScriptLibraryIndexProvider.PSL_FUNCTION_PARAM_TYPE_CATEGORY));
	}

	/**
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryElement#getName()
	 *
	 * @return
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryElement#accept(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor)
	 *
	 * @param aVisitor
	 */
	@Override
	public void accept(ProcessScriptLibraryVisitor aVisitor, Object aContext)
	{
		aVisitor.visit(this, aContext);
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @return the length
	 */
	public String getLength()
	{
		return length;
	}

	/**
	 * @return the isArray
	 */
	public boolean isArray()
	{
		return isArray;
	}

	/**
	 * @return the decimals
	 */
	public String getDecimals()
	{
		return decimals;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId()
	{
		return typeId;
	}

	/**
	 * @return the typeCategory
	 */
	public ProcessScriptFunctionParamCategories getTypeCategory()
	{
		return typeCategory;
	}

	/**
	 * @return <code>true</code> if return parameter otherwise <code>false</code>.
	 * 
	 */
	public boolean isReturnParam()
	{
		return PslEditorUtil.RETURN_PARAMETER_NAME.equals(getName());
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "ProcessScriptFunctionParam [name=" + name + ", index=" + index + ", isArray=" + isArray
				+ ", typeCategory=" + typeCategory + "]";
	}

}
