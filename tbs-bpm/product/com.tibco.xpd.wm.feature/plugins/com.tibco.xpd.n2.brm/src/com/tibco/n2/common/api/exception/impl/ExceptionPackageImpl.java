/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.brm.api.impl.N2BRMPackageImpl;

import com.tibco.n2.brm.workmodel.WorkmodelPackage;

import com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl;

import com.tibco.n2.common.api.exception.DeploymentParameterFaultType;
import com.tibco.n2.common.api.exception.DetailedErrorLine;
import com.tibco.n2.common.api.exception.DetailedException;
import com.tibco.n2.common.api.exception.DocumentRoot;
import com.tibco.n2.common.api.exception.ErrorLine;
import com.tibco.n2.common.api.exception.ExceptionFactory;
import com.tibco.n2.common.api.exception.ExceptionPackage;
import com.tibco.n2.common.api.exception.InternalServiceFaultType;
import com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType;
import com.tibco.n2.common.api.exception.SeverityType;
import com.tibco.n2.common.api.exception.WorkTypeFaultType;

import com.tibco.n2.common.datamodel.DatamodelPackage;

import com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;

import com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl;

import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

import com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl;

import com.tibco.n2.common.worktype.WorktypePackage;

import com.tibco.n2.common.worktype.impl.WorktypePackageImpl;

import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExceptionPackageImpl extends EPackageImpl implements ExceptionPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass deploymentParameterFaultTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass detailedErrorLineEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass detailedExceptionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass errorLineEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass internalServiceFaultTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass invalidWorkTypeFaultTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass workTypeFaultTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum severityTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType severityTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.n2.common.api.exception.ExceptionPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ExceptionPackageImpl() {
        super(eNS_URI, ExceptionFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link ExceptionPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ExceptionPackage init() {
        if (isInited) return (ExceptionPackage)EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI);

        // Obtain or create and register package
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ExceptionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ExceptionPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) instanceof N2BRMPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) : N2BRMPackage.eINSTANCE);
        DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) : DatamodelPackage.eINSTANCE);
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
        DescriptorPackageImpl theDescriptorPackage = (DescriptorPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) : DescriptorPackage.eINSTANCE);
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) instanceof WorkmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) : WorkmodelPackage.eINSTANCE);
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) instanceof WorktypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) : WorktypePackage.eINSTANCE);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) instanceof PageactivitymodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) : PageactivitymodelPackage.eINSTANCE);

        // Create package meta-data objects
        theExceptionPackage.createPackageContents();
        theN2BRMPackage.createPackageContents();
        theDatamodelPackage.createPackageContents();
        theOrganisationPackage.createPackageContents();
        theDescriptorPackage.createPackageContents();
        theWorkmodelPackage.createPackageContents();
        theWorktypePackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();

        // Initialize created meta-data
        theExceptionPackage.initializePackageContents();
        theN2BRMPackage.initializePackageContents();
        theDatamodelPackage.initializePackageContents();
        theOrganisationPackage.initializePackageContents();
        theDescriptorPackage.initializePackageContents();
        theWorkmodelPackage.initializePackageContents();
        theWorktypePackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theExceptionPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ExceptionPackage.eNS_URI, theExceptionPackage);
        return theExceptionPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDeploymentParameterFaultType() {
        return deploymentParameterFaultTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDeploymentParameterFaultType_Error() {
        return (EReference)deploymentParameterFaultTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDetailedErrorLine() {
        return detailedErrorLineEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDetailedErrorLine_ColumnNumber() {
        return (EAttribute)detailedErrorLineEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDetailedErrorLine_LineNumber() {
        return (EAttribute)detailedErrorLineEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDetailedErrorLine_Severity() {
        return (EAttribute)detailedErrorLineEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDetailedException() {
        return detailedExceptionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDetailedException_Error() {
        return (EReference)detailedExceptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getErrorLine() {
        return errorLineEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorLine_Parameter() {
        return (EAttribute)errorLineEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorLine_Code() {
        return (EAttribute)errorLineEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorLine_Message() {
        return (EAttribute)errorLineEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_DeploymentParameterFault() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_InternalServiceFault() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_InvalidWorkTypeFault() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_WorkTypeFault() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getInternalServiceFaultType() {
        return internalServiceFaultTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getInternalServiceFaultType_Error() {
        return (EReference)internalServiceFaultTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getInvalidWorkTypeFaultType() {
        return invalidWorkTypeFaultTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getInvalidWorkTypeFaultType_Error() {
        return (EReference)invalidWorkTypeFaultTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkTypeFaultType() {
        return workTypeFaultTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkTypeFaultType_Error() {
        return (EReference)workTypeFaultTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSeverityType() {
        return severityTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSeverityTypeObject() {
        return severityTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExceptionFactory getExceptionFactory() {
        return (ExceptionFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        deploymentParameterFaultTypeEClass = createEClass(DEPLOYMENT_PARAMETER_FAULT_TYPE);
        createEReference(deploymentParameterFaultTypeEClass, DEPLOYMENT_PARAMETER_FAULT_TYPE__ERROR);

        detailedErrorLineEClass = createEClass(DETAILED_ERROR_LINE);
        createEAttribute(detailedErrorLineEClass, DETAILED_ERROR_LINE__COLUMN_NUMBER);
        createEAttribute(detailedErrorLineEClass, DETAILED_ERROR_LINE__LINE_NUMBER);
        createEAttribute(detailedErrorLineEClass, DETAILED_ERROR_LINE__SEVERITY);

        detailedExceptionEClass = createEClass(DETAILED_EXCEPTION);
        createEReference(detailedExceptionEClass, DETAILED_EXCEPTION__ERROR);

        errorLineEClass = createEClass(ERROR_LINE);
        createEAttribute(errorLineEClass, ERROR_LINE__PARAMETER);
        createEAttribute(errorLineEClass, ERROR_LINE__CODE);
        createEAttribute(errorLineEClass, ERROR_LINE__MESSAGE);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT);
        createEReference(documentRootEClass, DOCUMENT_ROOT__WORK_TYPE_FAULT);

        internalServiceFaultTypeEClass = createEClass(INTERNAL_SERVICE_FAULT_TYPE);
        createEReference(internalServiceFaultTypeEClass, INTERNAL_SERVICE_FAULT_TYPE__ERROR);

        invalidWorkTypeFaultTypeEClass = createEClass(INVALID_WORK_TYPE_FAULT_TYPE);
        createEReference(invalidWorkTypeFaultTypeEClass, INVALID_WORK_TYPE_FAULT_TYPE__ERROR);

        workTypeFaultTypeEClass = createEClass(WORK_TYPE_FAULT_TYPE);
        createEReference(workTypeFaultTypeEClass, WORK_TYPE_FAULT_TYPE__ERROR);

        // Create enums
        severityTypeEEnum = createEEnum(SEVERITY_TYPE);

        // Create data types
        severityTypeObjectEDataType = createEDataType(SEVERITY_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        detailedErrorLineEClass.getESuperTypes().add(this.getErrorLine());

        // Initialize classes and features; add operations and parameters
        initEClass(deploymentParameterFaultTypeEClass, DeploymentParameterFaultType.class, "DeploymentParameterFaultType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDeploymentParameterFaultType_Error(), this.getErrorLine(), null, "error", null, 1, -1, DeploymentParameterFaultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(detailedErrorLineEClass, DetailedErrorLine.class, "DetailedErrorLine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDetailedErrorLine_ColumnNumber(), theXMLTypePackage.getInt(), "columnNumber", null, 0, 1, DetailedErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDetailedErrorLine_LineNumber(), theXMLTypePackage.getInt(), "lineNumber", null, 0, 1, DetailedErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getDetailedErrorLine_Severity(), this.getSeverityType(), "severity", null, 1, 1, DetailedErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(detailedExceptionEClass, DetailedException.class, "DetailedException", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDetailedException_Error(), this.getErrorLine(), null, "error", null, 0, -1, DetailedException.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(errorLineEClass, ErrorLine.class, "ErrorLine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getErrorLine_Parameter(), theXMLTypePackage.getString(), "parameter", null, 0, -1, ErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorLine_Code(), theXMLTypePackage.getString(), "code", null, 1, 1, ErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorLine_Message(), theXMLTypePackage.getString(), "message", null, 1, 1, ErrorLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_DeploymentParameterFault(), this.getDeploymentParameterFaultType(), null, "deploymentParameterFault", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_InternalServiceFault(), this.getInternalServiceFaultType(), null, "internalServiceFault", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_InvalidWorkTypeFault(), this.getInvalidWorkTypeFaultType(), null, "invalidWorkTypeFault", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_WorkTypeFault(), this.getWorkTypeFaultType(), null, "workTypeFault", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(internalServiceFaultTypeEClass, InternalServiceFaultType.class, "InternalServiceFaultType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getInternalServiceFaultType_Error(), this.getErrorLine(), null, "error", null, 1, 1, InternalServiceFaultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(invalidWorkTypeFaultTypeEClass, InvalidWorkTypeFaultType.class, "InvalidWorkTypeFaultType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getInvalidWorkTypeFaultType_Error(), this.getErrorLine(), null, "error", null, 1, 1, InvalidWorkTypeFaultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(workTypeFaultTypeEClass, WorkTypeFaultType.class, "WorkTypeFaultType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkTypeFaultType_Error(), this.getErrorLine(), null, "error", null, 1, 1, WorkTypeFaultType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(severityTypeEEnum, SeverityType.class, "SeverityType");
        addEEnumLiteral(severityTypeEEnum, SeverityType.WARNING);
        addEEnumLiteral(severityTypeEEnum, SeverityType.ERROR);
        addEEnumLiteral(severityTypeEEnum, SeverityType.INFORMATION);

        // Initialize data types
        initEDataType(severityTypeObjectEDataType, SeverityType.class, "SeverityTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (deploymentParameterFaultTypeEClass, 
           source, 
           new String[] {
             "name", "DeploymentParameterFault_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDeploymentParameterFaultType_Error(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "error"
           });			
        addAnnotation
          (detailedErrorLineEClass, 
           source, 
           new String[] {
             "name", "DetailedErrorLine",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getDetailedErrorLine_ColumnNumber(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "columnNumber"
           });			
        addAnnotation
          (getDetailedErrorLine_LineNumber(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "lineNumber"
           });			
        addAnnotation
          (getDetailedErrorLine_Severity(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "severity"
           });		
        addAnnotation
          (detailedExceptionEClass, 
           source, 
           new String[] {
             "name", "DetailedException",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getDetailedException_Error(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "error"
           });			
        addAnnotation
          (errorLineEClass, 
           source, 
           new String[] {
             "name", "ErrorLine",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getErrorLine_Parameter(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "parameter"
           });			
        addAnnotation
          (getErrorLine_Code(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "code"
           });			
        addAnnotation
          (getErrorLine_Message(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "message"
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "",
             "kind", "mixed"
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard",
             "name", ":mixed"
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xmlns:prefix"
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "xsi:schemaLocation"
           });		
        addAnnotation
          (getDocumentRoot_DeploymentParameterFault(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "DeploymentParameterFault",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getDocumentRoot_InternalServiceFault(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "InternalServiceFault",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getDocumentRoot_InvalidWorkTypeFault(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "InvalidWorkTypeFault",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getDocumentRoot_WorkTypeFault(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkTypeFault",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (internalServiceFaultTypeEClass, 
           source, 
           new String[] {
             "name", "InternalServiceFault_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getInternalServiceFaultType_Error(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "error"
           });		
        addAnnotation
          (invalidWorkTypeFaultTypeEClass, 
           source, 
           new String[] {
             "name", "InvalidWorkTypeFault_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getInvalidWorkTypeFaultType_Error(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "error"
           });		
        addAnnotation
          (severityTypeEEnum, 
           source, 
           new String[] {
             "name", "severity_._type"
           });		
        addAnnotation
          (severityTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "severity_._type:Object",
             "baseType", "severity_._type"
           });		
        addAnnotation
          (workTypeFaultTypeEClass, 
           source, 
           new String[] {
             "name", "WorkTypeFault_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkTypeFaultType_Error(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "error"
           });
    }

} //ExceptionPackageImpl
