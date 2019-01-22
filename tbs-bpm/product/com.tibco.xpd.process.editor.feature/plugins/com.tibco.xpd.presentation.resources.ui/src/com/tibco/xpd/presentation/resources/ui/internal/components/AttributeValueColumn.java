/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.components;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.presentation.channels.AttributeValue;
import com.tibco.xpd.presentation.channels.ChannelsFactory;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.AttributeType;
import com.tibco.xpd.presentation.channeltypes.EnumLiteral;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;

/**
 * {@link AbstractTableControl Table} column for {@link Attribute} values. This
 * will display the appropriate cell editor for each attribute type.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class AttributeValueColumn extends AbstractColumn {

    private final TextCellEditor txtEditor;

    private final ComboBoxCellEditor boolEditor;

    private final Composite root;

    private final Image errImg;

    private final ResourcePickerCellEditor resourceEditor;

    /**
     * <code>Attribute</code> value column that displayed the appropriate cell
     * editor depending on the <code>Attribute</code> type.
     * 
     * @param editingDomain
     * @param viewer
     *            table viewer
     * @param label
     *            column name
     * @param width
     *            column width
     */
    public AttributeValueColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String label, int width) {
        super(editingDomain, viewer, SWT.LEFT, label, width);

        root = (Composite) viewer.getControl();
        txtEditor = new TextCellEditor(root);
        boolEditor =
                new ComboBoxCellEditor(root, new String[] { "", //$NON-NLS-1$
                        Boolean.TRUE.toString(), Boolean.FALSE.toString() },
                        SWT.READ_ONLY);
        resourceEditor = new ResourcePickerCellEditor(root);
        errImg =
                PlatformUI.getWorkbench().getSharedImages()
                        .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
        setShowImage(true);
    }

    @Override
    protected CellEditor getCellEditor(Object element) {
        CellEditor editor = null;
        Attribute attr = getAttribute(element);
        if (attr != null) {
            switch (attr.getType()) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
                editor = txtEditor;
                break;
            case RESOURCE:
                editor = resourceEditor;
                break;
            case BOOLEAN:
                editor = boolEditor;
                break;
            }
        }
        return editor;
    }

    @Override
    protected final Command getSetValueCommand(Object element, Object value) {
        Attribute attr = getAttribute(element);
        if (attr != null) {
            String str;

            switch (attr.getType()) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
            case RESOURCE:
                return getSetCommand(element, value);
            case BOOLEAN:
                str = null;
                if (value instanceof Integer) {
                    Integer idx = (Integer) value;
                    String[] items = boolEditor.getItems();
                    if (items.length > idx && idx >= 0) {
                        str = items[idx];
                    }
                }
                return getSetCommand(element, str);
            }
            return getSetCommand(element, value);
        }
        return null;
    }

    @Override
    protected Object getValueForEditor(Object element) {
        Attribute attr = getAttribute(element);
        if (attr != null) {
            String text = getText(element);

            switch (attr.getType()) {
            case TEXT:
            case DECIMAL:
            case INTEGER:
            case RESOURCE:
                return text;
            case BOOLEAN:
                String[] values = boolEditor.getItems();
                for (int idx = 0; idx < values.length; idx++) {
                    if (values[idx].equals(text)) {
                        return idx;
                    }
                }
                return 0;
            }
        }
        return getText(element);
    }

    @Override
    protected Image getImage(Object element) {
        Object value = getValueForEditor(element);
        Attribute attr = getAttribute(element);
        /*
         * Validate the DECIMAL and INTEGER inputs - if invalid then show error
         * icon
         */
        if (attr != null && value instanceof String && errImg != null) {
            try {
                switch (attr.getType()) {
                case DECIMAL:
                    Double.parseDouble((String) value);
                    break;
                case INTEGER:
                    Integer.parseInt((String) value);
                    break;
                }
            } catch (NumberFormatException e) {
                return errImg;
            }
        }
        return null;
    }

    /**
     * Get the {@link Command} to set the value of the given
     * <code>Attribute</code>.
     * 
     * @param element
     *            element being edited
     * @param value
     *            the attribute value
     * @return {@link Command} or <code>null</code> if error.
     */
    protected Command getSetCommand(Object element, Object value) {
        Command cmd = null;
        Object input = getViewer().getInput();
        if (input instanceof TypeAssociation && element instanceof Attribute
                && ((Attribute) element).getType() != null) {
            TypeAssociation typeAssociation = (TypeAssociation) input;
            Attribute attr = (Attribute) element;
            AttributeValue attrValue = getAttributeValue(attr);
            if (attrValue != null) {
                // if (attr.getType() == AttributeType.ENUM
                // || attr.getType() == AttributeType.ENUM_SET) {
                // Collection<?> enumValues = null;
                //                    
                // if (value instanceof EnumValue) {
                // enumValues = Collections.singletonList(value);
                // } else if (value instanceof Collection<?>) {
                // enumValues = (Collection<?>) value;
                // } else {
                // enumValues = Collections.EMPTY_LIST;
                // }
                //    
                // CompoundCommand ccmd = new CompoundCommand();
                // ccmd.append(SetCommand.create(ed, attrValue,
                // OMPackage.eINSTANCE.getAttributeValue_Value(),
                // SetCommand.UNSET_VALUE));
                // ccmd.append(SetCommand.create(ed, attrValue,
                // OMPackage.eINSTANCE
                // .getAttributeValue_EnumSetValues(),
                // enumValues));
                // cmd = ccmd;
                // } else {
                // cmd = SetCommand.create(ed, attrValue,
                // OMPackage.eINSTANCE.getAttributeValue_Value(),
                // value);
                // }
                cmd =
                        SetCommand.create(getEditingDomain(),
                                attrValue,
                                ChannelsPackage.eINSTANCE
                                        .getAttributeValue_Value(),
                                value);
            } else {
                attrValue = ChannelsFactory.eINSTANCE.createAttributeValue();
                attrValue.setAttribute(attr);
                // if (attr.getType() == AttributeType.ENUM
                // || attr.getType() == AttributeType.ENUM_SET) {
                // Collection<?> enumValues = null;
                //
                // if (value instanceof EnumValue) {
                // enumValues = Collections.singletonList(value);
                // } else if (value instanceof Collection<?>) {
                // enumValues = (Collection<?>) value;
                // } else {
                // enumValues = Collections.EMPTY_LIST;
                // }
                // attrValue.eSet(OMPackage.eINSTANCE
                // .getAttributeValue_EnumSetValues(), enumValues);
                // } else if (value instanceof String) {
                // attrValue.setValue((String) value);
                // }
                attrValue.setValue((String) value);
                cmd =
                        AddCommand.create(getEditingDomain(),
                                input,
                                ChannelsPackage.eINSTANCE
                                        .getTypeAssociation_AttributeValues(),
                                attrValue);
            }
        }

        return cmd;
    }

    @Override
    protected String getText(Object element) {
        String text = null;

        if (element instanceof Attribute) {
            Attribute attr = (Attribute) element;
            AttributeValue value = getAttributeValue(attr);

            if (value != null) {
                if (attr.getType() == AttributeType.ENUM
                        || attr.getType() == AttributeType.ENUM_SET) {
                    StringBuffer buffer = new StringBuffer();
                    if (value.getEnumValues() != null) {
                        for (EnumLiteral enumValue : value.getEnumValues()) {
                            if (buffer.length() > 0) {
                                buffer.append(", "); //$NON-NLS-1$
                            }
                            buffer.append(enumValue.getName());
                        }
                    }
                    text = buffer.toString();
                } else {
                    text = value.getValue();
                }
            } else {
                text = getAttributeDefaultValue(attr);
            }
        }

        return text != null ? text : ""; //$NON-NLS-1$
    }

    /**
     * Get the {@link Attribute} from the given element.
     * 
     * @param element
     *            context element.
     * @return attribute element if it is an <code>Attribute</code>,
     *         <code>null</code> otherwise.
     */
    protected Attribute getAttribute(Object element) {
        return (Attribute) (element instanceof Attribute ? element : null);
    }

    protected AttributeValue getAttributeValue(Object element) {
        if (element instanceof Attribute) {
            Attribute attribute = (Attribute) element;
            Object input = getViewer().getInput();
            if (input instanceof TypeAssociation) {
                TypeAssociation typeAssociation = (TypeAssociation) input;
                for (AttributeValue attrValue : typeAssociation
                        .getAttributeValues()) {
                    if (attribute.equals(attrValue.getAttribute())) {
                        return attrValue;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the default value of this attribute if set
     * 
     * @param attr
     * @return
     */
    protected String getAttributeDefaultValue(Attribute attr) {
        if (attr != null) {
            if (attr.getType() == AttributeType.ENUM
                    || attr.getType() == AttributeType.ENUM_SET) {
                StringBuffer buff = new StringBuffer();
                for (EnumLiteral value : attr.getDefaultEnumSet()) {
                    if (buff.length() > 0) {
                        buff.append(", "); //$NON-NLS-1$
                    }
                    buff.append(value.getName());
                }
                return buff.toString();
            }

            return attr.getDefaultValue();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected void setValueFromEditor(Object element, Object value) {
        super.setValueFromEditor(element, value);

        // TODO workaround - remove when proper
        // adapter->factory->content/label provider->viewer will be
        // working.
        getViewer().refresh();
    }

    protected IProject getContextProject() {
        Object input = getViewer().getInput();
        if (input instanceof TypeAssociation) {
            TypeAssociation ta = (TypeAssociation) input;
            Resource resource = ta.eResource();
            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);
                if (file != null) {
                    return file.getProject();
                }
            }
        }
        return null;
    }

    /**
     * Editor with picker for resource from "Presentation" special folders.
     */
    private class ResourcePickerCellEditor extends DialogCellEditor {

        public ResourcePickerCellEditor(Composite parent) {
            super(parent);
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            String result = null;
            PresentationResourcePicker picker =
                    new PresentationResourcePicker(cellEditorWindow.getShell(),
                            getValue().toString(), getContextProject());
            if (picker.open() == org.eclipse.jface.window.Window.OK) {
                result = (String) picker.getFirstResult();
                if (result == null) { // CLEAR button was pressed.
                    result = ""; //$NON-NLS-1$
                }
            }
            return result;
        }
    }

}
