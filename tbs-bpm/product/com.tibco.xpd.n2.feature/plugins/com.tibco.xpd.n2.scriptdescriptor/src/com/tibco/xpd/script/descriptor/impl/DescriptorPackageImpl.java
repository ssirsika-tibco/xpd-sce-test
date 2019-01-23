/**
 * <copyright>

 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor.impl;

import com.tibco.xpd.script.descriptor.CacType;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.script.descriptor.DescriptorFactory;
import com.tibco.xpd.script.descriptor.DescriptorPackage;
import com.tibco.xpd.script.descriptor.DocumentRoot;
import com.tibco.xpd.script.descriptor.EnumType;
import com.tibco.xpd.script.descriptor.FactoryType;
import com.tibco.xpd.script.descriptor.ProcessType;
import com.tibco.xpd.script.descriptor.ScriptDescriptorType;
import com.tibco.xpd.script.descriptor.ScriptType;

/*
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DescriptorPackageImpl extends EPackageImpl implements DescriptorPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass cacTypeEClass = null;

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
    private EClass enumTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass factoryTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass processTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scriptDescriptorTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass scriptTypeEClass = null;

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
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DescriptorPackageImpl() {
        super(eNS_URI, DescriptorFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link DescriptorPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DescriptorPackage init() {
        if (isInited) return (DescriptorPackage)EPackage.Registry.INSTANCE.getEPackage(DescriptorPackage.eNS_URI);

        // Obtain or create and register package
        DescriptorPackageImpl theDescriptorPackage = (DescriptorPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DescriptorPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DescriptorPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theDescriptorPackage.createPackageContents();

        // Initialize created meta-data
        theDescriptorPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theDescriptorPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(DescriptorPackage.eNS_URI, theDescriptorPackage);
        return theDescriptorPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCacType() {
        return cacTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCacType_ScriptingName() {
        return (EAttribute)cacTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCacType_CanonicalClassName() {
        return (EAttribute)cacTypeEClass.getEStructuralFeatures().get(1);
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
    public EReference getDocumentRoot_Scriptdescriptor() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEnumType() {
        return enumTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumType_ScriptingName() {
        return (EAttribute)enumTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEnumType_CanonicalClassName() {
        return (EAttribute)enumTypeEClass.getEStructuralFeatures().get(1);
    }


    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFactoryType() {
        return factoryTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFactoryType_ScriptingName() {
        return (EAttribute)factoryTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFactoryType_CanonicalClassName() {
        return (EAttribute)factoryTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFactoryType_Namespace() {
        return (EAttribute)factoryTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getProcessType() {
        return processTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessType_ProcessId() {
        return (EAttribute)processTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProcessType_PackageId() {
        return (EAttribute)processTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScriptDescriptorType() {
        return scriptDescriptorTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptDescriptorType_Script() {
        return (EReference)scriptDescriptorTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScriptDescriptorType_Version() {
        return (EAttribute)scriptDescriptorTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getScriptType() {
        return scriptTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptType_Process() {
        return (EReference)scriptTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getScriptType_TaskLibraryId() {
        return (EAttribute)scriptTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptType_Factory() {
        return (EReference)scriptTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptType_Enum() {
        return (EReference)scriptTypeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getScriptType_Cac() {
        return (EReference)scriptTypeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DescriptorFactory getDescriptorFactory() {
        return (DescriptorFactory)getEFactoryInstance();
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
        cacTypeEClass = createEClass(CAC_TYPE);
        createEAttribute(cacTypeEClass, CAC_TYPE__SCRIPTING_NAME);
        createEAttribute(cacTypeEClass, CAC_TYPE__CANONICAL_CLASS_NAME);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__SCRIPTDESCRIPTOR);

        enumTypeEClass = createEClass(ENUM_TYPE);
        createEAttribute(enumTypeEClass, ENUM_TYPE__SCRIPTING_NAME);
        createEAttribute(enumTypeEClass, ENUM_TYPE__CANONICAL_CLASS_NAME);

        factoryTypeEClass = createEClass(FACTORY_TYPE);
        createEAttribute(factoryTypeEClass, FACTORY_TYPE__SCRIPTING_NAME);
        createEAttribute(factoryTypeEClass, FACTORY_TYPE__CANONICAL_CLASS_NAME);
        createEAttribute(factoryTypeEClass, FACTORY_TYPE__NAMESPACE);

        processTypeEClass = createEClass(PROCESS_TYPE);
        createEAttribute(processTypeEClass, PROCESS_TYPE__PROCESS_ID);
        createEAttribute(processTypeEClass, PROCESS_TYPE__PACKAGE_ID);

        scriptDescriptorTypeEClass = createEClass(SCRIPT_DESCRIPTOR_TYPE);
        createEReference(scriptDescriptorTypeEClass, SCRIPT_DESCRIPTOR_TYPE__SCRIPT);
        createEAttribute(scriptDescriptorTypeEClass, SCRIPT_DESCRIPTOR_TYPE__VERSION);

        scriptTypeEClass = createEClass(SCRIPT_TYPE);
        createEReference(scriptTypeEClass, SCRIPT_TYPE__PROCESS);
        createEAttribute(scriptTypeEClass, SCRIPT_TYPE__TASK_LIBRARY_ID);
        createEReference(scriptTypeEClass, SCRIPT_TYPE__FACTORY);
        createEReference(scriptTypeEClass, SCRIPT_TYPE__ENUM);
        createEReference(scriptTypeEClass, SCRIPT_TYPE__CAC);
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

        // Initialize classes and features; add operations and parameters
        initEClass(cacTypeEClass, CacType.class, "CacType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCacType_ScriptingName(), theXMLTypePackage.getString(), "scriptingName", null, 1, 1, CacType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getCacType_CanonicalClassName(), theXMLTypePackage.getString(), "canonicalClassName", null, 1, 1, CacType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getDocumentRoot_Scriptdescriptor(), this.getScriptDescriptorType(), null, "scriptdescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(enumTypeEClass, EnumType.class, "EnumType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getEnumType_ScriptingName(), theXMLTypePackage.getString(), "scriptingName", null, 0, 1, EnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getEnumType_CanonicalClassName(), theXMLTypePackage.getString(), "canonicalClassName", null, 1, 1, EnumType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(factoryTypeEClass, FactoryType.class, "FactoryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFactoryType_ScriptingName(), theXMLTypePackage.getString(), "scriptingName", null, 1, 1, FactoryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFactoryType_CanonicalClassName(), theXMLTypePackage.getString(), "canonicalClassName", null, 1, 1, FactoryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getFactoryType_Namespace(), theXMLTypePackage.getString(), "namespace", null, 1, 1, FactoryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(processTypeEClass, ProcessType.class, "ProcessType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getProcessType_ProcessId(), theXMLTypePackage.getNMTOKEN(), "processId", null, 1, 1, ProcessType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getProcessType_PackageId(), theXMLTypePackage.getNMTOKEN(), "packageId", null, 1, 1, ProcessType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scriptDescriptorTypeEClass, ScriptDescriptorType.class, "ScriptDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScriptDescriptorType_Script(), this.getScriptType(), null, "script", null, 1, -1, ScriptDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getScriptDescriptorType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, ScriptDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(scriptTypeEClass, ScriptType.class, "ScriptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getScriptType_Process(), this.getProcessType(), null, "process", null, 0, 1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getScriptType_TaskLibraryId(), theXMLTypePackage.getNMTOKEN(), "taskLibraryId", null, 0, 1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScriptType_Factory(), this.getFactoryType(), null, "factory", null, 0, -1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScriptType_Enum(), this.getEnumType(), null, "enum", null, 0, -1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getScriptType_Cac(), this.getCacType(), null, "cac", null, 0, -1, ScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
          (cacTypeEClass, 
           source, 
           new String[] {
             "name", "CacType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getCacType_ScriptingName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scriptingName"
           });			
        addAnnotation
          (getCacType_CanonicalClassName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "canonicalClassName"
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
          (getDocumentRoot_Scriptdescriptor(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scriptdescriptor",
             "namespace", "##targetNamespace"
           });			
        addAnnotation
          (enumTypeEClass, 
           source, 
           new String[] {
             "name", "EnumType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getEnumType_ScriptingName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scriptingName"
           });			
        addAnnotation
          (getEnumType_CanonicalClassName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "canonicalClassName"
           });			
        addAnnotation
          (factoryTypeEClass, 
           source, 
           new String[] {
             "name", "FactoryType",
             "kind", "elementOnly"
           });			
        addAnnotation
          (getFactoryType_ScriptingName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "scriptingName"
           });			
        addAnnotation
          (getFactoryType_CanonicalClassName(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "canonicalClassName"
           });			
        addAnnotation
          (getFactoryType_Namespace(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "namespace"
           });		
        addAnnotation
          (processTypeEClass, 
           source, 
           new String[] {
             "name", "process_._type",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getProcessType_ProcessId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "processId"
           });		
        addAnnotation
          (getProcessType_PackageId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "packageId"
           });		
        addAnnotation
          (scriptDescriptorTypeEClass, 
           source, 
           new String[] {
             "name", "ScriptDescriptorType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getScriptDescriptorType_Script(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "script"
           });		
        addAnnotation
          (getScriptDescriptorType_Version(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "version"
           });			
        addAnnotation
          (scriptTypeEClass, 
           source, 
           new String[] {
             "name", "ScriptType",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getScriptType_Process(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "process"
           });		
        addAnnotation
          (getScriptType_TaskLibraryId(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "taskLibraryId"
           });			
        addAnnotation
          (getScriptType_Factory(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "factory"
           });			
        addAnnotation
          (getScriptType_Enum(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "enum"
           });			
        addAnnotation
          (getScriptType_Cac(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "cac"
           });
    }

} //DescriptorPackageImpl
