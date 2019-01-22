/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * @author bharge
 * 
 */
public class LabelColumn extends AbstractColumn {

    private TextCellEditor editor;

    /**
     * Label Column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param editable
     *            <code>true</code> if this column should be editable.
     */
    public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable) {
        this(editingDomain, viewer, Messages.LabelColumn_column_title,
                editable, 150);
    }

    public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean editable, int width) {
        this(editingDomain, viewer, Messages.LabelColumn_column_title,
                editable, width);
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
    public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String title, boolean editable, int width) {
        super(editingDomain, viewer, SWT.NONE, title, width);
        setShowImage(true);

        if (editable) {
            editor = new TextCellEditor((Composite) viewer.getControl());
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
        if (editor != null && element instanceof NamedElement && value != null
                && ((String) value).length() > 0) {
            final NamedElement namedElement = (NamedElement) element;
            final String val = (String) value;
            // Can edit this column
            cmd =
                    SetCommand.create(getEditingDomain(),
                            element,
                            UMLPackage.eINSTANCE.getNamedElement_Name(),
                            value);
            cmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) getEditingDomain()) {
                        @Override
                        protected void doExecute() {
                            PrimitivesUtil.setDisplayLabel(namedElement, val);
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
        if (element instanceof NamedElement) {
            NamedElement namedElement = (NamedElement) element;
            txt = PrimitivesUtil.getDisplayLabel(namedElement);
        }
        return txt != null ? txt : ""; //$NON-NLS-1$
    }

    @Override
    protected Image getImage(Object element) {
        Image img = null;
        if (element instanceof NamedElement && !((EObject) element).eIsProxy()) {
            img = WorkingCopyUtil.getImage((EObject) element);
        }
        return img != null ? img : null;
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

}
