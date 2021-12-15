/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigFactory
 * @model kind="package"
 *        extendedMetaData="qualified='true'"
 * @generated
 */
public interface ProjectConfigPackage extends EPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "projectconfig"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.tibco.com/XPD/projectConfig"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "config"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ProjectConfigPackage eINSTANCE = com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl.init();

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.DocumentRootImpl <em>Document Root</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.DocumentRootImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDocumentRoot()
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
     * The feature id for the '<em><b>Project Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT__PROJECT_CONFIG = 3;

    /**
     * The number of structural features of the '<em>Document Root</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DOCUMENT_ROOT_FEATURE_COUNT = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl <em>Project Config</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectConfig()
     * @generated
     */
    int PROJECT_CONFIG = 1;

    /**
     * The feature id for the '<em><b>Asset Types</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG__ASSET_TYPES = 0;

    /**
     * The feature id for the '<em><b>Special Folders</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG__SPECIAL_FOLDERS = 1;

    /**
     * The feature id for the '<em><b>Project</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG__PROJECT = 2;

    /**
     * The feature id for the '<em><b>Project Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG__PROJECT_TYPE = 3;

    /**
     * The feature id for the '<em><b>Project Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG__PROJECT_DETAILS = 4;

    /**
     * The number of structural features of the '<em>Project Config</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_CONFIG_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link org.eclipse.core.runtime.IAdaptable <em>Adadptable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.core.runtime.IAdaptable
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getAdadptable()
     * @generated
     */
    int ADADPTABLE = 6;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl <em>Special Folder</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getSpecialFolder()
     * @generated
     */
    int SPECIAL_FOLDER = 3;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl <em>Special Folders</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getSpecialFolders()
     * @generated
     */
    int SPECIAL_FOLDERS = 4;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.UniqueIdContainerImpl <em>Unique Id Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.UniqueIdContainerImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getUniqueIdContainer()
     * @generated
     */
    int UNIQUE_ID_CONTAINER = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.AssetTypeImpl <em>Asset Type</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.AssetTypeImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getAssetType()
     * @generated
     */
    int ASSET_TYPE = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSET_TYPE__ID = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSET_TYPE__VERSION = 1;

    /**
     * The number of structural features of the '<em>Asset Type</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSET_TYPE_FEATURE_COUNT = 2;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_CONTAINER__ID = 0;

    /**
     * The number of structural features of the '<em>Unique Id Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int UNIQUE_ID_CONTAINER_FEATURE_COUNT = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDER__ID = UNIQUE_ID_CONTAINER__ID;

    /**
     * The feature id for the '<em><b>Kind</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDER__KIND = UNIQUE_ID_CONTAINER_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDER__LOCATION = UNIQUE_ID_CONTAINER_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Generated</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDER__GENERATED = UNIQUE_ID_CONTAINER_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Special Folder</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDER_FEATURE_COUNT = UNIQUE_ID_CONTAINER_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Folders</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDERS__FOLDERS = 0;

    /**
     * The feature id for the '<em><b>Config</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDERS__CONFIG = 1;

    /**
     * The number of structural features of the '<em>Special Folders</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SPECIAL_FOLDERS_FEATURE_COUNT = 2;

    /**
     * The number of structural features of the '<em>Adadptable</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ADADPTABLE_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset <em>IProject Asset</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIProjectAsset()
     * @generated
     */
    int IPROJECT_ASSET = 7;

    /**
     * The number of structural features of the '<em>IProject Asset</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IPROJECT_ASSET_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl <em>Project Details</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectDetails()
     * @generated
     */
    int PROJECT_DETAILS = 8;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS__ID = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS__VERSION = 1;

    /**
     * The feature id for the '<em><b>Status</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS__STATUS = 2;

    /**
     * The feature id for the '<em><b>Global Destinations</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS__GLOBAL_DESTINATIONS = 3;

    /**
     * The feature id for the '<em><b>Custom Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS__CUSTOM_PROPERTIES = 4;

    /**
     * The number of structural features of the '<em>Project Details</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PROJECT_DETAILS_FEATURE_COUNT = 5;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.DestinationImpl <em>Destination</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.DestinationImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDestination()
     * @generated
     */
    int DESTINATION = 9;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESTINATION__TYPE = 0;

    /**
     * The number of structural features of the '<em>Destination</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESTINATION_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.DestinationsImpl <em>Destinations</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.DestinationsImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDestinations()
     * @generated
     */
    int DESTINATIONS = 10;

    /**
     * The feature id for the '<em><b>Destination</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESTINATIONS__DESTINATION = 0;

    /**
     * The number of structural features of the '<em>Destinations</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DESTINATIONS_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.CustomPropertyImpl <em>Custom Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.CustomPropertyImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCustomProperty()
     * @generated
     */
    int CUSTOM_PROPERTY = 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CUSTOM_PROPERTY__NAME = 0;

    /**
     * The feature id for the '<em><b>Value</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CUSTOM_PROPERTY__VALUE = 1;

    /**
     * The number of structural features of the '<em>Custom Property</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CUSTOM_PROPERTY_FEATURE_COUNT = 2;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.impl.CustomPropertiesImpl <em>Custom Properties</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.impl.CustomPropertiesImpl
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCustomProperties()
     * @generated
     */
    int CUSTOM_PROPERTIES = 12;

    /**
     * The feature id for the '<em><b>Custom Property</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CUSTOM_PROPERTIES__CUSTOM_PROPERTY = 0;

    /**
     * The number of structural features of the '<em>Custom Properties</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CUSTOM_PROPERTIES_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link com.tibco.xpd.resources.projectconfig.ProjectStatus <em>Project Status</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.ProjectStatus
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectStatus()
     * @generated
     */
    int PROJECT_STATUS = 13;

    /**
     * The meta object id for the '<em>IProject</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.core.resources.IProject
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIProject()
     * @generated
     */
    int IPROJECT = 14;

    /**
     * The meta object id for the '<em>IFolder</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.core.resources.IFolder
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIFolder()
     * @generated
     */
    int IFOLDER = 15;

    /**
     * The meta object id for the '<em>IResource</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.core.resources.IResource
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIResource()
     * @generated
     */
    int IRESOURCE = 16;

    /**
     * The meta object id for the '<em>ISpecial Folder Model</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getISpecialFolderModel()
     * @generated
     */
    int ISPECIAL_FOLDER_MODEL = 17;

    /**
     * The meta object id for the '<em>Collection</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see java.util.Collection
     * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCollection()
     * @generated
     */
    int COLLECTION = 18;

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.DocumentRoot <em>Document Root</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Document Root</em>'.
     * @see com.tibco.xpd.resources.projectconfig.DocumentRoot
     * @generated
     */
    EClass getDocumentRoot();

    /**
     * Returns the meta object for the attribute list '{@link com.tibco.xpd.resources.projectconfig.DocumentRoot#getMixed <em>Mixed</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Mixed</em>'.
     * @see com.tibco.xpd.resources.projectconfig.DocumentRoot#getMixed()
     * @see #getDocumentRoot()
     * @generated
     */
    EAttribute getDocumentRoot_Mixed();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.resources.projectconfig.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
     * @see com.tibco.xpd.resources.projectconfig.DocumentRoot#getXMLNSPrefixMap()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XMLNSPrefixMap();

    /**
     * Returns the meta object for the map '{@link com.tibco.xpd.resources.projectconfig.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the map '<em>XSI Schema Location</em>'.
     * @see com.tibco.xpd.resources.projectconfig.DocumentRoot#getXSISchemaLocation()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_XSISchemaLocation();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.resources.projectconfig.DocumentRoot#getProjectConfig <em>Project Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Project Config</em>'.
     * @see com.tibco.xpd.resources.projectconfig.DocumentRoot#getProjectConfig()
     * @see #getDocumentRoot()
     * @generated
     */
    EReference getDocumentRoot_ProjectConfig();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig <em>Project Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Project Config</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig
     * @generated
     */
    EClass getProjectConfig();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getAssetTypes <em>Asset Types</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Asset Types</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getAssetTypes()
     * @see #getProjectConfig()
     * @generated
     */
    EReference getProjectConfig_AssetTypes();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders <em>Special Folders</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Special Folders</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders()
     * @see #getProjectConfig()
     * @generated
     */
    EReference getProjectConfig_SpecialFolders();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProject <em>Project</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Project</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getProject()
     * @see #getProjectConfig()
     * @generated
     */
    EAttribute getProjectConfig_Project();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectType <em>Project Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Project Type</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectType()
     * @see #getProjectConfig()
     * @generated
     */
    EAttribute getProjectConfig_ProjectType();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectDetails <em>Project Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Project Details</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectDetails()
     * @see #getProjectConfig()
     * @generated
     */
    EReference getProjectConfig_ProjectDetails();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder <em>Special Folder</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Special Folder</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolder
     * @generated
     */
    EClass getSpecialFolder();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getKind <em>Kind</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Kind</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolder#getKind()
     * @see #getSpecialFolder()
     * @generated
     */
    EAttribute getSpecialFolder_Kind();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getLocation <em>Location</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Location</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolder#getLocation()
     * @see #getSpecialFolder()
     * @generated
     */
    EAttribute getSpecialFolder_Location();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getGenerated <em>Generated</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Generated</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolder#getGenerated()
     * @see #getSpecialFolder()
     * @generated
     */
    EAttribute getSpecialFolder_Generated();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.SpecialFolders <em>Special Folders</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Special Folders</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolders
     * @generated
     */
    EClass getSpecialFolders();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.resources.projectconfig.SpecialFolders#getFolders <em>Folders</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Folders</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolders#getFolders()
     * @see #getSpecialFolders()
     * @generated
     */
    EReference getSpecialFolders_Folders();

    /**
     * Returns the meta object for the container reference '{@link com.tibco.xpd.resources.projectconfig.SpecialFolders#getConfig <em>Config</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Config</em>'.
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolders#getConfig()
     * @see #getSpecialFolders()
     * @generated
     */
    EReference getSpecialFolders_Config();

    /**
     * Returns the meta object for class '{@link org.eclipse.core.runtime.IAdaptable <em>Adadptable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Adadptable</em>'.
     * @see org.eclipse.core.runtime.IAdaptable
     * @model instanceClass="org.eclipse.core.runtime.IAdaptable"
     * @generated
     */
    EClass getAdadptable();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.UniqueIdContainer <em>Unique Id Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Unique Id Container</em>'.
     * @see com.tibco.xpd.resources.projectconfig.UniqueIdContainer
     * @generated
     */
    EClass getUniqueIdContainer();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.UniqueIdContainer#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.resources.projectconfig.UniqueIdContainer#getId()
     * @see #getUniqueIdContainer()
     * @generated
     */
    EAttribute getUniqueIdContainer_Id();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.AssetType <em>Asset Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Asset Type</em>'.
     * @see com.tibco.xpd.resources.projectconfig.AssetType
     * @generated
     */
    EClass getAssetType();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.AssetType#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.resources.projectconfig.AssetType#getId()
     * @see #getAssetType()
     * @generated
     */
    EAttribute getAssetType_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.AssetType#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.resources.projectconfig.AssetType#getVersion()
     * @see #getAssetType()
     * @generated
     */
    EAttribute getAssetType_Version();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset <em>IProject Asset</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>IProject Asset</em>'.
     * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset
     * @model instanceClass="com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset"
     * @generated
     */
    EClass getIProjectAsset();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails <em>Project Details</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Project Details</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails
     * @generated
     */
    EClass getProjectDetails();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getId <em>Id</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails#getId()
     * @see #getProjectDetails()
     * @generated
     */
    EAttribute getProjectDetails_Id();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails#getVersion()
     * @see #getProjectDetails()
     * @generated
     */
    EAttribute getProjectDetails_Version();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getStatus <em>Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Status</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails#getStatus()
     * @see #getProjectDetails()
     * @generated
     */
    EAttribute getProjectDetails_Status();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getGlobalDestinations <em>Global Destinations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Global Destinations</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails#getGlobalDestinations()
     * @see #getProjectDetails()
     * @generated
     */
    EReference getProjectDetails_GlobalDestinations();

    /**
     * Returns the meta object for the containment reference '{@link com.tibco.xpd.resources.projectconfig.ProjectDetails#getCustomProperties <em>Custom Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Custom Properties</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectDetails#getCustomProperties()
     * @see #getProjectDetails()
     * @generated
     */
    EReference getProjectDetails_CustomProperties();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.Destination <em>Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Destination</em>'.
     * @see com.tibco.xpd.resources.projectconfig.Destination
     * @generated
     */
    EClass getDestination();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.Destination#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see com.tibco.xpd.resources.projectconfig.Destination#getType()
     * @see #getDestination()
     * @generated
     */
    EAttribute getDestination_Type();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.Destinations <em>Destinations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Destinations</em>'.
     * @see com.tibco.xpd.resources.projectconfig.Destinations
     * @generated
     */
    EClass getDestinations();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.resources.projectconfig.Destinations#getDestination <em>Destination</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Destination</em>'.
     * @see com.tibco.xpd.resources.projectconfig.Destinations#getDestination()
     * @see #getDestinations()
     * @generated
     */
    EReference getDestinations_Destination();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.CustomProperty <em>Custom Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Custom Property</em>'.
     * @see com.tibco.xpd.resources.projectconfig.CustomProperty
     * @generated
     */
    EClass getCustomProperty();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.CustomProperty#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see com.tibco.xpd.resources.projectconfig.CustomProperty#getName()
     * @see #getCustomProperty()
     * @generated
     */
    EAttribute getCustomProperty_Name();

    /**
     * Returns the meta object for the attribute '{@link com.tibco.xpd.resources.projectconfig.CustomProperty#getValue <em>Value</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Value</em>'.
     * @see com.tibco.xpd.resources.projectconfig.CustomProperty#getValue()
     * @see #getCustomProperty()
     * @generated
     */
    EAttribute getCustomProperty_Value();

    /**
     * Returns the meta object for class '{@link com.tibco.xpd.resources.projectconfig.CustomProperties <em>Custom Properties</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Custom Properties</em>'.
     * @see com.tibco.xpd.resources.projectconfig.CustomProperties
     * @generated
     */
    EClass getCustomProperties();

    /**
     * Returns the meta object for the containment reference list '{@link com.tibco.xpd.resources.projectconfig.CustomProperties#getCustomProperty <em>Custom Property</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Custom Property</em>'.
     * @see com.tibco.xpd.resources.projectconfig.CustomProperties#getCustomProperty()
     * @see #getCustomProperties()
     * @generated
     */
    EReference getCustomProperties_CustomProperty();

    /**
     * Returns the meta object for enum '{@link com.tibco.xpd.resources.projectconfig.ProjectStatus <em>Project Status</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Project Status</em>'.
     * @see com.tibco.xpd.resources.projectconfig.ProjectStatus
     * @generated
     */
    EEnum getProjectStatus();

    /**
     * Returns the meta object for data type '{@link org.eclipse.core.resources.IProject <em>IProject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>IProject</em>'.
     * @see org.eclipse.core.resources.IProject
     * @model instanceClass="org.eclipse.core.resources.IProject"
     * @generated
     */
    EDataType getIProject();

    /**
     * Returns the meta object for data type '{@link org.eclipse.core.resources.IFolder <em>IFolder</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>IFolder</em>'.
     * @see org.eclipse.core.resources.IFolder
     * @model instanceClass="org.eclipse.core.resources.IFolder"
     * @generated
     */
    EDataType getIFolder();

    /**
     * Returns the meta object for data type '{@link org.eclipse.core.resources.IResource <em>IResource</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>IResource</em>'.
     * @see org.eclipse.core.resources.IResource
     * @model instanceClass="org.eclipse.core.resources.IResource"
     * @generated
     */
    EDataType getIResource();

    /**
     * Returns the meta object for data type '{@link com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel <em>ISpecial Folder Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>ISpecial Folder Model</em>'.
     * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel
     * @model instanceClass="com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel"
     * @generated
     */
    EDataType getISpecialFolderModel();

    /**
     * Returns the meta object for data type '{@link java.util.Collection <em>Collection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Collection</em>'.
     * @see java.util.Collection
     * @model instanceClass="java.util.Collection" typeParameters="String"
     * @generated
     */
    EDataType getCollection();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ProjectConfigFactory getProjectConfigFactory();

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
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.DocumentRootImpl <em>Document Root</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.DocumentRootImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDocumentRoot()
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
         * The meta object literal for the '<em><b>Project Config</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DOCUMENT_ROOT__PROJECT_CONFIG = eINSTANCE.getDocumentRoot_ProjectConfig();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl <em>Project Config</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectConfig()
         * @generated
         */
        EClass PROJECT_CONFIG = eINSTANCE.getProjectConfig();

        /**
         * The meta object literal for the '<em><b>Asset Types</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT_CONFIG__ASSET_TYPES = eINSTANCE.getProjectConfig_AssetTypes();

        /**
         * The meta object literal for the '<em><b>Special Folders</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT_CONFIG__SPECIAL_FOLDERS = eINSTANCE.getProjectConfig_SpecialFolders();

        /**
         * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT_CONFIG__PROJECT = eINSTANCE.getProjectConfig_Project();

        /**
         * The meta object literal for the '<em><b>Project Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT_CONFIG__PROJECT_TYPE = eINSTANCE.getProjectConfig_ProjectType();

        /**
         * The meta object literal for the '<em><b>Project Details</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT_CONFIG__PROJECT_DETAILS = eINSTANCE.getProjectConfig_ProjectDetails();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl <em>Special Folder</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.SpecialFolderImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getSpecialFolder()
         * @generated
         */
        EClass SPECIAL_FOLDER = eINSTANCE.getSpecialFolder();

        /**
         * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPECIAL_FOLDER__KIND = eINSTANCE.getSpecialFolder_Kind();

        /**
         * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPECIAL_FOLDER__LOCATION = eINSTANCE.getSpecialFolder_Location();

        /**
         * The meta object literal for the '<em><b>Generated</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SPECIAL_FOLDER__GENERATED = eINSTANCE.getSpecialFolder_Generated();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl <em>Special Folders</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.SpecialFoldersImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getSpecialFolders()
         * @generated
         */
        EClass SPECIAL_FOLDERS = eINSTANCE.getSpecialFolders();

        /**
         * The meta object literal for the '<em><b>Folders</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SPECIAL_FOLDERS__FOLDERS = eINSTANCE.getSpecialFolders_Folders();

        /**
         * The meta object literal for the '<em><b>Config</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SPECIAL_FOLDERS__CONFIG = eINSTANCE.getSpecialFolders_Config();

        /**
         * The meta object literal for the '{@link org.eclipse.core.runtime.IAdaptable <em>Adadptable</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.core.runtime.IAdaptable
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getAdadptable()
         * @generated
         */
        EClass ADADPTABLE = eINSTANCE.getAdadptable();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.UniqueIdContainerImpl <em>Unique Id Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.UniqueIdContainerImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getUniqueIdContainer()
         * @generated
         */
        EClass UNIQUE_ID_CONTAINER = eINSTANCE.getUniqueIdContainer();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute UNIQUE_ID_CONTAINER__ID = eINSTANCE.getUniqueIdContainer_Id();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.AssetTypeImpl <em>Asset Type</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.AssetTypeImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getAssetType()
         * @generated
         */
        EClass ASSET_TYPE = eINSTANCE.getAssetType();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSET_TYPE__ID = eINSTANCE.getAssetType_Id();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ASSET_TYPE__VERSION = eINSTANCE.getAssetType_Version();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset <em>IProject Asset</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIProjectAsset()
         * @generated
         */
        EClass IPROJECT_ASSET = eINSTANCE.getIProjectAsset();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl <em>Project Details</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectDetails()
         * @generated
         */
        EClass PROJECT_DETAILS = eINSTANCE.getProjectDetails();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT_DETAILS__ID = eINSTANCE.getProjectDetails_Id();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT_DETAILS__VERSION = eINSTANCE.getProjectDetails_Version();

        /**
         * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute PROJECT_DETAILS__STATUS = eINSTANCE.getProjectDetails_Status();

        /**
         * The meta object literal for the '<em><b>Global Destinations</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT_DETAILS__GLOBAL_DESTINATIONS = eINSTANCE.getProjectDetails_GlobalDestinations();

        /**
         * The meta object literal for the '<em><b>Custom Properties</b></em>' containment reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PROJECT_DETAILS__CUSTOM_PROPERTIES = eINSTANCE.getProjectDetails_CustomProperties();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.DestinationImpl <em>Destination</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.DestinationImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDestination()
         * @generated
         */
        EClass DESTINATION = eINSTANCE.getDestination();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute DESTINATION__TYPE = eINSTANCE.getDestination_Type();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.DestinationsImpl <em>Destinations</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.DestinationsImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getDestinations()
         * @generated
         */
        EClass DESTINATIONS = eINSTANCE.getDestinations();

        /**
         * The meta object literal for the '<em><b>Destination</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference DESTINATIONS__DESTINATION = eINSTANCE.getDestinations_Destination();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.CustomPropertyImpl <em>Custom Property</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.CustomPropertyImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCustomProperty()
         * @generated
         */
        EClass CUSTOM_PROPERTY = eINSTANCE.getCustomProperty();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CUSTOM_PROPERTY__NAME = eINSTANCE.getCustomProperty_Name();

        /**
         * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute CUSTOM_PROPERTY__VALUE = eINSTANCE.getCustomProperty_Value();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.impl.CustomPropertiesImpl <em>Custom Properties</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.impl.CustomPropertiesImpl
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCustomProperties()
         * @generated
         */
        EClass CUSTOM_PROPERTIES = eINSTANCE.getCustomProperties();

        /**
         * The meta object literal for the '<em><b>Custom Property</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference CUSTOM_PROPERTIES__CUSTOM_PROPERTY = eINSTANCE.getCustomProperties_CustomProperty();

        /**
         * The meta object literal for the '{@link com.tibco.xpd.resources.projectconfig.ProjectStatus <em>Project Status</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.ProjectStatus
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getProjectStatus()
         * @generated
         */
        EEnum PROJECT_STATUS = eINSTANCE.getProjectStatus();

        /**
         * The meta object literal for the '<em>IProject</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.core.resources.IProject
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIProject()
         * @generated
         */
        EDataType IPROJECT = eINSTANCE.getIProject();

        /**
         * The meta object literal for the '<em>IFolder</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.core.resources.IFolder
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIFolder()
         * @generated
         */
        EDataType IFOLDER = eINSTANCE.getIFolder();

        /**
         * The meta object literal for the '<em>IResource</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.core.resources.IResource
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getIResource()
         * @generated
         */
        EDataType IRESOURCE = eINSTANCE.getIResource();

        /**
         * The meta object literal for the '<em>ISpecial Folder Model</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getISpecialFolderModel()
         * @generated
         */
        EDataType ISPECIAL_FOLDER_MODEL = eINSTANCE.getISpecialFolderModel();

        /**
         * The meta object literal for the '<em>Collection</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see java.util.Collection
         * @see com.tibco.xpd.resources.projectconfig.impl.ProjectConfigPackageImpl#getCollection()
         * @generated
         */
        EDataType COLLECTION = eINSTANCE.getCollection();

    }

} //ProjectConfigPackage
