/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.parameters;

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
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.properties.table.CheckboxCellEditor;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParameterTable
 * 
 * 
 * @author bharge
 * @since 3.3 (22 Oct 2009)
 */
public class ParameterTable extends DataFieldTable {
    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    /**
     * @param parent
     * @param toolkit
     * @param editingDomain
     */
    public ParameterTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {
            contentProvider = new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Process) {
                        return ((Process) inputElement).getFormalParameters()
                                .toArray();
                    }
                    if (inputElement instanceof ProcessInterface) {
                        return ((ProcessInterface) inputElement)
                                .getFormalParameters().toArray();
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
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #getMovableFeatures()
     */
    @Override
    protected Set<EStructuralFeature> getMovableFeatures() {
        if (null == editingDomain)
            return null;
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(Xpdl2Package.eINSTANCE
                .getFormalParametersContainer_FormalParameters());
        return features;
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
     * #addColumns(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {

        new LabelColumn(editingDomain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer);
        }
        new MandatoryColumn(editingDomain, viewer);
        new ReadOnlyColumn(editingDomain, viewer);
        new ModeColumn(editingDomain, viewer);
        new TypeColumn(editingDomain, viewer);
        new LengthColumn(editingDomain, viewer);
        new DecimalPlacesColumn(editingDomain, viewer);
        new ArrayColumn(editingDomain, viewer);
        new ExternalReferenceColumn(editingDomain, viewer, "", 90); //$NON-NLS-1$
        new CaseRefTypeColumn(editingDomain, viewer);
        new TypeDeclarationColumn(editingDomain, viewer);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            setColumnProportions(new float[] { 0.090f, // Label,
                    0.090f, // Name,
                    0.060f, // Mandatory
                    0.060f, // Readonly
                    0.05f, // Mode
                    0.075f, // Type,
                    0.065f, // Length
                    0.090f, // Decimals
                    0.05f, // Is array
                    0.1f, // External Reference
                    0.12f, // Case Ref Type
                    0.1f, // Type declaration.
            });
        } else {
            setColumnProportions(new float[] { 0.1f, // Label,
                    0.060f, // Mandatory
                    0.060f, // Readonly
                    0.05f, // Mode
                    0.075f, // Type,
                    0.065f, // Length
                    0.090f, // Decimals
                    0.05f, // Is array
                    0.1f, // External Reference
                    0.1f, // Case Ref Type
                    0.1f, // Type declaration.
            });
        }
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
        return new TableAddAction(viewer, Messages.PropertiesSection_AddLabel,
                Messages.ParametersSection_AddDataFieldButton_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (null != input) {
                    String firstCellVal = getNewRowFirstCellVal();
                    FormalParameter formalParameter =
                            createFileTemplate(firstCellVal);
                    // the label cell in the table viewer was allowing duplicate
                    // name

                    // DON'T IGNORE THE FIRST CELL VAL BY GOING
                    // getNewRowFirstCellVal which will kill the user entered
                    // name!
                    String propName =
                            getUniqueParameterName(firstCellVal,
                                    formalParameter);

                    formalParameter.setName(NameUtil.getInternalName(propName,
                            true));
                    Xpdl2ModelUtil.setOtherAttribute(formalParameter,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            propName);
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ParametersSection_createParameter_menu);
                    cmd.append(AddCommand.create(editingDomain,
                            input,
                            Xpdl2Package.eINSTANCE.getFormalParameter(),
                            formalParameter));
                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                String propName =
                        Messages.ParametersSection_ParameterName_value;
                FormalParameter formalParameter = createFileTemplate(propName);
                String uniqueParameterName =
                        getUniqueParameterName(propName, formalParameter);
                if (uniqueParameterName != null
                        && !uniqueParameterName.equals(propName)) {
                    propName = uniqueParameterName;
                }
                return propName;
            }

            private FormalParameter createFileTemplate(String name) {
                Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
                FormalParameter input = fact.createFormalParameter();
                input.setName(NameUtil.getInternalName(name, true));
                Xpdl2ModelUtil.setOtherAttribute(input,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        name);
                // Set basic string type
                BasicType basicType = fact.createBasicType();
                basicType.setType(BasicTypeType.STRING_LITERAL);
                Length len = Xpdl2Factory.eINSTANCE.createLength();
                len.setValue("50"); //$NON-NLS-1$
                basicType.setLength(len);

                input.setDataType(basicType);
                // Default to INOUT
                input.setMode(ModeType.INOUT_LITERAL);
                boolean isMandatory = isMandatoryParameter();
                input.setRequired(isMandatory);
                input.setReadOnly(false);

                return input;
            }

            private String getUniqueParameterName(String baseName,
                    FormalParameter formalParameter) {
                String finalName = baseName;
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayFieldOrParam(getInput(),
                                formalParameter,
                                finalName) != null
                        || Xpdl2ModelUtil.getDuplicateFieldOrParam(getInput(),
                                formalParameter,
                                NameUtil.getInternalName(finalName, true)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                return finalName;
            }

            /**
             * Helps to identify whether mandatory parameters should be created
             * or not.
             * 
             * @return
             */
            private boolean isMandatoryParameter() {
                /*
                 * XPD-708: Default to mandatory now regardless of whether it's
                 * an interace for process parameter (when used for message
                 * activities input parameters are also required)
                 */
                return Boolean.TRUE;
                // boolean isMandatory = Boolean.TRUE;
                // EObject fpContainer = getInput();
                // if (fpContainer instanceof com.tibco.xpd.xpdl2.Process) {
                // isMandatory = Boolean.FALSE;
                // }
                // return isMandatory;
            }

        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processeditor.xpdl2.properties.dataFields.DataFieldTable
     * #createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.PropertiesSection_DeleteLabel,
                Messages.ParametersSection_DeleteDataFieldButton_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                if (selection != null && !selection.isEmpty()) {
                    List<FormalParameter> parameterList =
                            new ArrayList<FormalParameter>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof FormalParameter) {
                            parameterList.add((FormalParameter) next);
                        }
                    }
                    if (!parameterList.isEmpty()) {
                        DeleteAction.deleteXpdlObject(Display.getCurrent()
                                .getActiveShell(), selection);
                    }
                }
            }

        };
    }

    private class MandatoryColumn extends AbstractColumn {
        private final CheckboxCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public MandatoryColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.ParametersSection_mandatoryColumn_label, 65);
            editor =
                    new CheckboxCellEditor((Composite) viewer.getControl(),
                            SWT.NONE);
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
            if (element instanceof FormalParameter) {
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
            if (element instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) element;
                if (formalParameter.isRequired()) {
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
            if (element instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) element;
                if (value instanceof Boolean) {
                    cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_setDataTypeIsMandatory_menu);
                    cmd.append(SetCommand.create(editingDomain,
                            formalParameter,
                            Xpdl2Package.eINSTANCE
                                    .getFormalParameter_Required(),
                            value));
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
            if (element instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) element;
                return new Boolean(formalParameter.isRequired());
            }
            return null;
        }

    }

    private class ModeColumn extends AbstractColumn {
        private ComboBoxViewerCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         * @param style
         * @param heading
         * @param width
         */
        public ModeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.ParametersSection_modeColumn_label, 50);
            /*
             * XPD-6789: Saket: This editor should be read only.
             */
            editor =
                    new ComboBoxViewerCellEditor(
                            (Composite) viewer.getControl(), SWT.READ_ONLY);
            String[] modeItems =
                    new String[] { Messages.ParametersSection_Mode_In,
                            Messages.ParametersSection_Mode_Out,
                            Messages.ParametersSection_Mode_InOut };
            editor.setContenProvider(new ArrayContentProvider());
            editor.setLabelProvider(new LabelProvider());
            editor.setInput(modeItems);
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
            if (element instanceof FormalParameter) {
                return editor;
            }
            return null;
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
            if (element instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) element;

                if (value instanceof String) {
                    String valStr = (String) value;

                    ModeType modeType = null;
                    if (Messages.ParametersSection_Mode_InOut
                            .equalsIgnoreCase(valStr)) {
                        modeType = ModeType.INOUT_LITERAL;
                    } else if (Messages.ParametersSection_Mode_In
                            .equalsIgnoreCase(valStr)) {
                        modeType = ModeType.IN_LITERAL;
                    } else if (Messages.ParametersSection_Mode_Out
                            .equalsIgnoreCase(valStr)) {
                        modeType = ModeType.OUT_LITERAL;
                    }

                    if (null != modeType) {
                        cmd =
                                new CompoundCommand(
                                        Messages.ParametersSection_setParameterMode_menu);
                        cmd.append(SetCommand.create(editingDomain,
                                formalParameter,
                                Xpdl2Package.eINSTANCE
                                        .getFormalParameter_Mode(),
                                modeType));
                    }
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
        protected String getText(Object element) {
            String text = null;
            if (element instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) element;
                ModeType modeType = formalParameter.getMode();
                if (modeType != null) {
                    if (modeType.getLiteral()
                            .equals(ModeType.IN_LITERAL.getLiteral())) {
                        text = Messages.ParametersSection_Mode_In;
                    } else if (modeType.getLiteral()
                            .equals(ModeType.OUT_LITERAL.getLiteral())) {
                        text = Messages.ParametersSection_Mode_Out;
                    } else if (modeType.getLiteral()
                            .equals(ModeType.INOUT_LITERAL.getLiteral())) {
                        text = Messages.ParametersSection_Mode_InOut;
                    }
                }
            }
            return text;
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
            return getText(element);
        }

    }
}
