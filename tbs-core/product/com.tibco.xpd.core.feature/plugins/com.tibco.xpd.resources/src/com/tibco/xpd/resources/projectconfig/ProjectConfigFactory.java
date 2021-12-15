/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage
 * @generated
 */
public interface ProjectConfigFactory extends EFactory {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    ProjectConfigFactory eINSTANCE = com.tibco.xpd.resources.projectconfig.impl.ProjectConfigFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Document Root</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Document Root</em>'.
     * @generated
     */
    DocumentRoot createDocumentRoot();

    /**
     * Returns a new object of class '<em>Project Config</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Project Config</em>'.
     * @generated
     */
    ProjectConfig createProjectConfig();

    /**
     * Returns a new object of class '<em>Special Folder</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Special Folder</em>'.
     * @generated
     */
    SpecialFolder createSpecialFolder();

    /**
     * Returns a new object of class '<em>Special Folders</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Special Folders</em>'.
     * @generated
     */
    SpecialFolders createSpecialFolders();

    /**
     * Returns a new object of class '<em>Project Details</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Project Details</em>'.
     * @generated
     */
    ProjectDetails createProjectDetails();

    /**
     * Returns a new object of class '<em>Destination</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Destination</em>'.
     * @generated
     */
    Destination createDestination();

    /**
     * Returns a new object of class '<em>Destinations</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Destinations</em>'.
     * @generated
     */
    Destinations createDestinations();

    /**
     * Returns a new object of class '<em>Custom Property</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Custom Property</em>'.
     * @generated
     */
    CustomProperty createCustomProperty();

    /**
     * Returns a new object of class '<em>Custom Properties</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Custom Properties</em>'.
     * @generated
     */
    CustomProperties createCustomProperties();

    /**
     * Returns a new object of class '<em>Asset Type</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Asset Type</em>'.
     * @generated
     */
    AssetType createAssetType();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    ProjectConfigPackage getProjectConfigPackage();

} //ProjectConfigFactory
