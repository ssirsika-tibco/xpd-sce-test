/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditor;
import com.tibco.xpd.dependency.visualization.internal.views.DependencyVisualizationEditorInput;

/**
 * Provide mechanism to link the {@link DependencyVisualizationEditor} with the common navigator like project explorer.
 *
 * @author ssirsika
 * @since 06-Nov-2015
 */
public class DependencyEditorLinkHelper implements ILinkHelper {

	/**
	 * 
	 * @see org.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.IWorkbenchPage, org.eclipse.jface.viewers.IStructuredSelection)
	 * @param aPage
	 * @param aSelection
	 */
	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (aPage != null) {
			IEditorPart activeEditor = aPage.getActiveEditor();
			if (activeEditor instanceof DependencyVisualizationEditor) {
				((DependencyVisualizationEditor) activeEditor).updateInput(aSelection);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.navigator.ILinkHelper#findSelection(org.eclipse.ui.IEditorInput)
	 *
	 * @param anInput
	 * @return
	 */
	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		DependencyVisualizationEditorInput input = (DependencyVisualizationEditorInput) anInput;
		return input.getSelection();
	}

}
