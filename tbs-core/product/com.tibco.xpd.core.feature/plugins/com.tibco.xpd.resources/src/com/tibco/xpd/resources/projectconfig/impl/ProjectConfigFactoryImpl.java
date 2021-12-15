/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import com.tibco.xpd.resources.projectconfig.*;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import java.util.Collection;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class ProjectConfigFactoryImpl extends EFactoryImpl implements ProjectConfigFactory {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public static ProjectConfigFactory init() {
        try {
            ProjectConfigFactory theProjectConfigFactory =
                    (ProjectConfigFactory) EPackage.Registry.INSTANCE.getEFactory(ProjectConfigPackage.eNS_URI);
            if (theProjectConfigFactory != null) {
                return theProjectConfigFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ProjectConfigFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    public ProjectConfigFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case ProjectConfigPackage.DOCUMENT_ROOT:
            return createDocumentRoot();
        case ProjectConfigPackage.PROJECT_CONFIG:
            return createProjectConfig();
        case ProjectConfigPackage.ASSET_TYPE:
            return createAssetType();
        case ProjectConfigPackage.SPECIAL_FOLDER:
            return createSpecialFolder();
        case ProjectConfigPackage.SPECIAL_FOLDERS:
            return createSpecialFolders();
        case ProjectConfigPackage.PROJECT_DETAILS:
            return createProjectDetails();
        case ProjectConfigPackage.DESTINATION:
            return createDestination();
        case ProjectConfigPackage.DESTINATIONS:
            return createDestinations();
        case ProjectConfigPackage.CUSTOM_PROPERTY:
            return createCustomProperty();
        case ProjectConfigPackage.CUSTOM_PROPERTIES:
            return createCustomProperties();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case ProjectConfigPackage.PROJECT_STATUS:
            return createProjectStatusFromString(eDataType, initialValue);
        case ProjectConfigPackage.IPROJECT:
            return createIProjectFromString(eDataType, initialValue);
        case ProjectConfigPackage.IFOLDER:
            return createIFolderFromString(eDataType, initialValue);
        case ProjectConfigPackage.IRESOURCE:
            return createIResourceFromString(eDataType, initialValue);
        case ProjectConfigPackage.ISPECIAL_FOLDER_MODEL:
            return createISpecialFolderModelFromString(eDataType, initialValue);
        case ProjectConfigPackage.COLLECTION:
            return createCollectionFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case ProjectConfigPackage.PROJECT_STATUS:
            return convertProjectStatusToString(eDataType, instanceValue);
        case ProjectConfigPackage.IPROJECT:
            return convertIProjectToString(eDataType, instanceValue);
        case ProjectConfigPackage.IFOLDER:
            return convertIFolderToString(eDataType, instanceValue);
        case ProjectConfigPackage.IRESOURCE:
            return convertIResourceToString(eDataType, instanceValue);
        case ProjectConfigPackage.ISPECIAL_FOLDER_MODEL:
            return convertISpecialFolderModelToString(eDataType, instanceValue);
        case ProjectConfigPackage.COLLECTION:
            return convertCollectionToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot() {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectConfig createProjectConfig() {
        ProjectConfigImpl projectConfig = new ProjectConfigImpl();
        return projectConfig;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SpecialFolder createSpecialFolder() {
        SpecialFolderImpl specialFolder = new SpecialFolderImpl();
        return specialFolder;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SpecialFolders createSpecialFolders() {
        SpecialFoldersImpl specialFolders = new SpecialFoldersImpl();
        return specialFolders;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProjectDetails createProjectDetails() {
        ProjectDetailsImpl projectDetails = new ProjectDetailsImpl();
        return projectDetails;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Destination createDestination() {
        DestinationImpl destination = new DestinationImpl();
        return destination;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Destinations createDestinations() {
        DestinationsImpl destinations = new DestinationsImpl();
        return destinations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CustomProperty createCustomProperty() {
        CustomPropertyImpl customProperty = new CustomPropertyImpl();
        return customProperty;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CustomProperties createCustomProperties() {
        CustomPropertiesImpl customProperties = new CustomPropertiesImpl();
        return customProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProjectStatus createProjectStatusFromString(EDataType eDataType, String initialValue) {
        ProjectStatus result = ProjectStatus.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertProjectStatusToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AssetType createAssetType() {
        AssetTypeImpl assetType = new AssetTypeImpl();
        return assetType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IProject createIProjectFromString(EDataType eDataType, String initialValue) {
        return (IProject) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertIProjectToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IFolder createIFolderFromString(EDataType eDataType, String initialValue) {
        return (IFolder) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertIFolderToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public IResource createIResourceFromString(EDataType eDataType, String initialValue) {
        return (IResource) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertIResourceToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ISpecialFolderModel createISpecialFolderModelFromString(EDataType eDataType, String initialValue) {
        return (ISpecialFolderModel) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertISpecialFolderModelToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Collection<?> createCollectionFromString(EDataType eDataType, String initialValue) {
        return (Collection<?>) super.createFromString(initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertCollectionToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectConfigPackage getProjectConfigPackage() {
        return (ProjectConfigPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ProjectConfigPackage getPackage() {
        return ProjectConfigPackage.eINSTANCE;
    }

} // ProjectConfigFactoryImpl
