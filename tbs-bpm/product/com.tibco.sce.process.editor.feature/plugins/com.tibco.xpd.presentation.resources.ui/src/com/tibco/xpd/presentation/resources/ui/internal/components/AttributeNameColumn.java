/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.NamedElement;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;

/**
 * Name column for a {@link NamedElement}.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class AttributeNameColumn extends AbstractColumn {

    private TextCellEditor editor;

    private final boolean isReadOnly;

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     */
    public AttributeNameColumn(EditingDomain editingDomain,
            ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Name column
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public AttributeNameColumn(EditingDomain editingDomain,
            ColumnViewer viewer, boolean isReadOnly) {
        super(editingDomain, viewer, Messages.AttributeNameColumn_name_label, 200);
        this.isReadOnly = isReadOnly;
        if (!isReadOnly) {
            editor = new TextCellEditor((Composite) viewer.getControl());
        }
        setShowImage(true);
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof Attribute) {
            Attribute attribute = (Attribute) element;
            if (attribute.getName() != null) {
                return attribute.getDisplayName();
            }
        }
        return ""; //$NON-NLS-1$
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }
}
