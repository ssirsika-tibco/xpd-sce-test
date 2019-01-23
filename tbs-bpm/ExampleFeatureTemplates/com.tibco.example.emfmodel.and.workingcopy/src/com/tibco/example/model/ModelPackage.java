/**
 * Copyright (c) 2004 - 2015 TIBCO Software Inc. ALL RIGHTS RESERVED.
 */
package com.tibco.example.model;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.example.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "model";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/ExampleModel";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "model";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ModelPackage eINSTANCE = com.tibco.example.model.impl.ModelPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.example.model.impl.ChildTypeImpl <em>Child Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.example.model.impl.ChildTypeImpl
     * @see com.tibco.example.model.impl.ModelPackageImpl#getChildType()
     * @generated
     */
    int CHILD_TYPE = 0;

    /**
     * The feature id for the '<em><b>Test Element</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHILD_TYPE__TEST_ELEMENT = 0;

    /**
     * The feature id for the '<em><b>Child Attribute</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHILD_TYPE__CHILD_ATTRIBUTE = 1;

    /**
     * The number of structural features of the '<em>Child Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHILD_TYPE_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Child Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CHILD_TYPE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.example.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.example.model.impl.DocumentRootImpl
     * @see com.tibco.example.model.impl.ModelPackageImpl#getDocumentRoot()
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
     * The feature id for the '<em><b>Main Element</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MAIN_ELEMENT = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.example.model.impl.MainElementTypeImpl <em>Main Element Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.example.model.impl.MainElementTypeImpl
     * @see com.tibco.example.model.impl.ModelPackageImpl#getMainElementType()
     * @generated
     */
    int MAIN_ELEMENT_TYPE = 2;

    /**
     * The feature id for the '<em><b>Child Elements</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAIN_ELEMENT_TYPE__CHILD_ELEMENTS = 0;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAIN_ELEMENT_TYPE__ID = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAIN_ELEMENT_TYPE__NAME = 2;

    /**
     * The number of structural features of the '<em>Main Element Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAIN_ELEMENT_TYPE_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Main Element Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MAIN_ELEMENT_TYPE_OPERATION_COUNT = 0;


    /**
     * Returns the meta object for class '{@link com.tibco.example.model.ChildType <em>Child Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Child Type</em>'.
     * @see com.tibco.example.model.ChildType
     * @generated
     */
    EClass getChildType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.example.model.ChildType#getTestElement <em>Test Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Test Element</em>'.
     * @see com.tibco.example.model.ChildType#getTestElement()
     * @see #getChildType()
     * @generated
     */
    EAttribute getChildType_TestElement();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.example.model.ChildType#getChildAttribute <em>Child Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Child Attribute</em>'.
     * @see com.tibco.example.model.ChildType#getChildAttribute()
     * @see #getChildType()
     * @generated
     */
    EAttribute getChildType_ChildAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.example.model.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.example.model.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.example.model.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.example.model.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.example.model.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.example.model.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.example.model.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.example.model.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.example.model.DocumentRoot#getMainElement <em>Main Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Main Element</em>'.
     * @see com.tibco.example.model.DocumentRoot#getMainElement()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_MainElement();

    /**
     * Returns the meta object for class '{@link com.tibco.example.model.MainElementType <em>Main Element Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Main Element Type</em>'.
     * @see com.tibco.example.model.MainElementType
     * @generated
     */
    EClass getMainElementType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.example.model.MainElementType#getChildElements <em>Child Elements</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Child Elements</em>'.
     * @see com.tibco.example.model.MainElementType#getChildElements()
     * @see #getMainElementType()
     * @generated
     */
    EReference getMainElementType_ChildElements();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.example.model.MainElementType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.example.model.MainElementType#getId()
     * @see #getMainElementType()
     * @generated
     */
    EAttribute getMainElementType_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.example.model.MainElementType#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.example.model.MainElementType#getName()
     * @see #getMainElementType()
     * @generated
     */
    EAttribute getMainElementType_Name();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ModelFactory getModelFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each operation of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.example.model.impl.ChildTypeImpl <em>Child Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.example.model.impl.ChildTypeImpl
         * @see com.tibco.example.model.impl.ModelPackageImpl#getChildType()
         * @generated
         */
        EClass CHILD_TYPE = eINSTANCE.getChildType();

        /**
         * The meta object literal for the '<em><b>Test Element</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHILD_TYPE__TEST_ELEMENT = eINSTANCE.getChildType_TestElement();

        /**
         * The meta object literal for the '<em><b>Child Attribute</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CHILD_TYPE__CHILD_ATTRIBUTE = eINSTANCE.getChildType_ChildAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.example.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.example.model.impl.DocumentRootImpl
         * @see com.tibco.example.model.impl.ModelPackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Main Element</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__MAIN_ELEMENT = eINSTANCE.getDocumentRoot_MainElement();

        /**
         * The meta object literal for the '{@link com.tibco.example.model.impl.MainElementTypeImpl <em>Main Element Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.example.model.impl.MainElementTypeImpl
         * @see com.tibco.example.model.impl.ModelPackageImpl#getMainElementType()
         * @generated
         */
        EClass MAIN_ELEMENT_TYPE = eINSTANCE.getMainElementType();

        /**
         * The meta object literal for the '<em><b>Child Elements</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MAIN_ELEMENT_TYPE__CHILD_ELEMENTS = eINSTANCE.getMainElementType_ChildElements();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MAIN_ELEMENT_TYPE__ID = eINSTANCE.getMainElementType_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MAIN_ELEMENT_TYPE__NAME = eINSTANCE.getMainElementType_Name();

    }

} //ModelPackage
