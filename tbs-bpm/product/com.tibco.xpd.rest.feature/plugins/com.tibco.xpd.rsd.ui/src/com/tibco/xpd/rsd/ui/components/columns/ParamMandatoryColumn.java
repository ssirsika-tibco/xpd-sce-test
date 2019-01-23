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
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;

/**
 * Column for editing RSD parameter's 'mandatory' property.
 *
 * @author jarciuch
 * @since 13 Feb 2015
 */
public class ParamMandatoryColumn extends AbstractColumn {
    private final CheckboxCellEditor editor;

    /**
     * Creates column.
     */
    public ParamMandatoryColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer, SWT.NONE, Messages.ParamMandatoryColumn_MandatoryColumn_label, 80);
        editor =
                new CheckboxCellEditor((Composite) viewer.getControl(),
                        SWT.NONE);
    }

    /** {@inheritDoc} */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof Parameter) {
            return editor;
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
        if (element instanceof Parameter) {
            Parameter param = (Parameter) element;
            if (param.isMandatory()) {
                return CheckboxCellEditor.getImgChecked();
            }
            return CheckboxCellEditor.getImgUnchecked();
        }
        return super.getImage(element);
    }

    /** {@inheritDoc} */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        CompoundCommand cmd = null;
        if (element instanceof Parameter) {
            Parameter param = (Parameter) element;
            if (value instanceof Boolean) {
                cmd = new CompoundCommand(Messages.ParamMandatoryColumn_SetMandatoryCmd_label);
                cmd.append(SetCommand.create(getEditingDomain(),
                        param,
                        RsdPackage.eINSTANCE.getParameter_Mandatory(),
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
        if (element instanceof Parameter) {
            Parameter param = (Parameter) element;
            return new Boolean(param.isMandatory());
        }
        return null;
    }

}