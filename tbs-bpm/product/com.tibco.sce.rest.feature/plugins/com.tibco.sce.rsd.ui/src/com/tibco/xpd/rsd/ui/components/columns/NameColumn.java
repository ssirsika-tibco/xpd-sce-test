/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.NamedElement;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Column for editing the name value of a NamedElement.
 *
 * @author jarciuch
 * @since 9 Mar 2015
 */
public class NameColumn extends AbstractColumn {

    private TextCellEditor editor;

    /** The currently selected named element */
    private NamedElement selected;

    private StatusMessageDisplayCellEditorListener displayStatusListener;

    /**
     * Label Column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param editable
     *            <code>true</code> if this column should be editable.
     */
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable) {
        this(editingDomain, viewer, Messages.NameColumn_NameColumn_label,
                editable, 150);
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
    public NameColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, boolean editable, int width) {
        super(editingDomain, viewer, SWT.NONE, title, width);
        setShowImage(true);

        if (editable) {
            editor = new TextCellEditor((Composite) viewer.getControl());
            editor.setValidator(new NameValidator());
            editor.addListener(new NamedElementCellEditorListener(editor));
            displayStatusListener =
                    new StatusMessageDisplayCellEditorListener(editor);
            editor.addListener(displayStatusListener);
        }
    }

    private class NameValidator implements ICellEditorValidator {

        @Override
        public String isValid(Object value) {
            String err = null;
            NamedElement named = getNamedElement();
            if (value instanceof String && named != null) {
                String newName = (String) value;
                if (null == err) {
                    err = verifyName(named, newName);
                }
            }
            return err;
        }
    }

    /**
     * Verify that the text of the named element is valid and return the error
     * text if it's invalid (<code>null</code> otherwise).
     * 
     * The base method will check the name is not empty. Clients should provide
     * custom validations in the overridden method if needed.
     * 
     * @param element
     *            the context named element.
     * @param nameText
     *            the new name to verify.
     * 
     * @return The error text if it's invalid (<code>null</code> otherwise).
     */
    protected String verifyName(NamedElement element, String nameText) {
        if (nameText == null || nameText.isEmpty()) {
            return "Name cannot be empty.";
        }

        return null;
    }

    private static class NamedElementCellEditorListener implements
            ICellEditorListener {
        private TextCellEditor editor;

        public NamedElementCellEditorListener(TextCellEditor editor) {
            this.editor = editor;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ICellEditorListener#applyEditorValue()
         */
        @Override
        public void applyEditorValue() {
            editor.getControl().setToolTipText(null);
            editor.getControl().setForeground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND));
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ICellEditorListener#cancelEditor()
         */
        @Override
        public void cancelEditor() {
            editor.getControl().setToolTipText(null);
            editor.getControl().setForeground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND));
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ICellEditorListener#editorValueChanged(
         * boolean, boolean)
         */
        @Override
        public void editorValueChanged(boolean oldValidState,
                boolean newValidState) {
            Display display = Display.getCurrent();
            if (!newValidState) {
                editor.getControl().setToolTipText(editor.getErrorMessage());
                editor.getControl()
                        .setForeground(display.getSystemColor(SWT.COLOR_RED));

            } else {
                editor.getControl().setToolTipText(null);
                editor.getControl()
                        .setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (displayStatusListener != null) {
            displayStatusListener.setCurrentStatusLineManager();
        }
        return editor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        Command cmd = null;
        if (editor != null && element instanceof NamedElement && value != null
                && ((String) value).length() > 0) {
            final NamedElement namedElement = (NamedElement) element;
            final String val = (String) value;
            cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            namedElement.setName(val);
                        }
                    };
        }
        return cmd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getText(Object element) {
        String txt = null;
        if (element instanceof NamedElement) {
            NamedElement namedElement = (NamedElement) element;
            selected = namedElement;
            txt = namedElement.getName();
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Image getImage(Object element) {
        Image img = null;
        if (element instanceof NamedElement && !((EObject) element).eIsProxy()) {
            img = WorkingCopyUtil.getImage((EObject) element);
        }
        return img != null ? img : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

    /**
     * Returns currently selected named element.
     * 
     * @return currently selected named element.
     */
    protected NamedElement getNamedElement() {
        return selected;
    }
}
