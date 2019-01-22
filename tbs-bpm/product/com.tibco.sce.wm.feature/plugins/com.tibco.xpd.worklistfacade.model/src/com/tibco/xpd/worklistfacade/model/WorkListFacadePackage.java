/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadeFactory
 * @model kind="package"
 * @generated
 */
public interface WorkListFacadePackage extends EPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "worklistfacade"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/workListFacade1.0.0"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "wlf"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    WorkListFacadePackage eINSTANCE =
            com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl.init();

    /**
     * The meta object id for the '
     * {@link com.tibco.xpd.worklistfacade.impl.DocumentRootImpl
     * <em>Document Root</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see com.tibco.xpd.worklistfacade.impl.DocumentRootImpl
     * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 0;

    /**
     * The feature id for the '<em><b>Mixed</b></em>' attribute list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__MIXED = 0;

    /**
     * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
     * The feature id for the '<em><b>XSI Schema Location</b></em>' map. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
     * The feature id for the '<em><b>Work List Facade</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WORK_LIST_FACADE = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.worklistfacade.impl.WorkItemAttributeImpl <em>Work Item Attribute</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.worklistfacade.impl.WorkItemAttributeImpl
     * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkItemAttribute()
     * @generated
     */
    int WORK_ITEM_ATTRIBUTE = 1;

    /**
     * The feature id for the '<em><b>Display Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTE__DISPLAY_LABEL = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTE__NAME = 1;

    /**
     * The number of structural features of the '<em>Work Item Attribute</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTE_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.worklistfacade.impl.WorkItemAttributesImpl <em>Work Item Attributes</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.worklistfacade.impl.WorkItemAttributesImpl
     * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkItemAttributes()
     * @generated
     */
    int WORK_ITEM_ATTRIBUTES = 2;

    /**
     * The feature id for the '<em><b>Work Item Attribute</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE = 0;

    /**
     * The number of structural features of the '<em>Work Item Attributes</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_ITEM_ATTRIBUTES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl <em>Work List Facade</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl
     * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkListFacade()
     * @generated
     */
    int WORK_LIST_FACADE = 3;

    /**
     * The feature id for the '<em><b>Work Item Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES = 0;

    /**
     * The feature id for the '<em><b>Format Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_FACADE__FORMAT_VERSION = 1;

    /**
     * The number of structural features of the '<em>Work List Facade</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_LIST_FACADE_FEATURE_COUNT = 2;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.worklistfacade.model.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.worklistfacade.model.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '
     * {@link com.tibco.xpd.worklistfacade.model.DocumentRoot#getXMLNSPrefixMap
     * <em>XMLNS Prefix Map</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '
     * {@link com.tibco.xpd.worklistfacade.model.DocumentRoot#getXSISchemaLocation
     * <em>XSI Schema Location</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '
     * {@link com.tibco.xpd.worklistfacade.model.DocumentRoot#getWorkListFacade
     * <em>Work List Facade</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return the meta object for the containment reference '
     *         <em>Work List Facade</em>'.
     * @see com.tibco.xpd.worklistfacade.model.DocumentRoot#getWorkListFacade()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WorkListFacade();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute <em>Work Item Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Attribute</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttribute
     * @generated
     */
    EClass getWorkItemAttribute();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getDisplayLabel <em>Display Label</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Display Label</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getDisplayLabel()
     * @see #getWorkItemAttribute()
     * @generated
     */
    EAttribute getWorkItemAttribute_DisplayLabel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getName()
     * @see #getWorkItemAttribute()
     * @generated
     */
    EAttribute getWorkItemAttribute_Name();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttributes <em>Work Item Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Item Attributes</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttributes
     * @generated
     */
    EClass getWorkItemAttributes();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttributes#getWorkItemAttribute <em>Work Item Attribute</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Item Attribute</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkItemAttributes#getWorkItemAttribute()
     * @see #getWorkItemAttributes()
     * @generated
     */
    EReference getWorkItemAttributes_WorkItemAttribute();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.worklistfacade.model.WorkListFacade <em>Work List Facade</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work List Facade</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacade
     * @generated
     */
    EClass getWorkListFacade();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getWorkItemAttributes <em>Work Item Attributes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Item Attributes</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacade#getWorkItemAttributes()
     * @see #getWorkListFacade()
     * @generated
     */
    EReference getWorkListFacade_WorkItemAttributes();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getFormatVersion <em>Format Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Format Version</em>'.
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacade#getFormatVersion()
     * @see #getWorkListFacade()
     * @generated
     */
    EAttribute getWorkListFacade_FormatVersion();

    /**
     * Returns the factory that creates the instances of the model. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the factory that creates the instances of the model.
     * @generated
     */
    WorkListFacadeFactory getWorkListFacadeFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that
     * represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link com.tibco.xpd.worklistfacade.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @see com.tibco.xpd.worklistfacade.impl.DocumentRootImpl
         * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getDocumentRoot()
         * @generated
         */
        EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

        /**
         * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

        /**
         * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE
                .getDocumentRoot_XMLNSPrefixMap();

        /**
         * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE
                .getDocumentRoot_XSISchemaLocation();

        /**
         * The meta object literal for the '<em><b>Work List Facade</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WORK_LIST_FACADE = eINSTANCE
                .getDocumentRoot_WorkListFacade();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.worklistfacade.impl.WorkItemAttributeImpl <em>Work Item Attribute</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.worklistfacade.impl.WorkItemAttributeImpl
         * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkItemAttribute()
         * @generated
         */
        EClass WORK_ITEM_ATTRIBUTE = eINSTANCE.getWorkItemAttribute();

        /**
         * The meta object literal for the '<em><b>Display Label</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTE__DISPLAY_LABEL = eINSTANCE
                .getWorkItemAttribute_DisplayLabel();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_ITEM_ATTRIBUTE__NAME = eINSTANCE
                .getWorkItemAttribute_Name();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.worklistfacade.impl.WorkItemAttributesImpl <em>Work Item Attributes</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.worklistfacade.impl.WorkItemAttributesImpl
         * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkItemAttributes()
         * @generated
         */
        EClass WORK_ITEM_ATTRIBUTES = eINSTANCE.getWorkItemAttributes();

        /**
         * The meta object literal for the '<em><b>Work Item Attribute</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_ITEM_ATTRIBUTES__WORK_ITEM_ATTRIBUTE = eINSTANCE
                .getWorkItemAttributes_WorkItemAttribute();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl <em>Work List Facade</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadeImpl
         * @see com.tibco.xpd.worklistfacade.impl.WorkListFacadePackageImpl#getWorkListFacade()
         * @generated
         */
        EClass WORK_LIST_FACADE = eINSTANCE.getWorkListFacade();

        /**
         * The meta object literal for the '<em><b>Work Item Attributes</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_LIST_FACADE__WORK_ITEM_ATTRIBUTES = eINSTANCE
                .getWorkListFacade_WorkItemAttributes();

        /**
         * The meta object literal for the '<em><b>Format Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_LIST_FACADE__FORMAT_VERSION = eINSTANCE
                .getWorkListFacade_FormatVersion();

    }

} // WorkListFacadePackage
