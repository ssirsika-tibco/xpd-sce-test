/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.workmodel.impl;

import com.tibco.n2.brm.api.N2BRMPackage;

import com.tibco.n2.brm.api.impl.N2BRMPackageImpl;

import com.tibco.n2.brm.workmodel.DocumentRoot;
import com.tibco.n2.brm.workmodel.WorkModel;
import com.tibco.n2.brm.workmodel.WorkmodelFactory;
import com.tibco.n2.brm.workmodel.WorkmodelPackage;

import com.tibco.n2.common.api.exception.ExceptionPackage;

import com.tibco.n2.common.api.exception.impl.ExceptionPackageImpl;

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
public class WorkmodelPackageImpl extends EPackageImpl implements WorkmodelPackage {
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
    private EClass workModelEClass = null;

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
     * @see com.tibco.n2.brm.workmodel.WorkmodelPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private WorkmodelPackageImpl() {
        super(eNS_URI, WorkmodelFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link WorkmodelPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static WorkmodelPackage init() {
        if (isInited) return (WorkmodelPackage)EPackage.Registry.INSTANCE.getEPackage(WorkmodelPackage.eNS_URI);

        // Obtain or create and register package
        WorkmodelPackageImpl theWorkmodelPackage = (WorkmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof WorkmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new WorkmodelPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Obtain or create and register interdependencies
        N2BRMPackageImpl theN2BRMPackage = (N2BRMPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) instanceof N2BRMPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI) : N2BRMPackage.eINSTANCE);
        DatamodelPackageImpl theDatamodelPackage = (DatamodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) instanceof DatamodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DatamodelPackage.eNS_URI) : DatamodelPackage.eINSTANCE);
        ExceptionPackageImpl theExceptionPackage = (ExceptionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) instanceof ExceptionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExceptionPackage.eNS_URI) : ExceptionPackage.eINSTANCE);
        OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
        DescriptorPackageImpl theDescriptorPackage = (DescriptorPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI) : DescriptorPackage.eINSTANCE);
        WorktypePackageImpl theWorktypePackage = (WorktypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) instanceof WorktypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(WorktypePackage.eNS_URI) : WorktypePackage.eINSTANCE);
        PageactivitymodelPackageImpl thePageactivitymodelPackage = (PageactivitymodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) instanceof PageactivitymodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PageactivitymodelPackage.eNS_URI) : PageactivitymodelPackage.eINSTANCE);

        // Create package meta-data objects
        theWorkmodelPackage.createPackageContents();
        theN2BRMPackage.createPackageContents();
        theDatamodelPackage.createPackageContents();
        theExceptionPackage.createPackageContents();
        theOrganisationPackage.createPackageContents();
        theDescriptorPackage.createPackageContents();
        theWorktypePackage.createPackageContents();
        thePageactivitymodelPackage.createPackageContents();

        // Initialize created meta-data
        theWorkmodelPackage.initializePackageContents();
        theN2BRMPackage.initializePackageContents();
        theDatamodelPackage.initializePackageContents();
        theExceptionPackage.initializePackageContents();
        theOrganisationPackage.initializePackageContents();
        theDescriptorPackage.initializePackageContents();
        theWorktypePackage.initializePackageContents();
        thePageactivitymodelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theWorkmodelPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(WorkmodelPackage.eNS_URI, theWorkmodelPackage);
        return theWorkmodelPackage;
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
    public EReference getDocumentRoot_WorkModels() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getWorkModel() {
        return workModelEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getWorkModel_WorkModel() {
        return (EReference)workModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModel_OrgModelVersion() {
        return (EAttribute)workModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getWorkModel_Version() {
        return (EAttribute)workModelEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkmodelFactory getWorkmodelFactory() {
        return (WorkmodelFactory)getEFactoryInstance();
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
        createEReference(documentRootEClass, DOCUMENT_ROOT__WORK_MODELS);

        workModelEClass = createEClass(WORK_MODEL);
        createEReference(workModelEClass, WORK_MODEL__WORK_MODEL);
        createEAttribute(workModelEClass, WORK_MODEL__ORG_MODEL_VERSION);
        createEAttribute(workModelEClass, WORK_MODEL__VERSION);
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
        N2BRMPackage theN2BRMPackage = (N2BRMPackage)EPackage.Registry.INSTANCE.getEPackage(N2BRMPackage.eNS_URI);
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_WorkModels(), this.getWorkModel(), null, "workModels", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(workModelEClass, WorkModel.class, "WorkModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getWorkModel_WorkModel(), theN2BRMPackage.getWorkModel(), null, "workModel", null, 1, -1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModel_OrgModelVersion(), theXMLTypePackage.getInt(), "orgModelVersion", null, 0, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getWorkModel_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, WorkModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
          (getDocumentRoot_WorkModels(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModels",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (workModelEClass, 
           source, 
           new String[] {
             "name", "WorkModel",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getWorkModel_WorkModel(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "WorkModel",
             "namespace", "##targetNamespace"
           });		
        addAnnotation
          (getWorkModel_OrgModelVersion(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "orgModelVersion"
           });		
        addAnnotation
          (getWorkModel_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });
    }

} //WorkmodelPackageImpl
