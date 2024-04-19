/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataFields;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.UIBasicTypes;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * shows quick tabular view for the data fields of process / package
 * 
 * 
 * @author bharge
 * @since 3.3 (20 Oct 2009)
 */
public class DataFieldTable extends AbstractProcessRelevantDataTable {
    private final EditingDomain editingDomain;

    protected IContentProvider contentProvider;

	private int					options					= 0;

	public static final int		HIDE_LABEL_COLUMN		= 0x01;

	public static final int		DISABLE_NAME_COLUMN		= 0x02;

	public static final int		HIDE_READONLY_COLUMN		= 0x04;

	public static final int		ADD_VOID_TYPE				= 0x08;

	public static final int		HIDE_TYPE_DECLARATION_COLUMN	= 0x10;

	public static final int		ADD_DESCRIPTION_COLUMN			= 0x20;

    /**
     * @param parent
     * @param toolkit
     * @param object
     * @param b
     */
    public DataFieldTable(Composite parent, XpdToolkit toolkit,
			EditingDomain editingDomain, int options)
	{
        super(parent, toolkit, null, false);

		this.options = options;


        this.editingDomain = editingDomain;
        if (null != editingDomain) {
            createContents(parent, toolkit, null);
            ViewerFilter filter = new DataFieldFilter(false);
            ViewerFilter[] filters = new ViewerFilter[] { filter };
            getViewer().setFilters(filters);
        }
    }

	public DataFieldTable(Composite parent, XpdToolkit toolkit, EditingDomain editingDomain)
	{
		this(parent, toolkit, editingDomain, 0);
	}

    /**
     * @return the editingDomain
     */
    protected EditingDomain getEditingDomain() {
        return editingDomain;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            contentProvider = new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Package) {
                        return ((Package) inputElement).getDataFields()
                                .toArray();
                    }
                    if (inputElement instanceof Process) {
                        return ((Process) inputElement).getDataFields()
                                .toArray();
                    }
                    if (inputElement instanceof Activity) {
                        Activity activity = (Activity) inputElement;
                        return activity.getDataFields().toArray();
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
        return contentProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {

    	
		// Only add Label column if not optionally set to hidden (hide label column bit NOT set in options)
		if ((options & HIDE_LABEL_COLUMN) == 0)
		{
			new LabelColumn(editingDomain, viewer);
		}

		if (CapabilityUtil.isDeveloperActivityEnabled())
		{
			if (((options & DISABLE_NAME_COLUMN) == DISABLE_NAME_COLUMN)
					|| ((options & HIDE_LABEL_COLUMN) == HIDE_LABEL_COLUMN))
			{
				boolean disableColumn = false;
				String columnInitialText = ""; //$NON-NLS-1$
				
				if(((options & DISABLE_NAME_COLUMN) == DISABLE_NAME_COLUMN)) {
					 disableColumn = true;
					 columnInitialText = "<return>"; //$NON-NLS-1$
				}
				
				new NameColumn(editingDomain, viewer, disableColumn, columnInitialText) {
					/**
					 * ACE-7394 : IF we are hiding the label column then show the type images for Name column.
					 * 
					 * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
					 *
					 * @return
					 */
					@Override
					protected boolean getShowImage() {
						return ((options & HIDE_LABEL_COLUMN) == HIDE_LABEL_COLUMN);
					}
				};
			}
			else {
				new NameColumn(editingDomain, viewer);
			}

        }

		/**
		 * ACE-7966 : Chaitanya Add Parameter description editing facility for PSL Parameter table.
		 * Only add Description column when the ADD_DESCRIPTION_COLUMN bit is SET in options.
		 */
		if ((options & ADD_DESCRIPTION_COLUMN) == ADD_DESCRIPTION_COLUMN)
		{
			new DescriptionColumn(editingDomain, viewer);
		}

		// Only add Read Only column if not optionally set to hidden (Read Only column bit NOT set in options)
		if ((options & HIDE_READONLY_COLUMN) == 0)
		{
			new ReadOnlyColumn(editingDomain, viewer);
		}

        new TypeColumn(editingDomain, viewer);
        new LengthColumn(editingDomain, viewer);
        new DecimalPlacesColumn(editingDomain, viewer);
        new ArrayColumn(editingDomain, viewer);

        /*
         * Sid ACE-5387 enumeration type selection is not supported in datafields, parameters, type-declarations or
         * payload parameters.
         */
        ExternalReferenceColumn extRefColumn = new ExternalReferenceColumn(editingDomain, viewer, "", 90); //$NON-NLS-1$

        extRefColumn.setBOMTypeFilter(new String[] { BOMTypeQuery.CLASS_TYPE, BOMTypeQuery.PRIMITIVE_TYPE,
                BOMTypeQuery.CASE_CLASS_TYPE, BOMTypeQuery.GLOBAL_CLASS_TYPE });

        new CaseRefTypeColumn(editingDomain, viewer);

		// Only add Type Declaration Column if not optionally set to hidden (hide label column bit NOT set in options)
		if ((options & HIDE_TYPE_DECLARATION_COLUMN) == 0)
		{
			new TypeDeclarationColumn(editingDomain, viewer);
		}

		setColumnProportions(computeColumnsWidthProportions());
    }

    /**
	 * Function to add the colums width proportions into array , depedning upon weather the column is included or not in
	 * the DataFieldTable.
	 * 
	 * @return
	 */
	private float[] computeColumnsWidthProportions()
	{
		List<Float> colList = new ArrayList<>();

		// Only add Label column width proporations if not optionally set to hidden (hide label column bit NOT set in options)
		if ((options & HIDE_LABEL_COLUMN) == 0)
		{
			colList.add(0.1f); // Label
		}


		if (CapabilityUtil.isDeveloperActivityEnabled())
		{
			colList.add(0.1f); // Name
		}

		/**
		 * ACE-7966 : Chaitanya Add Parameter description editing facility for PSL Parameter table.
		 * Only add Description column width proporations when the ADD_DESCRIPTION_COLUMN bit is SET in options.
		 */
		if ((options & ADD_DESCRIPTION_COLUMN) == ADD_DESCRIPTION_COLUMN)
		{
			colList.add(0.1f); // Description
		}


		// Only add Read only column width proporations if not optionally set to hidden (hide Read Only column bit NOT set in options)
		if ((options & HIDE_READONLY_COLUMN) == 0)
		{
			colList.add(0.060f); // Readonly
		}
		

		colList.add(0.1f); // Type
		colList.add(0.075f); // Length
		colList.add(0.085f); // Decimals
		colList.add(0.075f); // Is array
		colList.add(0.1f); // External Reference
		colList.add(0.1f); // Case Ref Type

		// Only add Type Declaration Column width proporations if not optionally set to hidden (hide label column bit NOT set in options)
		if ((options & HIDE_LABEL_COLUMN) == 0)
		{
			colList.add(0.1f); // Type declaration
		}

		// Convert ArrayList to float[]
		float[] col = new float[colList.size()];
		for (int i = 0; i < colList.size(); i++)
		{
			col[i] = colList.get(i);
		}

		return col;
	}

	/**
	 * Get the input of this table.
	 * 
	 * @return
	 */
    private EObject getInput() {
        return (EObject) (getViewer() != null ? getViewer().getInput() : null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, getAddLabel(),
                getAddTooltip()) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (null != input) {
                    String firstCellVal = getNewRowFirstCellVal();
                    DataField dataField = createFileTemplate(firstCellVal);
                    // the label cell in the table viewer was allowing
                    // duplicate name

                    // DON'T IGNORE THE FIRST CELL VAL BY GOING
                    // getNewRowFirstCellVal which will kill the user entered
                    // name!
                    String propName =
                            getUniqueDataFieldName(firstCellVal, dataField);

                    dataField.setName(NameUtil.getInternalName(propName, true));

					if ((options & HIDE_LABEL_COLUMN) == 0)
					{
						Xpdl2ModelUtil.setOtherAttribute(dataField,
								XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(), propName);
					}

					
					/**
					 * ACE-7966 : Chaitanya Add Parameter description editing facility for PSL Parameter table.
					 * Add description object while adding new row, when the ADD_DESCRIPTION_COLUMN bit is SET in options.
					 */
					if ((options & ADD_DESCRIPTION_COLUMN) == ADD_DESCRIPTION_COLUMN)
					{
						String descMessage = Messages.PSLPropertyPanel_Parameters_Description;
						String descValue = String.format(descMessage, propName);

						Description description = Xpdl2Factory.eINSTANCE.createDescription();
						description.setValue(descValue);
						dataField.setDescription(description);
					}

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    getAddFieldCommandLabel());
                    cmd.append(AddCommand.create(editingDomain,
                            input,
                            Xpdl2Package.eINSTANCE.getDataField(),
                            dataField));
                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                String propName = getAddFieldNamePrefix();
                DataField dataField = createFileTemplate(propName);
                String uniqueDataFieldName =
                        getUniqueDataFieldName(propName, dataField);
                if (uniqueDataFieldName != null
                        && !uniqueDataFieldName.equals(propName)) {
                    propName = uniqueDataFieldName;
                }
                return propName;
            }

            private DataField createFileTemplate(String dataFieldName) {
                Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
                DataField input = fact.createDataField();
                input.setName(NameUtil.getInternalName(dataFieldName, true));
                
				if ((options & HIDE_LABEL_COLUMN) == 0)
				{
                    Xpdl2ModelUtil.setOtherAttribute(input,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            dataFieldName);
                }
                // Set basic string type
                BasicType basicType = fact.createBasicType();
                basicType.setType(BasicTypeType.STRING_LITERAL);
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue("50"); //$NON-NLS-1$
                basicType.setLength(len);
                input.setReadOnly(false);

                input.setDataType(basicType);
                // MR 40156 - begin
                setBasicTypeCmd(input);
                // MR 40156 - end
                return input;
            }

            private void setBasicTypeCmd(DataField dataField) {
                Expression blankInitExpression = getBasicInitValue(""); //$NON-NLS-1$
                CompoundCommand compoundCmd = new CompoundCommand();
                Command setCommand =
                        SetCommand.create(editingDomain,
                                dataField,
                                Xpdl2Package.eINSTANCE
                                        .getDataField_InitialValue(),
                                blankInitExpression);
                compoundCmd.append(setCommand);
                // MR 38533 - begin
                Command removeCmd =
                        getRemoveConditionalExpressionCommand(dataField);
                if (removeCmd != null) {
                    compoundCmd.append(removeCmd);
                }
                // MR 38533 - end
                if (compoundCmd != null && compoundCmd.canExecute()) {
                    editingDomain.getCommandStack().execute(compoundCmd);
                }
            }

            private Command getRemoveConditionalExpressionCommand(
                    DataField dataField) {
                Command removeCommand = null;
                EReference feature =
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ConditionalParticipant();
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(dataField, feature);
                if (otherElement != null) {
                    removeCommand =
                            Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            dataField,
                                            feature,
                                            null);
                }
                return removeCommand;
            }

            /**
             * Get expression for the init value literal entered by user.
             * 
             * @return
             */
            private Expression getBasicInitValue(String value) {
                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression();

                expression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                value);
                return expression;
            }

            private String getUniqueDataFieldName(String baseName,
                    DataField dataField) {
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayFieldOrParam(getInput(),
                                dataField,
                                finalName) != null
                        || Xpdl2ModelUtil.getDuplicateFieldOrParam(getInput(),
                                dataField,
                                NameUtil.getInternalName(finalName, true)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                return finalName;
            }

        };

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                getDeleteLabel(),
                getDeleteToolTip()) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                if (selection != null && !selection.isEmpty()) {
                    List<DataField> dataFieldsList = new ArrayList<DataField>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof DataField) {
                            dataFieldsList.add((DataField) next);
                        }
                    }
                    if (!dataFieldsList.isEmpty()) {
                        DeleteAction.deleteXpdlObject(Display.getCurrent()
                                .getActiveShell(), selection);
                    }
                }
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        Set<EStructuralFeature> movableFeatures = super.getMovableFeatures();
        movableFeatures.add(Xpdl2Package.eINSTANCE
                .getDataFieldsContainer_DataFields());
        return movableFeatures;
    }

	/**
	 * Gets the tooltip for "Add Button" while adding new "Item/Row" to the table.
	 * 
	 * @return
	 */
	protected String getAddTooltip()
	{
		return Messages.DataFieldsSection_AddDataFieldButton_tooltip;
	}

	/**
	 * Gets the Label for "Add Button" while adding new "Item/Row" to the table.
	 * 
	 * @return
	 */
	protected String getAddLabel()
	{
		return Messages.PropertiesSection_AddLabel;
	}

	/**
	 * Gets the command label while adding new "Item/Row" to the table.
	 * 
	 * @return
	 */
	protected String getAddFieldCommandLabel()
	{
		return Messages.DataFieldsSection_createDataField_menu;
	}

	/**
	 * Gets the Field Name Prefix used while adding new "Item/Row" to the table.
	 * 
	 * @return
	 */
	protected String getAddFieldNamePrefix()
	{
		return Messages.DataFieldsSection_FieldName_value;
	}

	/**
	 * Gets the Label for "Delete Button" while deleting selected "Item/Row" from the table.
	 * 
	 * @return
	 */
	protected String getDeleteToolTip()
	{
		return Messages.DataFieldsSection_DeleteDataFieldButton_tooltip;
	}

	/**
	 * Gets the tooltip for "Delete Button" while deleting selected "Item/Row" from the table.
	 * 
	 * @return
	 */
	protected String getDeleteLabel()
	{
		return Messages.PropertiesSection_DeleteLabel;
	}

	protected class ReadOnlyColumn extends AbstractColumn
	{
        private final CheckboxCellEditor editor;

        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ReadOnlyColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_readOnlyColumn_label, 65);
            editor =
                    new CheckboxCellEditor((Composite) viewer.getControl(),
                            SWT.NONE);
            this.editingDomain = editingDomain;
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
            if (element instanceof ProcessRelevantData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData processRelevantData =
                        (ProcessRelevantData) element;
                if (processRelevantData.isReadOnly()) {
                    return CheckboxCellEditor.getImgChecked();
                }
                return CheckboxCellEditor.getImgUnchecked();
            }
            return super.getImage(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (value instanceof Boolean) {
                    cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_setDataTypeIsReadOnly_menu);
                    cmd.append(SetCommand.create(editingDomain,
                            prd,
                            Xpdl2Package.eINSTANCE
                                    .getProcessRelevantData_ReadOnly(),
                            value));
                    /*
                     * XPD-5368: Saket- Ensuring that the implementations of
                     * AbstractColumn.getSetValueCommand() return the command
                     * and do not execute it.
                     */

                    return cmd;
                }
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                return new Boolean(prd.isReadOnly());
            }
            return null;
        }

    }

    protected class ArrayColumn extends AbstractColumn {
        private final CheckboxCellEditor editor;

        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ArrayColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.DataFieldsSection_arrayColumn_label, 40);
            editor =
                    new CheckboxCellEditor((Composite) viewer.getControl(),
                            SWT.NONE);
            this.editingDomain = editingDomain;
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
            if (element instanceof ProcessRelevantData) {
				DataType dataType = ((ProcessRelevantData) element).getDataType();
				if (dataType != null)
				{
					return editor;
				}
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData processRelevantData =
                        (ProcessRelevantData) element;

				if (((ProcessRelevantData) element).getDataType() != null)
				{
					if (processRelevantData.isIsArray())
					{
						return CheckboxCellEditor.getImgChecked();
					}
					return CheckboxCellEditor.getImgUnchecked();
				}
				else
				{
					return null;
				}
            }
            return super.getImage(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                if (value instanceof Boolean) {
                    cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_setDataTypeIsArray_menu);
                    cmd.append(SetCommand.create(editingDomain,
                            prd,
                            Xpdl2Package.eINSTANCE
                                    .getProcessRelevantData_IsArray(),
                            value));
                    /*
                     * XPD-5368: Saket- Ensuring that the implementations of
                     * AbstractColumn.getSetValueCommand() return the command
                     * and do not execute it.
                     */
                }
            }
            return cmd;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
		@Override
		protected String getText(Object element)
		{
			if (element instanceof ProcessRelevantData)
			{
				ProcessRelevantData processRelevantData = (ProcessRelevantData) element;

				if (((ProcessRelevantData) element).getDataType() == null)
				{
					return "NA";
				}
			}
			return null;
		}

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                return new Boolean(prd.isIsArray());
            }
            return null;
        }

    }

    protected class TypeColumn extends AbstractTypeColumn {
        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         */
        public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer);
            this.editingDomain = editingDomain;
		}

		/**
         * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable.AbstractTypeColumn#getTypes()
         *
         * @return
         */
        @Override
        protected Map<String, String> getTypes()
        {
        	Map<String, String> typeMap = super.getTypes();
        	
        	
        	// ACE-7880 :- If we are hiding the Type Declaration column , remove the Type Declaration options from the Type column.
			if ((options & HIDE_TYPE_DECLARATION_COLUMN) == HIDE_TYPE_DECLARATION_COLUMN)
			{
				if (typeMap.get(ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE) != null)
				{
					typeMap.remove(ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE);
				}
        	}
        	

			// ACE-7394 :- Added VOID type for PSL (i.e. Process Script Library) Return Parameters.
			if ((options & ADD_VOID_TYPE) == ADD_VOID_TYPE)
			{
				String typeName = "Void (no return)"; //$NON-NLS-1$
				String typeLit = ProcessRelevantDataUtil.VOID_REFERNCE_TYPE;
				typeMap.put(typeLit, typeName);
			}

			return typeMap;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                DataType dataType = prd.getDataType();
                String currentType = null;
                if (dataType instanceof BasicType) {
                    BasicType basicType = (BasicType) dataType;
                    currentType = basicType.getType().getName();
                } else if (dataType instanceof ExternalReference) {
                    currentType =
                            ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE;
                } else if (dataType instanceof DeclaredType) {
                    currentType = ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE;
                } else if (dataType instanceof RecordType) {
                    /*
                     * Sid ACE-1094 - didn't used to be able to change from Case
                     * Ref to anything else in table view.
                     */
                    currentType = ProcessRelevantDataUtil.CASE_REFERENCE_TYPE;
                }
				else if (dataType == null)
				{
					currentType = ProcessRelevantDataUtil.VOID_REFERNCE_TYPE;
				}

                int typeIndex = getTypeIndex((String) value);

                if (currentType != null) {
                    cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_createDataType_menu);
                    String newType = getTypeValue(typeIndex);
                    if (newType != null && !newType.equals(currentType)) {
                        DataType newDataType = createNewDataType(newType);
                        cmd.append(SetCommand.create(editingDomain,
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                newDataType));

						// For Void Type (i.e. when newDataType is null) unset the isArray if already set.
						if (prd.isSetIsArray() && newDataType == null)
						{
							cmd.append(SetCommand.create(editingDomain, prd,
									Xpdl2Package.eINSTANCE.getProcessRelevantData_IsArray(), false));
						}
                    }
                }
            }
            return cmd;
        }

    }

    protected class LengthColumn extends AbstractLengthColumn {
        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         */
        public LengthColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer);
            this.editingDomain = editingDomain;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                DataType lDataType = prd.getDataType();
                String currentLength = null;
                if (lDataType instanceof BasicType) {
                    BasicType basicType = (BasicType) lDataType;

                    /*
                     * Sid ACE-192 Don't allow length set on URI fields.
                     */
                    UIBasicTypes uiBasicType = UIBasicTypes.fromBasicType(basicType);

                    if (UIBasicTypes.String.equals(uiBasicType)) {
                        Length length = basicType.getLength();
                        if (length == null) {
                            currentLength = ""; //$NON-NLS-1$
                        } else {
                            currentLength = length.getValue();
                        }
                        if (value != null) {
                            if (currentLength == null
                                    || !currentLength.equals(value)) {
                                String newValue = null;
                                if (value instanceof String) {
                                    if ("".equals(value)) { //$NON-NLS-1$
                                        newValue = ""; //$NON-NLS-1$
                                    } else {
                                        Short saveShortValue =
                                                ProcessRelevantDataUtil
                                                        .safeParseShort((String) value);
                                        if (saveShortValue != null) {
                                            newValue =
                                                    saveShortValue.toString();
                                        }
                                    }
                                }
                                if (newValue != null) {
                                    cmd =
                                            new CompoundCommand(
                                                    Messages.DataFieldsSection_setDataTypeLength_menu);

                                    Length newLength = null;
                                    if (!"".equals(newValue)) { //$NON-NLS-1$
                                        newLength =
                                                Xpdl2Factory.eINSTANCE
                                                        .createLength();
                                        newLength.setValue(newValue);
                                    }
                                    cmd.append(SetCommand.create(editingDomain,
                                            basicType,
                                            Xpdl2Package.eINSTANCE
                                                    .getBasicType_Length(),
                                            newLength));
                                }
                            }
                        }
                    } else {
                        Precision precision = basicType.getPrecision();
                        if (precision != null) {
                            currentLength =
                                    Short.toString(precision.getValue());
                            if (currentLength == null
                                    || !currentLength.equals(value)) {
                                Short newValue = null;
                                if (value instanceof String) {
                                    newValue =
                                            ProcessRelevantDataUtil
                                                    .safeParseShort((String) value);
                                }
                                if (newValue != null) {
                                    cmd =
                                            new CompoundCommand(
                                                    Messages.DataFieldsSection_setDataTypePrecision_menu);
                                    Precision newPrecision =
                                            Xpdl2Factory.eINSTANCE
                                                    .createPrecision();
                                    newPrecision.setValue(newValue);
                                    cmd.append(SetCommand.create(editingDomain,
                                            basicType,
                                            Xpdl2Package.eINSTANCE
                                                    .getBasicType_Precision(),
                                            newPrecision));
                                }
                            }
                        }
                    }
                }
            }
            return cmd;
        }

    }

    protected class DecimalPlacesColumn extends AbstractDecimalPlacesColumn {
        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         */
        public DecimalPlacesColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer);
            this.editingDomain = editingDomain;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;
            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;
                DataType dpDataType = prd.getDataType();
                String currentScale = null;
                if (dpDataType instanceof BasicType) {
                    BasicType basicType = (BasicType) dpDataType;
                    Scale scale = basicType.getScale();
                    if (scale != null) {
                        currentScale = Short.toString(scale.getValue());
                        if (currentScale == null || !currentScale.equals(value)) {
                            Short newValue = null;
                            if (value instanceof String) {
                                newValue =
                                        ProcessRelevantDataUtil
                                                .safeParseShort((String) value);
                            }
                            if (newValue != null) {
                                cmd =
                                        new CompoundCommand(
                                                Messages.DataFieldsSection_setDataTypeScale_menu);
                                Scale newScale =
                                        Xpdl2Factory.eINSTANCE.createScale();
                                newScale.setValue(newValue);
                                cmd.append(SetCommand.create(editingDomain,
                                        basicType,
                                        Xpdl2Package.eINSTANCE
                                                .getBasicType_Scale(),
                                        newScale));
                            }
                        }
                    }
                }
            }
            return cmd;
        }

    }

    protected class TypeDeclarationColumn extends AbstractTypeDeclarationColumn {
        private final ComboBoxViewerCellEditor editor;

        EditingDomain editingDomain;

        /**
         * @param editingDomain
         * @param viewer
         */
        public TypeDeclarationColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer);
            this.editingDomain = editingDomain;
            editor = getEditor();

            editor.setContenProvider(new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Package) {
                        return ((Package) inputElement).getTypeDeclarations()
                                .toArray();
                    }
                    if (inputElement instanceof Process) {
                        Process process = (Process) inputElement;
                        Package package1 = Xpdl2ModelUtil.getPackage(process);
                        return package1.getTypeDeclarations().toArray();
                    }
                    if (inputElement instanceof Activity) {
                        Activity activity = (Activity) inputElement;
                        Package package1 = Xpdl2ModelUtil.getPackage(activity);
                        return package1.getTypeDeclarations().toArray();
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

            });

            editor.setLabelProvider(new TransactionalAdapterFactoryLabelProvider(
                    XpdResourcesPlugin.getDefault().getEditingDomain(),
                    XpdResourcesPlugin.getDefault().getAdapterFactory()));
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            CompoundCommand cmd = null;

            if (element instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) element;

                if (value instanceof TypeDeclaration) {
                    /*
                     * XPD-4692: When user selects type declaration then set
                     * appropriate dataType even if field not currently set to
                     * TypeDeclaration type (that matches how external ref
                     * column works too).
                     */
                    String newTypeDeclarationId =
                            ((TypeDeclaration) value).getId();

                    DataType currentDataType = prd.getDataType();

                    if (!(currentDataType instanceof DeclaredType)
                            || !((DeclaredType) currentDataType)
                                    .getTypeDeclarationId()
                                    .equals(newTypeDeclarationId)) {

                        DeclaredType newDataType =
                                Xpdl2Factory.eINSTANCE.createDeclaredType();
                        newDataType.setTypeDeclarationId(newTypeDeclarationId);

                        cmd =
                                new CompoundCommand(
                                        Messages.DataFieldsSection_setDeclaredTypeExternalRef_menu);

                        cmd.append(SetCommand.create(editingDomain,
                                prd,
                                Xpdl2Package.eINSTANCE
                                        .getProcessRelevantData_DataType(),
                                newDataType));
                    }
                }
            }
            return cmd;
        }

    }

}
