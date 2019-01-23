/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadeFactory;
import com.tibco.xpd.worklistfacade.model.WorkListFacadePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class WorkListFacadePackageImpl extends EPackageImpl implements
        WorkListFacadePackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass documentRootEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass workItemAttributeEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass workItemAttributesEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private EClass workListFacadeEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
     * package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory
     * method {@link #init init()}, which also performs initialization of the
     * package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private WorkListFacadePackageImpl() {
        super(eNS_URI, WorkListFacadeFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model,
     * and for any others upon which it depends.
     * 
     * <p>
     * This method is used to initialize {@link WorkListFacadePackage#eINSTANCE}
     * when that field is accessed. Clients should not invoke it directly.
     * Instead, they should simply access that field to obtain the package. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static WorkListFacadePackage init() {
        if (isInited)
            return (WorkListFacadePackage) EPackage.Registry.INSTANCE
                    .getEPackage(WorkListFacadePackage.eNS_URI);

        // Obtain or create and register package
        WorkListFacadePackageImpl theWorkListFacadePackage =
                (WorkListFacadePackageImpl) (EPackage.Registry.INSTANCE
                        .get(eNS_URI) instanceof WorkListFacadePackageImpl ? EPackage.Registry.INSTANCE
                        .get(eNS_URI) : new WorkListFacadePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theWorkListFacadePackage.createPackageContents();

        // Initialize created meta-data
        theWorkListFacadePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theWorkListFacadePackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(WorkListFacadePackage.eNS_URI,
                theWorkListFacadePackage);
        return theWorkListFacadePackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getDocumentRoot() {
        return documentRootEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getDocumentRoot_Mixed() {
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getDocumentRoot_WorkListFacade() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getWorkItemAttribute() {
        return workItemAttributeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getWorkItemAttribute_DisplayLabel() {
        return (EAttribute) workItemAttributeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getWorkItemAttribute_Name() {
        return (EAttribute) workItemAttributeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getWorkItemAttributes() {
        return workItemAttributesEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getWorkItemAttributes_WorkItemAttribute() {
        return (EReference) workItemAttributesEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EClass getWorkListFacade() {
        return workListFacadeEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EReference getWorkListFacade_WorkItemAttributes() {
        return (EReference) workListFacadeEClass.getEStructuralFeatures()
                .get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EAttribute getWorkListFacade_FormatVersion() {
        return (EAttribute) workListFacadeEClass.getEStructuralFeatures()
                .get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public WorkListFacadeFactory getWorkListFacadeFactory() {
        return (WorkListFacadeFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to
     * have no affect on any invocation but its first. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__WORK_LIST_FACADE);

        workItemAttributeEClass = createEClass(WORK_ITEM_ATTRIBUTE);
        createEAttribute(workItemAttributeEClass,
                WORK_ITEM_ATTRIBUTE__DISPLAY_LABEL);
        createEAttribute(workItemAttributeEClass, WORK_ITEM_ATTRIBUTE__NAME);

        workItemAttributesEClass = createEClass(WORK_ITEM_ATTRIBUTES);
        createEReference(workItemAttributesEClass,
                WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE);

        workListFacadeEClass = createEClass(WORK_LIST_FACADE);
        createEReference(workListFacadeEClass,
                WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES);
        createEAttribute(workListFacadeEClass, WORK_LIST_FACADE__FORMAT_VERSION);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This
     * method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE
                        .getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getDocumentRoot_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_WorkListFacade(),
                this.getWorkListFacade(),
                null,
                "workListFacade", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(workItemAttributeEClass,
                WorkItemAttribute.class,
                "WorkItemAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getWorkItemAttribute_DisplayLabel(),
                theXMLTypePackage.getString(),
                "displayLabel", null, 1, 1, WorkItemAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getWorkItemAttribute_Name(),
                theXMLTypePackage.getString(),
                "name", null, 1, 1, WorkItemAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(workItemAttributesEClass,
                WorkItemAttributes.class,
                "WorkItemAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getWorkItemAttributes_WorkItemAttribute(),
                this.getWorkItemAttribute(),
                null,
                "workItemAttribute", null, 0, -1, WorkItemAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(workListFacadeEClass,
                WorkListFacade.class,
                "WorkListFacade", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getWorkListFacade_WorkItemAttributes(),
                this.getWorkItemAttributes(),
                null,
                "workItemAttributes", null, 1, 1, WorkListFacade.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getWorkListFacade_FormatVersion(),
                theXMLTypePackage.getInteger(),
                "formatVersion", null, 0, 1, WorkListFacade.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for
     * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation(documentRootEClass, source, new String[] { "name", "", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_Mixed(), source, new String[] {
                "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
                "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getDocumentRoot_WorkListFacade(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "WorkListFacade", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(workItemAttributeEClass, source, new String[] {
                "name", "WorkItemAttribute", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getWorkItemAttribute_DisplayLabel(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "DisplayLabel", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWorkItemAttribute_Name(), source, new String[] {
                "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "Name", //$NON-NLS-1$ //$NON-NLS-2$
                "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(workItemAttributesEClass, source, new String[] {
                "name", "WorkItemAttributes", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getWorkItemAttributes_WorkItemAttribute(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WorkItemAttribute", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(workListFacadeEClass, source, new String[] {
                "name", "WorkListFacade", //$NON-NLS-1$ //$NON-NLS-2$
                "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
        });
        addAnnotation(getWorkListFacade_WorkItemAttributes(),
                source,
                new String[] { "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
                        "name", "WorkItemAttributes", //$NON-NLS-1$ //$NON-NLS-2$
                        "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
                });
        addAnnotation(getWorkListFacade_FormatVersion(), source, new String[] {
                "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
                "name", "FormatVersion" //$NON-NLS-1$ //$NON-NLS-2$
        });
    }

} // WorkListFacadePackageImpl
