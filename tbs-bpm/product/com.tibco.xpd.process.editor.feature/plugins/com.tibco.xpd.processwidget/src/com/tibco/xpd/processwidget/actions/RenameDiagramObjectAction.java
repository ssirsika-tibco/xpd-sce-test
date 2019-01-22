/**
 * RenameDiagramObjectAction.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.actions;

import java.util.List;

import org.eclipse.gef.Disposable;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.processwidget.ProcessWidget;

/**
 * RenameDiagramObjectAction
 * 
 */
public class RenameDiagramObjectAction extends Action implements Disposable,
        ISelectionChangedListener {

    private ProcessWidget widget;

    public RenameDiagramObjectAction(ProcessWidget widget) {
        this.widget = widget;
        setEnabled(false);
        widget.addSelectionChangedListener(this);
    }

    @Override
    public String getId() {
        return ActionFactory.RENAME.getId();
    }

    @Override
    public void run() {
        GraphicalViewer viewer = widget.getGraphicalViewer();

        List sel = viewer.getSelectedEditParts();

        if (sel != null && sel.size() == 1 && sel.get(0) instanceof EditPart) {
            ((EditPart) sel.get(0)).performRequest(new DirectEditRequest());
        }
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     * 
     * @param event
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        boolean isEnabled = false;
        GraphicalViewer viewer = widget.getGraphicalViewer();

        if (viewer != null) {
            List sel = viewer.getSelectedEditParts();

            isEnabled =
                    sel != null && sel.size() == 1
                            && sel.get(0) instanceof EditPart;
        }

        setEnabled(isEnabled);
    }

    /**
     * @see com.tibco.xpd.processwidget.actions.DisposableAction#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (widget != null) {
            widget.removeSelectionChangedListener(this);
        }
    }

}
