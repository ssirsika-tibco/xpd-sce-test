/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.editor.input;

import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processscriptlibrary.resource.editor.ProcessScriptLibraryEditor;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Editor Input factory for to create editor input for {@link ProcessScriptLibraryEditor}.
 *
 * @author ssirsika
 * @since 24-Jan-2024
 */
public class ProcessScriptLibraryEditorInputFactory implements EditorInputFactory
{


	/**
	 * @see com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory#getEditorInputFor(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public IEditorInput getEditorInputFor(Object obj)
	{
		ProcessScriptLibraryEditorInput editorInput = null;
		if (obj instanceof Activity)
		{
			Activity activity = (Activity) obj;
			WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(activity);
			if (workingCopy != null)
			{
				// check in cache first.
				editorInput = (ProcessScriptLibraryEditorInput) workingCopy.getAttributes().get(activity);
				if (editorInput == null)
				{
					editorInput = new ProcessScriptLibraryEditorInput(workingCopy,
							activity);
					workingCopy.getAttributes().put(activity, editorInput);
				}
			}

		}
		return editorInput;
	}

}
