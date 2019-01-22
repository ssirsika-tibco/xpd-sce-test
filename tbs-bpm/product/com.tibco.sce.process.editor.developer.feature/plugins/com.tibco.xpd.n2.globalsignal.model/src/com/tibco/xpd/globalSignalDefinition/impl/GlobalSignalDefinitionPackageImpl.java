/**
 */
package com.tibco.xpd.globalSignalDefinition.impl;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalSignalDefinitionPackageImpl extends EPackageImpl implements GlobalSignalDefinitionPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass globalSignalEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass globalSignalDefinitionsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass payloadDataFieldEClass = null;

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
     * @see com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private GlobalSignalDefinitionPackageImpl() {
        super(eNS_URI, GlobalSignalDefinitionFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link GlobalSignalDefinitionPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static GlobalSignalDefinitionPackage init() {
        if (isInited) return (GlobalSignalDefinitionPackage)EPackage.Registry.INSTANCE.getEPackage(GlobalSignalDefinitionPackage.eNS_URI);

        // Obtain or create and register package
        GlobalSignalDefinitionPackageImpl theGlobalSignalDefinitionPackage = (GlobalSignalDefinitionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof GlobalSignalDefinitionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new GlobalSignalDefinitionPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        EcorePackage.eINSTANCE.eClass();
        Xpdl2Package.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theGlobalSignalDefinitionPackage.createPackageContents();

        // Initialize created meta-data
        theGlobalSignalDefinitionPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theGlobalSignalDefinitionPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(GlobalSignalDefinitionPackage.eNS_URI, theGlobalSignalDefinitionPackage);
        return theGlobalSignalDefinitionPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGlobalSignal() {
        return globalSignalEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGlobalSignal_PayloadDataFields() {
        return (EReference)globalSignalEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignal_CorrelationTimeout() {
        return (EAttribute)globalSignalEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignal_Description() {
        return (EAttribute)globalSignalEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignal_Name() {
        return (EAttribute)globalSignalEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getGlobalSignalDefinitions() {
        return globalSignalDefinitionsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGlobalSignalDefinitions_GlobalSignals() {
        return (EReference)globalSignalDefinitionsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignalDefinitions_Description() {
        return (EAttribute)globalSignalDefinitionsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignalDefinitions_FormatVersion() {
        return (EAttribute)globalSignalDefinitionsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getGlobalSignalDefinitions_Mixed() {
        return (EAttribute)globalSignalDefinitionsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGlobalSignalDefinitions_XMLNSPrefixMap() {
        return (EReference)globalSignalDefinitionsEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getGlobalSignalDefinitions_XSISchemaLocation() {
        return (EReference)globalSignalDefinitionsEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPayloadDataField() {
        return payloadDataFieldEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPayloadDataField_Mandatory() {
        return (EAttribute)payloadDataFieldEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionFactory getGlobalSignalDefinitionFactory() {
        return (GlobalSignalDefinitionFactory)getEFactoryInstance();
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
        globalSignalEClass = createEClass(GLOBAL_SIGNAL);
        createEReference(globalSignalEClass, GLOBAL_SIGNAL__PAYLOAD_DATA_FIELDS);
        createEAttribute(globalSignalEClass, GLOBAL_SIGNAL__CORRELATION_TIMEOUT);
        createEAttribute(globalSignalEClass, GLOBAL_SIGNAL__DESCRIPTION);
        createEAttribute(globalSignalEClass, GLOBAL_SIGNAL__NAME);

        globalSignalDefinitionsEClass = createEClass(GLOBAL_SIGNAL_DEFINITIONS);
        createEAttribute(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION);
        createEReference(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS);
        createEAttribute(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION);
        createEAttribute(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__MIXED);
        createEReference(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP);
        createEReference(globalSignalDefinitionsEClass, GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION);

        payloadDataFieldEClass = createEClass(PAYLOAD_DATA_FIELD);
        createEAttribute(payloadDataFieldEClass, PAYLOAD_DATA_FIELD__MANDATORY);
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
        Xpdl2Package theXpdl2Package = (Xpdl2Package)EPackage.Registry.INSTANCE.getEPackage(Xpdl2Package.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        payloadDataFieldEClass.getESuperTypes().add(theXpdl2Package.getDataField());

        // Initialize classes and features; add operations and parameters
        initEClass(globalSignalEClass, GlobalSignal.class, "GlobalSignal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getGlobalSignal_PayloadDataFields(), this.getPayloadDataField(), null, "payloadDataFields", null, 0, -1, GlobalSignal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getGlobalSignal_CorrelationTimeout(), theXMLTypePackage.getInteger(), "correlationTimeout", null, 0, 1, GlobalSignal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getGlobalSignal_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, GlobalSignal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getGlobalSignal_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, GlobalSignal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(globalSignalDefinitionsEClass, GlobalSignalDefinitions.class, "GlobalSignalDefinitions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getGlobalSignalDefinitions_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, GlobalSignalDefinitions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getGlobalSignalDefinitions_GlobalSignals(), this.getGlobalSignal(), null, "globalSignals", null, 0, -1, GlobalSignalDefinitions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getGlobalSignalDefinitions_FormatVersion(), theXMLTypePackage.getInteger(), "formatVersion", null, 0, 1, GlobalSignalDefinitions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getGlobalSignalDefinitions_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, GlobalSignalDefinitions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getGlobalSignalDefinitions_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, GlobalSignalDefinitions.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getGlobalSignalDefinitions_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, GlobalSignalDefinitions.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(payloadDataFieldEClass, PayloadDataField.class, "PayloadDataField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getPayloadDataField_Mandatory(), theXMLTypePackage.getBoolean(), "mandatory", null, 0, 1, PayloadDataField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData"; //$NON-NLS-1$		
        addAnnotation
          (this, 
           source, 
           new String[] {
             "name", "", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (globalSignalEClass, 
           source, 
           new String[] {
             "name", "GlobalSignal", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignal_PayloadDataFields(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "PayloadDataField", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
             "wrap", "PayloadDataFields" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (getGlobalSignal_CorrelationTimeout(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "CorrelationTimeout" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignal_Description(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Description", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignal_Name(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Name" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (globalSignalDefinitionsEClass, 
           source, 
           new String[] {
             "name", "GlobalSignalDefinitions", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_Description(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Description", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_GlobalSignals(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "GlobalSignal", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace", //$NON-NLS-1$ //$NON-NLS-2$
             "wrap", "GlobalSignals" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_FormatVersion(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "FormatVersion" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
             "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getGlobalSignalDefinitions_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (payloadDataFieldEClass, 
           source, 
           new String[] {
             "name", "PayloadDataField", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getPayloadDataField_Mandatory(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Mandatory" //$NON-NLS-1$ //$NON-NLS-2$
           });
    }

} //GlobalSignalDefinitionPackageImpl
