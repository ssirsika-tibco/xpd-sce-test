/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.ui.properties.PropertiesPlugin;
import com.tibco.xpd.ui.properties.PropertiesPluginConstants;
import com.tibco.xpd.ui.properties.components.ViewerAction;
import com.tibco.xpd.ui.properties.internal.Messages;

/**
 * Add new row action class for TableWithButtons.
 * <p>
 * The creator of TableWithButtons can create a subclass of this class and then
 * add it to the actionsManager... <b>getActionsManager().add().</b>
 * <p>
 * When and action of this type added to the actions manager it will also switch
 * on the auto-new-row keyboard behaviour.
 * <p>
 * This just leaves the subclass to...
 * <li>Provide Initial value for new row first cell (getNewRowFirstCellVal())
 * <li>Create and Append data for row at end of current list (createNewRow)
 * <p>
 * 
 * @author aallway
 */
public abstract class TableWithButtonsNewRowAction extends ViewerAction {
    private InternalCustomTableViewer internalCustomTableViewer;

    public TableWithButtonsNewRowAction(StructuredViewer viewer, String text) {
        super(
                viewer,
                Messages.TableWithButtonsNewRowAction_text,
                PropertiesPlugin.getDefault().getImageRegistry()
                        .getDescriptor(PropertiesPluginConstants.IMG_TABLE_NEW_BUTTON));

        if (!(viewer instanceof InternalCustomTableViewer)) {
            throw new IllegalArgumentException(
                    "Attempt to use TableWithButtons action on invalid table viewer"); //$NON-NLS-1$
        }

        this.internalCustomTableViewer = (InternalCustomTableViewer) viewer;

        setToolTipText(text);
    }

    /**
     * Append a new row to end of current data list.
     * 
     * @param firstCellVal
     * @return Data Object that is added (must be same as that provided by
     *         viewer content provider.
     */
    protected abstract Object createNewRow(String firstCellVal);

    /**
     * Return the default value for first cell of a new row.
     * 
     * @return
     */
    protected abstract String getNewRowFirstCellVal();

    final public void run() {
        //
        // When we run (i.e. from button etc but not from auto-new row
        // caused by viewer itself)
        // Then we will ask the viwer to add a new row.
        //
        // THEN VIEWER WILL call our
        // MapActionsToActionsListener.createWithDefaults() method to create
        // the raw data.
        // That in turn will call the createNewRow() method here.
        //
        // This is done this way so that control of items in the table in
        // the view remain under control of the tableviewer and the data
        // remains under the control of the action provider.
        String newRowId = getNewRowFirstCellVal();

        internalCustomTableViewer.addNewRow(newRowId);

    }
}