/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;

/**
 * Column for displaying and editing HTTP Status Code.
 *
 * @author jarciuch
 * @since 17 Feb 2015
 */
public class StatusCodeColumn extends AbstractColumn {

    private TextCellEditor editor;

    /**
     * Status Code column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param editable
     *            <code>true</code> if this column should be editable.
     */
    public StatusCodeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable) {
        this(editingDomain, viewer,
                Messages.StatusCodeColumn_StatusCodeCloumn_label, editable, 100);
    }

    /**
     * Label Column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param title
     *            column title
     * @param editable
     *            <code>true</code> if this column should be editable
     */
    public StatusCodeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, boolean editable, int width) {
        super(editingDomain, viewer, SWT.NONE, title, width);
        setShowImage(false);

        if (editable) {
            editor = new StatusCodeCellEditor((Composite) viewer.getControl());
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        return editor;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param element
     * @param value
     * @return
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        Command cmd = null;
        if (editor != null && element instanceof Fault && value != null
                && ((String) value).length() > 0) {
            final Fault fault = (Fault) element;
            final String val = (String) value;
            cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain(),
                            Messages.StatusCodeColumn_SetStatusCodeCmd_label) {
                        @Override
                        protected void doExecute() {
                            fault.setStatusCode(val);
                            ;
                        }
                    };
        }
        return cmd;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected String getText(Object element) {
        String txt = null;
        if (element instanceof Fault) {
            Fault fault = (Fault) element;
            txt = fault.getStatusCode();
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    public static class StatusCodeCellEditor extends TextCellEditor {

        public StatusCodeCellEditor(Composite composite) {
            super(composite);
            setValidator(new ICellEditorValidator() {
                @Override
                public String isValid(Object object) {
                    if (object instanceof Integer) {
                        return null;
                    } else {
                        String string = (String) object;
                        try {
                            Integer.parseInt(string);
                            return null;
                        } catch (NumberFormatException exception) {
                            return Messages.StatusCodeColumn_InvalidStatusCode_message;
                        }
                    }
                }
            });
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Control createControl(Composite parent) {
            Control c = super.createControl(parent);
            text.addVerifyListener(new DigitTextVerifyListener());
            return c;
        }

        @Override
        public Object doGetValue() {
            String s = (String) super.doGetValue();
            if (s != null) {
                try {
                    int code = Integer.parseInt(s);
                    return String.valueOf(code);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public void doSetValue(Object value) {
            super.doSetValue(value.toString());
        }
    }
}
