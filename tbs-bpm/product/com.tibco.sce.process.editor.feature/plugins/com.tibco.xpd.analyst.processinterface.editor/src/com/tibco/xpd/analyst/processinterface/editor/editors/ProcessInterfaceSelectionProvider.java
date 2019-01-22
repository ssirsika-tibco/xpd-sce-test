/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;

/**
 * Selection provider set on the process interface editor site.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceSelectionProvider extends
		MultiPageSelectionProvider {

	public ProcessInterfaceSelectionProvider(ProcessInterfaceFormEditor editor) {
		super(editor);
	}

	private List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<ISelectionChangedListener>();

	private ISelection selection;

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		if (selection == null) {
			return new StructuredSelection(
					((ProcessInterfaceEditorInput) getMultiPageEditor()
							.getEditorInput()).getProcessInterface());
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
