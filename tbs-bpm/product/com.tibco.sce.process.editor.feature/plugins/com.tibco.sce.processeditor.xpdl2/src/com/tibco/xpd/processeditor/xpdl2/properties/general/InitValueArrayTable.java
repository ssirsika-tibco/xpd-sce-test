/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * InitValueArrayTable
 * 
 * 
 * @author bharge
 * @since 3.3 (27 Nov 2009)
 */
public class InitValueArrayTable extends AbstractInitArrayTable {
    private EditingDomain editingDomain;

    private String columnLabel;

    /**
     * @param parent
     * @param toolkit
     */
    public InitValueArrayTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain, String columnLabel) {
        super(parent, toolkit, editingDomain, false);
        this.editingDomain = editingDomain;
        this.columnLabel = columnLabel;
        createContents(parent, toolkit, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new InitValueColumn(editingDomain, viewer, columnLabel);
        setColumnProportions(new float[] { 1.0f });

    }

    protected class InitValueColumn extends AbstractInitColumn {
        private final TextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public InitValueColumn(EditingDomain editingDomain,
                ColumnViewer viewer, String columnLabel) {
            super(editingDomain, viewer, columnLabel);
            editor = new TextCellEditor((Composite) viewer.getControl());
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof String) {
                return editor;
            }
            return null;
        }

    }
}
