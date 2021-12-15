/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Project configuration type
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getAssetTypes <em>Asset Types</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders <em>Special Folders</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProject <em>Project</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectDetails <em>Project Details</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig()
 * @model extendedMetaData="name='ProjectConfig' kind='elementOnly'"
 * @generated
 */
public interface ProjectConfig extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Asset Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.resources.projectconfig.AssetType}.
     * <!-- begin-user-doc -->
     * <p>
     * This is the list of asset types that have been configured for this project.
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Asset Types</em>' containment reference list.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig_AssetTypes()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='assetTypes' namespace='##targetNamespace'"
     * @generated
     */
    EList<AssetType> getAssetTypes();

    /**
     * Returns the value of the '<em><b>Special Folders</b></em>' containment reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.resources.projectconfig.SpecialFolders#getConfig <em>Config</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Special Folders</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Special Folders</em>' containment reference.
     * @see #setSpecialFolders(SpecialFolders)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig_SpecialFolders()
     * @see com.tibco.xpd.resources.projectconfig.SpecialFolders#getConfig
     * @model opposite="config" containment="true" required="true"
     *        extendedMetaData="kind='element' name='specialFolders' namespace='##targetNamespace'"
     * @generated
     */
    SpecialFolders getSpecialFolders();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders <em>Special Folders</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Special Folders</em>' containment reference.
     * @see #getSpecialFolders()
     * @generated
     */
    void setSpecialFolders(SpecialFolders value);

    /**
     * Returns the value of the '<em><b>Project</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Project</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Project</em>' attribute.
     * @see #setProject(IProject)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig_Project()
     * @model dataType="com.tibco.xpd.resources.projectconfig.IProject" transient="true"
     * @generated
     */
    IProject getProject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProject <em>Project</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Project</em>' attribute.
     * @see #getProject()
     * @generated
     */
    void setProject(IProject value);

    /**
     * Returns the value of the '<em><b>Project Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Project Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Project Type</em>' attribute.
     * @see #setProjectType(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig_ProjectType()
     * @model extendedMetaData="kind='attribute' name='projectType'"
     * @generated
     */
    String getProjectType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectType <em>Project Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Project Type</em>' attribute.
     * @see #getProjectType()
     * @generated
     */
    void setProjectType(String value);

    /**
     * Returns the value of the '<em><b>Project Details</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Project details such as project id, version, status and destination environments.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Project Details</em>' containment reference.
     * @see #setProjectDetails(ProjectDetails)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getProjectConfig_ProjectDetails()
     * @model containment="true"
     * @generated
     */
    ProjectDetails getProjectDetails();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getProjectDetails <em>Project Details</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Project Details</em>' containment reference.
     * @see #getProjectDetails()
     * @generated
     */
    void setProjectDetails(ProjectDetails value);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Save the working copy of the model.
     * </p>
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    void saveWorkingCopy();

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Add the given asset type Id to the project config. If the asset
     * type with this id has already been added to the project then this
     * method will do nothing.
     * </p>
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    void addAssetTask(String id);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Add the list of asset type Ids to the project config. If the asset
     * type(s) with this id(s) has already been added to the project then this
     * method will do nothing.
     * </p>
     * <!-- end-user-doc -->
     * @model idsDataType="org.eclipse.emf.ecore.xml.type.String" idsMany="true"
     * @generated
     */
    void addAssetTypes(EList<String> ids);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get all registered project asset types. This will return a list of
     * <code>{@link IProjectAsset}</code> objects.  If no asset 
     * types are registered then an empty list will be returned.
     * </p>
     * <!-- end-user-doc -->
     * @model kind="operation" type="com.tibco.xpd.resources.projectconfig.IProjectAsset"
     * @generated
     */
    EList<IProjectAsset> getRegisteredAssetTypes();

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the asset type of the given Id. If no asset type is defined of the
     * given id then <b>null</b> will be returned.
     * </p>
     * 
     * @throws <code>NullPointerException</code> If the id is null.
     * 
     * <!-- end-user-doc -->
     * @model type="com.tibco.xpd.resources.projectconfig.IProjectAsset" idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    IProjectAsset getAssetById(String id);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Check if the given asset type (id) is configured for this project.
     * </p>
     * <!-- end-user-doc -->
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean" assetIdDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    boolean hasAssetType(String assetId);

} // ProjectConfig