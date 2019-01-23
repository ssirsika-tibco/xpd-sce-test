/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageSelectionProvider;

import com.tibco.xpd.rcp.internal.OverviewEditorInput;

/**
 * Selection provider for Overview Editor.
 * 
 * @author mtorres
 * 
 * 
 */
public class OverviewEditorSelectionProvider extends MultiPageSelectionProvider {

    public OverviewEditorSelectionProvider(MultiPageEditorPart editor) {
        super(editor);
    }

    private ISelection selection;

    @Override
    public ISelection getSelection() {
        if (selection == null) {
            return new StructuredSelection(
                    ((OverviewEditorInput) getMultiPageEditor()
                            .getEditorInput()).getResource());

        }
        return selection;
    }

    @Override
    public void setSelection(ISelection selection) {
        if (this.selection != selection) {
            this.selection = selection;
            SelectionChangedEvent event =
                    new SelectionChangedEvent(this, selection);
            fireSelectionChanged(event);

            /*
             * This is required for the problem view to update correctly, as the
             * Overview editor is now acting as the project explorer
             */
            firePostSelectionChanged(event);
        }
    }

}
