/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes;

import java.util.Collection;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateTimeCellEditor;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractTableControl;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.components.calendar.DateType;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Abstract section for the attributes table section. This class should be
 * extended by a section that needs to show an attribute table. The section will
 * be responsible for adding the columns and actions to the table.
 * 
 * @author njpatel
 * 
 */
public abstract class AttributesTableSection extends
        AbstractTransactionalSection {

    private AttributesTable table;
    private Font tableHeaderFont;
    private final TransactionalAdapterFactoryLabelProvider modelLabelProvider;
    private final String title;
    private XpdFormToolkit toolkit;

    /**
     * Abstract attribute section.
     */
    public AttributesTableSection() {
        this(Messages.AttributesTableSection_attributesSection_title);
    }

    public AttributesTableSection(String title) {
        this.title = title;
        modelLabelProvider = new TransactionalAdapterFactoryLabelProvider(
                XpdResourcesPlugin.getDefault().getEditingDomain(),
                XpdResourcesPlugin.getDefault().getAdapterFactory());
    }

    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EditPart) {
            return (EObject) ((EditPart) object).getAdapter(EObject.class);
        }

        return super.resollveInput(object);
    }

    /**
     * Get the model adapter factory label provider.
     * 
     * @return
     */
    protected ILabelProvider getModelLabelProvider() {
        return modelLabelProvider;
    }

    protected XpdFormToolkit getToolkit() {
        return toolkit;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        this.toolkit = toolkit;
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        Label label = toolkit.createLabel(root, title);
        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;
        label.setLayoutData(data);
        label.setForeground(ColorConstants.darkGray);
        tableHeaderFont = new Font(root.getDisplay(), "Arial", 10, //$NON-NLS-1$
                SWT.BOLD);
        label.setFont(tableHeaderFont);

        table = new AttributesTable(root, toolkit, SWT.FULL_SELECTION
                | SWT.MULTI);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // Handled by the table
        return null;
    }

    @Override
    public void dispose() {
        if (tableHeaderFont != null) {
            tableHeaderFont.dispose();
        }

        modelLabelProvider.dispose();

        super.dispose();
    }

    /**
     * Get actions to add to the table.
     * 
     * @param viewer
     *            table viewer
     * @return collection of {@link ViewerAction}s or <code>null</code> (or
     *         empty) collection for no actions.
     */
    protected abstract Collection<ViewerAction> getActions(TableViewer viewer);

    /**
     * Add columns to the table.
     * 
     * @see #createColumn(TableViewer, int, String, int)
     * @param viewer
     */
    protected abstract void addColumns(TableViewer viewer);

    /**
     * Create a column in the given table viewer.
     * 
     * @param viewer
     *            table viewer
     * @param style
     *            {@link SWT} style
     * @param label
     *            column label
     * @param width
     *            initial column width
     * @return table viewer column
     */
    protected TableViewerColumn createColumn(TableViewer viewer, int style,
            String label, int width) {
        return AbstractTableControl.createColumn(viewer, style, label, width);
    }

    /**
     * Set the elements to display in the table.
     * 
     * @param elements
     */
    protected void setElements(EList<? extends EObject> elements) {
        if (table != null) {
            table.setElements(elements);
        }
    }

    /**
     * Table to display a list of {@link Attribute}s. This will contain the
     * name, type and default value columns.
     * 
     * @author njpatel
     * 
     */
    private class AttributesTable extends AbstractTableControl {

        /**
         * Attributes table showing the name, type and default value columns.
         * 
         * @param parent
         *            parent control
         * @param toolkit
         *            form toolkit
         * @param style
         *            {@link SWT} style
         */
        public AttributesTable(Composite parent, XpdFormToolkit toolkit,
                int style) {
            super(parent, toolkit, style);
        }

        @Override
        protected Collection<com.tibco.xpd.resources.ui.components.ViewerAction> getActions(
                TableViewer viewer) {
            return AttributesTableSection.this.getActions(viewer);
        }

        @Override
        protected void addColumns(TableViewer viewer) {
            AttributesTableSection.this.addColumns(viewer);
        }
    }

    /**
     * Table column editing support for attribute value setting. This will show
     * the appropriate cell editor based on attribute type being edited.
     * 
     * @author njpatel
     * 
     */
    protected abstract class AttributeDefinitionColumnEditingSupport extends
            EditingSupport {
        private final DateTimeCellEditor dateTimeCellEditor;
        private final TextCellEditor textEditor;
        private final ComboBoxCellEditor boolComboEditor;
        private final String[] booleanDefaultValues = new String[] { "", //$NON-NLS-1$
                Boolean.TRUE.toString(), Boolean.FALSE.toString() };
        private final EditingDomain editingDomain;

        public AttributeDefinitionColumnEditingSupport(ColumnViewer viewer,
                EditingDomain editingDomain) {
            super(viewer);
            this.editingDomain = editingDomain;

            // Set up editors
            textEditor = new TextCellEditor((Composite) viewer.getControl());
            boolComboEditor = new ComboBoxCellEditor((Composite) viewer
                    .getControl(), booleanDefaultValues, SWT.READ_ONLY);
            dateTimeCellEditor = new DateTimeCellEditor((Composite) viewer
                    .getControl());
        }

        /**
         * Get the {@link Attribute} from the given element in the table. The
         * default implementation will try to cast the element to an
         * <code>Attribute</code>.
         * 
         * @param element
         * @return <code>Attribute</code> or null if the element is not an
         *         <code>Attribute</code> or is <code>null</code>.
         */
        protected Attribute getAttribute(Object element) {
            if (element instanceof Attribute) {
                return (Attribute) element;
            }
            return null;
        }

        /**
         * Get the value of this <code>Attribute</code>.
         * 
         * @param attr
         * @return value or <code>null</code> if not set.
         */
        protected abstract String getValue(Attribute attr);

        protected abstract Command getSetValueCommand(Object element,
                Object value);

        @Override
        protected boolean canEdit(Object element) {
            return getCellEditor(element) != null;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            CellEditor editor = null;
            Attribute attr = getAttribute(element);
            if (attr != null) {
                switch (attr.getType()) {
                case TEXT:
                case INTEGER:
                case DECIMAL:
                    editor = textEditor;
                    break;

                case BOOLEAN:
                    editor = boolComboEditor;
                    break;

                case DATE:
                    dateTimeCellEditor.setType(DateType.DATE);
                    editor = dateTimeCellEditor;
                    break;
                case TIME:
                    dateTimeCellEditor.setType(DateType.TIME);
                    editor = dateTimeCellEditor;
                    break;
                case DATE_TIME:
                    dateTimeCellEditor.setType(DateType.DATETIME);
                    editor = dateTimeCellEditor;
                    break;
                }
            }

            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            Object ret = null;
            Attribute attr = getAttribute(element);
            String value;
            if (attr != null) {
                switch (attr.getType()) {
                case TEXT:
                case INTEGER:
                case DECIMAL:
                case DATE:
                case TIME:
                case DATE_TIME:
                    value = getValue(attr);
                    ret = value != null ? value : ""; //$NON-NLS-1$
                    break;

                case BOOLEAN:
                    value = getValue(attr);
                    ret = 0;
                    if (value != null && value.length() > 0) {
                        if (value.equals(Boolean.TRUE.toString())) {
                            ret = 1;
                        } else if (value.equals(Boolean.FALSE.toString())) {
                            ret = 2;
                        }
                    }
                    break;
                }
            }

            return ret;
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (editingDomain != null) {
                Attribute attr = getAttribute(element);

                if (attr != null) {
                    if (attr.getType() == AttributeType.BOOLEAN
                            && value instanceof Integer) {
                        switch ((Integer) value) {
                        case 1:
                            value = Boolean.TRUE;
                            break;
                        case 2:
                            value = Boolean.FALSE;
                            break;
                        default:
                            value = null;
                            break;
                        }
                        // Get the actual boolean value selection
                    }
                    Command cmd = getSetValueCommand(element, value);
                    if (cmd != null) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
            }
        }
    }

    protected abstract class AttributeValueColumnEditingSupport extends
            AttributeDefinitionColumnEditingSupport {

        private ExtendedComboBoxCellEditor enumEditor;

        public AttributeValueColumnEditingSupport(ColumnViewer viewer,
                EditingDomain editingDomain) {
            super(viewer, editingDomain);
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof Attribute) {
                Attribute attr = (Attribute) element;
                if (attr.getType() == AttributeType.ENUM) {
                    if (enumEditor != null) {
                        enumEditor.dispose();
                    }
                    enumEditor = new ExtendedComboBoxCellEditor(
                            (Composite) getViewer().getControl(), attr
                                    .getValues(), getModelLabelProvider());

                    return enumEditor;
                }
            }

            return super.getCellEditor(element);
        }

    }

}
