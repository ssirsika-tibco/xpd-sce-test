/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.DialogCellWithClearEditor;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;

/**
 * {@link OrgTypedElement#getType() Type} attribute column of the
 * <code>OrgTypedElement</code> for use in the groups
 * {@link AbstractTableControl table}.
 * 
 * @author njpatel
 * 
 */
public class TypeColumn extends AbstractColumn {

    private TypeEditor editor;

    // OM specific picker content type.
    private final String type;

    private final boolean isReadOnly;

    /**
     * Type column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param type
     *            type of the picker content that the picker of the value in
     *            this column will show.
     */
    public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String type) {
        this(editingDomain, viewer, type, false);
    }

    /**
     * Type column.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param type
     *            type of the picker content that the picker of the value in
     *            this column will show. Can be <code>null</code> if this is a
     *            read-only column
     * @param isReadOnly
     *            <code>true</code> if this is a read-only column,
     *            <code>false</code> if editing is allowed.
     */
    public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer,
            String type, boolean isReadOnly) {
        super(editingDomain, viewer, Messages.TypeColumn_typeColumn_title, 250);
        this.type = type;
        this.isReadOnly = isReadOnly;

        if (!isReadOnly) {
            editor = new TypeEditor((Composite) viewer.getControl());
        }
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        if (!isReadOnly && element instanceof OrgTypedElement) {
            editor.setInput((OrgTypedElement) element);
            return editor;
        }
        return null;
    }

    @Override
    protected Command getSetValueCommand(Object element, Object value) {
        if (element instanceof OrgTypedElement) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getOrgTypedElement_Type(),
                    value instanceof OrgElementType ? value
                            : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    @Override
    protected String getText(Object element) {
        if (element instanceof OrgTypedElement) {
            return getTypeText(((OrgTypedElement) element).getType());
        }
        return null;
    }

    @Override
    protected Image getImage(Object element) {
        // Get the type image
        if (element instanceof OrgTypedElement) {
            element = ((OrgTypedElement) element).getType();
        } else {
            element = null;
        }
        return super.getImage(element);
    }

    /**
     * Get the display name of the element type.
     * 
     * @param type
     * @return
     */
    private String getTypeText(OrgElementType type) {
        if (type != null) {
            return getModelLabelProvider().getText(type);
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        if (element instanceof OrgTypedElement) {
            return ((OrgTypedElement) element).getType();
        }
        return null;
    }

    /**
     * Editor to show the {@link OrgElementType} picker.
     * 
     * @author njpatel
     * 
     */
    private class TypeEditor extends DialogCellWithClearEditor {

        private OrgTypedElement input;

        private OrgElementType currentType;

        protected TypeEditor(Composite parent) {
            super(parent);
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            if (input != null && type != null) {
                return PickerService
                        .getInstance()
                        .openSinglePickerDialog(cellEditorWindow.getShell(),
                                new PickerTypeQuery[] { new OMTypeQuery(type) },
                                null,
                                null,
                                null,
                                new IFilter[] { new SameResourceFilter(input) });
            }
            return null;
        }

        protected void setInput(OrgTypedElement input) {
            this.input = input;
        }

        @Override
        protected void doSetValue(Object value) {
            if (value instanceof OrgElementType) {
                currentType = (OrgElementType) value;
                value = getTypeText(currentType);
            } else {
                currentType = null;
            }
            super.doSetValue(value);
        }

        @Override
        protected Object doGetValue() {
            return currentType;
        }
    }

}
