/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * <p>
 * This class wraps a given content provider and if the given actionsExecutor
 * says it can handle new-row behaiour then it will always append a
 * PotentialNewRowData object so that InternalCustomTableViewer has a dummy new
 * row which it can tell whether to ask actionsExecutor to create new row when
 * appropriate.
 * 
 * <i>Created: Nov 10, 2006</i>.
 * 
 * @author mmaciuki
 */
class CustomTableContentProviderWrapper implements IStructuredContentProvider {

    private final IStructuredContentProvider provider;

    private InternalTableActionsListener actionsExecutor;

    private InternalCustomTableViewer internalCustomTableViewer;

    /**
     * @param provider
     *            param
     * @param internalCustomTableViewer
     */
    public CustomTableContentProviderWrapper(
            IStructuredContentProvider provider,
            InternalTableActionsListener actionsExecutor,
            InternalCustomTableViewer internalCustomTableViewer) {
        this.provider = provider;
        this.actionsExecutor = actionsExecutor;

        this.internalCustomTableViewer = internalCustomTableViewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        Object[] result = null;

        Object[] orginalElements = this.provider.getElements(inputElement);

        // If the actions exectutor supports add new row then add a dummy new
        // row data to end.
        // The InternalCustomTableViewer will treat this specially.

        // BUT ONLY if the first column is editable - i.e. you must have an
        // AddNewRow action handler BUT you may want the add new row action to
        // do something like 'browse for object' and therefore ONLY work from
        // add button - therefore only add the special "..." nerw row creator
        // entry iof the first column cell is editable.
        
        boolean firstCellEditable = false;
        
        if (internalCustomTableViewer.getCellEditors() != null) {
            CellEditor[] cells = internalCustomTableViewer.getCellEditors();
            if (cells.length > 0 && cells[0] != null) {
                firstCellEditable = true;
            }
        }

        if (firstCellEditable && actionsExecutor.canCreateNewRows()) {
            result = new Object[orginalElements.length + 1];
            System.arraycopy(orginalElements,
                    0,
                    result,
                    0,
                    orginalElements.length);

            result[orginalElements.length] = PotentialNewRowData.INSTANCE;
        } else {
            result = orginalElements;
        }

        return result;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
        this.provider.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.provider.inputChanged(viewer, oldInput, newInput);
    }
}
