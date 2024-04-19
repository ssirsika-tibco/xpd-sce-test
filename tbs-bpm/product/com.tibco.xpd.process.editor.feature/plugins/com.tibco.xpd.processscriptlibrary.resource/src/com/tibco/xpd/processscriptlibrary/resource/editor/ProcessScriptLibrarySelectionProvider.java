/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.processscriptlibrary.resource.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;

/**
 * Selection provider set on the PSL editor site.
 * 
 * @author ssirsika
 * 
 * 
 */
public class ProcessScriptLibrarySelectionProvider extends
		MultiPageSelectionProvider {

	public ProcessScriptLibrarySelectionProvider(ProcessScriptLibraryEditor editor)
	{
		super(editor);
	}

	private List<ISelectionChangedListener>	selectionChangedListeners	= new ArrayList<>();

	private ISelection selection;

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public ISelection getSelection() {
		if (selection == null) {
			return new StructuredSelection(
					((ProcessScriptLibraryEditorInput) getMultiPageEditor()
							.getEditorInput()).getActivity());
		}
		return selection;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionChangedListeners.clear();
	}

	public void setSelection(ISelection selection) {
		if (this.selection != selection) {
			this.selection = selection;
			selectionChanged(new SelectionChangedEvent(this, selection));
		}
	}

	/**
	 * The selection has changed. Process the event.
	 * 
	 * @param event
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		// pass on the notification to listeners
		Object[] listeners = selectionChangedListeners.toArray();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			Platform.run(new SafeRunnable() {
				public void run() {
					l.selectionChanged(event);
				}
			});
		}
	}

}
