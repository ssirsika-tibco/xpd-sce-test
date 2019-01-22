/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.ui.properties.PropertiesPlugin;
import com.tibco.xpd.ui.properties.PropertiesPluginConstants;
import com.tibco.xpd.ui.properties.components.ViewerAction;
import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * Delete row(s) action class for TableWithButtons.
 * <p>
 * Action to remove the selected row(s) - subclass need only override the 
 * deleteRows() method to delete the data for the rows that the content provider provides.
 * <p>
 *   
 * @author aallway
 *
 */
public abstract class TableWithButtonsDeleteRowAction extends ViewerAction {
    private InternalCustomTableViewer internalCustomTableViewer;

    public TableWithButtonsDeleteRowAction(StructuredViewer viewer, String text) {
        super(
                viewer,
                Messages.TableWithButtonsDeleteRowAction_text,
                PropertiesPlugin.getDefault().getImageRegistry()
                        .getDescriptor(PropertiesPluginConstants.IMG_TABLE_DEL_BUTTON));

        if (!(viewer instanceof InternalCustomTableViewer)) {
            throw new IllegalArgumentException(
                    "Attempt to use TableWithButtons action on invalid table viewer"); //$NON-NLS-1$
        }

        this.internalCustomTableViewer = (InternalCustomTableViewer)viewer;

        setEnabled(false);
        
        setToolTipText(text);
        
    }

    /**
     * Delete the given data rows from the list.
     * 
     * @param selection
     */
    protected abstract void deleteRows(IStructuredSelection selection);

    final public void run() {
        //
        // When we run (i.e. from button etc but not from auto-new row
        // caused by viewer itself)
        // Then we will ask the viwer to actual;l;y delete the rows.
        //
        // THEN VIEWER WILL call our MapActionsToActionsListener.delete()
        // method to create the raw data.
        // That in turn will call the deleteRows() method here.
        //
        // This is done this way so that control of items in the table in
        // the view remain under control of the tableviewer and the data
        // remains under the control of the action provider.

        IStructuredSelection selection = (IStructuredSelection) internalCustomTableViewer
                .getSelection();

        internalCustomTableViewer.deleteSelection(selection);

    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        boolean isEnabled = !selection.isEmpty();
        setEnabled(isEnabled);
    }
}