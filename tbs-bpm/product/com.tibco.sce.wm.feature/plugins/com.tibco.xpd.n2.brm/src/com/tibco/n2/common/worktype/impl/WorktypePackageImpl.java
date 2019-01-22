/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.worktype.impl;

import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.brm.api.impl.N2BRMPackageImpl;

import com.tibco.n2.brm.workmodel.WorkmodelPackage;

import com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl;

import com.tibco.n2.common.api.exception.ExceptionPackage;

import com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl;

import com.tibco.n2.common.datamodel.DatamodelPackage;

import com.tibco.n2.common.datamodel.impl.DatamodelPackageImpl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;

import com.tibco.n2.common.organisation.api.impl.OrganisationPackageImpl;

import com.tibco.n2.common.pageactivitymodel.PageactivitymodelPackage;

import com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl;

import com.tibco.n2.common.worktype.DocumentRoot;
import com.tibco.n2.common.worktype.WorkType;
import com.tibco.n2.common.worktype.WorktypeFactory;
import com.tibco.n2.common.worktype.WorktypePackage;

import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.impl.DescriptorPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class WorktypePackageImpl extends EPackageImpl implements WorktypePackage {
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
    private EClass workTypeEClass = null;

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
     * @see com.tibco.n2.common.worktype.WorktypePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private WorktypePackageImpl() {
        super(eNS_URI, WorktypeFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link WorktypePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static WorktypePackage init() {
        if (isInited) return (WorktypePackage)EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI);

        // Obtain or create and register package
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof WorktypePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new WorktypePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) instanceof N2BRMPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) : N2BRMPackage.eINSTANCE);
        DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) : DatamodelPackage.eINSTANCE);
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) instanceof ExceptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) : ExceptionPackage.eINSTANCE);
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
        DescriptorPackageImpl theDescriptorPackage = (DescriptorPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) : DescriptorPackage.eINSTANCE);
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) instanceof WorkmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI) : WorkmodelPackage.eINSTANCE);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) instanceof PageactivitymodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) : PageactivitymodelPackage.eINSTANCE);

        // Create package meta-data objects
        theWorktypePackage.createPackageContents();
        theN2BRMPackage.createPackageContents();
        theDatamodelPackage.createPackageContents();
        theExceptionPackage.createPackageContents();
        theOrganisationPackage.createPackageContents();
        theDescriptorPackage.createPackageContents();
        theWorkmodelPackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();

        // Initialize created meta-data
        theWorktypePackage.initializePackageContents();
        theN2BRMPackage.initializePackageContents();
        theDatamodelPackage.initializePackageContents();
        theExceptionPackage.initializePackageContents();
        theOrganisationPackage.initializePackageContents();
        theDescriptorPackage.initializePackageContents();
        theWorkmodelPackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theWorktypePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(WorktypePackage.eNS_URI, theWorktypePackage);
        return theWorktypePackage;
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
    public EReference getDocumentRoot_WorkTypes() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkType() {
        return workTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkType_WorkType() {
        return (EReference)workTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkType_Version() {
        return (EAttribute)workTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorktypeFactory getWorktypeFactory() {
        return (WorktypeFactory)getEFactoryInstance();
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
        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__WORK_TYPES);

        workTypeEClass = createEClass(WORK_TYPE);
        createEReference(workTypeEClass, WORK_TYPE__WORK_TYPE);
        createEAttribute(workTypeEClass, WORK_TYPE__VERSION);
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
        DatamodelPackage theDatamodelPackage = (DatamodelPackage)EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI);
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_WorkTypes(), this.getWorkType(), null, "workTypes", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(workTypeEClass, WorkType.class, "WorkType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkType_WorkType(), theDatamodelPackage.getWorkType(), null, "workType", null, 1, -1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, WorkType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
          (getDocumentRoot_WorkTypes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkTypes",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (workTypeEClass, 
           source, 
           new String[] {
             "name", "WorkType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkType_WorkType(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkType",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });
    }

} //WorktypePackageImpl
