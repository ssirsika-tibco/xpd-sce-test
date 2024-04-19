/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.properties.general;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.xpdl2.DataField;

/**
 * PslFunctionDataFieldFilter for filtering the Data Fields from the PSL function.
 *
 *
 * @author cbabar
 * @since Feb 5, 2024
 */
public class PslFunctionDataFieldFilter extends ViewerFilter
{

	private boolean				filterReturnType;

	/**
	 * @param filterReturnType
	 * 
	 */
	public PslFunctionDataFieldFilter(boolean filterReturnType)
	{
		this.filterReturnType = filterReturnType;
	}

	/**
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @param viewer
	 * @param parentElement
	 * @param element
	 * @return
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element)
	{
		if (element instanceof DataField)
		{
			String dataFieldName = ((DataField) element).getName();
			return filterReturnType ? PslEditorUtil.RETURN_PARAMETER_NAME.equals(dataFieldName)
					: !PslEditorUtil.RETURN_PARAMETER_NAME.equals(dataFieldName);
		}
		return true;
	}

}
