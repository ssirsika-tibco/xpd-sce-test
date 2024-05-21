/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.processscriptlibrary.resource.ui.ScriptFunctionGroup;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;

/**
 * The Navigator Label provider for the Process Script Library Assets.
 *
 * @author ssirsika
 * @since 03-Jan-2024
 */
public class PSLLabelProvider extends BpmContentLabelProvider
{
	/**
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element)
	{
		/*
		 * Override getImage() so we can provide alternate icons for script library content....
		 */

		/*
		 * If the element passed in is a structured selection then get the object from it. This can be the case when the
		 * Properties view is acquiring the image for the title
		 */
		if (element instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection) element;
			element = selection.getFirstElement();
		}

		if (element instanceof Activity
				&& TaskType.SCRIPT_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict((Activity) element)))
		{
			return ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
					.get(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION);
		}
		else if (element instanceof ScriptFunctionGroup)
		{
			return ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
					.get(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION_FOLDER);
		}

		return super.getImage(element);
	}

	/**
	 * 
	 * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.BpmContentLabelProvider#getDescription(java.lang.Object)
	 *
	 * @param anElement
	 * @return
	 */
	@Override
	public String getDescription(Object anElement)
	{
		// ACE-8218 In the property sheet toolbar, show "Script Function" instead of the actual function name
		if (anElement instanceof Activity)
		{
			return Messages.ScriptFunction_title;
		}

		return super.getDescription(anElement);
	}

}
