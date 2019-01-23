/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;

/**
 * Column for editing RSD payload's 'array' flag.
 * 
 * @author nwilson
 * @since 18 Aug 2015
 */
public class PayloadArrayColumn extends AbstractColumn {
    private final CheckboxCellEditor editor;

    /**
     * Creates column.
     */
    public PayloadArrayColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer, SWT.NONE, Messages.PayloadArrayColumn_HeaderLabel, 80);
        editor =
                new CheckboxCellEditor((Composite) viewer.getControl(),
                        SWT.NONE);
    }

    /** {@inheritDoc} */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof PayloadRefContainer) {
            PayloadRefContainer container = (PayloadRefContainer) element;
            PayloadReference ref = container.getPayloadReference();
            if (ref != null) {
                JsonTypeReference jsonReference =
                        JsonTypeReference.getJsonReference(ref);
                if (jsonReference != null
                        && !JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                                .equals(jsonReference.getRef())) {
                    return editor;
                }
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected boolean getShowImage() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected Image getImage(Object element) {
        if (element instanceof PayloadRefContainer) {
            PayloadRefContainer refContainer = (PayloadRefContainer) element;
            PayloadReference ref = refContainer.getPayloadReference();
            if (ref != null) {
                JsonTypeReference jsonReference =
                        JsonTypeReference.getJsonReference(ref);
                if (jsonReference != null
                        && !JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                                .equals(jsonReference.getRef())) {
                    if (ref.isArray()) {
                        return CheckboxCellEditor.getImgChecked();
                    }
                    return CheckboxCellEditor.getImgUnchecked();
                }
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        CompoundCommand cmd = null;
        if (value instanceof Boolean && element instanceof PayloadRefContainer) {
            PayloadRefContainer refContainer = (PayloadRefContainer) element;
            PayloadReference ref = refContainer.getPayloadReference();
            if (ref != null) {
                cmd = new CompoundCommand(Messages.PayloadArrayColumn_SetPayloadArrayCommand);
                cmd.append(SetCommand.create(getEditingDomain(),
                        ref,
                        RsdPackage.eINSTANCE.getPayloadReference_Array(),
                        value));
            }
        }
        return cmd;
    }

    /** {@inheritDoc} */
    @Override
    protected String getText(Object element) {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected Object getValueForEditor(Object element) {
        if (element instanceof PayloadRefContainer) {
            PayloadRefContainer refContainer = (PayloadRefContainer) element;
            PayloadReference ref = refContainer.getPayloadReference();
            if (ref != null) {
                return ref.isArray();
            }
        }
        return false;
    }

}