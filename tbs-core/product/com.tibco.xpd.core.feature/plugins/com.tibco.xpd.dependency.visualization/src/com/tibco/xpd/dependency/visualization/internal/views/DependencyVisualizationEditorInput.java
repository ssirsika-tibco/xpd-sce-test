/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.views;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.tibco.xpd.dependency.visualization.internal.Messages;

/**
 * Editor input to Dependency visualisation editor.
 *
 * @author ssirsika
 * @since 14-Nov-2015
 */
public class DependencyVisualizationEditorInput implements IEditorInput {

	private IStructuredSelection selection;

	/**
	 * 
	 */
	public DependencyVisualizationEditorInput(IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#exists()
	 *
	 * @return
	 */
	@Override
	public boolean exists() {
		return false;
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 *
	 * @param adapter
	 * @return
	 */
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 *
	 * @return
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return Messages.DependencyVisualizationEditorInput_EditorInputName;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 *
	 * @return
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * @return the selection
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 *
	 * @return
	 */
	@Override
	public String getToolTipText() {
		return Messages.DependencyVisualizationEditorInput_EditorInputTooltip;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}
}
