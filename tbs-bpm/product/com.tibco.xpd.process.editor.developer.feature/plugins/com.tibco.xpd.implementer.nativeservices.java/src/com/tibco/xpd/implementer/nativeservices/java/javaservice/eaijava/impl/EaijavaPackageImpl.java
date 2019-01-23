/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType;

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
public class EaijavaPackageImpl extends EPackageImpl implements EaijavaPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass classTypeEClass = null;

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
    private EClass eaiJavaEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass methodTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parametersTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass parameterTypeEClass = null;

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
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private EaijavaPackageImpl() {
        super(eNS_URI, EaijavaFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static EaijavaPackage init() {
        if (isInited) return (EaijavaPackage)EPackage.Registry.INSTANCE.getEPackage(EaijavaPackage.eNS_URI);

        // Obtain or create and register package
        EaijavaPackageImpl theEaijavaPackage = (EaijavaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof EaijavaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new EaijavaPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theEaijavaPackage.createPackageContents();

        // Initialize created meta-data
        theEaijavaPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theEaijavaPackage.freeze();

        return theEaijavaPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getClassType() {
        return classTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getClassType_Method() {
        return (EReference)classTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getClassType_Name() {
        return (EAttribute)classTypeEClass.getEStructuralFeatures().get(1);
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
    public EReference getDocumentRoot_EAIJava() {
        return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getEAIJava() {
        return eaiJavaEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEAIJava_Pojo() {
        return (EReference)eaiJavaEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getEAIJava_Factory() {
        return (EReference)eaiJavaEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEAIJava_Project() {
        return (EAttribute)eaiJavaEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMethodType() {
        return methodTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMethodType_Return() {
        return (EReference)methodTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMethodType_Parameters() {
        return (EReference)methodTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMethodType_Name() {
        return (EAttribute)methodTypeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParametersType() {
        return parametersTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getParametersType_Parameter() {
        return (EReference)parametersTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getParameterType() {
        return parameterTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_ClassName() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getParameterType_Signature() {
        return (EAttribute)parameterTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EaijavaFactory getEaijavaFactory() {
        return (EaijavaFactory)getEFactoryInstance();
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
        classTypeEClass = createEClass(CLASS_TYPE);
        createEReference(classTypeEClass, CLASS_TYPE__METHOD);
        createEAttribute(classTypeEClass, CLASS_TYPE__NAME);

        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__EAI_JAVA);

        eaiJavaEClass = createEClass(EAI_JAVA);
        createEReference(eaiJavaEClass, EAI_JAVA__POJO);
        createEReference(eaiJavaEClass, EAI_JAVA__FACTORY);
        createEAttribute(eaiJavaEClass, EAI_JAVA__PROJECT);

        methodTypeEClass = createEClass(METHOD_TYPE);
        createEReference(methodTypeEClass, METHOD_TYPE__RETURN);
        createEReference(methodTypeEClass, METHOD_TYPE__PARAMETERS);
        createEAttribute(methodTypeEClass, METHOD_TYPE__NAME);

        parametersTypeEClass = createEClass(PARAMETERS_TYPE);
        createEReference(parametersTypeEClass, PARAMETERS_TYPE__PARAMETER);

        parameterTypeEClass = createEClass(PARAMETER_TYPE);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__CLASS_NAME);
        createEAttribute(parameterTypeEClass, PARAMETER_TYPE__SIGNATURE);
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

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(classTypeEClass, ClassType.class, "ClassType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getClassType_Method(), this.getMethodType(), null, "method", null, 1, 1, ClassType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getClassType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, ClassType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getDocumentRoot_EAIJava(), this.getEAIJava(), null, "eAIJava", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(eaiJavaEClass, EAIJava.class, "EAIJava", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getEAIJava_Pojo(), this.getClassType(), null, "pojo", null, 1, 1, EAIJava.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getEAIJava_Factory(), this.getClassType(), null, "factory", null, 0, 1, EAIJava.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getEAIJava_Project(), theXMLTypePackage.getString(), "project", null, 0, 1, EAIJava.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(methodTypeEClass, MethodType.class, "MethodType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getMethodType_Return(), this.getParameterType(), null, "return", null, 0, 1, MethodType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getMethodType_Parameters(), this.getParametersType(), null, "parameters", null, 0, 1, MethodType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getMethodType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, MethodType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parametersTypeEClass, ParametersType.class, "ParametersType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(getParametersType_Parameter(), this.getParameterType(), null, "parameter", null, 1, -1, ParametersType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(parameterTypeEClass, ParameterType.class, "ParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getParameterType_ClassName(), theXMLTypePackage.getString(), "className", null, 0, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getParameterType_Signature(), theXMLTypePackage.getString(), "signature", null, 1, 1, ParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

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
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";			 //$NON-NLS-1$
        addAnnotation
          (classTypeEClass, 
           source, 
           new String[] {
             "name", "ClassType", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (getClassType_Method(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Method", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (getClassType_Name(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (documentRootEClass, 
           source, 
           new String[] {
             "name", "", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "mixed" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getDocumentRoot_Mixed(), 
           source, 
           new String[] {
             "kind", "elementWildcard", //$NON-NLS-1$ //$NON-NLS-2$
             "name", ":mixed" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getDocumentRoot_XMLNSPrefixMap(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "xmlns:prefix" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getDocumentRoot_XSISchemaLocation(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "xsi:schemaLocation" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getDocumentRoot_EAIJava(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "EAIJava", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (eaiJavaEClass, 
           source, 
           new String[] {
             "name", "EAIJava", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getEAIJava_Pojo(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Pojo", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getEAIJava_Factory(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Factory", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (getEAIJava_Project(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "project" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (methodTypeEClass, 
           source, 
           new String[] {
             "name", "MethodType", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getMethodType_Return(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Return", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getMethodType_Parameters(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Parameters", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getMethodType_Name(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "name" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (parametersTypeEClass, 
           source, 
           new String[] {
             "name", "ParametersType", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "elementOnly" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getParametersType_Parameter(), 
           source, 
           new String[] {
             "kind", "element", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "Parameter", //$NON-NLS-1$ //$NON-NLS-2$
             "namespace", "##targetNamespace" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (parameterTypeEClass, 
           source, 
           new String[] {
             "name", "ParameterType", //$NON-NLS-1$ //$NON-NLS-2$
             "kind", "empty" //$NON-NLS-1$ //$NON-NLS-2$
           });		
        addAnnotation
          (getParameterType_ClassName(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "className" //$NON-NLS-1$ //$NON-NLS-2$
           });			
        addAnnotation
          (getParameterType_Signature(), 
           source, 
           new String[] {
             "kind", "attribute", //$NON-NLS-1$ //$NON-NLS-2$
             "name", "signature" //$NON-NLS-1$ //$NON-NLS-2$
           });
    }

} //EaijavaPackageImpl
