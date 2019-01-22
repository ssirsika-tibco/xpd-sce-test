/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.typeDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.DeleteAction;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcessRelevantDataTable;
import com.tibco.xpd.processeditor.xpdl2.properties.general.TypeDeclarationPropertySection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessRelevantDataUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerAddAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Precision;
import com.tibco.xpd.xpdl2.Scale;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * TypeDeclarationTable
 * 
 * 
 * @author bharge
 * @since 3.3 (13 Oct 2009)
 */
public class TypeDeclarationTable extends AbstractProcessRelevantDataTable {

    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    public TypeDeclarationTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
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
                        return ((Package) inputElement).getTypeDeclarations()
                                .toArray();
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

        new LabelColumn(editingDomain, viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(editingDomain, viewer);
        }
        new TypeColumn(editingDomain, viewer);
        new LengthColumn(editingDomain, viewer);
        new DecimalPlacesColumn(editingDomain, viewer);
        new ExternalReferenceColumn(editingDomain, viewer, "", 90); //$NON-NLS-1$
        new CaseRefTypeColumn(editingDomain, viewer);
        new TypeDeclarationColumn(editingDomain, viewer);

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            setColumnProportions(new float[] { 0.1f, // Label,
                    0.1f, // Name,
                    0.1f, // Type,
                    0.075f, // Length
                    0.075f, // Decimals
                    0.1f, // External Reference
                    0.1f, // Case Ref Type
                    0.1f, // Type declaration.
            });
        } else {
            setColumnProportions(new float[] { 0.15f, // Label,
                    0.1f, // Type,
                    0.075f, // Length
                    0.075f, // Decimals
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
     * com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createAddAction
     * (org.eclipse.jface.viewers.ColumnViewer)
     */
    @Override
    protected ViewerAddAction createAddAction(ColumnViewer viewer) {
        return new TableAddAction(
                viewer,
                Messages.PropertiesSection_AddLabel,
                Messages.TypeDeclarationsSection_AddTypeDeclarationButton_tooltip) {

            @Override
            protected Object addRow(StructuredViewer viewer) {
                Object input = getInput();
                if (null != input) {
                    String firstCellVal = getNewRowFirstCellVal();
                    TypeDeclaration typeDeclaration =
                            createFileTemplate(firstCellVal);

                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.TypeDeclarationsSection_createTypeDeclaration_menu);

                    String propName =
                            getUniqueTypeDeclarationName(firstCellVal,
                                    typeDeclaration);
                    typeDeclaration.setName(NameUtil.getInternalName(propName,
                            true));
                    Xpdl2ModelUtil.setOtherAttribute(typeDeclaration,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            propName);
                    cmd.append(AddCommand.create(editingDomain,
                            input,
                            Xpdl2Package.eINSTANCE.getTypeDeclaration(),
                            typeDeclaration));
                    if (cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                        Collection<?> result = cmd.getResult();
                        if (result != null && result.size() == 1) {
                            return result.iterator().next();
                        }
                    }
                }
                return null;
            }

            protected String getNewRowFirstCellVal() {
                String propName =
                        Messages.TypeDeclarationsSection_TypeDeclarationName_value;
                TypeDeclaration typeDeclaration = createFileTemplate(propName);
                String uniqueTypeDeclarationName =
                        getUniqueTypeDeclarationName(propName, typeDeclaration);
                if (uniqueTypeDeclarationName != null
                        && !uniqueTypeDeclarationName.equals(propName)) {
                    propName = uniqueTypeDeclarationName;
                }
                return propName;
            }

            private TypeDeclaration createFileTemplate(String name) {
                Xpdl2Factory fact = Xpdl2Factory.eINSTANCE;
                TypeDeclaration input = fact.createTypeDeclaration();
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

                input.setBasicType(basicType);

                return input;
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
        return new TableDeleteAction(
                viewer,
                Messages.PropertiesSection_DeleteLabel,
                Messages.TypeDeclarationsSection_DeleteTypeDeclarationButton_tooltip) {

            @Override
            protected void deleteRows(IStructuredSelection selection) {
                if (selection != null && !selection.isEmpty()) {
                    List<TypeDeclaration> typeDecl =
                            new ArrayList<TypeDeclaration>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof TypeDeclaration) {
                            typeDecl.add((TypeDeclaration) next);
                        }
                    }
                    if (!typeDecl.isEmpty()) {
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
        Set<EStructuralFeature> features = super.getMovableFeatures();
        features.add(Xpdl2Package.eINSTANCE.getPackage_TypeDeclarations());
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

    private String getUniqueTypeDeclarationName(String baseName,
            TypeDeclaration typeDeclaration) {
        String finalName = baseName;
        if (getInput() != null) {
            Package pkg = null;
            if (getInput() instanceof Package) {
                pkg = (Package) getInput();
            } else {
                pkg = Xpdl2ModelUtil.getPackage(getInput());
            }
            if (pkg != null) {
                int idx = 1;
                while (Xpdl2ModelUtil.getDuplicateDisplayTypeDeclaration(pkg,
                        typeDeclaration,
                        finalName) != null
                        || Xpdl2ModelUtil.getDuplicateTypeDeclaration(pkg,
                                typeDeclaration,
                                finalName) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
            }
        }
        return finalName;
    }

    private class TypeColumn extends AbstractTypeColumn {
        /**
         * @param editingDomain
         * @param viewer
         */
        public TypeColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer);
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
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                String currentType = null;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();
                    currentType = basicType.getType().getName();
                } else if (typeDeclaration.getExternalReference() != null) {
                    currentType =
                            ProcessRelevantDataUtil.EXTERNAL_REFERENCE_TYPE;
                } else if (typeDeclaration.getDeclaredType() != null) {
                    currentType = ProcessRelevantDataUtil.TYPE_DECLARATION_TYPE;
                }
                int typeIndex = getTypeIndex((String) value);

                if (currentType != null) {
                    cmd =
                            new CompoundCommand(
                                    Messages.DataFieldsSection_createDataType_menu);
                    String newType = getTypeValue(typeIndex);
                    if (newType != null && !newType.equals(currentType)) {
                        DataType newDataType =
                                ProcessRelevantDataUtil
                                        .createNewDataType(newType);

                        cmd.append(TypeDeclarationPropertySection
                                .getClearTypeDeclarationTypeCommand(getEditingDomain(),
                                        typeDeclaration));

                        if (newDataType instanceof ExternalReference) {
                            ExternalReference externalReference =
                                    (ExternalReference) newDataType;
                            cmd.append(SetCommand
                                    .create(editingDomain,
                                            typeDeclaration,
                                            Xpdl2Package.eINSTANCE
                                                    .getTypeDeclaration_ExternalReference(),
                                            externalReference));

                        } else if (newDataType instanceof BasicType) {
                            BasicType basicType = (BasicType) newDataType;
                            cmd.append(SetCommand.create(editingDomain,
                                    typeDeclaration,
                                    Xpdl2Package.eINSTANCE
                                            .getTypeDeclaration_BasicType(),
                                    basicType));

                        } else if (newDataType instanceof DeclaredType) {
                            DeclaredType declaredType =
                                    (DeclaredType) newDataType;
                            cmd.append(SetCommand.create(editingDomain,
                                    typeDeclaration,
                                    Xpdl2Package.eINSTANCE
                                            .getTypeDeclaration_DeclaredType(),
                                    declaredType));
                        }
                    }
                }
            }
            return cmd;
        }

    }

    private class LengthColumn extends AbstractLengthColumn {

        public LengthColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer);
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
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                String currentLength = null;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();
                    if (basicType.getType() == BasicTypeType.STRING_LITERAL) {
                        Length length = basicType.getLength();
                        if (length != null) {
                            currentLength = length.getValue();
                            if (value != null && !value.equals("")) {//$NON-NLS-1$
                                if (currentLength == null
                                        || !currentLength.equals(value)) {
                                    String newValue = null;
                                    if (value instanceof String) {
                                        Short saveShortValue =
                                                ProcessRelevantDataUtil
                                                        .safeParseShort((String) value);
                                        if (saveShortValue != null) {
                                            newValue =
                                                    saveShortValue.toString();
                                        }
                                    }
                                    if (newValue != null) {
                                        cmd =
                                                new CompoundCommand(
                                                        Messages.DataFieldsSection_setDataTypeLength_menu);
                                        Length newLength =
                                                Xpdl2Factory.eINSTANCE
                                                        .createLength();
                                        newLength.setValue(newValue);
                                        cmd.append(SetCommand
                                                .create(editingDomain,
                                                        basicType,
                                                        Xpdl2Package.eINSTANCE
                                                                .getBasicType_Length(),
                                                        newLength));
                                    }
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

    private class DecimalPlacesColumn extends AbstractDecimalPlacesColumn {

        public DecimalPlacesColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer);
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
            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;
                String currentScale = null;
                if (typeDeclaration.getBasicType() != null) {
                    BasicType basicType = typeDeclaration.getBasicType();
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

    private class TypeDeclarationColumn extends AbstractTypeDeclarationColumn {

        private final ComboBoxViewerCellEditor editor;

        @SuppressWarnings("deprecation")
        public TypeDeclarationColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer);
            editor = getEditor();
            editor.setContenProvider(new IStructuredContentProvider() {

                @Override
                public Object[] getElements(Object inputElement) {
                    if (inputElement instanceof Package) {
                        EList<TypeDeclaration> typeDeclarations =
                                ((Package) inputElement).getTypeDeclarations();
                        List<TypeDeclaration> typeDeclList =
                                new ArrayList<TypeDeclaration>();

                        StructuredSelection selection =
                                (StructuredSelection) getTableViewer()
                                        .getSelection();
                        Object element = selection.getFirstElement();

                        TypeDeclaration selectedTypeDeclaration = null;
                        if (element instanceof TypeDeclaration) {
                            selectedTypeDeclaration = (TypeDeclaration) element;
                        }

                        for (TypeDeclaration typeDeclaration : typeDeclarations) {
                            if (null != selectedTypeDeclaration) {
                                if (!selectedTypeDeclaration.getId()
                                        .equals(typeDeclaration.getId())) {
                                    typeDeclList.add(typeDeclaration);
                                }
                            }
                        }
                        return typeDeclList.toArray();
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
            TypeDeclaration newTypeDeclaration = null;
            int declaredTypeIndex = 0;

            if (element instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) element;

                if (value instanceof TypeDeclaration) {
                    /*
                     * XPD-4692: When user selects type declaration then set
                     * appropriate dataType even if field not currently set to
                     * TypeDeclaration type (that matches how external ref
                     * column works too).
                     */
                    String newTypeDeclarationId =
                            ((TypeDeclaration) value).getId();

                    DeclaredType currentDeclaredType =
                            typeDeclaration.getDeclaredType();

                    if ((currentDeclaredType == null)
                            || !currentDeclaredType.getTypeDeclarationId()
                                    .equals(newTypeDeclarationId)) {

                        DeclaredType newDelcaredType =
                                Xpdl2Factory.eINSTANCE.createDeclaredType();
                        newDelcaredType
                                .setTypeDeclarationId(newTypeDeclarationId);

                        cmd =
                                new CompoundCommand(
                                        Messages.TypeDeclarationsSection_setDeclaredType_menu);

                        cmd.append(TypeDeclarationPropertySection
                                .getClearTypeDeclarationTypeCommand(editingDomain,
                                        typeDeclaration));

                        cmd.append(SetCommand.create(editingDomain,
                                typeDeclaration,
                                Xpdl2Package.eINSTANCE
                                        .getTypeDeclaration_DeclaredType(),
                                newDelcaredType));
                    }
                }
            }

            return cmd;
        }

    }
}
