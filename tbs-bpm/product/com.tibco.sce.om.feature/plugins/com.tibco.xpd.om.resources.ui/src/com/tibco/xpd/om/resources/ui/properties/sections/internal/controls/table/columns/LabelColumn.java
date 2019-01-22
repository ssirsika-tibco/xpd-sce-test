/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * @author bharge
 * 
 */
public class LabelColumn extends AbstractColumn {

    private TextCellEditor editor;

    private final boolean isReadOnly;

    /**
     * Label column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Label column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public LabelColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean isReadOnly) {
        super(editingDomain, viewer, Messages.LabelColumn_title, 200);
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor = new TextCellEditor((Composite) viewer.getControl());
        }
        setShowImage(true);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (!isReadOnly && element instanceof NamedElement) {
            return editor;
        }
        return null;
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
        // Cannot set an empty or null name
        if (element instanceof NamedElement && value instanceof String
                && ((String) value).trim().length() > 0) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getNamedElement_DisplayName(),
                    value != null ? value : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected String getText(Object element) {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getDisplayName();
        }
        return ""; //$NON-NLS-1$
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
