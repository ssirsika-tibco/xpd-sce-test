/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaFactory
 * @model kind="package"
 * @generated
 */
public interface EaijavaPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "eaijava"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/EAIJava1.0.0"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "ej"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EaijavaPackage eINSTANCE = com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl <em>Class Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getClassType()
     * @generated
     */
    int CLASS_TYPE = 0;

    /**
     * The feature id for the '<em><b>Method</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLASS_TYPE__METHOD = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLASS_TYPE__NAME = 1;

    /**
     * The number of structural features of the '<em>Class Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CLASS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.DocumentRootImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 1;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>EAI Java</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__EAI_JAVA = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl <em>EAI Java</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getEAIJava()
     * @generated
     */
    int EAI_JAVA = 2;

    /**
     * The feature id for the '<em><b>Pojo</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EAI_JAVA__POJO = 0;

    /**
     * The feature id for the '<em><b>Factory</b></em>' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int EAI_JAVA__FACTORY = 1;

    /**
     * The feature id for the '<em><b>Project</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EAI_JAVA__PROJECT = 2;

    /**
     * The number of structural features of the '<em>EAI Java</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int EAI_JAVA_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl <em>Method Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getMethodType()
     * @generated
     */
    int METHOD_TYPE = 3;

    /**
     * The feature id for the '<em><b>Return</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_TYPE__RETURN = 0;

    /**
     * The feature id for the '<em><b>Parameters</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_TYPE__PARAMETERS = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_TYPE__NAME = 2;

    /**
     * The number of structural features of the '<em>Method Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int METHOD_TYPE_FEATURE_COUNT = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParametersTypeImpl <em>Parameters Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParametersTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getParametersType()
     * @generated
     */
    int PARAMETERS_TYPE = 4;

    /**
     * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETERS_TYPE__PARAMETER = 0;

    /**
     * The number of structural features of the '<em>Parameters Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETERS_TYPE_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParameterTypeImpl
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getParameterType()
     * @generated
     */
    int PARAMETER_TYPE = 5;

    /**
     * The feature id for the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__CLASS_NAME = 0;

    /**
     * The feature id for the '<em><b>Signature</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE__SIGNATURE = 1;

    /**
     * The number of structural features of the '<em>Parameter Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PARAMETER_TYPE_FEATURE_COUNT = 2;


    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType <em>Class Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Class Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType
     * @generated
     */
    EClass getClassType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getMethod <em>Method</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Method</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getMethod()
     * @see #getClassType()
     * @generated
     */
    EReference getClassType_Method();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType#getName()
     * @see #getClassType()
     * @generated
     */
    EAttribute getClassType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getEAIJava <em>EAI Java</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>EAI Java</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.DocumentRoot#getEAIJava()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_EAIJava();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava <em>EAI Java</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>EAI Java</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava
     * @generated
     */
    EClass getEAIJava();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getPojo <em>Pojo</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Pojo</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getPojo()
     * @see #getEAIJava()
     * @generated
     */
    EReference getEAIJava_Pojo();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getFactory <em>Factory</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Factory</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getFactory()
     * @see #getEAIJava()
     * @generated
     */
	EReference getEAIJava_Factory();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getProject <em>Project</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Project</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava#getProject()
     * @see #getEAIJava()
     * @generated
     */
    EAttribute getEAIJava_Project();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType <em>Method Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Method Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType
     * @generated
     */
    EClass getMethodType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getReturn <em>Return</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Return</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getReturn()
     * @see #getMethodType()
     * @generated
     */
    EReference getMethodType_Return();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getParameters <em>Parameters</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Parameters</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getParameters()
     * @see #getMethodType()
     * @generated
     */
    EReference getMethodType_Parameters();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.MethodType#getName()
     * @see #getMethodType()
     * @generated
     */
    EAttribute getMethodType_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType <em>Parameters Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameters Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType
     * @generated
     */
    EClass getParametersType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType#getParameter <em>Parameter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Parameter</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParametersType#getParameter()
     * @see #getParametersType()
     * @generated
     */
    EReference getParametersType_Parameter();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType <em>Parameter Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Parameter Type</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType
     * @generated
     */
    EClass getParameterType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getClassName <em>Class Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Class Name</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getClassName()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_ClassName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getSignature <em>Signature</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Signature</em>'.
     * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ParameterType#getSignature()
     * @see #getParameterType()
     * @generated
     */
    EAttribute getParameterType_Signature();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    EaijavaFactory getEaijavaFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals  {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl <em>Class Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ClassTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getClassType()
         * @generated
         */
        EClass CLASS_TYPE = eINSTANCE.getClassType();

        /**
         * The meta object literal for the '<em><b>Method</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CLASS_TYPE__METHOD = eINSTANCE.getClassType_Method();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CLASS_TYPE__NAME = eINSTANCE.getClassType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.DocumentRootImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>EAI Java</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__EAI_JAVA = eINSTANCE.getDocumentRoot_EAIJava();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl <em>EAI Java</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getEAIJava()
         * @generated
         */
        EClass EAI_JAVA = eINSTANCE.getEAIJava();

        /**
         * The meta object literal for the '<em><b>Pojo</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference EAI_JAVA__POJO = eINSTANCE.getEAIJava_Pojo();

        /**
         * The meta object literal for the '<em><b>Factory</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference EAI_JAVA__FACTORY = eINSTANCE.getEAIJava_Factory();

        /**
         * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute EAI_JAVA__PROJECT = eINSTANCE.getEAIJava_Project();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl <em>Method Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.MethodTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getMethodType()
         * @generated
         */
        EClass METHOD_TYPE = eINSTANCE.getMethodType();

        /**
         * The meta object literal for the '<em><b>Return</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference METHOD_TYPE__RETURN = eINSTANCE.getMethodType_Return();

        /**
         * The meta object literal for the '<em><b>Parameters</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference METHOD_TYPE__PARAMETERS = eINSTANCE.getMethodType_Parameters();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute METHOD_TYPE__NAME = eINSTANCE.getMethodType_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParametersTypeImpl <em>Parameters Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParametersTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getParametersType()
         * @generated
         */
        EClass PARAMETERS_TYPE = eINSTANCE.getParametersType();

        /**
         * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PARAMETERS_TYPE__PARAMETER = eINSTANCE.getParametersType_Parameter();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.ParameterTypeImpl
         * @see com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EaijavaPackageImpl#getParameterType()
         * @generated
         */
        EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

        /**
         * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__CLASS_NAME = eINSTANCE.getParameterType_ClassName();

        /**
         * The meta object literal for the '<em><b>Signature</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PARAMETER_TYPE__SIGNATURE = eINSTANCE.getParameterType_Signature();

    }

} //EaijavaPackage
