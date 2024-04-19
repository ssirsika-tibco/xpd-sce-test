/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */

package com.tibco.xpd.processscriptlibrary.resource.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.processscriptlibrary.resource.ui.ScriptFunctionGroup;
import com.tibco.xpd.resources.util.GovernanceStateService;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action provider to add a children for PSL file in the project explorer. This action add's 'Script function' menu
 * under 'Add Child' in the Navigator.
 *
 * @author ssirsika
 * @since 11-Jan-2024
 */
public class PSLAddChildActionProvider extends CommonActionProvider
{

	@Override
	public void fillContextMenu(IMenuManager menu)
	{
		MenuManager subMenu = new MenuManager(Messages.PSLAddChildActionProvider_AddChildMenu);

		if (getContext() != null && getContext().getSelection() instanceof IStructuredSelection)
		{
			IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

			if (selection.size() == 1)
			{
				Object first = selection.getFirstElement();
				com.tibco.xpd.xpdl2.Process process = null;
				if (first instanceof ScriptFunctionGroup)
				{
					process = (Process) ((ScriptFunctionGroup) first).getParent();
				}
				else if (first instanceof Activity)
				{
					process = Xpdl2ModelUtil.getProcess((EObject) first);
				}

				if (process != null)
				{
					AddScriptLibraryFunctionAction addAction = new AddScriptLibraryFunctionAction(process);

					boolean isLocked = (new GovernanceStateService()).isLockedForProduction(process);
					addAction.setEnabled(!isLocked);

					subMenu.add(addAction);
				}
				if (!subMenu.isEmpty())
				{
					menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, subMenu);
				}
			}
		}
	}

	/**
	 * Action to handle addition of activity (i.e script function) under PSL process.
	 *
	 * @author ssirsika
	 * @since 21-Jan-2024
	 */
	private class AddScriptLibraryFunctionAction extends Action
	{
		private Process process;

		/**
		 * @param aProcess
		 * 
		 */
		public AddScriptLibraryFunctionAction(Process aProcess)
		{
			this.process = aProcess;
			setText(Messages.PSLAddChildActionProvider_ScriptFnMenu);
			setImageDescriptor(ProcessScriptLibraryResourcePluginActivtor.getDefault().getImageRegistry()
					.getDescriptor(ProcessScriptLibraryConstants.IMG_SCRIPT_FUNCTION));
		}

		/**
		 * @see org.eclipse.jface.action.Action#run()
		 *
		 */
		@Override
		public void run()
		{
			Activity newActivity = PslEditorUtil.addNewScriptFunctionInProcess(process);
			if (newActivity != null)
			{
				StructuredViewer viewer = getActionSite().getStructuredViewer();
				IStructuredSelection oldSel = (IStructuredSelection) viewer.getSelection();
				StructuredSelection newSelection = new StructuredSelection(newActivity);
				if (!oldSel.isEmpty())
				{
					Object oldElement = oldSel.getFirstElement();
					if (oldElement instanceof Activity)
					{
						// if old element is Activity then refresh using PSL file object.
						IFile file = WorkingCopyUtil.getFile((EObject) oldElement);
						viewer.refresh(file);
					}
					else
					{
						viewer.refresh(oldSel.getFirstElement());
					}
				}
				viewer.setSelection(newSelection, true);

				ProcessScriptLibraryEditorInput editorInput = new ProcessScriptLibraryEditorInput(
						WorkingCopyUtil.getWorkingCopyFor(process), newActivity);

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				try
				{
					page.openEditor(editorInput, PslEditorUtil.PROCESS_SCRIPT_LIBRARY_EDITOR_ID, true);
				}
				catch (PartInitException e)
				{
					ProcessScriptLibraryResourcePluginActivtor.getDefault()
							.logError("Error while opnein the PSL editor", e); //$NON-NLS-1$
				}

			}

		}
	}
}
