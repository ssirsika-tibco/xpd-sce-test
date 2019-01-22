/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.internal.impl.UMLPackageImpl;

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeExtPointHelper;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypesMergedInfo;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Member;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Rules specific to payload data.
 * 
 * @author sajain
 * @since Feb 26, 2015
 */
public class PayloadDataRule implements IValidationRule {

    /**
     * Complex data types info.
     */
    private ComplexDataTypesMergedInfo _complexTypesInfo = null;

    /**
     * Complex data type references.
     */
    private ComplexDataTypeReference complexDataTypeRef = null;

    /**
     * Unresolved complex data for type to be used in additional info.
     */
    private static final String UNRESOLVEDCOMPLEXDATAFOR_TYPE =
            "UNRESOLVEDCOMPLEXDATAFOR_TYPE"; //$NON-NLS-1$

    /**
     * Global Signal: Incorrect namespace on external data reference.
     */
    private static final String INCORRECT_NAMESPACE_ON_EXT_REF =
            "gsd.incorrectNamespaceOnExtRef"; //$NON-NLS-1$

    /**
     * Payload Data name cannot be empty.
     */
    private static final String PAYLOAD_DATA_NAME_ISSUE =
            "gsd.payloadDataNameCannotBeEmpty"; //$NON-NLS-1$

    /**
     * Name '%1$s' can contain only alpha-numeric and underscore characters and
     * cannot have leading numerics.
     */
    private static final String INVALID_PAYLOAD_NAME = "gsd.invalidNameError"; //$NON-NLS-1$

    /**
     * Global Signal: Unresolved business object model data type reference
     * (location: '%1$s' ref: '%2$s').
     */
    private static final String UNRESOLVED_DATATYPE_REFERENCE =
            "gsd.unresolvedDataExternalReference"; //$NON-NLS-1$ 

    /**
     * Global Signal: Unresolved generated BOM data type reference (generated
     * type reference identification changed, ref: %2$s).
     */
    private static final String UNRESOLVED_DATATYPE_REFERENCE_FOR__TYPE =
            "gsd.unresolvedDataExternalReferenceFor_Type"; //$NON-NLS-1$ 

    /**
     * Global Signal: The reference to business object model data type has not
     * been set.
     */
    private static final String UNSET_DATATYPE_REFERENCE =
            "gsd.unsetDataExternalReference"; //$NON-NLS-1$

    /**
     * Correlation Data must be a non-array basic type.
     */
    private static final String CORRELATION_FIELD_BASIC_NON_ARRAY_ISSUE =
            "gsd.correlationDataMustBeNonArrayBasicType"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return PayloadDataField.class;
    }

    /**
     * Default constructor.
     */
    public PayloadDataRule() {

    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof PayloadDataField) {

            PayloadDataField pdf = (PayloadDataField) o;

            /*
             * Payload Data name cannot be empty.
             */
            if (null == pdf.getName() || pdf.getName().isEmpty()) {

                addIssue(PAYLOAD_DATA_NAME_ISSUE, scope, pdf, null);

            } else {

                /*
                 * Name '%1$s' can contain only alpha-numeric and underscore
                 * characters and cannot have leading numerics.
                 */

                String name = pdf.getName();

                if (!NameUtil.isValidName(name, false)) {

                    List<String> messages = new ArrayList<String>();

                    messages.add(name);

                    addIssue(INVALID_PAYLOAD_NAME, pdf, messages, null, scope);
                }

            }

            /*
             * Correlation Data must be a non-array basic type.
             */
            validateSignalForTimeout(scope, pdf);

            /*
             * Validate payload data for broken BOM/Case data references.
             */
            validateBrokenReferences(scope, pdf);

        }
    }

    /**
     * Validate Payload Data against broken BOM/Case data references.
     * 
     * @param scope
     * @param pdf
     */
    private void validateBrokenReferences(IValidationScope scope,
            PayloadDataField pdf) {

        DataType dataType = pdf.getDataType();

        if (dataType != null) {

            if (dataType instanceof RecordType) {

                RecordType recordType = (RecordType) dataType;

                Member member = recordType.getMember().get(0);

                ExternalReference externalReference =
                        member.getExternalReference();

                if (null != externalReference) {

                    validateExternalReference(scope, pdf, externalReference);
                }
            }

            if (dataType != null
                    && dataType.eClass() != null
                    && Xpdl2Package.EXTERNAL_REFERENCE == dataType.eClass()
                            .getClassifierID()) {

                ExternalReference extRef = (ExternalReference) dataType;

                validateExternalReference(scope, pdf, extRef);
            }
        }

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
     * Return complex types info.
     * 
     * @return The complexTypesInfo
     */
    public ComplexDataTypesMergedInfo getComplexTypesInfo() {

        if (_complexTypesInfo == null) {

            _complexTypesInfo =
                    ComplexDataTypeExtPointHelper
                            .getAllComplexDataTypesMergedInfo();

        }

        return _complexTypesInfo;
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
    private void checkNamespaceValidity(IValidationScope scope,
            EObject validateItem, ExternalReference externalRef,
            IProject project) {

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

                            /*
                             * Raise an issue if namespaces do not match.
                             */

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
                                    additionalInfo,
                                    scope);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate external references.
     * 
     * @param validateItem
     * @param parent
     * @param name
     * @param extRef
     */
    private void validateExternalReference(IValidationScope scope,
            EObject validateItem, ExternalReference extRef) {

        if (isExternalReferenceSet(extRef)) {

            ComplexDataTypesMergedInfo complexTypesInfo = getComplexTypesInfo();

            if (complexTypesInfo != null) {

                complexDataTypeRef = xpdl2RefToComplexDataTypeRef(extRef);

                if (complexDataTypeRef != null) {

                    IProject project = getProject(validateItem);

                    if (project != null) {

                        /*
                         * Check if the namespace of extRef matches the
                         * namespace of BOM it references
                         */

                        checkNamespaceValidity(scope,
                                validateItem,
                                extRef,
                                project);

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
                                        addInfo,
                                        scope);

                            } else {

                                List<String> msgs = new ArrayList<String>();
                                String location = extRef.getLocation();
                                msgs.add(location != null ? location : ""); //$NON-NLS-1$
                                String xref = extRef.getXref();
                                msgs.add(xref != null ? xref : ""); //$NON-NLS-1$

                                addIssue(UNRESOLVED_DATATYPE_REFERENCE,
                                        validateItem,
                                        msgs,
                                        null,
                                        scope);
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
                                msgs,
                                null,
                                scope);
                    }
                }
            }

        } else {

            /*
             * External reference value has not been set.
             */

            List<String> msgs = new ArrayList<String>();

            msgs.add(((PayloadDataField) validateItem).getName());

            addIssue(UNSET_DATATYPE_REFERENCE, validateItem, msgs, null, scope);
        }
    }

    /**
     * Return the project for the current input.
     * 
     * @return Project for the current input.
     */
    protected IProject getProject(Object input) {

        IProject project = null;

        if (input instanceof EObject) {

            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor((EObject) input);

            /*
             * If working copy turns out to be null, then fetch the working copy
             * of the container.
             */
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

    /***
     * Get the new generated bom type reference name after removing _type if
     * found, <code>null</code> otherwise.
     * 
     * @param project
     * @param complexTypesInfo
     * 
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
     * 
     * @param project
     * @param complexTypesInfo
     * @return
     */
    private String refreshComplexDataType(IProject project,
            ComplexDataTypesMergedInfo complexTypesInfo) {

        if (complexTypesInfo != null) {

            /*
             * Attempt to get the name from the complex types supported by dest
             * environments.
             */
            String name =
                    complexTypesInfo
                            .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                    project);

            if (name != null && name.length() > 0) {

                return name;
            }

            /*
             * Not one of our supported complex data types. Try and resolve the
             * name from any complex data type but prefix with <Unsupported>
             */
            name =
                    ComplexDataTypeExtPointHelper
                            .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                    project);

            if (name != null && name.length() > 0) {

                return name;
            }

            /*
             * Try to resolve it in referenced projects
             */
            try {
                Set<IProject> refProjects =
                        ProjectUtil2.getReferencedProjectsHierarchy(project,
                                true);

                for (IProject refProject : refProjects) {

                    /*
                     * Attempt to get the name from the complex types supported
                     * by dest environments.
                     */
                    name =
                            complexTypesInfo
                                    .getComplexDataTypeDescriptiveName(complexDataTypeRef,
                                            refProject);

                    if (name != null && name.length() > 0) {

                        return name;
                    }

                    /*
                     * Not one of our supported complex data types. Try and
                     * resolve the name from any complex data type but prefix
                     * with <Unsupported>
                     */
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
     * @return Converted Complext Data Type Reference.
     */
    private ComplexDataTypeReference xpdl2RefToComplexDataTypeRef(
            ExternalReference extRef) {

        String nameSpace = extRef.getNamespace();
        String location = extRef.getLocation();
        String xRef = extRef.getXref();

        /*
         * Must have at least some info defined.
         */
        int length = (nameSpace == null ? 0 : nameSpace.length());
        length += (location == null ? 0 : location.length());
        length += (xRef == null ? 0 : xRef.length());

        if (length == 0) {
            return null;
        }

        return new ComplexDataTypeReference(location, xRef, nameSpace);
    }

    /**
     * Correlation Data must be a non-array basic type.
     * 
     * @param scope
     * @param pdf
     */
    private void validateSignalForTimeout(IValidationScope scope,
            PayloadDataField pdf) {

        if (pdf.isCorrelation()) {

            if (pdf.isIsArray()) {

                addIssue(CORRELATION_FIELD_BASIC_NON_ARRAY_ISSUE,
                        scope,
                        pdf,
                        null);
            }

            DataType dataType = pdf.getDataType();

            if (dataType instanceof BasicType) {

                BasicTypeType basicTypeType = ((BasicType) dataType).getType();

                if (basicTypeType == BasicTypeType.REFERENCE_LITERAL) {

                    addIssue(CORRELATION_FIELD_BASIC_NON_ARRAY_ISSUE,
                            scope,
                            pdf,
                            null);

                }
            } else {

                addIssue(CORRELATION_FIELD_BASIC_NON_ARRAY_ISSUE,
                        scope,
                        pdf,
                        null);
            }
        }
    }

    /**
     * Creates Issue of the given Id for the {@link PayloadDataField}.
     * 
     * @param issueId
     * @param scope
     * @param pdf
     * @param label
     */
    private void addIssue(String issueId, IValidationScope scope,
            PayloadDataField pdf, String label) {

        Map<String, String> additionalInfo = new HashMap<String, String>();

        additionalInfo.put(issueId, label);

        scope.createIssue(issueId,
                label,
                pdf.eResource().getURIFragment(pdf),
                Collections.singletonList(label),
                additionalInfo);
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     * @param messages
     *            Messages to use in formatting error message text.
     * @param additionalInfo
     *            Additional info map.
     */
    protected void addIssue(String id, EObject o, List<String> messages,
            Map<String, String> additionalInfo, IValidationScope scope) {

        Resource resource = o.eResource();

        if (resource != null) {

            String uri = resource.getURIFragment(o);

            scope.createIssue(id, null, uri, messages, additionalInfo);

        }
    }

}