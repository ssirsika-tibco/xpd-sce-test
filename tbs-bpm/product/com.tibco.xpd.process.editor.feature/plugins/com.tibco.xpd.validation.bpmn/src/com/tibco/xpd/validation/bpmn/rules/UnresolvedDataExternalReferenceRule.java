/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.internal.impl.UMLPackageImpl;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check any un-set or unresolvedprocess data type external references
 * in a package or process
 * 
 * @author glewis (original) abstracted by aallway at v3.2
 * @since 3.2
 */
public class UnresolvedDataExternalReferenceRule extends PackageValidationRule {

    private ComplexDataTypeReference complexDataTypeRef = null;

    private ComplexDataTypesMergedInfo _complexTypesInfo = null;

    public static final String UNRESOLVED_DATATYPE_REFERENCE =
            "bpmn.unresolvedDataExternalReference"; //$NON-NLS-1$ 

    public static final String UNRESOLVED_DATATYPE_REFERENCE_FOR__TYPE =
            "bpmn.unresolvedDataExternalReferenceFor_Type"; //$NON-NLS-1$ 

    public static final String UNSET_DATATYPE_REFERENCE =
            "bpmn.unsetDataExternalReference"; //$NON-NLS-1$ 

    public static final String UNRESOLVEDCOMPLEXDATAFOR_TYPE =
            "UNRESOLVEDCOMPLEXDATAFOR_TYPE"; //$NON-NLS-1$

    /* Incorrect namespace on external data reference. */
    public static final String INCORRECT_NAMESPACE_ON_EXT_REF =
            "bpmn.incorrectNamespaceOnExtRef"; //$NON-NLS-1$

    /**
     * @param pckg
     *            The package to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {

        EList<DataField> dataFields = null;
        EList<FormalParameter> formalParameterFields = null;

        dataFields = pckg.getDataFields();
        validateDataFields(dataFields, pckg);

        List<Process> processes = pckg.getProcesses();
        for (Process process : processes) {
            dataFields = process.getDataFields();
            formalParameterFields = process.getFormalParameters();

            validateDataFields(dataFields, process);
            validateFormalParams(formalParameterFields, process);

            /**
             * Validate if an activity or an Embedded sub-process has unresolved
             * external references
             */
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                dataFields = activity.getDataFields();
                validateDataFields(dataFields, activity);
            }
        }

        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(pckg);
        if (processInterfaces != null) {
            List<ProcessInterface> processInterfaceList =
                    processInterfaces.getProcessInterface();
            for (ProcessInterface processInterface : processInterfaceList) {
                formalParameterFields = processInterface.getFormalParameters();
                validateFormalParams(formalParameterFields, processInterface);
            }

        }

        validateTypeDeclarations(pckg);
    }

    /**
     * @param pckg
     */
    private void validateTypeDeclarations(Package pckg) {
        for (TypeDeclaration type : pckg.getTypeDeclarations()) {
            /* XPD-3127: validate case ref types for unresolved reference */
            RecordType recordType = type.getRecordType();
            if (null != recordType) {
                EList<Member> member = recordType.getMember();
                ExternalReference externalReference =
                        member.get(0).getExternalReference();
                if (null != externalReference) {
                    validateExternalReference(type, pckg, externalReference);
                }
            }
            if (type.getExternalReference() != null) {
                validateExternalReference(type,
                        pckg,
                        type.getExternalReference());
            }
        }
    }

    /**
     * @param formalParameterFields
     * @param process
     */
    private void validateFormalParams(
            EList<FormalParameter> formalParameterFields, Process process) {
        for (FormalParameter data : formalParameterFields) {
            validateProcessRelevantData(data, process);
        }
    }

    /**
     * @param formalParameterFields
     * @param process
     */
    private void validateFormalParams(
            EList<FormalParameter> formalParameterFields,
            ProcessInterface processInterface) {
        for (FormalParameter data : formalParameterFields) {
            validateProcessRelevantData(data, processInterface);
        }
    }

    /**
     * Validates the process relevant data to see if any are unresolved
     * 
     * @param datafields
     */
    private void validateDataFields(EList<DataField> datafields, Object parent) {
        for (DataField data : datafields) {
            validateProcessRelevantData(data, parent);
        }
    }

    /**
     * 
     * @param data
     * @param parent
     */
    private void validateProcessRelevantData(ProcessRelevantData data,
            Object parent) {
        DataType dataType = data.getDataType();
        if (dataType != null) {
            validateDataType(data, parent, dataType);
        }
    }

    /**
     * @param data
     * @param parent
     * @param name
     * @param dataType
     */
    private void validateDataType(EObject validateItem, Object parent,
            DataType dataType) {
        /* XPD-3127: validate case ref types for unresolved reference */
        if (dataType instanceof RecordType) {
            RecordType recordType = (RecordType) dataType;
            Member member = recordType.getMember().get(0);
            ExternalReference externalReference = member.getExternalReference();
            if (null != externalReference) {
                validateExternalReference(validateItem,
                        parent,
                        externalReference);
            }
        }
        if (dataType != null
                && dataType.eClass() != null
                && Xpdl2Package.EXTERNAL_REFERENCE == dataType.eClass()
                        .getClassifierID()) {
            ExternalReference extRef = (ExternalReference) dataType;
            validateExternalReference(validateItem, parent, extRef);
        }
    }

    /**
     * @param validateItem
     * @param parent
     * @param name
     * @param extRef
     */
    private void validateExternalReference(EObject validateItem, Object parent,
            ExternalReference extRef) {

        if (isExternalReferenceSet(extRef)) {
            ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();
            if (complexTypesInfo != null) {
                complexDataTypeRef = xpdl2RefToComplexDataTypeRef(extRef);
                if (complexDataTypeRef != null) {
                    IProject project = getProject(parent);
                    if (project != null) {

                        /*
                         * Check if the namespace of extRef matches the
                         * namespace of BOM it references
                         */
                        checkNamespaceValidity(validateItem, extRef, project);

                        String name =
                                refreshComplexDataType(project,
                                        complexTypesInfo);
                        if (name == null || name.length() == 0) {

                            /*
                             * check if we could resolve the reference after
                             * removing _type
                             */
                            String nameAfterRemoving_Type =
                                    getNameAfterRemoving_Type(project,
                                            complexTypesInfo);
                            /*
                             * if the reference is resolved after removing
                             * _type, then raise a different issue with a
                             * different quick fix
                             */
                            if (null != nameAfterRemoving_Type) {
                                List<String> msgs = new ArrayList<String>();
                                String location = extRef.getLocation();
                                msgs.add(location != null ? location : ""); //$NON-NLS-1$
                                String xref = extRef.getXref();
                                msgs.add(xref != null ? xref : ""); //$NON-NLS-1$

                                /*
                                 * Add the new correct Xref on
                                 * compexDataTypeReference to the additional
                                 * info for quick fix to use.
                                 */
                                HashMap<String, String> addInfo =
                                        new HashMap<String, String>();
                                addInfo.put(UNRESOLVEDCOMPLEXDATAFOR_TYPE,
                                        complexDataTypeRef.getXRef());

                                addIssue(UNRESOLVED_DATATYPE_REFERENCE_FOR__TYPE,
                                        validateItem,
                                        msgs,
                                        addInfo);
                            } else {

                                List<String> msgs = new ArrayList<String>();
                                String location = extRef.getLocation();
                                msgs.add(location != null ? location : ""); //$NON-NLS-1$
                                String xref = extRef.getXref();
                                msgs.add(xref != null ? xref : ""); //$NON-NLS-1$

                                addIssue(UNRESOLVED_DATATYPE_REFERENCE,
                                        validateItem,
                                        msgs);
                            }
                        }
                    }
                } else {
                    if (null == extRef.getLocation()
                            || extRef.getLocation().trim().length() == 0) {

                        List<String> msgs = new ArrayList<String>();
                        String location = extRef.getLocation();
                        msgs.add(location != null ? location : ""); //$NON-NLS-1$
                        String xref = extRef.getXref();
                        msgs.add(xref != null ? xref : ""); //$NON-NLS-1$

                        addIssue(UNRESOLVED_DATATYPE_REFERENCE,
                                validateItem,
                                msgs);
                    }
                }
            }
        } else {
            // External reference value has not been set
            addIssue(UNSET_DATATYPE_REFERENCE, validateItem);
        }
    }

    /**
     * Raises invalid namespace issue if the external reference namespace does
     * not match the namespace of the BOM file it refers.
     * 
     * @param validateItem
     * @param externalRef
     * @param project
     */
    @SuppressWarnings({ "restriction", "unchecked" })
    private void checkNamespaceValidity(EObject validateItem,
            ExternalReference externalRef, IProject project) {

        IFile file =
                SpecialFolderUtil.resolveSpecialFolderRelativePath(project,
                        ConceptUtil.BOM_SPECIAL_FOLDER_KIND,
                        URI.decode(externalRef.getLocation()),
                        true);
        if (file != null) {
            WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopy(file);
            EObject rootElement = workingCopy.getRootElement();

            if (rootElement != null) {

                if (null != rootElement.eClass()
                        && null != rootElement.eClass().eContainer()) {

                    EObject eContainer = rootElement.eClass().eContainer();

                    if (eContainer instanceof UMLPackageImpl) {

                        UMLPackageImpl umlPackage = (UMLPackageImpl) eContainer;
                        String bomNamespaceURI = umlPackage.getNsURI();

                        if (bomNamespaceURI != null
                                && !externalRef.getNamespace()
                                        .equals(bomNamespaceURI)) {
                            /* raise issue if namespaces dont match */

                            /*
                             * Additional info to get the namespace in the
                             * resolution.
                             */
                            HashMap<String, String> additionalInfo =
                                    new HashMap<String, String>();
                            additionalInfo.put(INCORRECT_NAMESPACE_ON_EXT_REF,
                                    bomNamespaceURI);

                            addIssue(INCORRECT_NAMESPACE_ON_EXT_REF,
                                    validateItem,
                                    Collections.EMPTY_LIST,
                                    additionalInfo);
                        }
                    }
                }
            }
        }
    }

    /***
     * 
     * @param project
     * @param complexTypesInfo
     * @return the new generated bom type reference name after removing _type if
     *         found, null otherwise
     */
    private String getNameAfterRemoving_Type(IProject project,
            ComplexDataTypesMergedInfo complexTypesInfo) {

        String nameFoundAfter_typeRemoved = null;

        if (complexDataTypeRef.getXRef().contains("_type")) { //$NON-NLS-1$

            complexDataTypeRef.setXRef(complexDataTypeRef.getXRef()
                    .replaceFirst("_type", "")); //$NON-NLS-1$//$NON-NLS-2$
            nameFoundAfter_typeRemoved =
                    refreshComplexDataType(project, complexTypesInfo);
            if (null != nameFoundAfter_typeRemoved) {
                return nameFoundAfter_typeRemoved;
            }
        }

        return null;
    }

    /**
     * Check if the external reference is set.
     * 
     * @param ref
     * @return <code>true</code> if the location and xref values are set,
     *         <code>false</code> otherwise.
     */
    private boolean isExternalReferenceSet(ExternalReference ref) {
        return ref != null && ref.getLocation() != null
                && ref.getLocation().length() > 0 && ref.getXref() != null
                && ref.getXref().length() > 0;
    }

    /**
     * @return The project for the current input.
     */
    protected IProject getProject(Object input) {
        IProject project = null;
        if (input instanceof EObject) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);

            // Sometimes (like new EObject Wizard) input Eobject is not yet
            // associated with working copy. in which case we should try getting
            // working copy for input container.
            if (wc == null) {
                EObject container = ((EObject) input).eContainer();
                if (container != null) {
                    wc = WorkingCopyUtil.getWorkingCopyFor(container);
                }
            }

            if (wc != null) {
                IResource resource = wc.getEclipseResources().get(0);
                if (resource != null) {
                    project = resource.getProject();
                }
            }
        }
        return project;
    }

    /**
     * @return the complexTypesInfo
     */
    public ComplexDataTypesMergedInfo getComplexTypesInfo() {
        if (_complexTypesInfo == null) {
            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();
        }
        return _complexTypesInfo;
    }

    private String refreshComplexDataType(IProject project,
            ComplexDataTypesMergedInfo complexTypesInfo) {

        if (complexTypesInfo != null) {
            // Attempt to get the name from the complex types supported
            // by dest environments.
            String name =
                    complexTypesInfo
                            .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                    project);

            if (name != null && name.length() > 0) {
                return name;
            }

            // Not one of our supported complex data types.
            // Try and resolve the name from any complex data type
            // but prefix with <Unsupported>
            name =
                    ComplexDataTypeExtPointHelper
                            .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                    project);

            if (name != null && name.length() > 0) {
                return name;
            }

            // Try to resolve it in referenced projects
            try {
                Set<IProject> refProjects =
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                true);

                for (IProject refProject : refProjects) {
                    // Attempt to get the name from the complex types supported
                    // by dest environments.
                    name =
                            complexTypesInfo
                                    .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                            refProject);

                    if (name != null && name.length() > 0) {
                        return name;
                    }

                    // Not one of our supported complex data types.
                    // Try and resolve the name from any complex data type
                    // but prefix with <Unsupported>
                    name =
                            ComplexDataTypeExtPointHelper
                                    .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                            refProject);

                    if (name != null && name.length() > 0) {
                        return name;
                    }
                }
            } catch (CyclicDependencyException e1) {
                // Do nothing
            }
        }
        return null;
    }

    /**
     * Convert an xpdl2 External reference to a complex data type extension
     * point reference.
     * 
     * @param extRef
     * @return
     */
    private ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {

        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        // Must have at least some infop defined.
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());
        if (length == 0) {
            return null;
        }

        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

}
