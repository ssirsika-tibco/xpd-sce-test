/** 
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.editor;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadeFactory;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.worklistfacade.resource.util.PhysicalWorkItemAttributesProvider;

/**
 * WorkListFacadeTable, is defined to edit/add Physical Work Item Attribute
 * Display Labels. This table is used in the {@link WorkListFacadeEditorSection}
 * embedded in {@link WorkListFacadeEditor}. It extends {@link BaseTableControl}
 * which provides the basic table control .Only those attributes which define a
 * Display Label are saved in the Facade file.The table contains 3 columns
 * representing 'Physical Work Item Attribute Name', 'Type' and 'Display
 * Label'.The table supports editing of Display Label where as the Name and Type
 * columns are read-only.
 * 
 * @author aprasad
 * 
 */
public class WorkListFacadeTable extends BaseTableControl {

    final static String BLANK_STRING = ""; //$NON-NLS-1$

    private final TransactionalEditingDomain editingDomain;

    private IContentProvider tableContentProvider;

    private WorkListFacade workListFacade;

    /**
     * Linked Hash Map of attribute name and the AttributeDataRow representing the Physical Attribute.LinkedHashMap is
     * used to preserve the ordering of the Attributes.
     */
    private Map<String, AttributeDataRow> tableInput;

    /**
     * Constructor , initialises the table with columns and contents.
     * 
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public WorkListFacadeTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);

        this.editingDomain =
                (editingDomain instanceof TransactionalEditingDomain) ? (TransactionalEditingDomain) editingDomain
                        : null;

        if (null != this.editingDomain) {
            createContents(parent, toolkit, null);
        }
        // initialises table with attribute list
        initTableInputWithPhysicalAttributes();
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        if (viewer instanceof TableViewer) {

            TableViewer tableViewer = (TableViewer) viewer;
            new AttributeNameColumn(editingDomain, tableViewer);// Name Column
            new AttributeTypeColumn(editingDomain, tableViewer);// Type Column
            // Display Label Column
            new AttributeDisplayLabelColumn(editingDomain, tableViewer);
            // set column width proportions
            setColumnProportions(new float[] { 0.2f, // Attribute Name
                    0.2f, // Type,
                    0.6f, // Display Label

            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (tableContentProvider == null) {

            tableContentProvider = new IStructuredContentProvider() {
                @Override
                public Object[] getElements(Object inputElement) {

                    if (inputElement instanceof Map) {
                        // tableInput is map containing AttributeDataRow set as
                        // input.
                        return ((Map) inputElement).values().toArray();
                    }
                    return new Object[0];
                }

                @Override
                public void dispose() {
                }

                @Override
                public void inputChanged(Viewer viewer, Object oldInput,
                        Object newInput) {
                }
            };
        }
        return tableContentProvider;
    }

    /**
     * Column for Physical Attribute's Type.
     * 
     * @author aprasad
     * 
     */
    protected class AttributeTypeColumn extends AbstractColumn {

        /**
         * @param editingDomain
         * @param viewer
         */
        public AttributeTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.WorkListFacadeTable_Type_Column, 90);
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, Type name to be displayed in the Type column.
         */
        @Override
        protected String getText(Object element) {

            String text = null;
            if (element instanceof AttributeDataRow) {

                AttributeDataRow row = (AttributeDataRow) element;
                if (null != row) {
                    // return type of attribute
                    text = row.getTypeForDisplay();
                }
            }

            if (null == text || text.isEmpty())
                text = Messages.WorkListFacadeTable_ATTRIBUTE_TYPE_NOT_SET;
            return text;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return String, value to be display in the {@link CellEditor}.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return null, Type is Read-Only.
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            // is READ_ONLY
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return null, Type is Read-Only.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            // is READ_ONLY
            return null;
        }
    }

    /**
     * Column for Physical Attribute Name.
     * 
     * @author aprasad
     * 
     */
    protected class AttributeNameColumn extends AbstractColumn {

        /**
         * Returns image based on the type of the Physical Work Item Attribute.
         * 
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return {@link Image}, to be displayed in the Attribute Name column.
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof AttributeDataRow) {
                AttributeDataRow row = (AttributeDataRow) element;
                if (row.physicalAttribute != null) {
                    return PhysicalWorkItemAttributesProvider.INSTANCE
                            .getImage(row.physicalAttribute);
                }
            }
            return super.getImage(element);
        }

        /**
         * @param editingDomain
         * @param viewer
         */
        public AttributeNameColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.WorkListFacadeTable_Name_Column, 90);
            setShowImage(true);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            // is READ_ONLY
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return null, Name column is Read_only.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            // is READ_ONLY
            return null;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, name of the Physical Work Item Attribute.
         */
        @Override
        protected String getText(Object element) {

            String text = null;
            if (element instanceof AttributeDataRow) {
                AttributeDataRow row = (AttributeDataRow) element;
                if (null != row) {
                    text = row.getName();
                }
            }

            if (null == text || text.isEmpty()) {
                text = Messages.WorkListFacadeTable_ATTRIBUTE_NAME_MISSING;
                // Attributes list is picked from the common model file
                // WorkItemScripting.bom, MISSING NAME CAN ONLY HAPPEN OUT OF
                // SOME ERROR, LOG THIS.
                WorkListFacadeResourcePlugin
                        .getDefault()
                        .logInfo(Messages.WorkListFacadeTable_ATTRIBUTE_NAME_MISSING);
            }

            return text;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return String, Name of Physical Work Item Attribute.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }
    }

    /**
     * Display label Column for the Physical Work Item Attribute.
     * 
     * @author aprasad
     * @since 03-Jan-2014
     */
    protected class AttributeDisplayLabelColumn extends AbstractColumn {

        /**
         * Cell Editor for the Display Label.
         */
        private final TextCellEditor editor;

        /**
         * Column for Display Label of the Physical Work Item Attribute, with
         * inbuilt actions and cell editor.
         * 
         * @param editingDomain
         * @param viewer
         */
        public AttributeDisplayLabelColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.WorkListFacadeTable_DisplayLabel_Column, 90);
            editor = new TextCellEditor((Composite) viewer.getControl());

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {

            if (element instanceof AttributeDataRow) {
                return editor;
            }
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#canEdit(java.lang.Object)
         *
         * @param element
         * @return
         */
        @Override
        protected boolean canEdit(Object element) {
            return super.canEdit(element) && !isReadOnly();
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return {@link Command}, to set value of the Display Label for the
         *         Physical Work Item Attribute.
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            // Set/Reset Physical Attribute Display Label
            CompoundCommand cmd = null;

            if (null != element) {

                if (value instanceof String) {
                    String newDisplayLabel = (String) value;

                    cmd =
                            new CompoundCommand(
                                    Messages.WorkListFacadeTable_Set_Display_Label_Command_Title);

                    if (element instanceof AttributeDataRow) {
                        // get Table Row Data
                        AttributeDataRow dataRow = (AttributeDataRow) element;
                        WorkItemAttribute displayAttribute =
                                dataRow.getWorkItemAttribute();

                        // WorkItemAttribute's container
                        WorkItemAttributes displayAttributes =
                                workListFacade.getWorkItemAttributes();

                        if (newDisplayLabel.isEmpty()) {
                            // if the Display label is reset to empty, remove
                            // the WorkItemAttribute entry from Work List Facade
                            if (displayAttribute != null) {
                                appendRemoveDisplayLabelForAttributeCommand(cmd,
                                        displayAttribute,
                                        displayAttributes);
                            }
                        } else {
                            // Command to create AttributeDisplayLabel entry in
                            // Facade for the given Physical Attribute.
                            if (displayAttribute == null) {
                                appendAddDisplayLabelForAttributeCommand(cmd,
                                        newDisplayLabel,
                                        dataRow,
                                        displayAttributes);
                            } else {
                                // Command to set Display Label for the existing
                                // Attribute entry in Facade for the given
                                // Physical Label.
                                cmd.append(SetCommand
                                        .create(getEditingDomain(),
                                                displayAttribute,
                                                WorkListFacadePackage.eINSTANCE
                                                        .getWorkItemAttribute_DisplayLabel(),
                                                value));
                            }
                        }
                    }
                }
            }
            return cmd;
        }

        /**
         * Creates and appends the command to add {@link WorkItemAttribute} for
         * the given row and new Display Label value.Creates the
         * {@link WorkItemAttributes} container if does not exist.
         * 
         * @param cmd
         *            , Command to which the Add command will be appended.
         * @param newDisplayLabel
         *            , new Display label value.
         * @param row
         *            , {@link AttributeDataRow} representing the current
         *            Physical Work Item Attribute.
         * @param attributesContainer
         *            , {@link WorkItemAttributes} which contains the List of
         *            {@link WorkItemAttribute}.
         */
        private void appendAddDisplayLabelForAttributeCommand(
                CompoundCommand cmd, String newDisplayLabel,
                AttributeDataRow row, WorkItemAttributes attributesContainer) {
            WorkItemAttribute displayAttribute;

            displayAttribute =
                    WorkListFacadeFactory.eINSTANCE.createWorkItemAttribute();
            displayAttribute.setDisplayLabel(newDisplayLabel);
            displayAttribute.setName(row.getName());

            if (attributesContainer == null) {
                attributesContainer =
                        WorkListFacadeFactory.eINSTANCE
                                .createWorkItemAttributes();

                // add new Attributes Container to Facade .
                cmd.append(SetCommand.create(editingDomain,
                        workListFacade,
                        WorkListFacadePackage.eINSTANCE
                                .getWorkListFacade_WorkItemAttributes(),
                        attributesContainer));
            }
            // add new Attribute entry in Facade
            cmd.append(AddCommand.create(editingDomain,
                    attributesContainer,
                    WorkListFacadePackage.eINSTANCE
                            .getWorkItemAttributes_WorkItemAttribute(),
                    displayAttribute));
        }

        /**
         * Creates and appends command to remove the Attribute Display Label
         * entry from the Work List Facade. Removes the container
         * {@link WorkItemAttributes} when the last {@link WorkItemAttribute} is
         * removed.
         * 
         * @param cmd
         *            , Command to which the remove commands will be appended.
         * @param attribute
         *            , The {@link WorkItemAttribute} to remove.
         * @param attributesContainer
         *            , {@link WorkItemAttributes} container for the list of
         *            {@link WorkItemAttribute}.
         */
        private void appendRemoveDisplayLabelForAttributeCommand(
                CompoundCommand cmd, WorkItemAttribute attribute,
                WorkItemAttributes attributesContainer) {
            if (attributesContainer != null) {
                if (attributesContainer.getWorkItemAttribute().size() == 1) {
                    // remove Display Label Attributes container if
                    // this is the last Display Label Attribute
                    cmd.append(SetCommand.create(getEditingDomain(),
                            workListFacade,
                            WorkListFacadePackage.eINSTANCE
                                    .getWorkListFacade_WorkItemAttributes(),
                            null));
                } else {
                    // remove this particular Display Label Attribute
                    // only.
                    cmd.append(RemoveCommand.create(getEditingDomain(),
                            attributesContainer,
                            WorkListFacadePackage.eINSTANCE
                                    .getWorkItemAttributes_WorkItemAttribute(),
                            attribute));
                }
            }
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return String, Display Label of the Physical Work Item Attribute
         *         from {@link WorkItemAttribute}.
         */
        @Override
        protected String getText(Object element) {

            String text = null;
            if (element instanceof AttributeDataRow) {
                AttributeDataRow row = (AttributeDataRow) element;
                if (null != row) {
                    WorkItemAttribute displayAttribute =
                            row.getWorkItemAttribute();
                    text =
                            (displayAttribute == null) ? BLANK_STRING
                                    : displayAttribute.getDisplayLabel();
                }
            }

            if (text == null) {
                text = BLANK_STRING;
            }
            return text;
        }

        /**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.ExternalReferenceColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return String, value to be displayed in the {@link CellEditor}.
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return null, {@link WorkListFacadeTable} does not support Add action.
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        // TABLE DOES NOT SUPPROT ADD ACTION
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return null, {@link WorkListFacadeTable} does not support Delete action.
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        // TABLE DOES NOT SUPPROT DELETE ACTION
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return null, {@link WorkListFacadeTable} does not support MoveDown
     *         action.
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        // TABLE DOES NOT SUPPROT MOVE ACTION
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return null, {@link WorkListFacadeTable} does not support MoveUp action.
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        // TABLE DOES NOT SUPPROT MOVE ACTION
        return null;
    }

    /**
     * Sets input to the table. Prepares table input using prepareTableInput() ,
     * before setting to the table. The method preparTableInput(), reloads the
     * available display label entries from the WorkListFacade file if any.
     * 
     * @param input
     */
    public void setInput(EObject input) {

        if (getViewer() != null) {

            getViewer().setInput(prepareTableInputWithDisplayLabels(input));
        }
    }

    /**
     * Prepares Input for the table, by appropriately combining the Physical
     * Attribute with the available Display Label data from WorkListFacade
     * file.Creates an instance of {@link AttributeDataRow} for each Physical
     * Attribute {@link Property} containing the attribute details and the
     * reference to corresponding {@link WorkItemAttribute} in WorkLiatFacade
     * [if exists].
     * 
     * @param input
     * @return, list of {@link AttributeDataRow}, where each
     *          {@link AttributeDataRow} represents the data for a Physical
     *          Attribute.
     */
    private Map<String, AttributeDataRow> prepareTableInputWithDisplayLabels(
            EObject input) {

        if (input instanceof WorkListFacade) {
            workListFacade = (WorkListFacade) input;

            WorkItemAttributes attributeAliases =
                    ((WorkListFacade) input).getWorkItemAttributes();

            // clear Display Label details from table rows
            for (AttributeDataRow tableRow : tableInput.values()) {
                tableRow.setWorkItemAttribute(null);
            }
            // if Attribute Display Label data Exist
            if (attributeAliases != null) {
                EList<WorkItemAttribute> attributeAliasList =
                        attributeAliases.getWorkItemAttribute();

                // reload Display Label data from Work List Facade file
                for (WorkItemAttribute displayAttribute : attributeAliasList) {
                    String attributeName = displayAttribute.getName();

                    if (tableInput.containsKey(attributeName)) {
                        // Display Label details to table row
                        AttributeDataRow row = tableInput.get(attributeName);
                        row.setWorkItemAttribute(displayAttribute);
                    }
                }
            }
        }
        return tableInput;
    }

    /**
     * Initialises table input with the available Physical Attributes.Reads
     * Physical Attribute data from {@link PhysicalWorkItemAttributesProvider}
     * and initialises the table input with its data.This method ONLY
     * initialises and NOT sets the data to the table.
     */
    private void initTableInputWithPhysicalAttributes() {

        tableInput = new LinkedHashMap<String, AttributeDataRow>();
        // get Physical Attributes from the provider
        Collection<Property> workItemAttributes =
                PhysicalWorkItemAttributesProvider.INSTANCE
                        .getWorkItemAttributes();

        for (Property property : workItemAttributes) {
            // create table rows for the Physical Attribute
            AttributeDataRow row = new AttributeDataRow(property);
            tableInput.put(property.getName(), row);
        }

    }

    /**
     * 
     * Type for input to the Work Item Attribute table.It binds the Physical
     * Work Item Attribute represented in {@link Property} with its Display
     * Label data in {@link WorkItemAttribute}, to be displayed in the table.
     * 
     * @author aprasad
     * @since 19-Dec-2013
     */
    class AttributeDataRow {

        /**
         * Physical Attribute as read from the
         * {@link PhysicalWorkItemAttributesProvider} .
         */
        private Property physicalAttribute;

        private String lengthRestriction;

        /**
         * Display Label Attribute as in Work List Facade File.
         */
        private WorkItemAttribute workItemAttribute;

        /**
         * @return the name
         */
        public String getName() {
            return physicalAttribute.getName();
        }

        /**
         * @param property
         */
        public AttributeDataRow(Property property) {
            physicalAttribute = property;
        }

        /**
         * Returns type of the Physical Attribute, for display purpose,
         * formatted as type(length)/type(length, decimal places).
         * 
         * @return String, type to be displayed in format type(length), for
         *         decimal type(length,decimal point)
         */
        public String getTypeForDisplay() {

            // Evaluate ONLY if not done so.
            if (lengthRestriction == null) {
                lengthRestriction =
                        WorkListFacadeEditorUtil
                                .getLengthRestriction(physicalAttribute);
            }

            String typeName =
                    (physicalAttribute.getType() != null) ? physicalAttribute
                            .getType().getName() : ""; //$NON-NLS-1$
            if (lengthRestriction != null && !lengthRestriction.isEmpty()) {
                typeName = String.format("%s (%s)", //$NON-NLS-1$
                        typeName,
                        lengthRestriction);
            }

            return typeName;
        }

        /**
         * Type of Physical Attribute , contained in the
         * {@link AttributeDataRow}.
         * 
         * @return String, type of the physical Attribute represented by the
         *         {@link AttributeDataRow}.
         */
        public String getType() {
            if (physicalAttribute != null
                    && physicalAttribute.getType() instanceof PrimitiveType) {
                return physicalAttribute.getType().getName();
            }
            return null;
        }

        /*
         * XPD-7222: The private methods 'getLengthRestriction' and
         * 'getRestrictionValue' have been moved to Util class
         * 'WorkListFacadeEditorUtil' as they were useful and can be used at
         * other places like WLF documentation generation.
         */

        /**
         * @return the attributeAlias
         */
        public WorkItemAttribute getWorkItemAttribute() {
            return workItemAttribute;
        }

        /**
         * @param attributeAlias
         *            the attributeAlias to set
         */
        public void setWorkItemAttribute(WorkItemAttribute workItemAttribute) {
            this.workItemAttribute = workItemAttribute;
        }

    }
}
