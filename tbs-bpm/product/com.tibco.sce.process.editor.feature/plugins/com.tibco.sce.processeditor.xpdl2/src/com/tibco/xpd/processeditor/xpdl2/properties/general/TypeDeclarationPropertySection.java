/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesFolder;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesItem;
import com.tibco.xpd.xpdl2.edit.ui.properties.general.ObjectReferencesSection;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2TypeDeclarationReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Type Declaration property section
 * 
 * @author njpatel
 */
public class TypeDeclarationPropertySection extends BaseTypeSection {

    /**
     * Type Declaration property section
     */
    public TypeDeclarationPropertySection() {
        super(Xpdl2Package.eINSTANCE.getTypeDeclaration(), false, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#getInputType()
     */
    @Override
    protected DataType getInputType() {
        TypeDeclaration tDecl = getTypeDeclaration();
        DataType inputType = null;

        // Check for type
        if (tDecl.getBasicType() != null) {
            // Basic type
            inputType = tDecl.getBasicType();
        } else if (tDecl.getDeclaredType() != null) {
            // Declared type
            inputType = tDecl.getDeclaredType();
        } else if (tDecl.getExternalReference() != null) {
            // External reference
            inputType = tDecl.getExternalReference();
        } else if (null != tDecl.getRecordType()) {
            inputType = tDecl.getRecordType();
        }

        return inputType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdlr2.properties.general.BaseTypeSection#setBasicTypeCmd
     * (com.tibco.xpd.xpdlr2.BasicType)
     */
    @Override
    protected Command setBasicTypeCmd(BasicType basicType) {

        BasicType bt = getModelBasicType();
        if (bt != null) {
            if (bt.getType().equals(basicType.getType())) {
                return null; // don't bother if changing to same as already
                // is
            }
        }

        CompoundCommand cmpCommand = new CompoundCommand();

        // Clear the currently set declaration type
        clearTypeDeclaration(cmpCommand);
        // Set the new selection
        cmpCommand.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getTypeDeclaration_BasicType(),
                basicType));

        cmpCommand
                .setLabel(Messages.TypeDeclarationPropertySection_SetTypeDeclType_menu);
        return cmpCommand;
    }

    @Override
    protected Command setDeclaredTypeCmd(DeclaredType declaredType) {

        // Don't bother running command if typoe not changing.
        if (getTypeDeclaration().getDeclaredType() != null) {
            String currType =
                    getTypeDeclaration().getDeclaredType()
                            .getTypeDeclarationId();
            if (currType != null
                    && currType.equals(declaredType.getTypeDeclarationId())) {
                return null;
            }
        }

        CompoundCommand cmpCommand = new CompoundCommand();

        // Clear the currently set declaration type
        clearTypeDeclaration(cmpCommand);
        // Set the new selection
        cmpCommand.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getTypeDeclaration_DeclaredType(),
                declaredType));

        cmpCommand
                .setLabel(Messages.TypeDeclarationPropertySection_SetTypeDeclType_menu);
        return cmpCommand;
    }

    @Override
    protected Command setExternalRefCmd(ExternalReference externalReference) {
        CompoundCommand cmpCommand = new CompoundCommand();

        // Clear the currently selected declaration type
        clearTypeDeclaration(cmpCommand);
        // Set the new selection
        cmpCommand.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getTypeDeclaration_ExternalReference(),
                externalReference));

        cmpCommand
                .setLabel(Messages.TypeDeclarationPropertySection_SetTypeDeclType_menu);
        return cmpCommand;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#setCaseRefTypeCmd(com.tibco.xpd.xpdl2.RecordType)
     * 
     * @param caseRefType
     * @return
     */
    @Override
    protected Command setCaseRefTypeCmd(RecordType caseRefType) {
        CompoundCommand cmpCommand = new CompoundCommand();

        // Clear the currently selected declaration type
        clearTypeDeclaration(cmpCommand);
        // Set the new selection
        cmpCommand.append(SetCommand.create(getEditingDomain(),
                getInput(),
                Xpdl2Package.eINSTANCE.getTypeDeclaration_RecordType(),
                caseRefType));

        cmpCommand
                .setLabel(Messages.TypeDeclarationPropertySection_SetTypeDeclType_menu);
        return cmpCommand;
    }

    /**
     * Clear the type declaration setting in the model
     * 
     * @param cmd
     */
    private void clearTypeDeclaration(CompoundCommand cmd) {
        cmd.append(getClearTypeDeclarationTypeCommand(getEditingDomain(),
                getTypeDeclaration()));
    }

    /**
     * @param editingDomain
     * @param typeDeclaration
     * 
     * @return Command to clear all data type info from type declaration (ready
     *         for new data type detail to be added.
     */
    public static Command getClearTypeDeclarationTypeCommand(
            EditingDomain editingDomain, TypeDeclaration typeDeclaration) {
        CompoundCommand cmd = new CompoundCommand();
        // Clear the array type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_ArrayType(),
                null));
        // Clear the basic type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_BasicType(),
                null));
        // Clear the declared type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_DeclaredType(),
                null));
        // Clear the enumeration type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_EnumerationType(),
                null));
        // Clear the external reference type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_ExternalReference(),
                null));
        // Clear the list type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_ListType(),
                null));
        // Clear the record type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_RecordType(),
                null));
        // Clear the schema type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_SchemaType(),
                null));
        // Clear the union type
        cmd.append(SetCommand.create(editingDomain,
                typeDeclaration,
                Xpdl2Package.eINSTANCE.getTypeDeclaration_UnionType(),
                null));

        return cmd;
    }

    /**
     * Get the TypeDeclaration input
     * 
     * @return TypeDeclaration
     */
    private TypeDeclaration getTypeDeclaration() {
        return (TypeDeclaration) getInput();
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        // For new type declaration - Reset default name to something unique.
        // Ensure name is unique at the very start as we now check for
        // uniqueness we don't want user to be shown an error by default!
        TypeDeclaration typeDecl = (TypeDeclaration) getInput();
        if (typeDecl != null && typeDecl.eContainer() == null) {
            String baseName =
                    (String) Xpdl2ModelUtil.getOtherAttribute(typeDecl,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            String finalName = baseName;

            EObject container = getInputContainer();
            if (container instanceof Package) {
                int idx = 1;
                while (Xpdl2ModelUtil
                        .getDuplicateDisplayTypeDeclaration((Package) container,
                                typeDecl,
                                finalName) != null
                        || Xpdl2ModelUtil
                                .getDuplicateTypeDeclaration((Package) container,
                                        typeDecl,
                                        NameUtil.getInternalName(finalName,
                                                true)) != null) {
                    idx++;
                    finalName = baseName + idx;
                }
                Xpdl2ModelUtil.setOtherAttribute(typeDecl,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        finalName);
                typeDecl.setName(NameUtil.getInternalName(finalName, true));
            }
        }

        return;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#createObjectReferencesSection()
     * 
     * @return
     */
    @Override
    protected Object createObjectReferencesSection() {
        return new ObjectReferencesSection();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection#createObjectReferencesControls(java.lang.Object,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param objectReferencesSection
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    protected Control createObjectReferencesControls(
            Object objectReferencesSection, Composite container,
            XpdFormToolkit toolkit) {
        return ((ObjectReferencesSection) objectReferencesSection)
                .createControls(container, toolkit);
    }

    @Override
    protected void doLoadReferencesList(Object section) {
        if (section instanceof ObjectReferencesSection) {
            ObjectReferencesSection referencesSection =
                    (ObjectReferencesSection) section;

            ImageRegistry ir = Xpdl2UiPlugin.getDefault().getImageRegistry();

            ObjectReferencesFolder pkgDataFieldsFolder = null;
            ObjectReferencesFolder pkgTypeDeclarationsFolder = null;

            // Map of process object to the process folder for it.
            HashMap<NamedElement, ObjectReferencesFolder> processFolders =
                    new HashMap<NamedElement, ObjectReferencesFolder>();

            // Map of process interface object to the process inteface folder
            // for it.
            HashMap<NamedElement, ObjectReferencesFolder> processIfcFolders =
                    new HashMap<NamedElement, ObjectReferencesFolder>();

            // Map of process object to the activity folder for it.
            HashMap<NamedElement, ObjectReferencesFolder> procFieldFolders =
                    new HashMap<NamedElement, ObjectReferencesFolder>();
            // Map of process object to the transitions folder for it.
            HashMap<NamedElement, ObjectReferencesFolder> procParamFolders =
                    new HashMap<NamedElement, ObjectReferencesFolder>();

            EObject in = getInput();

            if (in instanceof TypeDeclaration) {
                TypeDeclaration inputData = (TypeDeclaration) in;

                // Fetch all the field references according to scope of field.
                Set<EObject> referenceObjects =
                        Xpdl2TypeDeclarationReferenceResolver
                                .getReferencingObjects(inputData);

                // Now add all the items into list.
                // Creating a tree of process/activity/transition folders.
                for (Iterator iter = referenceObjects.iterator(); iter
                        .hasNext();) {
                    EObject obj = (EObject) iter.next();

                    // If it's a package level item then add it to the package
                    // level folder.
                    if (obj.eContainer() instanceof Package) {
                        if (obj instanceof DataField) {
                            if (pkgDataFieldsFolder == null) {
                                pkgDataFieldsFolder =
                                        new ObjectReferencesFolder(
                                                Messages.TypeDeclarationPropertySection_DataFields_label,
                                                ir.get(Xpdl2UiPlugin.IMG_DATAFIELD_DECLAREDTYPE));
                            }

                            pkgDataFieldsFolder.addChild(ObjectReferencesItem
                                    .create(((DataField) obj)));

                        } else if (obj instanceof TypeDeclaration) {
                            if (pkgTypeDeclarationsFolder == null) {
                                pkgTypeDeclarationsFolder =
                                        new ObjectReferencesFolder(
                                                Messages.TypeDeclarationPropertySection_TypeDeclarations_label,
                                                ir.get(Xpdl2UiPlugin.IMG_TYPEDECLARATION_DECLAREDTYPE));
                            }

                            pkgTypeDeclarationsFolder
                                    .addChild(ObjectReferencesItem
                                            .create(((TypeDeclaration) obj)));
                        }
                    } else if (obj.eContainer() instanceof Process) {
                        Process proc = (Process) obj.eContainer();

                        if (obj instanceof DataField) {
                            DataField dataField = (DataField) obj;

                            // Add folder for process if necessary.

                            if (proc != null) {
                                // Create the folder for this process if we
                                // haven't
                                // already.
                                ObjectReferencesFolder procFolder =
                                        processFolders.get(proc);
                                if (procFolder == null) {
                                    procFolder =
                                            new ObjectReferencesFolder(proc);
                                    processFolders.put(proc, procFolder);
                                }

                                // Create the activity folder for this process
                                // if
                                // not exists.
                                ObjectReferencesFolder fieldFolder =
                                        procFieldFolders.get(proc);
                                if (fieldFolder == null) {
                                    fieldFolder =
                                            new ObjectReferencesFolder(
                                                    Messages.TypeDeclarationPropertySection_DataFields_label,
                                                    ir.get(Xpdl2UiPlugin.IMG_DATAFIELD_DECLAREDTYPE));

                                    procFieldFolders.put(proc, fieldFolder);

                                    procFolder.addChild(fieldFolder);
                                }

                                fieldFolder.addChild(ObjectReferencesItem
                                        .create(dataField));
                            }

                        } else if (obj instanceof FormalParameter) {
                            FormalParameter formalParameter =
                                    (FormalParameter) obj;

                            // Add folder for process if necessary.

                            if (proc != null) {
                                // Create the folder for this process if we
                                // haven't
                                // already.
                                ObjectReferencesFolder procFolder =
                                        processFolders.get(proc);
                                if (procFolder == null) {
                                    procFolder =
                                            new ObjectReferencesFolder(proc);
                                    processFolders.put(proc, procFolder);
                                }

                                // Create the activity folder for this process
                                // if
                                // not exists.
                                ObjectReferencesFolder paramFolder =
                                        procParamFolders.get(proc);
                                if (paramFolder == null) {
                                    paramFolder =
                                            new ObjectReferencesFolder(
                                                    Messages.TypeDeclarationPropertySection_FormalParameters_label,
                                                    ir.get(Xpdl2UiPlugin.IMG_FORMALPARAM_INOUT));

                                    procParamFolders.put(proc, paramFolder);

                                    procFolder.addChild(paramFolder);
                                }

                                paramFolder.addChild(ObjectReferencesItem
                                        .create(formalParameter));
                            }

                        }
                    }
                    // Check for formal params in process interface.
                    else if (obj.eContainer() instanceof ProcessInterface) {
                        ProcessInterface procIfc =
                                (ProcessInterface) obj.eContainer();

                        if (obj instanceof FormalParameter) {
                            FormalParameter formalParameter =
                                    (FormalParameter) obj;

                            // Add folder for process if necessary.

                            if (procIfc != null) {
                                // Create the folder for this process if we
                                // haven't already.
                                ObjectReferencesFolder procIfcFolder =
                                        processIfcFolders.get(procIfc);
                                if (procIfcFolder == null) {
                                    procIfcFolder =
                                            new ObjectReferencesFolder(procIfc);
                                    processIfcFolders.put(procIfc,
                                            procIfcFolder);
                                }

                                // Create the activity folder for this process
                                // if
                                // not exists.
                                ObjectReferencesFolder paramFolder =
                                        procParamFolders.get(procIfc);
                                if (paramFolder == null) {
                                    paramFolder =
                                            new ObjectReferencesFolder(
                                                    Messages.TypeDeclarationPropertySection_FormalParameters_label,
                                                    ir.get(Xpdl2UiPlugin.IMG_INTERFACE_PARAM_INOUT));

                                    procParamFolders.put(procIfc, paramFolder);

                                    procIfcFolder.addChild(paramFolder);
                                }

                                paramFolder.addChild(ObjectReferencesItem
                                        .create(formalParameter));
                            }

                        }
                    }
                }
            }

            // Add the package level type declares / fields if there are any.
            List<ObjectReferencesFolder> finalList =
                    new ArrayList<ObjectReferencesFolder>();
            if (pkgTypeDeclarationsFolder != null) {
                finalList.add(pkgTypeDeclarationsFolder);
            }
            if (pkgDataFieldsFolder != null) {
                finalList.add(pkgDataFieldsFolder);
            }

            // Add a process interfaces folder if there are process interfaces.
            Collection<ObjectReferencesFolder> procIfcFold =
                    processIfcFolders.values();
            if (procIfcFold.size() > 0) {
                ObjectReferencesFolder procIfcs =
                        new ObjectReferencesFolder(
                                Messages.TypeDeclarationPropertySection_ProcessInterfaces_label,
                                ir.get(Xpdl2UiPlugin.IMG_PROCESSINTERFACE));
                for (ObjectReferencesFolder pf : procIfcFold) {
                    procIfcs.addChild(pf);
                }

                finalList.add(procIfcs);
            }

            // Add a processes folder if there are processes.
            Collection<ObjectReferencesFolder> procFold =
                    processFolders.values();
            if (procFold.size() > 0) {
                ObjectReferencesFolder procs =
                        new ObjectReferencesFolder(
                                Messages.TypeDeclarationPropertySection_Processes_label,
                                ir.get(Xpdl2UiPlugin.IMG_BUSINESS_PROCESS));
                for (ObjectReferencesFolder pf : procFold) {
                    procs.addChild(pf);
                }

                finalList.add(procs);
            }

            referencesSection.setContent(finalList);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.xpdl2.edit.ui.properties.general.BaseTypeSection#
     * getModelBasicType()
     */
    @Override
    protected BasicType getModelBasicType() {
        TypeDeclaration typeDecl = (TypeDeclaration) getInput();
        return typeDecl.getBasicType();
    }

    /**
     * Returns help context for sections to show individual help details
     * 
     * @return
     */
    public String getHelpContextID() {
        return "com.tibco.xpd.process.analyst.doc.TypeDeclare"; //$NON-NLS-1$
    }

    @Override
    protected Control doCreateInitialValuesControls(Composite root,
            XpdFormToolkit toolkit) {
        return null;
    }

    @Override
    protected void doRefreshExtraDetails() {
    }

    @Override
    protected String getInitialValuesTitle() {
        return ""; //$NON-NLS-1$
    }

    @Override
    protected void doCreatePreTypeControls(Composite parent,
            XpdFormToolkit toolkit) {
    }

    @Override
    protected String getSectionPreferencesId() {
        return this.getClass().getName();
    }

    // MR 38533 - begin
    @Override
    protected void contributeToExpandables(
            ExpandableSectionStacker expandableHeaderController2) {
        // do nothing
    }

    @Override
    protected Control createContributedExpandable(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        return null;
    }
    // MR 38533 - end
}
