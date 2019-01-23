/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.dnd;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEffect;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.fragments.dnd.FragmentLocalSelectionTransfer;
import com.tibco.xpd.fragments.internal.impl.FragmentRootCategory;

/**
 * Fragment drag source adapter.
 * 
 * @author njpatel
 * 
 */
public class FragmentDragSourceAdapter extends DragSourceEffect {

	private Image img;
	private final ISelectionProvider selectionProvider;

	/**
	 * Fragment drag source adapter.
	 * 
	 * @param control
	 *            the Control that the user clicks on to initiate the drag
	 * @param selectionProvider
	 *            the selection provider
	 */
	public FragmentDragSourceAdapter(Control control,
			ISelectionProvider selectionProvider) {
		super(control);
		this.selectionProvider = selectionProvider;
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		if (FragmentLocalSelectionTransfer.getTransfer().isSupportedType(
				event.dataType)) {
			event.data = FragmentLocalSelectionTransfer.getTransfer()
					.getSelection();
		}
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		super.dragStart(event);

		ISelection selection = selectionProvider.getSelection();

		if (selection != null && !selection.isEmpty()) {
			Object element = ((IStructuredSelection) selection)
					.getFirstElement();

			if (element instanceof FragmentRootCategory) {
				event.doit = false;
			} else {
				FragmentLocalSelectionTransfer.getTransfer().setSelection(
						selection);
			}
		} else {
			event.doit = false;
		}
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		if (img != null) {
			img.dispose();
			img = null;
		}
		FragmentLocalSelectionTransfer.getTransfer().setSelection(null);
		super.dragFinished(event);
	}
}