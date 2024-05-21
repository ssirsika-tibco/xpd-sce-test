/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.xpdl2.Activity;

/**
 * The Property sheet Label provider for the Process Script Library Assets.
 *
 * @author nkelkar
 * @since May 21, 2024
 */
public class PSLPropertySheetLabelProvider extends PSLLabelProvider
{
	/**
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element)
	{
		if (element instanceof IStructuredSelection)
		{
			element = ((IStructuredSelection) element).getFirstElement();
		}

		// ACE-8218 In the property sheet toolbar, show "Script Function" instead of the actual function name
		if (element instanceof Activity)
		{
			return Messages.ScriptFunction_title;
		}

		return super.getText(element);
	}
}
