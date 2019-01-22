/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.attributefacade;

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
 * @see com.tibco.n2.common.attributefacade.AttributefacadeFactory
 * @model kind="package"
 * @generated
 */
public interface AttributefacadePackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "attributefacade";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://attributefacade.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "attributefacade";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    AttributefacadePackage eINSTANCE = com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl <em>Attribute Alias Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl
     * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getAttributeAliasType()
     * @generated
     */
    int ATTRIBUTE_ALIAS_TYPE = 0;

    /**
     * The feature id for the '<em><b>Attribute Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS = 0;

    /**
     * The feature id for the '<em><b>Attribute Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME = 1;

    /**
     * The number of structural features of the '<em>Attribute Alias Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ATTRIBUTE_ALIAS_TYPE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.attributefacade.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.attributefacade.impl.DocumentRootImpl
     * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getDocumentRoot()
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
     * The feature id for the '<em><b>Work List Attribute Facade</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WORK_LIST_ATTRIBUTE_FACADE = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl <em>Work List Attribute Facade Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl
     * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getWorkListAttributeFacadeType()
     * @generated
     */
    int WORK_LIST_ATTRIBUTE_FACADE_TYPE = 2;

    /**
     * The feature id for the '<em><b>Alias</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION = 1;

    /**
     * The number of structural features of the '<em>Work List Attribute Facade Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_ATTRIBUTE_FACADE_TYPE_FEATURE_COUNT = 2;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.attributefacade.AttributeAliasType <em>Attribute Alias Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Attribute Alias Type</em>'.
     * @see com.tibco.n2.common.attributefacade.AttributeAliasType
     * @generated
     */
    EClass getAttributeAliasType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeAlias <em>Attribute Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Alias</em>'.
     * @see com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeAlias()
     * @see #getAttributeAliasType()
     * @generated
     */
    EAttribute getAttributeAliasType_AttributeAlias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeName <em>Attribute Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Attribute Name</em>'.
     * @see com.tibco.n2.common.attributefacade.AttributeAliasType#getAttributeName()
     * @see #getAttributeAliasType()
     * @generated
     */
    EAttribute getAttributeAliasType_AttributeName();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.attributefacade.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.common.attributefacade.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.common.attributefacade.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.common.attributefacade.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.attributefacade.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.common.attributefacade.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.attributefacade.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.common.attributefacade.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.attributefacade.DocumentRoot#getWorkListAttributeFacade <em>Work List Attribute Facade</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work List Attribute Facade</em>'.
     * @see com.tibco.n2.common.attributefacade.DocumentRoot#getWorkListAttributeFacade()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WorkListAttributeFacade();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType <em>Work List Attribute Facade Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List Attribute Facade Type</em>'.
     * @see com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType
     * @generated
     */
    EClass getWorkListAttributeFacadeType();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getAlias <em>Alias</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Alias</em>'.
     * @see com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getAlias()
     * @see #getWorkListAttributeFacadeType()
     * @generated
     */
    EReference getWorkListAttributeFacadeType_Alias();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.common.attributefacade.WorkListAttributeFacadeType#getVersion()
     * @see #getWorkListAttributeFacadeType()
     * @generated
     */
    EAttribute getWorkListAttributeFacadeType_Version();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    AttributefacadeFactory getAttributefacadeFactory();

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
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl <em>Attribute Alias Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.attributefacade.impl.AttributeAliasTypeImpl
         * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getAttributeAliasType()
         * @generated
         */
        EClass ATTRIBUTE_ALIAS_TYPE = eINSTANCE.getAttributeAliasType();

        /**
         * The meta object literal for the '<em><b>Attribute Alias</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_ALIAS = eINSTANCE.getAttributeAliasType_AttributeAlias();

        /**
         * The meta object literal for the '<em><b>Attribute Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ATTRIBUTE_ALIAS_TYPE__ATTRIBUTE_NAME = eINSTANCE.getAttributeAliasType_AttributeName();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.attributefacade.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.attributefacade.impl.DocumentRootImpl
         * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Work List Attribute Facade</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WORK_LIST_ATTRIBUTE_FACADE = eINSTANCE.getDocumentRoot_WorkListAttributeFacade();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl <em>Work List Attribute Facade Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.attributefacade.impl.WorkListAttributeFacadeTypeImpl
         * @see com.tibco.n2.common.attributefacade.impl.AttributefacadePackageImpl#getWorkListAttributeFacadeType()
         * @generated
         */
        EClass WORK_LIST_ATTRIBUTE_FACADE_TYPE = eINSTANCE.getWorkListAttributeFacadeType();

        /**
         * The meta object literal for the '<em><b>Alias</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_ATTRIBUTE_FACADE_TYPE__ALIAS = eINSTANCE.getWorkListAttributeFacadeType_Alias();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_ATTRIBUTE_FACADE_TYPE__VERSION = eINSTANCE.getWorkListAttributeFacadeType_Version();

    }

} //AttributefacadePackage
