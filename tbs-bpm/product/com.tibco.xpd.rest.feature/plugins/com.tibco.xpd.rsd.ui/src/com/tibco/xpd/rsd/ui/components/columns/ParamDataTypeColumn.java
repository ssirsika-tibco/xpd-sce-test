/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.components.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.rsd.DataType;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Attribute type column which displays a combo for type selection.
 * 
 * @author jarciuch
 * @since 6 Mar 2015
 */
public class ParamDataTypeColumn extends AbstractColumn {

    private final ExtendedComboBoxCellEditor cmbEditor;

    private DataTypeLabelProvider labelProvider;

    public ParamDataTypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
        super(editingDomain, viewer, SWT.LEFT,
                Messages.ParamDataTypeColumn_DataTypeColumn_label, 100);

        labelProvider = new DataTypeLabelProvider();
        cmbEditor =
                new ExtendedComboBoxCellEditor((Composite) viewer.getControl(),
                        DataType.VALUES, labelProvider, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        return cmbEditor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof Parameter && value instanceof DataType) {
            // If value has changed then update the type
            Parameter param = (Parameter) element;
            DataType newType = (DataType) value;

            if (!newType.equals(param.getDataType())) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.ParamDataTypeColumn_SetDataType_label);
                cmd.append(SetCommand.create(getEditingDomain(),
                        param,
                        RsdPackage.eINSTANCE.getParameter_DataType(),
                        newType));
                cmd.append(SetCommand.create(getEditingDomain(),
                        param,
                        RsdPackage.eINSTANCE.getParameter_DefaultValue(),
                        null));
                return cmd;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getText(Object element) {
        Object value = getValueForEditor(element);
        if (value instanceof DataType) {
            DataType dataType = (DataType) value;
            if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                    .equals(dataType.getName())) {
                return PrimitivesUtil.BOM_PRIMITIVE_NUMBER_NAME;
            } else {
                return dataType.getName();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object getValueForEditor(Object element) {
        if (element instanceof Parameter) {
            DataType type = ((Parameter) element).getDataType();
            if (type != null) {
                return type;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispose() {
        if (labelProvider != null) {
            labelProvider.dispose();
        }
        super.dispose();
    }

    /**
     * Label provider for Data Type column.
     *
     * @author jarciuch
     * @since 1 Mar 2015
     */
    private static class DataTypeLabelProvider extends DecoratingLabelProvider {

        public DataTypeLabelProvider() {
            super(new TransactionalAdapterFactoryLabelProvider(
                    XpdResourcesPlugin.getDefault().getEditingDomain(),
                    RsdUIPlugin.getPlugin().getItemProvidersAdapterFactory()),
                    null);
            AdapterFactoryLabelProvider labelProvider =
                    (AdapterFactoryLabelProvider) getLabelProvider();
            labelProvider.setFireLabelUpdateNotifications(true);
        }

        @Override
        public String getText(Object element) {
            if (element instanceof DataType) {
                DataType dataType = (DataType) element;
                if (PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME
                        .equals(dataType.getName())) {
                    return PrimitivesUtil.BOM_PRIMITIVE_NUMBER_NAME;
                } else {
                    return dataType.getName();
                }
            }
            return super.getText(element);
        }
    }
}