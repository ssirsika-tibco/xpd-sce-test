/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
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
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Shows quick tabular view for the payload data in a global signal.
 * 
 * @author sajain
 * @since Feb 23, 2015
 */
public class PayloadDataTable extends DataFieldTable {

    private final EditingDomain editingDomain;

    /**
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public PayloadDataTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {

        super(parent, toolkit, editingDomain);
        this.editingDomain = editingDomain;
        ViewerFilter filter = new PayloadDataFilter();
        ViewerFilter[] filters = new ViewerFilter[] { filter };
        getViewer().setFilters(filters);
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
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {

        Set<EStructuralFeature> features = super.getMovableFeatures();

        features.add(GlobalSignalDefinitionPackage.eINSTANCE
                .getGlobalSignal_PayloadDataFields());

        return features;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #createAddAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {

        /*
         * Create a new add table action and return it.
         */
        return new TableAddAction(viewer, Messages.PayloadDataSection_AddLabel,
                Messages.PayloadDataSection_AddPayloadDataButton_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {

                /*
                 * Add row.
                 */

                Object input = getInput();

                /*
                 * Check if input is not null.
                 */
                if (null != input) {

                    String firstCellVal = getNewRowFirstCellVal();

                    /*
                     * Create Payload Data template.
                     */
                    PayloadDataField pdf = createPDFTemplate(firstCellVal);

                    pdf.setName(NameUtil.getInternalName(firstCellVal, true));

                    Xpdl2ModelUtil.setOtherAttribute(pdf,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            firstCellVal);

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.PayloadDataSection_CreatePayloadData_menu);

                    cmd.append(AddCommand.create(editingDomain,
                            input,
                            GlobalSignalDefinitionPackage.eINSTANCE
                                    .getPayloadDataField(),
                            pdf));

                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);

                    }
                }
                return null;
            }

            /**
             * Get the first cell value which is the name of the Payload Data.
             * 
             * @return First cell value which is the name of the Payload Data.
             */
            protected String getNewRowFirstCellVal() {

                String propName =
                        Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName;

                PayloadDataField pdf = createPDFTemplate(propName);

                String uniquePdfName = getUniquePayloadDataName(propName, pdf);

                if (uniquePdfName != null && !uniquePdfName.equals(propName)) {

                    propName = uniquePdfName;
                }

                return propName;
            }

            /**
             * Create a default Payload Data template.
             * 
             * @param pdfName
             * 
             * @return A default Payload Data template.
             */
            private PayloadDataField createPDFTemplate(String pdfName) {

                GlobalSignalDefinitionFactory fact =
                        GlobalSignalDefinitionFactory.eINSTANCE;

                PayloadDataField input = fact.createPayloadDataField();

                input.setName(NameUtil.getInternalName(pdfName, true));

                Xpdl2ModelUtil.setOtherAttribute(input,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        pdfName);

                /*
                 * Set basic string type
                 */
                BasicType basicType = Xpdl2Factory.eINSTANCE.createBasicType();
                basicType.setType(BasicTypeType.STRING_LITERAL);
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue("50"); //$NON-NLS-1$
                basicType.setLength(len);

                input.setDataType(basicType);

                return input;
            }

            /**
             * Get unique name for the specified payload data.
             * 
             * @param baseName
             *            Base name of the payload data.
             * @param pdf
             *            Payload Data.
             * 
             * @return Unique name for the specified payload data.
             */
            private String getUniquePayloadDataName(String baseName,
                    PayloadDataField pdf) {

                String finalName = baseName;

                int idx = 1;

                while (GSDModelUtil.getDuplicatePayloadData(getInput(),
                        pdf,
                        finalName) != null
                        || GSDModelUtil.getDuplicatePayloadData(getInput(),
                                pdf,
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
                Messages.PayloadDataSection_DeleteLabel,
                Messages.PayloadDataSection_DeletePDFButton_tooltip) {

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
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {

        if (contentProvider == null) {

            contentProvider = new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {

                    if (inputElement instanceof GlobalSignal) {

                        return ((GlobalSignal) inputElement)
                                .getPayloadDataFields().toArray();
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

        /*
         * Add columns to the payload data table.
         */
        new LabelColumn(super.getEditingDomain(), viewer);
        new NameColumn(super.getEditingDomain(), viewer);
        new TypeColumn(super.getEditingDomain(), viewer);
        new LengthColumn(super.getEditingDomain(), viewer);
        new DecimalPlacesColumn(super.getEditingDomain(), viewer);
        new ArrayColumn(super.getEditingDomain(), viewer);
        new ExternalReferenceColumn(super.getEditingDomain(), viewer, "", 90); //$NON-NLS-1$
        new CaseRefTypeColumn(super.getEditingDomain(), viewer);
        new TypeDeclarationColumn(super.getEditingDomain(), viewer);
        new MandatoryColumn(super.getEditingDomain(), viewer);
        new UseForSignalCorrelationColumn(super.getEditingDomain(), viewer);

        /*
         * Set column proportions.
         */
        setColumnProportions(new float[] { 0.1f, // Label,
                0.1f, // Name,
                0.06f, // Type,
                0.075f, // Length
                0.085f, // Decimals
                0.075f, // Is array
                0.1f, // External Reference
                0.1f, // Case Ref Type
                0.1f, // Type declaration
                0.060f, // Mandatory
                0.12f,// Use for signal correlation
        });

    }

    /**
     * Column to show/set the mandatory attribute of Payload Data.
     * 
     * @author sajain
     * @since Feb 25, 2015
     */
    private class MandatoryColumn extends AbstractColumn {
        private final CheckboxCellEditor editor;

        EditingDomain editingDomain;

        /**
         * Column to show/set the mandatory attribute of Payload Data.
         * 
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public MandatoryColumn(EditingDomain editingDomain, ColumnViewer viewer) {

            super(editingDomain, viewer, SWT.NONE,
                    Messages.PayloadDataSection_Mandatory_Label, 65);

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

            if (element instanceof PayloadDataField) {

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

            if (element instanceof PayloadDataField) {

                PayloadDataField pdf = (PayloadDataField) element;

                if (pdf.isMandatory()) {

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

            if (element instanceof PayloadDataField) {

                PayloadDataField prd = (PayloadDataField) element;

                if (value instanceof Boolean) {

                    cmd =
                            new CompoundCommand(
                                    Messages.PayloadDataSection_Mandatory_Command_Label);

                    cmd.append(SetCommand.create(editingDomain,
                            prd,
                            GlobalSignalDefinitionPackage.eINSTANCE
                                    .getPayloadDataField_Mandatory(),
                            value));

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

            if (element instanceof PayloadDataField) {

                PayloadDataField pdf = (PayloadDataField) element;

                return new Boolean(pdf.isMandatory());
            }
            return null;
        }

    }

    /**
     * Column to show/set the 'Use for signal correlation' attribute of a
     * Payload Data.
     * 
     * @author sajain
     * @since Feb 25, 2015
     */
    private class UseForSignalCorrelationColumn extends AbstractColumn {
        private final CheckboxCellEditor editor;

        EditingDomain editingDomain;

        /**
         * Column to show/set the 'Use for signal correlation' attribute of a
         * Payload Data.
         * 
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public UseForSignalCorrelationColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.PayloadDataSection_UseForSignalCorrelation_Label,
                    65);
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
            if (element instanceof PayloadDataField) {
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
            if (element instanceof PayloadDataField) {
                PayloadDataField pdf = (PayloadDataField) element;
                if (pdf.isCorrelation()) {
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

            if (element instanceof PayloadDataField) {

                PayloadDataField pdf = (PayloadDataField) element;

                if (value instanceof Boolean) {

                    cmd =
                            new CompoundCommand(
                                    Messages.PayloadDataSection_UseForSignalCorrelation_Command_Label);

                    cmd.append(SetCommand.create(editingDomain,
                            pdf,
                            Xpdl2Package.eINSTANCE.getDataField_Correlation(),
                            value));

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

            if (element instanceof PayloadDataField) {

                PayloadDataField pdf = (PayloadDataField) element;

                return new Boolean(pdf.isCorrelation());
            }
            return null;
        }

    }
}
