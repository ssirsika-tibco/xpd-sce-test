/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.CustomProperties;
import com.tibco.xpd.resources.projectconfig.CustomProperty;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.UniqueIdContainer;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectConfigPackageImpl extends EPackageImpl implements ProjectConfigPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

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
    private EClass projectConfigEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass specialFolderEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass specialFoldersEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass adadptableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass uniqueIdContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass assetTypeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass iProjectAssetEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass projectDetailsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass destinationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass destinationsEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass customPropertyEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass customPropertiesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum projectStatusEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType iProjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType iFolderEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType iResourceEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType iSpecialFolderModelEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType collectionEDataType = null;

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
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ProjectConfigPackageImpl() {
        super(eNS_URI, ProjectConfigFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link ProjectConfigPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ProjectConfigPackage init() {
        if (isInited)
            return (ProjectConfigPackage) EPackage.Registry.INSTANCE.getEPackage(ProjectConfigPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredProjectConfigPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        ProjectConfigPackageImpl theProjectConfigPackage =
                registeredProjectConfigPackage instanceof ProjectConfigPackageImpl
                        ? (ProjectConfigPackageImpl) registeredProjectConfigPackage
                        : new ProjectConfigPackageImpl();

        isInited = true;

        // Initialize simple dependencies
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theProjectConfigPackage.createPackageContents();

        // Initialize created meta-data
        theProjectConfigPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theProjectConfigPackage.freeze();

        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ProjectConfigPackage.eNS_URI, theProjectConfigPackage);
        return theProjectConfigPackage;
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
        return (EAttribute) documentRootEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XMLNSPrefixMap() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_XSISchemaLocation() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDocumentRoot_ProjectConfig() {
        return (EReference) documentRootEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getProjectConfig() {
        return projectConfigEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectConfig_AssetTypes() {
        return (EReference) projectConfigEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectConfig_SpecialFolders() {
        return (EReference) projectConfigEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectConfig_Project() {
        return (EAttribute) projectConfigEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectConfig_ProjectType() {
        return (EAttribute) projectConfigEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectConfig_ProjectDetails() {
        return (EReference) projectConfigEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSpecialFolder() {
        return specialFolderEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSpecialFolder_Kind() {
        return (EAttribute) specialFolderEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSpecialFolder_Location() {
        return (EAttribute) specialFolderEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSpecialFolder_Generated() {
        return (EAttribute) specialFolderEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getSpecialFolders() {
        return specialFoldersEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSpecialFolders_Folders() {
        return (EReference) specialFoldersEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getSpecialFolders_Config() {
        return (EReference) specialFoldersEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAdadptable() {
        return adadptableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getUniqueIdContainer() {
        return uniqueIdContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getUniqueIdContainer_Id() {
        return (EAttribute) uniqueIdContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssetType() {
        return assetTypeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssetType_Id() {
        return (EAttribute) assetTypeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAssetType_Version() {
        return (EAttribute) assetTypeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getIProjectAsset() {
        return iProjectAssetEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getProjectDetails() {
        return projectDetailsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectDetails_Id() {
        return (EAttribute) projectDetailsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectDetails_Version() {
        return (EAttribute) projectDetailsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectDetails_Status() {
        return (EAttribute) projectDetailsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectDetails_GlobalDestinations() {
        return (EReference) projectDetailsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectDetails_CustomProperties() {
        return (EReference) projectDetailsEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDestination() {
        return destinationEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getDestination_Type() {
        return (EAttribute) destinationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getDestinations() {
        return destinationsEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getDestinations_Destination() {
        return (EReference) destinationsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCustomProperty() {
        return customPropertyEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCustomProperty_Name() {
        return (EAttribute) customPropertyEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getCustomProperty_Value() {
        return (EAttribute) customPropertyEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getCustomProperties() {
        return customPropertiesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getCustomProperties_CustomProperty() {
        return (EReference) customPropertiesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getProjectStatus() {
        return projectStatusEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIProject() {
        return iProjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIFolder() {
        return iFolderEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIResource() {
        return iResourceEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getISpecialFolderModel() {
        return iSpecialFolderModelEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCollection() {
        return collectionEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProjectConfigFactory getProjectConfigFactory() {
        return (ProjectConfigFactory) getEFactoryInstance();
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
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        documentRootEClass = createEClass(DOCUMENT_ROOT);
        createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        createEReference(documentRootEClass, DOCUMENT_ROOT__PROJECT_CONFIG);

        projectConfigEClass = createEClass(PROJECT_CONFIG);
        createEReference(projectConfigEClass, PROJECT_CONFIG__ASSET_TYPES);
        createEReference(projectConfigEClass, PROJECT_CONFIG__SPECIAL_FOLDERS);
        createEAttribute(projectConfigEClass, PROJECT_CONFIG__PROJECT);
        createEAttribute(projectConfigEClass, PROJECT_CONFIG__PROJECT_TYPE);
        createEReference(projectConfigEClass, PROJECT_CONFIG__PROJECT_DETAILS);

        assetTypeEClass = createEClass(ASSET_TYPE);
        createEAttribute(assetTypeEClass, ASSET_TYPE__ID);
        createEAttribute(assetTypeEClass, ASSET_TYPE__VERSION);

        specialFolderEClass = createEClass(SPECIAL_FOLDER);
        createEAttribute(specialFolderEClass, SPECIAL_FOLDER__KIND);
        createEAttribute(specialFolderEClass, SPECIAL_FOLDER__LOCATION);
        createEAttribute(specialFolderEClass, SPECIAL_FOLDER__GENERATED);

        specialFoldersEClass = createEClass(SPECIAL_FOLDERS);
        createEReference(specialFoldersEClass, SPECIAL_FOLDERS__FOLDERS);
        createEReference(specialFoldersEClass, SPECIAL_FOLDERS__CONFIG);

        uniqueIdContainerEClass = createEClass(UNIQUE_ID_CONTAINER);
        createEAttribute(uniqueIdContainerEClass, UNIQUE_ID_CONTAINER__ID);

        adadptableEClass = createEClass(ADADPTABLE);

        iProjectAssetEClass = createEClass(IPROJECT_ASSET);

        projectDetailsEClass = createEClass(PROJECT_DETAILS);
        createEAttribute(projectDetailsEClass, PROJECT_DETAILS__ID);
        createEAttribute(projectDetailsEClass, PROJECT_DETAILS__VERSION);
        createEAttribute(projectDetailsEClass, PROJECT_DETAILS__STATUS);
        createEReference(projectDetailsEClass, PROJECT_DETAILS__GLOBAL_DESTINATIONS);
        createEReference(projectDetailsEClass, PROJECT_DETAILS__CUSTOM_PROPERTIES);

        destinationEClass = createEClass(DESTINATION);
        createEAttribute(destinationEClass, DESTINATION__TYPE);

        destinationsEClass = createEClass(DESTINATIONS);
        createEReference(destinationsEClass, DESTINATIONS__DESTINATION);

        customPropertyEClass = createEClass(CUSTOM_PROPERTY);
        createEAttribute(customPropertyEClass, CUSTOM_PROPERTY__NAME);
        createEAttribute(customPropertyEClass, CUSTOM_PROPERTY__VALUE);

        customPropertiesEClass = createEClass(CUSTOM_PROPERTIES);
        createEReference(customPropertiesEClass, CUSTOM_PROPERTIES__CUSTOM_PROPERTY);

        // Create enums
        projectStatusEEnum = createEEnum(PROJECT_STATUS);

        // Create data types
        iProjectEDataType = createEDataType(IPROJECT);
        iFolderEDataType = createEDataType(IFOLDER);
        iResourceEDataType = createEDataType(IRESOURCE);
        iSpecialFolderModelEDataType = createEDataType(ISPECIAL_FOLDER_MODEL);
        collectionEDataType = createEDataType(COLLECTION);
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
    @SuppressWarnings("unchecked")
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage =
                (XMLTypePackage) EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

        // Create type parameters
        addETypeParameter(collectionEDataType, "String");

        // Set bounds for type parameters

        // Add supertypes to classes
        specialFolderEClass.getESuperTypes().add(this.getUniqueIdContainer());
        specialFolderEClass.getESuperTypes().add(this.getAdadptable());

        // Initialize classes and features; add operations and parameters
        initEClass(documentRootEClass,
                DocumentRoot.class,
                "DocumentRoot",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDocumentRoot_Mixed(),
                ecorePackage.getEFeatureMapEntry(),
                "mixed",
                null,
                0,
                -1,
                null,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_XMLNSPrefixMap(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xMLNSPrefixMap",
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_XSISchemaLocation(),
                ecorePackage.getEStringToStringMapEntry(),
                null,
                "xSISchemaLocation",
                null,
                0,
                -1,
                null,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getDocumentRoot_ProjectConfig(),
                this.getProjectConfig(),
                null,
                "projectConfig",
                null,
                0,
                -2,
                null,
                IS_TRANSIENT,
                IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);

        initEClass(projectConfigEClass,
                ProjectConfig.class,
                "ProjectConfig",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getProjectConfig_AssetTypes(),
                this.getAssetType(),
                null,
                "assetTypes",
                null,
                0,
                -1,
                ProjectConfig.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProjectConfig_SpecialFolders(),
                this.getSpecialFolders(),
                this.getSpecialFolders_Config(),
                "specialFolders",
                null,
                1,
                1,
                ProjectConfig.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getProjectConfig_Project(),
                this.getIProject(),
                "project",
                null,
                0,
                1,
                ProjectConfig.class,
                IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getProjectConfig_ProjectType(),
                ecorePackage.getEString(),
                "projectType",
                null,
                0,
                1,
                ProjectConfig.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProjectConfig_ProjectDetails(),
                this.getProjectDetails(),
                null,
                "projectDetails",
                null,
                0,
                1,
                ProjectConfig.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(projectConfigEClass, null, "saveWorkingCopy", 0, 1, IS_UNIQUE, IS_ORDERED);

        EOperation op = addEOperation(projectConfigEClass, null, "addAssetTask", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(projectConfigEClass, null, "addAssetTypes", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "ids", 0, -1, IS_UNIQUE, IS_ORDERED);

        addEOperation(projectConfigEClass,
                this.getIProjectAsset(),
                "getRegisteredAssetTypes",
                0,
                -1,
                IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(projectConfigEClass, this.getIProjectAsset(), "getAssetById", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "id", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(projectConfigEClass,
                theXMLTypePackage.getBoolean(),
                "hasAssetType",
                0,
                1,
                IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "assetId", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(assetTypeEClass,
                AssetType.class,
                "AssetType",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAssetType_Id(),
                theXMLTypePackage.getString(),
                "id",
                null,
                1,
                1,
                AssetType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getAssetType_Version(),
                theXMLTypePackage.getInt(),
                "version",
                null,
                0,
                1,
                AssetType.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(specialFolderEClass,
                SpecialFolder.class,
                "SpecialFolder",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSpecialFolder_Kind(),
                theXMLTypePackage.getString(),
                "kind",
                null,
                1,
                1,
                SpecialFolder.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getSpecialFolder_Location(),
                theXMLTypePackage.getString(),
                "location",
                null,
                1,
                1,
                SpecialFolder.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getSpecialFolder_Generated(),
                theXMLTypePackage.getString(),
                "generated",
                null,
                0,
                1,
                SpecialFolder.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                !IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        addEOperation(specialFolderEClass, this.getIProject(), "getProject", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(specialFolderEClass, this.getIFolder(), "getFolder", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(specialFoldersEClass,
                SpecialFolders.class,
                "SpecialFolders",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSpecialFolders_Folders(),
                this.getSpecialFolder(),
                null,
                "folders",
                null,
                1,
                -1,
                SpecialFolders.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getSpecialFolders_Config(),
                this.getProjectConfig(),
                this.getProjectConfig_SpecialFolders(),
                "config",
                null,
                0,
                1,
                SpecialFolders.class,
                IS_TRANSIENT,
                !IS_VOLATILE,
                !IS_CHANGEABLE,
                !IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                IS_DERIVED,
                IS_ORDERED);

        op = addEOperation(specialFoldersEClass, null, "getFoldersOfKind", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);
        EGenericType g1 = createEGenericType(ecorePackage.getEEList());
        EGenericType g2 = createEGenericType(this.getSpecialFolder());
        g1.getETypeArguments().add(g2);
        initEOperation(op, g1);

        op = addEOperation(specialFoldersEClass, this.getSpecialFolder(), "getFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folder", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, this.getSpecialFolder(), "getFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass,
                this.getSpecialFolder(),
                "getFolderContainer",
                0,
                1,
                IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, this.getIResource(), "resource", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, this.getSpecialFolder(), "addFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, null, "addFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folders", 0, -1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, null, "removeFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getSpecialFolder(), "specialFolder", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, null, "removeFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getSpecialFolder(), "specialFolders", 0, -1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, this.getSpecialFolder(), "changeFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getSpecialFolder(), "specialFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folder", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(specialFoldersEClass,
                this.getISpecialFolderModel(),
                "getFolderKindInfo",
                0,
                -1,
                IS_UNIQUE,
                IS_ORDERED);

        op = addEOperation(specialFoldersEClass,
                this.getISpecialFolderModel(),
                "getFolderKindInfo",
                0,
                1,
                IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass,
                this.getIFolder(),
                "getEclipseIFoldersOfKind",
                0,
                -1,
                IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, this.getSpecialFolder(), "addFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getIFolder(), "folder", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "kind", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "generated", 0, 1, IS_UNIQUE, IS_ORDERED);

        op = addEOperation(specialFoldersEClass, null, "markAsGenerated", 0, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, this.getSpecialFolder(), "specialFolder", 1, 1, IS_UNIQUE, IS_ORDERED);
        addEParameter(op, theXMLTypePackage.getString(), "generated", 0, 1, IS_UNIQUE, IS_ORDERED);

        initEClass(uniqueIdContainerEClass,
                UniqueIdContainer.class,
                "UniqueIdContainer",
                IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getUniqueIdContainer_Id(),
                theXMLTypePackage.getID(),
                "id",
                null,
                1,
                1,
                UniqueIdContainer.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                !IS_CHANGEABLE,
                !IS_UNSETTABLE,
                IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(adadptableEClass,
                IAdaptable.class,
                "Adadptable",
                IS_ABSTRACT,
                IS_INTERFACE,
                !IS_GENERATED_INSTANCE_CLASS);

        initEClass(iProjectAssetEClass,
                IProjectAsset.class,
                "IProjectAsset",
                IS_ABSTRACT,
                IS_INTERFACE,
                !IS_GENERATED_INSTANCE_CLASS);

        initEClass(projectDetailsEClass,
                ProjectDetails.class,
                "ProjectDetails",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getProjectDetails_Id(),
                ecorePackage.getEString(),
                "id",
                "",
                1,
                1,
                ProjectDetails.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getProjectDetails_Version(),
                ecorePackage.getEString(),
                "version",
                "1.0.0.qualifier",
                1,
                1,
                ProjectDetails.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getProjectDetails_Status(),
                this.getProjectStatus(),
                "status",
                "underRevision",
                1,
                1,
                ProjectDetails.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProjectDetails_GlobalDestinations(),
                this.getDestinations(),
                null,
                "globalDestinations",
                null,
                0,
                1,
                ProjectDetails.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEReference(getProjectDetails_CustomProperties(),
                this.getCustomProperties(),
                null,
                "customProperties",
                null,
                0,
                1,
                ProjectDetails.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        op = addEOperation(projectDetailsEClass,
                ecorePackage.getEBoolean(),
                "isGlobalDestinationEnabled",
                0,
                1,
                IS_UNIQUE,
                IS_ORDERED);
        addEParameter(op, ecorePackage.getEString(), "globalDestinationId", 0, 1, IS_UNIQUE, IS_ORDERED);

        addEOperation(projectDetailsEClass,
                ecorePackage.getEString(),
                "getEnabledGlobalDestinationIds",
                0,
                -1,
                IS_UNIQUE,
                IS_ORDERED);

        initEClass(destinationEClass,
                Destination.class,
                "Destination",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getDestination_Type(),
                ecorePackage.getEString(),
                "type",
                null,
                0,
                1,
                Destination.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(destinationsEClass,
                Destinations.class,
                "Destinations",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getDestinations_Destination(),
                this.getDestination(),
                null,
                "destination",
                null,
                1,
                -1,
                Destinations.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(customPropertyEClass,
                CustomProperty.class,
                "CustomProperty",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getCustomProperty_Name(),
                ecorePackage.getEString(),
                "name",
                null,
                0,
                1,
                CustomProperty.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);
        initEAttribute(getCustomProperty_Value(),
                ecorePackage.getEString(),
                "value",
                null,
                0,
                1,
                CustomProperty.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                !IS_UNSETTABLE,
                !IS_ID,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        initEClass(customPropertiesEClass,
                CustomProperties.class,
                "CustomProperties",
                !IS_ABSTRACT,
                !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        initEReference(getCustomProperties_CustomProperty(),
                this.getCustomProperty(),
                null,
                "customProperty",
                null,
                1,
                -1,
                CustomProperties.class,
                !IS_TRANSIENT,
                !IS_VOLATILE,
                IS_CHANGEABLE,
                IS_COMPOSITE,
                !IS_RESOLVE_PROXIES,
                !IS_UNSETTABLE,
                IS_UNIQUE,
                !IS_DERIVED,
                IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(projectStatusEEnum, ProjectStatus.class, "ProjectStatus");
        addEEnumLiteral(projectStatusEEnum, ProjectStatus.UNDER_REVISION);
        addEEnumLiteral(projectStatusEEnum, ProjectStatus.UNDER_TEST);
        addEEnumLiteral(projectStatusEEnum, ProjectStatus.RELEASED);

        // Initialize data types
        initEDataType(iProjectEDataType, IProject.class, "IProject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(iFolderEDataType, IFolder.class, "IFolder", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(iResourceEDataType, IResource.class, "IResource", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(iSpecialFolderModelEDataType,
                ISpecialFolderModel.class,
                "ISpecialFolderModel",
                IS_SERIALIZABLE,
                !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(collectionEDataType,
                Collection.class,
                "Collection",
                IS_SERIALIZABLE,
                !IS_GENERATED_INSTANCE_CLASS);

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
        addAnnotation(this, source, new String[] { "qualified", "true" });
        addAnnotation(documentRootEClass, source, new String[] { "name", "", "kind", "mixed" });
        addAnnotation(getDocumentRoot_Mixed(), source, new String[] { "kind", "elementWildcard", "name", ":mixed" });
        addAnnotation(getDocumentRoot_XMLNSPrefixMap(),
                source,
                new String[] { "kind", "attribute", "name", "xmlns:prefix" });
        addAnnotation(getDocumentRoot_XSISchemaLocation(),
                source,
                new String[] { "kind", "attribute", "name", "xsi:schemaLocation" });
        addAnnotation(getDocumentRoot_ProjectConfig(),
                source,
                new String[] { "kind", "element", "name", "ProjectConfig", "namespace", "##targetNamespace" });
        addAnnotation(projectConfigEClass, source, new String[] { "name", "ProjectConfig", "kind", "elementOnly" });
        addAnnotation(getProjectConfig_AssetTypes(),
                source,
                new String[] { "kind", "element", "name", "assetTypes", "namespace", "##targetNamespace" });
        addAnnotation(getProjectConfig_SpecialFolders(),
                source,
                new String[] { "kind", "element", "name", "specialFolders", "namespace", "##targetNamespace" });
        addAnnotation(getProjectConfig_ProjectType(),
                source,
                new String[] { "kind", "attribute", "name", "projectType" });
        addAnnotation(getAssetType_Id(), source, new String[] { "kind", "attribute", "name", "id" });
        addAnnotation(specialFolderEClass, source, new String[] { "name", "SpecialFolder", "kind", "elementOnly" });
        addAnnotation(getSpecialFolder_Kind(), source, new String[] { "kind", "attribute", "name", "kind" });
        addAnnotation(getSpecialFolder_Location(), source, new String[] { "kind", "attribute", "name", "location" });
        addAnnotation(getSpecialFolder_Generated(), source, new String[] { "kind", "attribute", "name", "generated" });
        addAnnotation(specialFoldersEClass, source, new String[] { "name", "SpecialFolders", "kind", "elementOnly" });
        addAnnotation(getSpecialFolders_Folders(),
                source,
                new String[] { "kind", "element", "name", "folder", "namespace", "##targetNamespace" });
        addAnnotation(getSpecialFolders_Config(), source, new String[] { "kind", "attribute", "name", "config" });
        addAnnotation(getUniqueIdContainer_Id(), source, new String[] { "kind", "attribute", "name", "id" });
    }

} //ProjectConfigPackageImpl
