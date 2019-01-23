/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.pageactivitymodel;

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
 * @see com.tibco.n2.common.pageactivitymodel.PageactivitymodelFactory
 * @model kind="package"
 * @generated
 */
public interface PageactivitymodelPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "pageactivitymodel";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://pageactivitymodel.common.n2.tibco.com";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "pageactivitymodel";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    PageactivitymodelPackage eINSTANCE = com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.n2.common.pageactivitymodel.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.pageactivitymodel.impl.DocumentRootImpl
     * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getDocumentRoot()
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
     * The feature id for the '<em><b>Page Activities</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PAGE_ACTIVITIES = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl <em>Page Activities</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl
     * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getPageActivities()
     * @generated
     */
    int PAGE_ACTIVITIES = 1;

    /**
     * The feature id for the '<em><b>Page Activity</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITIES__PAGE_ACTIVITY = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITIES__VERSION = 1;

    /**
     * The number of structural features of the '<em>Page Activities</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITIES_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl <em>Page Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl
     * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getPageActivity()
     * @generated
     */
    int PAGE_ACTIVITY = 2;

    /**
     * The feature id for the '<em><b>Activity Model ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__ACTIVITY_MODEL_ID = 0;

    /**
     * The feature id for the '<em><b>Activity Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__ACTIVITY_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Module Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__MODULE_NAME = 2;

    /**
     * The feature id for the '<em><b>Module Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__MODULE_VERSION = 3;

    /**
     * The feature id for the '<em><b>Process Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__PROCESS_NAME = 4;

    /**
     * The feature id for the '<em><b>Data Model</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY__DATA_MODEL = 5;

    /**
     * The number of structural features of the '<em>Page Activity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PAGE_ACTIVITY_FEATURE_COUNT = 6;


    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.pageactivitymodel.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.n2.common.pageactivitymodel.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.pageactivitymodel.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.n2.common.pageactivitymodel.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.pageactivitymodel.DocumentRoot#getPageActivities <em>Page Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Page Activities</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.DocumentRoot#getPageActivities()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_PageActivities();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.pageactivitymodel.PageActivities <em>Page Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page Activities</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivities
     * @generated
     */
    EClass getPageActivities();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.n2.common.pageactivitymodel.PageActivities#getPageActivity <em>Page Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Page Activity</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivities#getPageActivity()
     * @see #getPageActivities()
     * @generated
     */
    EReference getPageActivities_PageActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivities#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivities#getVersion()
     * @see #getPageActivities()
     * @generated
     */
    EAttribute getPageActivities_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.n2.common.pageactivitymodel.PageActivity <em>Page Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Page Activity</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity
     * @generated
     */
    EClass getPageActivity();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityModelID <em>Activity Model ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Model ID</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityModelID()
     * @see #getPageActivity()
     * @generated
     */
    EAttribute getPageActivity_ActivityModelID();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityDescription <em>Activity Description</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Description</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getActivityDescription()
     * @see #getPageActivity()
     * @generated
     */
    EAttribute getPageActivity_ActivityDescription();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleName <em>Module Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Module Name</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleName()
     * @see #getPageActivity()
     * @generated
     */
    EAttribute getPageActivity_ModuleName();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleVersion <em>Module Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Module Version</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getModuleVersion()
     * @see #getPageActivity()
     * @generated
     */
    EAttribute getPageActivity_ModuleVersion();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getProcessName <em>Process Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Process Name</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getProcessName()
     * @see #getPageActivity()
     * @generated
     */
    EAttribute getPageActivity_ProcessName();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.n2.common.pageactivitymodel.PageActivity#getDataModel <em>Data Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Data Model</em>'.
     * @see com.tibco.n2.common.pageactivitymodel.PageActivity#getDataModel()
     * @see #getPageActivity()
     * @generated
     */
    EReference getPageActivity_DataModel();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    PageactivitymodelFactory getPageactivitymodelFactory();

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
         * The meta object literal for the '{@link com.tibco.n2.common.pageactivitymodel.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.pageactivitymodel.impl.DocumentRootImpl
         * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Page Activities</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PAGE_ACTIVITIES = eINSTANCE.getDocumentRoot_PageActivities();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl <em>Page Activities</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.pageactivitymodel.impl.PageActivitiesImpl
         * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getPageActivities()
         * @generated
         */
        EClass PAGE_ACTIVITIES = eINSTANCE.getPageActivities();

        /**
         * The meta object literal for the '<em><b>Page Activity</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAGE_ACTIVITIES__PAGE_ACTIVITY = eINSTANCE.getPageActivities_PageActivity();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITIES__VERSION = eINSTANCE.getPageActivities_Version();

        /**
         * The meta object literal for the '{@link com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl <em>Page Activity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.n2.common.pageactivitymodel.impl.PageActivityImpl
         * @see com.tibco.n2.common.pageactivitymodel.impl.PageactivitymodelPackageImpl#getPageActivity()
         * @generated
         */
        EClass PAGE_ACTIVITY = eINSTANCE.getPageActivity();

        /**
         * The meta object literal for the '<em><b>Activity Model ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY__ACTIVITY_MODEL_ID = eINSTANCE.getPageActivity_ActivityModelID();

        /**
         * The meta object literal for the '<em><b>Activity Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY__ACTIVITY_DESCRIPTION = eINSTANCE.getPageActivity_ActivityDescription();

        /**
         * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY__MODULE_NAME = eINSTANCE.getPageActivity_ModuleName();

        /**
         * The meta object literal for the '<em><b>Module Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY__MODULE_VERSION = eINSTANCE.getPageActivity_ModuleVersion();

        /**
         * The meta object literal for the '<em><b>Process Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PAGE_ACTIVITY__PROCESS_NAME = eINSTANCE.getPageActivity_ProcessName();

        /**
         * The meta object literal for the '<em><b>Data Model</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PAGE_ACTIVITY__DATA_MODEL = eINSTANCE.getPageActivity_DataModel();

    }

} //PageactivitymodelPackage
