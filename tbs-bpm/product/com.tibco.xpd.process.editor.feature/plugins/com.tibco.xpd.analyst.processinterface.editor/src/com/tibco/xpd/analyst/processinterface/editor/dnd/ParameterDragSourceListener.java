/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.dnd;

import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ParameterDragSourceListener extends ViewerDragAdapter {

	public ParameterDragSourceListener(StructuredViewer viewer) {
		super(viewer);
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		super.dragStart(event);
		
		LocalSelectionTransfer.getTransfer().setSelection(viewer.getSelection());
	}

}
