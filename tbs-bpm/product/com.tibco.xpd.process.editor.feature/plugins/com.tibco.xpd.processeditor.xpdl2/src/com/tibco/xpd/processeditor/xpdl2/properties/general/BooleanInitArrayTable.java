/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.resources.ui.components.XpdToolkit;

/**
 * BooleanInitArrayTable
 * 
 * 
 * @author bharge
 * @since 3.3 (27 Nov 2009)
 */
public class BooleanInitArrayTable extends AbstractInitArrayTable {
    private EditingDomain editingDomain;

    private String columnLabel;

    /**
     * @param parent
     * @param toolkit
     */
    public BooleanInitArrayTable(Composite parent, XpdToolkit toolkit,
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
        new BooleanColumn(editingDomain, viewer, columnLabel);
    }

    private class BooleanColumn extends AbstractInitColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public BooleanColumn(EditingDomain editingDomain, ColumnViewer viewer,
                String columnLabel) {
            super(editingDomain, viewer, columnLabel);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            editor.getViewer().setData(ProcessDataUtil.UI_BOOLEAN_FALSE,
                    ProcessDataUtil.MODEL_BOOLEAN_FALSE);
            editor.getViewer().setData(ProcessDataUtil.UI_BOOLEAN_TRUE,
                    ProcessDataUtil.MODEL_BOOLEAN_TRUE);
            String[] values =
                    new String[] { ProcessDataUtil.UI_BOOLEAN_FALSE,
                            ProcessDataUtil.UI_BOOLEAN_TRUE };
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(values);
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

        @Override
        protected String getText(Object element) {
            Object modelFalse =
                    editor.getViewer()
                            .getData(ProcessDataUtil.UI_BOOLEAN_FALSE);
            Object modelTrue =
                    editor.getViewer().getData(ProcessDataUtil.UI_BOOLEAN_TRUE);
            if (element.equals(modelFalse)) {
                return super.getText(ProcessDataUtil.UI_BOOLEAN_FALSE);
            } else if (element.equals(modelTrue)) {
                return super.getText(ProcessDataUtil.UI_BOOLEAN_TRUE);
            }
            return super.getText(element);
        }

    }
}
