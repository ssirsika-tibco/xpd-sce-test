/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.QualifiedOrgElement;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.DateUtil;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateControl;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateControl.DateControlHandler;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.TableMoveUpAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;

/**
 * General section for an {@link Attribute}.
 * 
 * @author njpatel
 */
public class AttributeSection extends AbstractGeneralSection {

    private CCombo typeCombo;

    private ComboViewer typeComboViewer;

    private StackLayout valueAreaLayout;

    private Composite valueAreaContainer;

    // Default value / enum literal components
    private TextValueComponent textValueComponent;

    private BooleanValueComponent booleanValueComponent;

    private CalendarValueComponent calendarValueComponent;

    private EnumLiteralsComponent enumLiteralsComponent;

    private final static String DEFAULT_VALUE_LABEL =
            Messages.AttributeSection_defaultValue_label;

    /**
     * The current default value / enum literal component area depending on
     * attribute type selected
     */
    private ValueComponent currentValueComponent;

    private final TransactionalAdapterFactoryLabelProvider modelLabelProvider;

    public AttributeSection() {
        modelLabelProvider =
                new TransactionalAdapterFactoryLabelProvider(XpdResourcesPlugin
                        .getDefault().getEditingDomain(), XpdResourcesPlugin
                        .getDefault().getAdapterFactory());
        setShouldUseExtraSpace(true);
    }

    @Override
    public void dispose() {
        modelLabelProvider.dispose();
        super.dispose();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.AttributeSection_type_label);

        typeCombo =
                toolkit.createCCombo(root,
                        SWT.NONE,
                        "qualifiedOrgElement-qualifier"); //$NON-NLS-1$
        typeCombo.setEditable(false);
        typeComboViewer = new ComboViewer(typeCombo);
        typeComboViewer.setContentProvider(new ArrayContentProvider());
        typeComboViewer.setLabelProvider(modelLabelProvider);
        typeComboViewer.setInput(getTypes());
        GridData data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
        data.widthHint = 150;
        typeCombo.setLayoutData(data);

        manageControl(typeCombo);

        // Add the value controls that will handle the enum and default values
        // of the attribute type selection
        Composite valueControls = createValueArea(root, toolkit);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 2;
        valueControls.setLayoutData(data);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {

        if (obj == typeCombo) {
            EObject input = getInput();

            if (input instanceof Attribute) {
                Attribute attr = (Attribute) input;
                // Only update model if value has changed
                Object element =
                        ((IStructuredSelection) typeComboViewer.getSelection())
                                .getFirstElement();

                if (element instanceof AttributeType
                        && !element.equals(attr.getType())) {
                    return SetCommand.create(getEditingDomain(),
                            attr,
                            OMPackage.eINSTANCE.getAttribute_Type(),
                            element);
                }
            }
        }
        return currentValueComponent != null ? currentValueComponent
                .getCommand(obj) : null;
    }

    /**
     * Get the {@link AttributeType}s in sorted order.
     * 
     * @return sorted list of <code>AttributeType</code>s.
     */
    private Collection<AttributeType> getTypes() {
        List<AttributeType> types =
                new ArrayList<AttributeType>(AttributeType.VALUES);
        Collections.sort(types, new Comparator<AttributeType>() {

            @Override
            public int compare(AttributeType o1, AttributeType o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

        });

        return types;
    }

    @Override
    protected void doRefresh() {
        // Update the type setting
        EObject input = getInput();

        if (input instanceof Attribute) {
            AttributeType type = ((Attribute) input).getType();

            if (type != null) {
                typeComboViewer.setSelection(new StructuredSelection(type));
                showDefaultValueEntry(type);
                if (currentValueComponent != null) {
                    currentValueComponent.refresh((Attribute) input);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        if (toTest instanceof QualifiedOrgElement) {
            return ((QualifiedOrgElement) toTest).getQualifierAttribute() != null;
        }
        return false;
    }

    /**
     * Show the appropriate default value entry controls.
     * 
     * @param entry
     *            attribute type selected
     */
    private void showDefaultValueEntry(AttributeType type) {
        if (valueAreaLayout != null && valueAreaContainer != null
                && !valueAreaContainer.isDisposed()) {
            ValueComponent prevControl = currentValueComponent;
            switch (type) {
            case TEXT:
            case INTEGER:
            case DECIMAL:
                currentValueComponent = textValueComponent;
                break;
            case BOOLEAN:
                currentValueComponent = booleanValueComponent;
                break;
            case DATE_TIME:
            case DATE:
            case TIME:
                currentValueComponent = calendarValueComponent;
                break;
            case ENUM:
            case ENUM_SET:
                currentValueComponent = enumLiteralsComponent;
                break;
            case SET:
                // No default value can be set for a "Set"
                currentValueComponent = null;
                break;
            }

            // If type has changed then update the default entry controls
            if (currentValueComponent != null) {
                currentValueComponent.setAttributeType(type);
                if (currentValueComponent != prevControl) {
                    prevControl = currentValueComponent;
                    valueAreaLayout.topControl =
                            currentValueComponent.getControl();
                    valueAreaLayout.topControl.setVisible(true);
                    valueAreaContainer.layout();
                }
            } else if (valueAreaLayout.topControl != null) {
                valueAreaLayout.topControl.setVisible(false);
            }
        }
    }

    /**
     * Create the value area that will contain the setting of default values and
     * also the Enum values (as appropriate).
     * 
     * @param root
     *            parent composite
     * @param toolkit
     *            form toolkit
     * @return value composite.
     */
    private Composite createValueArea(Composite parent, XpdFormToolkit toolkit) {

        valueAreaContainer = toolkit.createComposite(parent);
        valueAreaLayout = new StackLayout();
        valueAreaLayout.marginHeight = 10;
        valueAreaContainer.setLayout(valueAreaLayout);

        /*
         * Add text control to add default value for Text, Integer and Decimal
         * types
         */
        textValueComponent =
                new TextValueComponent(valueAreaContainer, toolkit);
        /*
         * Add combo control to select default boolean value
         */
        booleanValueComponent =
                new BooleanValueComponent(valueAreaContainer, toolkit);
        /*
         * Add date control to selected default date/time
         */
        calendarValueComponent =
                new CalendarValueComponent(valueAreaContainer, toolkit);

        /*
         * Add the enumeration literal table
         */
        enumLiteralsComponent =
                new EnumLiteralsComponent(valueAreaContainer, toolkit);

        return valueAreaContainer;
    }

    /**
     * Create a label with the given alignment.
     * 
     * @see #createLabel(Composite, XpdFormToolkit, String)
     * 
     * @param parent
     *            parent container
     * @param toolkit
     *            form toolkit
     * @param text
     *            label
     * @param horizontalAlignment
     * @param verticalAlignment
     * @return
     */
    private Label createLabel(Composite parent, XpdFormToolkit toolkit,
            String text, int horizontalAlignment, int verticalAlignment) {
        Label label = super.createLabel(parent, toolkit, text);
        GridData data = (GridData) label.getLayoutData();
        data.horizontalAlignment = horizontalAlignment;
        data.verticalAlignment = verticalAlignment;

        return label;
    }

    /**
     * Abstract component for default value input. This will be extended by
     * various default value entry controls.
     * 
     * @author njpatel
     * 
     */
    private abstract class ValueComponent {
        private final Composite rootContainer;

        private AttributeType type;

        public ValueComponent(Composite parent, XpdFormToolkit toolkit,
                String label) {
            rootContainer = toolkit.createComposite(parent);
            GridLayout layout = new GridLayout(3, false);
            layout.marginWidth = 0;
            rootContainer.setLayout(layout);

            createLabel(rootContainer, toolkit, label, SWT.LEFT, SWT.TOP);
            createContent(rootContainer, toolkit);
        }

        /**
         * Create content after the label for the default value = this has 2
         * columns to use in the grid layout.
         * 
         * @param parent
         * @param toolkit
         * @return
         */
        abstract void createContent(Composite parent, XpdFormToolkit toolkit);

        /**
         * Get the main component of this control.
         * 
         * @return
         */
        Control getControl() {
            return rootContainer;
        }

        /**
         * Get command to set the default value. n a command.
         * 
         * @param obj
         * @return
         */
        Command getCommand(Object obj) {
            return null;
        }

        /**
         * Refresh the default value.
         * 
         * @param attr
         */
        abstract void refresh(Attribute attr);

        /**
         * Get the command to set the default value.
         * 
         * @param defaultValue
         * @return
         */
        Command getSetDefaultValueCommand(String defaultValue) {
            EObject input = getInput();
            if (input instanceof Attribute) {
                return SetCommand
                        .create(getEditingDomain(),
                                input,
                                OMPackage.eINSTANCE.getAttribute_DefaultValue(),
                                defaultValue != null
                                        && defaultValue.length() > 0 ? defaultValue
                                        : SetCommand.UNSET_VALUE);
            }
            return null;
        }

        /**
         * Set the type of <code>Attribute</code>.
         * 
         * @param type
         */
        public void setAttributeType(AttributeType type) {
            this.type = type;
        }

        /**
         * Get the <code>Attribute</code> type of this control.
         * 
         * @return
         */
        public AttributeType getAttributeType() {
            return type;
        }
    }

    /**
     * Control for entry of text-based default value.
     * 
     * @author njpatel
     * 
     */
    class TextValueComponent extends ValueComponent {
        private Text textControl;

        TextValueComponent(Composite parent, XpdFormToolkit toolkit) {
            super(parent, toolkit, DEFAULT_VALUE_LABEL);
        }

        @Override
        void createContent(Composite parent, XpdFormToolkit toolkit) {
            textControl = toolkit.createText(parent, "", //$NON-NLS-1$
                    "text-default-value"); //$NON-NLS-1$
            GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
            data.horizontalSpan = 2;
            textControl.setLayoutData(data);
            manageControlUpdateOnDeactivate(textControl);
        }

        @Override
        public Command getCommand(Object obj) {
            if (obj == textControl) {
                return getSetDefaultValueCommand(parseText(textControl
                        .getText()));
            }
            return super.getCommand(obj);
        }

        @Override
        public void refresh(Attribute attr) {
            if (attr != null) {
                updateText(textControl, formatText(attr.getDefaultValue()));
            }
        }

        /**
         * Parse the text into a locale-independent value (for numeric and
         * decimal).
         * 
         * @param text
         * @return
         */
        private String parseText(String text) {
            AttributeType type = getAttributeType();
            if (type != null && text != null) {
                try {
                    switch (type) {
                    case INTEGER:
                        Number num =
                                NumberFormat.getIntegerInstance().parse(text);
                        text = num.toString();
                    case DECIMAL:
                        Number intNum = NumberFormat.getInstance().parse(text);
                        text = intNum.toString();
                    }
                } catch (ParseException e) {
                    text = "0"; //$NON-NLS-1$
                }
            }
            return text;
        }

        /**
         * Format the text to return a locale-specific value (for numeric and
         * decimal).
         * 
         * @param text
         * @return
         */
        private String formatText(String text) {
            String parsedText = text;
            AttributeType type = getAttributeType();
            if (text != null && type != null) {
                try {
                    switch (type) {
                    case INTEGER:
                        Long intValue = Long.parseLong(parsedText);
                        parsedText =
                                NumberFormat.getIntegerInstance()
                                        .format(intValue);
                        break;
                    case DECIMAL:
                        Double dbl = Double.parseDouble(text);
                        parsedText = NumberFormat.getInstance().format(dbl);
                        break;
                    }
                } catch (Exception e) {
                    return text;
                }
            }
            return parsedText;
        }

    }

    /**
     * Control for entry of boolean default value.
     * 
     * @author njpatel
     * 
     */
    private class BooleanValueComponent extends ValueComponent {
        private CCombo booleanCombo;

        BooleanValueComponent(Composite parent, XpdFormToolkit toolkit) {
            super(parent, toolkit, DEFAULT_VALUE_LABEL);
        }

        @Override
        void createContent(Composite parent, XpdFormToolkit toolkit) {
            booleanCombo =
                    toolkit.createCCombo(parent, "boolean-default-value"); //$NON-NLS-1$
            GridData gData = new GridData(SWT.LEFT, SWT.TOP, false, false);
            gData.widthHint = 150;
            gData.horizontalSpan = 2;
            booleanCombo.setLayoutData(gData);
            booleanCombo.add("", 0); //$NON-NLS-1$
            // XPD-4006 Externalized boolean values
            booleanCombo.add(Messages.AttributeSection_BooleanFalse, 1);
            booleanCombo.add(Messages.AttributeSection_BooleanTrue, 2);
            manageControl(booleanCombo);
        }

        @Override
        public Command getCommand(Object obj) {
            if (obj == booleanCombo) {
                // If default value has changed then set it
                EObject input = getInput();
                if (input instanceof Attribute) {
                    String defaultValue = ((Attribute) input).getDefaultValue();
                    String selectedval = ""; //$NON-NLS-1$
                    int selectedIndex = booleanCombo.getSelectionIndex();
                    switch (selectedIndex) {
                    case 1:
                        selectedval = "false"; //$NON-NLS-1$
                        break;
                    case 2:
                        selectedval = "true"; //$NON-NLS-1$
                        break;

                    default:
                        break;
                    }
                    if (!selectedval.equals(defaultValue)) {
                        return getSetDefaultValueCommand(selectedval);
                    }
                }
            }
            return super.getCommand(obj);
        }

        @Override
        public void refresh(Attribute attr) {
            Boolean bool = null;
            if (attr != null) {

                String value = attr.getDefaultValue();

                if (value != null && value.length() > 0) {
                    bool = Boolean.parseBoolean(value);
                }
            }

            if (bool != null) {
                if (bool) {
                    booleanCombo.setText(Messages.AttributeSection_BooleanTrue);
                } else {
                    booleanCombo
                            .setText(Messages.AttributeSection_BooleanFalse);
                }
            } else {
                booleanCombo.setText(""); //$NON-NLS-1$
            }
        }
    }

    /**
     * Control for the entry of a date/time default value.
     * 
     * @author njpatel
     * 
     */
    private class CalendarValueComponent extends ValueComponent {

        public CalendarValueComponent(Composite parent, XpdFormToolkit toolkit) {
            super(parent, toolkit, DEFAULT_VALUE_LABEL);
        }

        private DateControl dateControl;

        private DateControlHandler getHander() {
            return new DateControlHandler() {

                @Override
                public Command getClearDateCommand(EditingDomain ed, Date date) {
                    return getSetDefaultValueCommand(null);
                }

                @Override
                public Command getSetDateCommand(EditingDomain ed, Date date) {
                    return getSetDefaultValueCommand(getValue(getAttributeType(),
                            date));
                }
            };
        }

        @Override
        void createContent(Composite parent, XpdFormToolkit toolkit) {
            dateControl =
                    new DateControl(parent, SWT.NONE, toolkit,
                            getEditingDomain(), getHander());           
            dateControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            dateControl.setValue((Date) null);
        }

        @Override
        public void setAttributeType(AttributeType type) {
            super.setAttributeType(type);

            // Update the date control to display the appropriate default value
            switch (type) {
            case DATE:
                dateControl.setType(DateType.DATE);
                break;

            case TIME:
                dateControl.setType(DateType.TIME);
                break;

            case DATE_TIME:
                dateControl.setType(DateType.DATETIME);
                break;
            }

        }

        @Override
        public void refresh(Attribute attr) {
            if (attr != null) {
                dateControl.setValue(attr.getDefaultValue());
            }
        }

        /**
         * Get the ISO 8601 format string representation of the given date.
         * 
         * @param type
         * @param date
         * @return
         */
        private String getValue(AttributeType type, Date date) {
            String value = null;

            if (type != null && date != null) {
                switch (type) {
                case DATE:
                    value = DateUtil.format(date, DateType.DATE);
                    break;

                case TIME:
                    value = DateUtil.format(date, DateType.TIME);
                    break;

                case DATE_TIME:
                    value = DateUtil.format(date, DateType.DATETIME);
                    break;
                }
            }

            return value;
        }
    }

    /**
     * Control for the enumeration entry.
     * 
     * @author njpatel
     * 
     */
    private class EnumLiteralsComponent extends ValueComponent {
        private EnumLiteralTable table;

        EnumLiteralsComponent(Composite parent, XpdFormToolkit toolkit) {
            super(parent, toolkit, Messages.AttributeSection_values_label);
        }

        @Override
        void createContent(Composite parent, XpdFormToolkit toolkit) {
            table =
                    new EnumLiteralTable(parent, toolkit, SWT.FULL_SELECTION,
                            getEditingDomain());
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
            data.horizontalSpan = 2;
            table.setLayoutData(data);
        }

        @Override
        public void refresh(Attribute attr) {
            if (attr != null && attr.getValues() != null) {
                table.setInput(attr);
            }
        }
    }

    /**
     * Table to show the enumeration literals and allowed edition for these
     * values.
     * 
     * @author njpatel
     */
    private class EnumLiteralTable extends BaseTableControl {

        private final EditingDomain editingDomain;

        private TableViewerColumn defaultCol;

        private TableViewerColumn valueCol;

        private Attribute input;

        private final boolean isEditable;

        private final IContentProvider contentProvider;

        /**
         * Table to show enumeration literals.
         * 
         * @param parent
         *            parent control
         * @param toolkit
         *            forms toolkit
         * @param style
         *            {@link SWT} style
         * @param editingDomain
         *            editing domain
         */
        public EnumLiteralTable(Composite parent, XpdFormToolkit toolkit,
                int style, EditingDomain editingDomain) {
            this(parent, toolkit, style, editingDomain, true);
        }

        /**
         * Table to show enumeration literals.
         * 
         * @param parent
         *            parent control
         * @param toolkit
         *            forms toolkit
         * @param style
         *            {@link SWT} style
         * @param editingDomain
         *            editing domain
         * @param editable
         *            <code>true</code> if enum values can be edited,
         *            <code>false</code> if only default value can be selected
         *            but no editing is allowed.
         */
        public EnumLiteralTable(Composite parent, XpdFormToolkit toolkit,
                int style, EditingDomain editingDomain, boolean editable) {
            super(parent, toolkit, style, false);
            this.editingDomain = editingDomain;
            this.isEditable = editable;
            this.contentProvider = new ArrayContentProvider();
            setBackground(parent.getBackground());

            createContents(this, toolkit, null);
        }

        /**
         * Set the input of this table.
         * 
         * @param input
         *            {@link Attribute}
         */
        public void setInput(Attribute input) {
            this.input = input;
            getTableViewer().setInput(input.getValues());
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerContentProvider()
         * 
         * @return
         */
        @Override
        protected IContentProvider getViewerContentProvider() {
            return contentProvider;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerAddAction createAddAction(ColumnViewer viewer) {
            if (isEditable) {
                return new TableAddAction(viewer) {

                    @Override
                    protected Object addRow(StructuredViewer viewer) {
                        String label = getNewRowFirstCellVal();

                        EnumValue val = OMFactory.eINSTANCE.createEnumValue();
                        val.setValue(label);

                        // Add the enum value to the attribute
                        if (input != null && editingDomain != null) {
                            editingDomain.getCommandStack()
                                    .execute(AddCommand.create(editingDomain,
                                            input,
                                            OMPackage.eINSTANCE
                                                    .getAttribute_Values(),
                                            val));
                        }

                        return val;
                    }

                    /**
                     * Get the new element label.
                     * 
                     * @return
                     */
                    private String getNewRowFirstCellVal() {
                        String newName =
                                Messages.AttributeSection_newValue_name;
                        EList<EnumValue> values = input.getValues();

                        if (values != null && !values.isEmpty()) {
                            Set<String> names = new HashSet<String>();
                            for (EnumValue value : values) {
                                names.add(value.getValue());
                            }
                            newName = OMUtil.getDefaultName(newName, names);
                        }
                        return newName;
                    }

                };
            }
            return super.createAddAction(viewer);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
            if (isEditable) {
                return new TableDeleteAction(viewer) {

                    @Override
                    protected void deleteRows(IStructuredSelection selection) {
                        if (selection != null && editingDomain != null
                                && input != null) {
                            Set<EnumValue> valuesToDelete =
                                    new HashSet<EnumValue>();
                            for (Iterator<?> iter = selection.iterator(); iter
                                    .hasNext();) {
                                Object next = iter.next();
                                if (next instanceof EnumValue) {
                                    valuesToDelete.add((EnumValue) next);
                                }
                            }

                            if (!valuesToDelete.isEmpty()) {
                                CompoundCommand ccmd = new CompoundCommand();

                                // Find out if any value has been set as default
                                // and unset if so
                                List<EnumValue> defaultValuesToRemove =
                                        new ArrayList<EnumValue>();

                                EList<EnumValue> defaultValues =
                                        input.getDefaultEnumSetValues();
                                if (defaultValues != null
                                        && !defaultValues.isEmpty()) {
                                    for (EnumValue value : valuesToDelete) {
                                        if (defaultValues.contains(value)) {
                                            defaultValuesToRemove.add(value);
                                        }
                                    }

                                    if (!defaultValuesToRemove.isEmpty()) {
                                        // Unset the default values
                                        ccmd.append(RemoveCommand
                                                .create(editingDomain,
                                                        input,
                                                        OMPackage.eINSTANCE
                                                                .getAttribute_DefaultEnumSetValues(),
                                                        defaultValuesToRemove));
                                    }
                                }

                                ccmd.append(RemoveCommand.create(editingDomain,
                                        input,
                                        OMPackage.eINSTANCE
                                                .getAttribute_Values(),
                                        valuesToDelete));

                                editingDomain.getCommandStack().execute(ccmd);
                            }
                        }
                    }
                };
            }
            return super.createDeleteAction(viewer);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
            return new TableMoveUpAction(viewer) {
                @Override
                protected void moveUp(Object element) {
                    EditingDomain ed = getEditingDomain();
                    if (input != null && input.getValues() != null
                            && ed != null) {
                        int idx = input.getValues().indexOf(element);
                        if (idx > 0) {
                            ed.getCommandStack().execute(MoveCommand.create(ed,
                                    input,
                                    OMPackage.eINSTANCE.getAttribute_Values(),
                                    element,
                                    idx - 1));
                        }
                    }
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         * @return
         */
        @Override
        protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
            return new TableMoveDownAction(viewer) {

                @Override
                protected void moveDown(Object element) {
                    EditingDomain ed = getEditingDomain();
                    if (input != null && input.getValues() != null
                            && ed != null) {
                        int idx = input.getValues().indexOf(element);
                        if (idx < input.getValues().size()) {
                            ed.getCommandStack().execute(MoveCommand.create(ed,
                                    input,
                                    OMPackage.eINSTANCE.getAttribute_Values(),
                                    element,
                                    idx + 1));
                        }
                    }
                }
            };
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer columnViewer) {
            TableViewer viewer = (TableViewer) columnViewer;
            valueCol =
                    createColumn(viewer,
                            SWT.LEFT,
                            Messages.AttributeSection_valueColumn_title,
                            250);
            if (isEditable) {
                valueCol.setEditingSupport(new EnumLitEditingSupport(viewer,
                        valueCol));
            }
            valueCol.setLabelProvider(new EnumLitCellLabelProvider(valueCol));

            defaultCol =
                    createColumn(viewer,
                            SWT.LEFT,
                            Messages.AttributeSection_defaultColumn_title,
                            50);
            defaultCol.setEditingSupport(new EnumLitEditingSupport(viewer,
                    defaultCol));
            defaultCol
                    .setLabelProvider(new EnumLitCellLabelProvider(defaultCol));
        }

        /**
         * Check if the given enum value is set as the default.
         * 
         * @param value
         * @return <code>true</code> if default, <code>false</code> otherwise.
         */
        private boolean isDefault(EnumValue value) {
            boolean ret = false;

            if (value != null) {

                if (input != null) {
                    EList<EnumValue> values = input.getDefaultEnumSetValues();

                    if (values != null) {
                        ret = values.contains(value);
                    }
                }
            }

            return ret;
        }

        /**
         * Label provider for the columns in the enumeration literal table.
         * 
         * @author njpatel
         * 
         */
        private class EnumLitCellLabelProvider extends ColumnLabelProvider {

            private final TableViewerColumn column;

            public EnumLitCellLabelProvider(TableViewerColumn column) {
                this.column = column;

            }

            @Override
            public Image getImage(Object element) {
                if (column == defaultCol) {
                    if (element instanceof EnumValue) {
                        if (isDefault((EnumValue) element))
                            return CheckboxCellEditor.getImgChecked();
                    }
                    return CheckboxCellEditor.getImgUnchecked();
                }
                return super.getImage(element);
            }

            @Override
            public String getText(Object element) {
                if (column == valueCol) {
                    return getViewerLabelProvider().getText(element);
                }

                return null;
            }
        }

        /**
         * Editing support for the columns in the enumeration literal table.
         * 
         * @author njpatel
         * 
         */
        private class EnumLitEditingSupport extends EditingSupport {

            private CellEditor editor;

            private final TableViewerColumn column;

            public EnumLitEditingSupport(ColumnViewer viewer,
                    TableViewerColumn column) {
                super(viewer);
                this.column = column;
                if (column == defaultCol) {
                    editor =
                            new CheckboxCellEditor(
                                    (Composite) viewer.getControl(), SWT.NONE);
                } else if (column == valueCol) {
                    editor =
                            new TextCellEditor((Composite) viewer.getControl());
                }
            }

            @Override
            protected boolean canEdit(Object element) {
                return editor != null;
            }

            @Override
            protected CellEditor getCellEditor(Object element) {
                return editor;
            }

            @Override
            protected Object getValue(Object element) {
                if (column == defaultCol) {
                    // Reverse the value returned from isDefault so that it will
                    // toggle the change in the editor
                    return element instanceof EnumValue
                            && isDefault((EnumValue) element);
                } else if (column == valueCol) {
                    return getViewerLabelProvider().getText(element);
                }

                return null;
            }

            @Override
            protected void setValue(Object element, Object value) {

                if (element instanceof EnumValue && value != null
                        && editingDomain != null) {
                    EnumValue enumValue = (EnumValue) element;
                    Command cmd = null;

                    if (column == defaultCol) {
                        /*
                         * If this is an EnumSet then multiple default values
                         * can be added, otherwise only one default value is
                         * allowed.
                         */
                        if (input != null) {
                            boolean isDefault =
                                    value instanceof Boolean ? (Boolean) value
                                            : false;
                            AttributeType type = input.getType();

                            if (type == AttributeType.ENUM) {
                                // Only one default is allowed
                                if (isDefault) {
                                    BasicEList<EnumValue> list =
                                            new BasicEList<EnumValue>();
                                    list.add(enumValue);
                                    cmd =
                                            SetCommand
                                                    .create(editingDomain,
                                                            input,
                                                            OMPackage.eINSTANCE
                                                                    .getAttribute_DefaultEnumSetValues(),
                                                            list);
                                } else {
                                    // Unset the default value
                                    cmd =
                                            RemoveCommand
                                                    .create(editingDomain,
                                                            input,
                                                            OMPackage.eINSTANCE
                                                                    .getAttribute_DefaultEnumSetValues(),
                                                            input.getDefaultEnumSetValues());
                                }

                            } else {
                                // Multiple defaults allowed
                                if (isDefault) {
                                    cmd =
                                            AddCommand
                                                    .create(editingDomain,
                                                            input,
                                                            OMPackage.eINSTANCE
                                                                    .getAttribute_DefaultEnumSetValues(),
                                                            enumValue);
                                } else {
                                    cmd =
                                            RemoveCommand
                                                    .create(editingDomain,
                                                            input,
                                                            OMPackage.eINSTANCE
                                                                    .getAttribute_DefaultEnumSetValues(),
                                                            enumValue);
                                }
                            }
                        }

                    } else if (column == valueCol && value instanceof String
                            && ((String) value).length() > 0) {
                        cmd =
                                SetCommand.create(editingDomain,
                                        enumValue,
                                        OMPackage.eINSTANCE
                                                .getEnumValue_Value(),
                                        value);
                    }

                    if (cmd != null) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
            }
        }

    }

}
