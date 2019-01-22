/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.command.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tibco.xpd.dependency.visualization.internal.DependencyVisualizationActivator;
import com.tibco.xpd.dependency.visualization.internal.Messages;
import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;
import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditorInput;

/**
 * This command handler will open the {@link DependencyVisualizationEditor} by providing {@link DependencyVisualizationEditorInput}
 *
 * @author ssirsika
 * @since 16-Nov-2015
 */
public class VisualizeDependenciesCommandHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object sel = structuredSelection.getFirstElement();
			if (sel instanceof IResource) {
				try {
					IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
					IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
					IEditorPart part = activePage.openEditor(new DependencyVisualizationEditorInput(structuredSelection), DependencyVisualizationEditor.EDITOR_ID, true);
					if (part instanceof DependencyVisualizationEditor) {
						((DependencyVisualizationEditor) part).updateInput(structuredSelection);
					}
				} catch (PartInitException e) {
					DependencyVisualizationActivator.logError(Messages.VisualizeDependenciesCommandHandler_ErrorOpeningEditorMessage, e);
				}
			}
		}
		return null;
	}

}
