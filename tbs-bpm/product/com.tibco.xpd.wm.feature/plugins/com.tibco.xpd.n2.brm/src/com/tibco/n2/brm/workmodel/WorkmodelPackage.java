/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.workmodel;

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
 * @see com.tibco.n2.brm.workmodel.WorkmodelFactory
 * @model kind="package"
 * @generated
 */
public interface WorkmodelPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "workmodel";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://workmodel.brm.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "workmodel";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    WorkmodelPackage eINSTANCE = com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.workmodel.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.workmodel.impl.DocumentRootImpl
     * @see com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl#getDocumentRoot()
     * @generated
     */
    int DOCUMENT_ROOT = 0;

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
     * The feature id for the '<em><b>Work Models</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__WORK_MODELS = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.brm.workmodel.impl.WorkModelImpl <em>Work Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.brm.workmodel.impl.WorkModelImpl
     * @see com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl#getWorkModel()
     * @generated
     */
    int WORK_MODEL = 1;

    /**
     * The feature id for the '<em><b>Work Model</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__WORK_MODEL = 0;

    /**
     * The feature id for the '<em><b>Org Model Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__ORG_MODEL_VERSION = 1;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL__VERSION = 2;

    /**
     * The number of structural features of the '<em>Work Model</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int WORK_MODEL_FEATURE_COUNT = 3;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.workmodel.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.brm.workmodel.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.brm.workmodel.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.brm.workmodel.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.brm.workmodel.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.brm.workmodel.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.brm.workmodel.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.brm.workmodel.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.brm.workmodel.DocumentRoot#getWorkModels <em>Work Models</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Work Models</em>'.
     * @see com.tibco.n2.brm.workmodel.DocumentRoot#getWorkModels()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_WorkModels();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.brm.workmodel.WorkModel <em>Work Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Work Model</em>'.
     * @see com.tibco.n2.brm.workmodel.WorkModel
     * @generated
     */
    EClass getWorkModel();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.brm.workmodel.WorkModel#getWorkModel <em>Work Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Work Model</em>'.
     * @see com.tibco.n2.brm.workmodel.WorkModel#getWorkModel()
     * @see #getWorkModel()
     * @generated
     */
    EReference getWorkModel_WorkModel();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion <em>Org Model Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Org Model Version</em>'.
     * @see com.tibco.n2.brm.workmodel.WorkModel#getOrgModelVersion()
     * @see #getWorkModel()
     * @generated
     */
    EAttribute getWorkModel_OrgModelVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.brm.workmodel.WorkModel#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.brm.workmodel.WorkModel#getVersion()
     * @see #getWorkModel()
     * @generated
     */
    EAttribute getWorkModel_Version();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    WorkmodelFactory getWorkmodelFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.brm.workmodel.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.workmodel.impl.DocumentRootImpl
         * @see com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Work Models</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__WORK_MODELS = eINSTANCE.getDocumentRoot_WorkModels();

        /**
         * The meta object literal for the '{@link com.tibco.n2.brm.workmodel.impl.WorkModelImpl <em>Work Model</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.brm.workmodel.impl.WorkModelImpl
         * @see com.tibco.n2.brm.workmodel.impl.WorkmodelPackageImpl#getWorkModel()
         * @generated
         */
        EClass WORK_MODEL = eINSTANCE.getWorkModel();

        /**
         * The meta object literal for the '<em><b>Work Model</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference WORK_MODEL__WORK_MODEL = eINSTANCE.getWorkModel_WorkModel();

        /**
         * The meta object literal for the '<em><b>Org Model Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL__ORG_MODEL_VERSION = eINSTANCE.getWorkModel_OrgModelVersion();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute WORK_MODEL__VERSION = eINSTANCE.getWorkModel_Version();

    }

} //WorkmodelPackage
