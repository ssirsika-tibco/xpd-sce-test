/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Base class for composite encapsulating table column viewer with an
 * accompanying button bar. A composite that shows a table with action buttons.
 * This uses the new 3.3 JFACE table creation method using
 * {@link EditingSupport} for each columns.
 * 
 * @author njpatel, Jan Arciuchiewicz
 */
public abstract class BaseTableControl extends BaseColumnViewerControl {

    private Table table;

    /**
     * @param parent
     * @param toolkit
     */
    public BaseTableControl(Composite parent, XpdToolkit toolkit) {
        super(parent, toolkit);
    }

    /**
     * @param parent
     * @param toolkit
     * @param viewerInput
     */
    public BaseTableControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput) {
        super(parent, toolkit, viewerInput);
    }

    /**
     * Call this constructor in the sub-class if you wish to initialise some
     * data that is passed into the constructor before
     * {@link #createContents(Composite, XpdToolkit, Object) createContents} is
     * called. Remember in this case the sub-class will be responsible to call
     * <code>createContent</code>.
     * 
     * @param parent
     * @param toolkit
     * @param viewerInput
     *            input for the table, can be <code>null</code>.
     * @param createContent
     *            <code>true</code> if the table content should be created, if
     *            <code>false</code> then subclass is responsible for calling
     *            <code>createContent</code>.
     */
    protected BaseTableControl(Composite parent, XpdToolkit toolkit,
            Object viewerInput, boolean createContent) {
        super(parent, toolkit, viewerInput, createContent);
    }

    /** {@inheritDoc} */
    @Override
    protected ColumnViewer createColumnViewer(Composite parent,
            XpdToolkit toolkit) {
        table =
                toolkit.createTable(parent, SWT.FULL_SELECTION | SWT.MULTI
                        | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER, this
                        .getClass().getName());
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        TableViewer tableViewer = new TableViewer(table);
        return tableViewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * setColumnProportions(float[])
     */
    @Override
    public void setColumnProportions(float[] columnProportions) {
        TableColumn[] columns = table.getColumns();

        if (columns.length != columnProportions.length) {
            throw new IllegalArgumentException(
                    "Must provide proportions for every column in table!"); //$NON-NLS-1$
        }

        TableColumnLayout tableColumnLayout = new TableColumnLayout();

        for (int i = 0; i < columns.length; i++) {
            TableColumn column = columns[i];

            ColumnLayoutData cld =
                    new ColumnWeightData((int) (columnProportions[i] * 100),
                            10, true);

            tableColumnLayout.setColumnData(column, cld);
        }

        Composite tableContainer = getColumnViewerContainer();
        tableContainer.setLayout(tableColumnLayout);
        tableContainer.layout(true, true);
        return;
    }

    /**
     * Get the table viewer being managed by this {@link Composite}.
     * 
     * @return TableViewer.
     */
    public TableViewer getTableViewer() {
        return (TableViewer) getViewer();
    }

    /**
     * Enable table traversal.
     */
    @Override
    protected void enableTraversal() {
        TableViewer tableViewer = getTableViewer();
        if (tableViewer != null && !tableViewer.getControl().isDisposed()) {
            TableViewerEditor.create(tableViewer,
                    new TableViewerFocusCellManager(tableViewer,
                            new FocusCellAndRowHighlighter(tableViewer)),
                    new ColumnActivationStrategy(tableViewer),
                    ColumnViewerEditor.KEYBOARD_ACTIVATION
                            | ColumnViewerEditor.TABBING_HORIZONTAL
                            | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
                            | ColumnViewerEditor.TABBING_VERTICAL);
        }
    }

    private class ColumnActivationStrategy extends
            ColumnViewerEditorActivationStrategy {

        public ColumnActivationStrategy(ColumnViewer viewer) {
            super(viewer);
        }

        @Override
        protected boolean isEditorActivationEvent(
                ColumnViewerEditorActivationEvent event) {

            if (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED) {
                if (event.keyCode == SWT.CR || event.keyCode == ' ') {
                    return true;
                }
            }
            return super.isEditorActivationEvent(event);
        }

    }

    public static TableViewerColumn createColumn(TableViewer viewer, int style,
            String label, int width) {
        TableViewerColumn column = new TableViewerColumn(viewer, style);
        column.getColumn().setText(label);
        column.getColumn().setWidth(width);
        return column;
    }
}
