/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Sections for the {@link Attribute}s tab for resource types to define
 * attributes for the type.
 * 
 * @author njpatel
 * 
 */
public class AttributesDefinitionSection extends AttributesTableSection {

    private AttributeValueColumnImpl valueCol;

    @Override
    protected Collection<ViewerAction> getActions(TableViewer viewer) {
        List<ViewerAction> actions = new ArrayList<ViewerAction>();

        actions.add(getAddAction(viewer));
        actions.add(getDeleteAction(viewer));
        actions.add(getMoveUpAction(viewer));
        actions.add(getMoveDownAction(viewer));
        return actions;
    }

    private ViewerAction getAddAction(TableViewer viewer) {
        return new TableAddAction(viewer) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Attribute attr = OMFactory.eINSTANCE.createAttribute();
                attr.setDisplayName(getNewRowFirstCellVal());
                attr.setType(AttributeType.TEXT);
                EObject input = getInput();
                EditingDomain editingDomain = getEditingDomain();

                // Add the attribute
                if (input != null && editingDomain != null) {
                    editingDomain.getCommandStack()
                            .execute(AddCommand.create(editingDomain,
                                    input,
                                    OMPackage.eINSTANCE
                                            .getOrgElementType_Attributes(),
                                    attr));
                }

                return attr;
            }

            private String getNewRowFirstCellVal() {
                String name =
                        Messages.AttributesDefinitionSection_defaultAttribute_name;
                EObject input = getInput();

                if (input instanceof OrgElementType) {
                    ItemProviderAdapter provider = getItemProvider(input);

                    if (provider != null) {
                        Collection<?> descriptors =
                                provider.getNewChildDescriptors(input,
                                        getEditingDomain(),
                                        null);

                        if (descriptors != null) {
                            for (Object descriptor : descriptors) {
                                if (descriptor instanceof CommandParameter) {
                                    Object value =
                                            ((CommandParameter) descriptor)
                                                    .getValue();
                                    if (value instanceof Attribute) {
                                        name =
                                                ((Attribute) value)
                                                        .getDisplayName();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                return name;
            }
        };
    }

    private ViewerAction getDeleteAction(TableViewer viewer) {
        return new TableDeleteAction(viewer) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();
                EditingDomain editingDomain = getEditingDomain();
                if (selection != null && editingDomain != null
                        && input instanceof OrgElementType) {
                    // Remove the selected enum value
                    editingDomain.getCommandStack()
                            .execute(RemoveCommand.create(editingDomain,
                                    input,
                                    OMPackage.eINSTANCE
                                            .getOrgElementType_Attributes(),
                                    selection.toList()));
                }
            }
        };
    }

    private ViewerAction getMoveUpAction(TableViewer viewer) {
        return new TableMoveUpAction(viewer) {
            @Override
            protected void moveUp(Object element) {
                if (element instanceof Attribute) {
                    moveAttribute((Attribute) element, -1);
                }
            }
        };
    }

    private ViewerAction getMoveDownAction(TableViewer viewer) {
        return new TableMoveDownAction(viewer) {
            @Override
            protected void moveDown(Object element) {
                if (element instanceof Attribute) {
                    moveAttribute((Attribute) element, 1);
                }
            }
        };
    }

    /**
     * Move the given attribute by the moveBy value in the attributes list.
     * 
     * @param attr
     * @param moveBy
     *            negative value to move up and positive value to move down.
     */
    private void moveAttribute(Attribute attr, int moveBy) {
        EObject input = getInput();
        EditingDomain ed = getEditingDomain();

        if (input instanceof OrgElementType && ed != null && attr != null
                && moveBy != 0) {
            EList<Attribute> attributes = getAttributes((OrgElementType) input);
            if (!attributes.isEmpty()) {
                int idx = attributes.indexOf(attr) + moveBy;

                if (idx >= 0 && idx < attributes.size()) {
                    ed.getCommandStack().execute(MoveCommand.create(ed,
                            input,
                            OMPackage.eINSTANCE.getOrgElementType_Attributes(),
                            attr,
                            idx));

                }
            }
        }
    }

    @Override
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
        new AttributeTypeColumn(getEditingDomain(), viewer);
        valueCol = new AttributeValueColumnImpl(getEditingDomain(), viewer);
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        EList<Attribute> attributes = null;

        if (input instanceof OrgElementType) {
            attributes = getAttributes((OrgElementType) input);
        }

        setElements(attributes);

        if (valueCol != null) {
            valueCol.refresh();
        }
    }

    /**
     * Get the {@link Attribute} children of this section's input.
     * 
     * @param elementType
     * @return list of Attributes or empty list if no children present
     */
    private EList<Attribute> getAttributes(OrgElementType elementType) {
        EList<Attribute> attributes = new BasicEList<Attribute>();

        if (elementType != null) {
            ItemProviderAdapter provider = getItemProvider(elementType);
            if (provider != null) {
                Collection<?> children = provider.getChildren(elementType);
                if (children != null) {
                    for (Object child : children) {
                        if (child instanceof Attribute) {
                            attributes.add((Attribute) child);
                        }
                    }
                }
            }
        }

        return attributes;
    }

    /**
     * Get item provider for the given EObject.
     * 
     * @param eo
     * @return
     */
    private ItemProviderAdapter getItemProvider(EObject eo) {
        if (eo != null) {
            IEditingDomainItemProvider provider =
                    (IEditingDomainItemProvider) XpdResourcesPlugin
                            .getDefault().getAdapterFactory()
                            .adapt(eo, IEditingDomainItemProvider.class);
            if (provider instanceof ItemProviderAdapter) {
                return (ItemProviderAdapter) provider;
            }
        }
        return null;
    }

    /**
     * Attribute type column which displays a combo for type selection.
     * 
     * @author njpatel
     * 
     */
    private class AttributeTypeColumn extends AbstractColumn {

        private final ExtendedComboBoxCellEditor cmbEditor;

        public AttributeTypeColumn(EditingDomain editingDomain,
                TableViewer viewer) {
            super(editingDomain, viewer, SWT.LEFT,
                    Messages.AttributesDefinitionSection_typeColumn_title, 100);

            cmbEditor =
                    new ExtendedComboBoxCellEditor(
                            (Composite) viewer.getControl(),
                            AttributeType.VALUES, getModelLabelProvider(), true);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return cmbEditor;
        }

        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            if (element instanceof Attribute && value instanceof AttributeType) {
                // If value has changed then update the type
                Attribute attr = (Attribute) element;
                AttributeType newType = (AttributeType) value;

                if (!newType.equals(attr.getType())) {
                    return SetCommand.create(getEditingDomain(),
                            attr,
                            OMPackage.eINSTANCE.getAttribute_Type(),
                            newType);
                }
            }
            return null;
        }

        @Override
        protected String getText(Object element) {
            Object value = getValueForEditor(element);
            if (value instanceof AttributeType) {
                return ((AttributeType) value).getName();
            }
            return null;
        }

        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof Attribute) {
                AttributeType type = ((Attribute) element).getType();
                if (type != null) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * Implementation of the attribute value column. This allows the setting of
     * the default value for each attribute.
     * 
     * @author njpatel
     * 
     */
    private class AttributeValueColumnImpl extends AttributeValueColumn {

        private final EnumSetDialogEditor dlgEditor;

        private final Image errImg;

        public AttributeValueColumnImpl(EditingDomain editingDomain,
                TableViewer viewer) {
            super(
                    editingDomain,
                    viewer,
                    Messages.AttributesDefinitionSection_defaultValueColumn_title,
                    200);

            dlgEditor =
                    new EnumSetDialogEditor((Composite) viewer.getControl());
            setShowImage(true);

            errImg =
                    PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
        }

        /**
         * Refresh the column
         */
        public void refresh() {
            if (dlgEditor != null) {
                dlgEditor.refresh();
            }
        }

        /**
         * @see com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.AttributeValueColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {
            Attribute attr = getAttribute(element);
            if (attr.getType() == AttributeType.SET) {
                return "-no default can be set-";
            }
            return super.getText(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Color getForeground(Object element) {
            Attribute attr = getAttribute(element);
            if (attr.getType() == AttributeType.SET) {
                return ColorConstants.gray;
            }
            return super.getForeground(element);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Attribute) {
                Attribute attr = (Attribute) element;

                if (attr.getType() != null) {
                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET) {
                        dlgEditor.setAttribute(attr);
                        return dlgEditor;
                    }
                }
            }
            return super.getCellEditor(element);
        }

        @Override
        protected Object getValue(Object element) {
            Attribute attr = getAttribute(element);
            if (attr != null && attr.getType() != null) {
                switch (attr.getType()) {
                case ENUM:
                case ENUM_SET:
                    return attr.getDefaultEnumSetValues();
                }
                return attr.getDefaultValue();
            }
            return null;
        }

        @Override
        protected Image getImage(Object element) {
            if (element instanceof Attribute) {
                boolean showError = false;
                Attribute attr = (Attribute) element;
                if (attr.getDefaultValue() != null
                        && attr.getDefaultValue().length() > 0) {
                    try {
                        switch (attr.getType()) {
                        case DECIMAL:
                            Double.parseDouble(attr.getDefaultValue());
                            break;
                        case INTEGER:
                            Integer.parseInt(attr.getDefaultValue());
                            break;
                        }
                    } catch (NumberFormatException e) {
                        showError = true;
                    }
                }

                if (showError) {
                    return errImg;
                }
            }
            return null;
        }

        @Override
        protected Command getSetCommand(Object element, Object value) {
            Command cmd = null;
            if (element instanceof Attribute) {
                Attribute attr = (Attribute) element;
                if (attr != null && attr.getType() != null) {

                    if (attr.getType() == AttributeType.ENUM
                            || attr.getType() == AttributeType.ENUM_SET) {
                        // Ignore if value is String for ENUM and ENUMSET =
                        // expect null or EnumValue or collection
                        if (!(value instanceof String)) {
                            cmd =
                                    SetCommand
                                            .create(getEditingDomain(),
                                                    attr,
                                                    OMPackage.eINSTANCE
                                                            .getAttribute_DefaultEnumSetValues(),
                                                    value != null ? value
                                                            : SetCommand.UNSET_VALUE);
                        }
                    } else {
                        cmd =
                                SetCommand.create(getEditingDomain(),
                                        attr,
                                        OMPackage.eINSTANCE
                                                .getAttribute_DefaultValue(),
                                        value != null ? value
                                                : SetCommand.UNSET_VALUE);
                    }
                }
            }
            return cmd;
        }
    }

    /**
     * Dialog editor for ENUM and ENUMSET attributes.
     * 
     * @author njpatel
     * 
     */
    private class EnumSetDialogEditor extends DialogCellEditor {

        private Attribute attribute;

        private EnumSetValueDialog dlg;

        public EnumSetDialogEditor(Composite parent) {
            super(parent);
        }

        public void refresh() {
            if (dlg != null) {
                dlg.refresh();
            }
        }

        protected void setAttribute(Attribute attribute) {
            this.attribute = attribute;
        }

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            dlg = new EnumSetValueDialog(cellEditorWindow);
            dlg.setAttribute(attribute);
            dlg.open();
            dlg = null;
            return null;
        }
    }

    /**
     * Dialog to set the EnumValues for the ENUM and ENUMSET attributes.
     * 
     * @author njpatel
     * 
     */
    private class EnumSetValueDialog extends Dialog {

        private EnumSetValueTable table;

        private Attribute attribute;

        private final Control control;

        protected EnumSetValueDialog(Control control) {
            super(control.getShell());
            this.control = control;
            setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE
                    | SWT.CLOSE | getDefaultOrientation());
        }

        public void refresh() {
            if (table != null && !table.isDisposed()) {
                table.getTableViewer().refresh();
            }
        }

        @Override
        protected Point getInitialLocation(Point initialSize) {

            Point location = null;
            if (control != null) {
                Rectangle r = control.getBounds();
                if (control.getParent() != null) {
                    location = control.getParent().toDisplay(r.x, r.y);
                    location = new Point(location.x + r.width, location.y);
                }
            }
            return location != null ? location : super
                    .getInitialLocation(initialSize);
        }

        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);
            newShell.setText(Messages.AttributesDefinitionSection_setValues_dialog_title);
        }

        protected void setAttribute(Attribute attribute) {
            this.attribute = attribute;
        }

        @Override
        protected Control createDialogArea(Composite parent) {
            Composite root = (Composite) super.createDialogArea(parent);

            CLabel lbl = new CLabel(root, SWT.NONE);
            GridData data = new GridData(SWT.FILL, SWT.CENTER, false, false);
            data.widthHint = 300;
            lbl.setLayoutData(data);
            String msg =
                    attribute != null ? String
                            .format(Messages.AttributesDefinitionSection_setValues_dialog_message,
                                    attribute.getDisplayName())
                            : Messages.AttributesDefinitionSection_setValues_label;
            lbl.setText(shortenText(msg, lbl));

            table =
                    new EnumSetValueTable(root, getToolkit(),
                            SWT.FULL_SELECTION | SWT.MULTI);
            table.setBackground(root.getBackground());
            data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.widthHint = 350;
            data.heightHint = 150;
            table.setLayoutData(data);

            if (attribute != null) {
                table.setElements(attribute.getValues());
            }

            return root;
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            createButton(parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true);
        }

        /**
         * Check if the given EnumValue is set as the default.
         * 
         * @param value
         * @return <code>true</code> if default, <code>false</code> otherwise.
         */
        protected boolean isDefault(EnumValue value) {
            if (value != null && attribute != null) {
                return attribute.getDefaultEnumSetValues() != null
                        && attribute.getDefaultEnumSetValues().contains(value);
            }
            return false;
        }

        /**
         * Get the next default enum value label.
         * 
         * @return
         */
        protected String getNewEnumValueLabel() {
            String newName = Messages.AttributesDefinitionSection_newValue_name;

            if (attribute != null) {
                EList<EnumValue> values = attribute.getValues();
                List<String> names = new ArrayList<String>();
                for (EnumValue value : values) {
                    names.add(value.getValue());
                }
                newName = OMUtil.getDefaultName(newName, names);
            }

            return newName;
        }

        /**
         * Create a new EnumValue.
         * 
         * @param name
         *            name of the EnumValue
         * @return created EnumValue or <code>null</code>.
         */
        protected EnumValue createNewValue(String name) {
            if (getEditingDomain() != null && attribute != null) {
                EnumValue value = OMFactory.eINSTANCE.createEnumValue();
                value.setValue(name);

                getEditingDomain().getCommandStack()
                        .execute(AddCommand.create(getEditingDomain(),
                                attribute,
                                OMPackage.eINSTANCE.getAttribute_Values(),
                                value));

                return value;
            }
            return null;
        }

        protected void deleteEnumValues(Collection<?> values) {
            EditingDomain ed = getEditingDomain();
            if (ed != null && attribute != null && values != null
                    && !values.isEmpty()) {
                CompoundCommand cmd = new CompoundCommand();

                if (!attribute.getDefaultEnumSetValues().isEmpty()) {
                    // Remove any values that are set as defaults
                    List<EnumValue> valuesToDelete = new ArrayList<EnumValue>();
                    for (EnumValue value : attribute.getDefaultEnumSetValues()) {
                        if (values.contains(value)) {
                            valuesToDelete.add(value);
                        }
                    }

                    if (!valuesToDelete.isEmpty()) {
                        cmd.append(RemoveCommand.create(ed,
                                attribute,
                                OMPackage.eINSTANCE
                                        .getAttribute_DefaultEnumSetValues(),
                                valuesToDelete));
                    }
                }
                cmd.append(RemoveCommand.create(ed,
                        attribute,
                        OMPackage.eINSTANCE.getAttribute_Values(),
                        values));

                ed.getCommandStack().execute(cmd);
            }
        }

        /**
         * Get the Command to set/unset the default enum value.
         * 
         * @param value
         * @param isDefault
         * @return
         */
        protected Command getSetDefaultCommand(EnumValue value,
                boolean isDefault) {
            Command cmd = null;
            if (value != null && attribute != null) {
                if (isDefault) {
                    if (attribute.getType() == AttributeType.ENUM) {
                        cmd =
                                SetCommand
                                        .create(getEditingDomain(),
                                                attribute,
                                                OMPackage.eINSTANCE
                                                        .getAttribute_DefaultEnumSetValues(),
                                                Collections
                                                        .singletonList(value));
                    } else if (attribute.getType() == AttributeType.ENUM_SET) {
                        cmd =
                                AddCommand
                                        .create(getEditingDomain(),
                                                attribute,
                                                OMPackage.eINSTANCE
                                                        .getAttribute_DefaultEnumSetValues(),
                                                value);
                    }
                } else {
                    cmd =
                            RemoveCommand
                                    .create(getEditingDomain(),
                                            attribute,
                                            OMPackage.eINSTANCE
                                                    .getAttribute_DefaultEnumSetValues(),
                                            value);
                }
            }
            return cmd;
        }

        /**
         * EnumValue table in the {@link EnumSetValueDialog}.
         * 
         * @author njpatel
         * 
         */
        private class EnumSetValueTable extends AbstractTableControl {

            public EnumSetValueTable(Composite parent, XpdFormToolkit toolkit,
                    int style) {
                super(parent, toolkit, style);
            }

            @Override
            protected TableAddAction getAddAction(TableViewer viewer) {
                return new TableAddAction(viewer) {
                    @Override
                    protected Object addRow(StructuredViewer viewer) {
                        return createNewValue(getNewEnumValueLabel());
                    }
                };
            }

            @Override
            protected TableDeleteAction getDeleteAction(TableViewer viewer) {
                return new TableDeleteAction(viewer) {

                    @Override
                    protected void deleteRows(IStructuredSelection selection) {
                        if (getEditingDomain() != null && selection != null
                                && !selection.isEmpty()) {
                            deleteEnumValues(selection.toList());
                        }
                    }

                };
            }

            @Override
            protected TableMoveUpAction getMoveUpAction(TableViewer viewer) {
                return new TableMoveUpAction(viewer) {
                    @Override
                    protected void moveUp(Object element) {
                        if (element instanceof EnumValue) {
                            moveValue((EnumValue) element, -1);
                        }
                    }
                };
            }

            @Override
            protected TableMoveDownAction getMoveDownAction(TableViewer viewer) {
                return new TableMoveDownAction(viewer) {
                    @Override
                    protected void moveDown(Object element) {
                        if (element instanceof EnumValue) {
                            moveValue((EnumValue) element, 1);
                        }
                    }
                };
            }

            private void moveValue(EnumValue value, int moveBy) {
                EditingDomain ed = getEditingDomain();

                if (attribute != null && ed != null && value != null
                        && moveBy != 0) {
                    EList<EnumValue> values = attribute.getValues();
                    if (!values.isEmpty()) {
                        int idx = values.indexOf(value) + moveBy;

                        if (idx >= 0 && idx < values.size()) {
                            ed.getCommandStack().execute(MoveCommand.create(ed,
                                    attribute,
                                    OMPackage.eINSTANCE.getAttribute_Values(),
                                    value,
                                    idx));

                        }
                    }
                }
            }

            @Override
            protected void addColumns(TableViewer viewer) {
                new EnumValueColumn(getEditingDomain(), viewer);
                new DefaultColumn(getEditingDomain(), viewer);
            }

            /**
             * The value column
             * 
             * @author njpatel
             * 
             */
            private class EnumValueColumn extends AbstractColumn {
                private final TextCellEditor txtEditor;

                public EnumValueColumn(EditingDomain editingDomain,
                        TableViewer viewer) {
                    super(
                            editingDomain,
                            viewer,
                            SWT.LEFT,
                            Messages.AttributesDefinitionSection_valueColumn_title,
                            150);
                    txtEditor =
                            new TextCellEditor((Composite) viewer.getControl());
                    setShowImage(true);
                }

                @Override
                protected CellEditor getCellEditor(Object element) {
                    return txtEditor;
                }

                @Override
                protected Command getSetValueCommand(Object element,
                        Object value) {
                    if (element instanceof EnumValue && value instanceof String) {
                        value = ((String) value).trim();

                        if (((String) value).length() > 0) {
                            return SetCommand.create(getEditingDomain(),
                                    element,
                                    OMPackage.eINSTANCE.getEnumValue_Value(),
                                    value);
                        }
                    }
                    return null;
                }

                @Override
                protected String getText(Object element) {
                    String text = null;
                    if (element instanceof EnumValue) {
                        text = ((EnumValue) element).getValue();
                    }
                    return text != null ? text : ""; //$NON-NLS-1$
                }

                @Override
                protected Object getValueForEditor(Object element) {
                    return getText(element);
                }
            }

            private class DefaultColumn extends AbstractColumn {

                private final CheckboxCellEditor chkEditor;

                public DefaultColumn(EditingDomain editingDomain,
                        TableViewer viewer) {
                    super(
                            editingDomain,
                            viewer,
                            SWT.RIGHT,
                            Messages.AttributesDefinitionSection_defaultColumn_title,
                            50);
                    setShowImage(true);
                    chkEditor =
                            new CheckboxCellEditor(
                                    (Composite) viewer.getControl(), SWT.NONE);
                }

                @Override
                protected CellEditor getCellEditor(Object element) {
                    return chkEditor;
                }

                @Override
                protected Command getSetValueCommand(Object element,
                        Object value) {
                    if (element instanceof EnumValue
                            && value instanceof Boolean) {
                        return getSetDefaultCommand((EnumValue) element,
                                (Boolean) value);
                    }
                    return null;
                }

                @Override
                protected String getText(Object element) {
                    return null;
                }

                @Override
                protected Image getImage(Object element) {
                    if (element instanceof EnumValue) {
                        return isDefault((EnumValue) element) ? CheckboxCellEditor
                                .getImgChecked() : CheckboxCellEditor
                                .getImgUnchecked();
                    }
                    return CheckboxCellEditor.getImgUnchecked();
                }

                @Override
                protected Object getValueForEditor(Object element) {
                    if (element instanceof EnumValue) {
                        return isDefault((EnumValue) element);
                    }
                    return false;
                }
            }
        }
    }
}
