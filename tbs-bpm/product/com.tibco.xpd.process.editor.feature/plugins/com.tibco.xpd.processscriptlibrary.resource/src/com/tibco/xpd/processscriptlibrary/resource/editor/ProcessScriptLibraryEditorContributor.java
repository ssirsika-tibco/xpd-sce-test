/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.editor;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.EditorActionBarContributor;

import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;

/**
 * PSL Editor contributor which contributes editor specific actions.
 *
 * @author ssirsika
 * @since 22-Feb-2024
 */
public class ProcessScriptLibraryEditorContributor extends EditorActionBarContributor
{

	/**
	 * @see org.eclipse.ui.part.EditorActionBarContributor#setActiveEditor(org.eclipse.ui.IEditorPart)
	 *
	 * @param targetEditor
	 */
	@Override
	public void setActiveEditor(IEditorPart targetEditor)
	{
		// Set Editor Actions
		if (targetEditor instanceof ProcessScriptLibraryEditor)
		{
			ProcessScriptLibraryEditor editor = (ProcessScriptLibraryEditor) targetEditor;
			String[] ids = new String[]{ActionFactory.UNDO.getId(), ActionFactory.REDO.getId()};

			for (int i = 0; i < ids.length; i++)
			{
				IAction act = editor.getAction(ids[i]);
				if (act != null)
				{
					getActionBars().setGlobalActionHandler(ids[i], act);
				}
				else
				{
					ProcessScriptLibraryResourcePluginActivtor.getDefault()
							.logError(String.format("Missing ActionHandler for: %s", ids[i])); //$NON-NLS-1$
				}
			}
			getActionBars().updateActionBars();
		}
	}
}
