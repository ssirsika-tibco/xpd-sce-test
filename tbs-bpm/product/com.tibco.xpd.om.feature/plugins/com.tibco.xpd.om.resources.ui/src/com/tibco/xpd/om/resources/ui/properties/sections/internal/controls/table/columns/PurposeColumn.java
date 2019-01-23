/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.MultilineTextCellEditor;

/**
 * Purpose column for use with <code>OrgElement</code>
 * 
 * @author rgreen
 * 
 */
public class PurposeColumn extends AbstractColumn {

    private MultilineTextCellEditor editor;

    private final boolean isReadOnly;

    /**
     * 
     * Purpose column
     * 
     * @param editingDomain
     * @param viewer
     */
    public PurposeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        this(editingDomain, viewer, false);
    }

    /**
     * Purpose column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public PurposeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            boolean isReadOnly) {
        super(editingDomain, viewer,
                Messages.PurposeColumn_purposeColumn_title, 150);
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor =
                    new MultilineTextCellEditor((Composite) viewer.getControl());
        }
        setShowImage(false);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        return element instanceof OrgElement ? editor : null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof OrgElement && value instanceof String) {

            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getOrgElement_Purpose(),
                    value);

        }

        return null;
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof OrgElement) {
            OrgElement qoe = (OrgElement) element;

            if (qoe.getPurpose() != null) {
                return qoe.getPurpose();
            }
        }
        return ""; //$NON-NLS-1$
    }

    @Override
    protected Image getImage(Object element) {
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        return getText(element);
    }

}
