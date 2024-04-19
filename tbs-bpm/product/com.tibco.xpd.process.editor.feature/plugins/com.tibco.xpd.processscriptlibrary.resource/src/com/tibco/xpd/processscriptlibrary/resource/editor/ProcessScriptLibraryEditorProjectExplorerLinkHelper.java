/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.processscriptlibrary.resource.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInputFactory;
import com.tibco.xpd.ui.projectexplorer.AbstractProjectExplorerLinkHelper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Link editor helper for the Project Explorer to link the selected Activity (Script Task ) to it's editor part and vice
 * versa
 * 
 * @author ssirsika
 * 
 */
public class ProcessScriptLibraryEditorProjectExplorerLinkHelper extends AbstractProjectExplorerLinkHelper
{

	@Override
	protected Object findMainSelection(IEditorInput editorInput)
	{
		// Check if the editor selected is the PSL editor
		if (editorInput instanceof ProcessScriptLibraryEditorInput)
		{
			return ((ProcessScriptLibraryEditorInput) editorInput).getActivity();
		}
		return null;
	}

	@Override
	protected boolean isChild(Object selObject, Object mainSelection)
	{
		if (mainSelection instanceof Activity && selObject instanceof EObject)
		{
			// If selected object is either an Activity or child of an Activity
			return mainSelection.equals(Xpdl2ModelUtil.getAncestor((EObject) selObject, Activity.class));
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui. IWorkbenchPage,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection)
	{
		// Check if the first item selected is a Process
		if (aSelection != null && !aSelection.isEmpty())
		{
			Object firstElement = aSelection.getFirstElement();

			Activity selObject = null;

			if (firstElement instanceof Activity)
			{
				selObject = (Activity) firstElement;
			}
			else if (firstElement instanceof INavigatorGroup
					&& (((INavigatorGroup) firstElement).getParent() instanceof Activity))
			{
				selObject = (Activity) ((INavigatorGroup) firstElement).getParent();

			}

			if (selObject != null)
			{

				ProcessScriptLibraryEditorInputFactory fact = new ProcessScriptLibraryEditorInputFactory();

				IEditorInput processEditorInput = fact.getEditorInputFor(selObject);

				if (processEditorInput != null)
				{
					IEditorPart editor = aPage.findEditor(processEditorInput);

					// If the editor is open then bring to top
					if (editor != null)
					{
						aPage.bringToTop(editor);
					}
				}
			}
		}
	}

}
