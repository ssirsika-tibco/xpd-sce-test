/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Special Folders</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.SpecialFolders#getFolders <em>Folders</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.SpecialFolders#getConfig <em>Config</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolders()
 * @model extendedMetaData="name='SpecialFolders' kind='elementOnly'"
 * @generated
 */
public interface SpecialFolders extends EObject {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Folders</b></em>' containment
     * reference list. The list contents are of type
     * {@link com.tibco.xpd.resources.projectconfig.SpecialFolder}. <!--
     * begin-user-doc -->
     * <p>
     * Get all SpecialFolders for this config.
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Folders</em>' containment reference
     *         list.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolders_Folders()
     * @model type="com.tibco.xpd.resources.projectconfig.SpecialFolder"
     *        containment="true" required="true"
     *        extendedMetaData="kind='element' name='folder'
     *        namespace='##targetNamespace'"
     * @generated
     */
    EList<SpecialFolder> getFolders();

    /**
     * Returns the value of the '<em><b>Config</b></em>' container
     * reference. It is bidirectional and its opposite is '{@link com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders <em>Special Folders</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
     * The ProjectConfig container of this SpecialFolders. <!-- end-model-doc
     * -->
     * 
     * @return the value of the '<em>Config</em>' container reference.
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolders_Config()
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfig#getSpecialFolders
     * @model opposite="specialFolders" changeable="false" derived="true"
     *        extendedMetaData="kind='attribute' name='config'"
     * @generated
     */
    ProjectConfig getConfig();

    /**
     * <!-- begin-user-doc --> Get the special folders of the given <i>kind</i>
     * for this project.
     * 
     * @return List of <code>SpecialFolder</code> objects that are of the
     *         given <i>kind</i>. Empty list will be returned if no special
     *         folders of the <i>kind</i> are defined. <!-- end-user-doc -->
     * @model many="false" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<SpecialFolder> getFoldersOfKind(String kind);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the <code>SpecialFolder</code> that has it's associated folder set
     * to the given <i>folder</i>.
     * 
     * @return <code>SpecialFolder</code> that has it's associated folder set
     *         to the given <i>folder</i>. If none is found then <b>null</b>
     *         will be returned.
     * 
     * @exception NullPointerException -
     *                If <i>folder</i> is <b>null</b>.
     * @exception IllegalArgumentException -
     *                if the <i>folder</i> is not from the same project as this
     *                config.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model folderDataType="com.tibco.xpd.resources.projectconfig.IFolder"
     * @generated
     */
    SpecialFolder getFolder(IFolder folder);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the <code>SpecialFolder</code> that has it's associated folder set
     * to the given <i>folder</i> and is of the given <i>kind</i>.
     * </p>
     * <p>
     * NOTE: If kind is <b>null</b> then this method will behave like
     * <code>{@link #getFolder(IFolder)}</code>.
     * 
     * @return <code>SpecialFolder</code> if found with the associated
     *         <i>folder</i> and <i>kind</i>, <b>null</b> will be returned
     *         otherwise.
     * 
     * @exception NullPointerException -
     *                If <i>folder</i> is <b>null</b>.
     * @exception IllegalArgumentException -
     *                if the <i>folder</i> is not from the same project as this
     *                config, or no such <i>kind</i> (if supplied) of
     *                <code>SpecialFolder</code> is registered.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model folderDataType="com.tibco.xpd.resources.projectconfig.IFolder" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    SpecialFolder getFolder(IFolder folder, String kind);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the <code>SpecialFolder</code> container of the <i>resource</i>.
     * If this <i>resource</i> is not contained in a <code>SpecialFolder</code>
     * (at any level within the special folder) then <b>null</b> will be
     * returned.
     * 
     * @return <code>SpecialFolder</code> container of the given resource path
     *         (container at any level), <b>null</b> will be returned if the
     *         resource is not contained in a special folder.
     * 
     * @exception NullPointerException -
     *                if <i>resource</i> is <b>null</b>.
     * @exception IllegalArgumentException -
     *                if the <i>resource</i> is not from the same project as
     *                this config.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model resourceDataType="com.tibco.xpd.resources.projectconfig.IResource"
     * @generated
     */
    SpecialFolder getFolderContainer(IResource resource);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Add a <code>SpecialFolder</code> with the location set to the given
     * <i>folder</i> and of the given <i>kind</i>.
     * </p>
     * <p>
     * CONDITIONS:<br/> 1. A special folder cannot contain other special
     * folders.<br/> 2. A folder that contains special folder(s) cannot be set
     * as a special folder.<br/> Breaking the above conditions will cause an
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     * 
     * @return The <code>SpecialFolder</code> added to this config.
     * 
     * @exception NullPointerException -
     *                if <i>folder</i> or <i>kind</i> is null.
     * 
     * @exception IllegalArgumentException -
     *                if the <i>folder</i> is not from the same project as this
     *                config, is not accessible, or no such <i>kind</i> of
     *                <code>SpecialFolder</code> is registered. Also thrown if
     *                the conditions mentioned above are broken.
     * 
     * <!-- end-user-doc -->
     * @model folderDataType="com.tibco.xpd.resources.projectconfig.IFolder" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    SpecialFolder addFolder(IFolder folder, String kind);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Add <code>SpecialFolder</code> objects for each folder given in the
     * <i>folders</i> list and set each of them to the given <i>kind</i>. The
     * <i>folders</i> list should contain <code>IFolder</code> objects.
     * </p>
     * <p>
     * CONDITIONS:<br/> 1. A special folder cannot contain other special
     * folders.<br/> 2. A folder that contains special folder(s) cannot be set
     * as a special folder.<br/> Breaking the above conditions will cause an
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     * 
     * @exception NullPointerException -
     *                If the <i>folders</i> or <i>kind</i> is <b>null</b> or
     *                if the <code>WorkingCopy</code> or
     *                <code>EditingDomain</code> used to execute the
     *                <code>Command</code> is <b>null</b>.
     * @exception IllegalArgumentException -
     *                If list contains an object that is not an
     *                <code>IFolder</code>, if an <code>IFolder</code> in
     *                the <i>folders</i> list doesn't belong to the same
     *                project as this config or is not accessible, there is
     *                already a <code>SpecialFolder</code> for the given
     *                folder but of different kind, or no such <i>kind</i> of
     *                <code>SpecialFolder</code> is registered. Also thrown if
     *                the conditions mentioned above are broken.
     * 
     * <!-- end-user-doc -->
     * @model foldersDataType="com.tibco.xpd.resources.projectconfig.IFolder" foldersMany="true" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    void addFolder(EList<IFolder> folders, String kind);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Remove the given <code>SpecialFolder</code> from the configuration.
     * 
     * @exception IOException -
     *                If failed to save the working copy.
     * @exception NullPointerException -
     *                If the <i>specialFolder</i> is <b>null</b> or
     *                <code>WorkingCopy</code> or <code>EditingDomain</code>
     *                used to execute the <code>Command</code> is <b>null</b>.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    void removeFolder(SpecialFolder specialFolder);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Remove the given special folders from the config.
     * 
     * @exception IOException -
     *                If failed to save the working copy.
     * @exception NullPointerException -
     *                If the <i>specialFolders</i> list,
     *                <code>WorkingCopy</code> or <code>EditingDomain</code>
     *                used to execute the <code>Command</code> is <b>null</b>.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model specialFoldersMany="true"
     * @generated
     */
    void removeFolder(EList<SpecialFolder> specialFolders);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Change the associated folder of the given <i>specialFolder</i> to the
     * given <i>folder</i>.
     * </p>
     * <p>
     * CONDITIONS:<br/> 1. A special folder cannot contain other special
     * folders.<br/> 2. A folder that contains special folder(s) cannot be set
     * as a special folder.<br/> Breaking the above conditions will cause an
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     * 
     * @return If successful the updated <code>SpecialFolder</code> will be
     *         returned, if failed <b>null</b> will be returned.
     * 
     * @exception IOException -
     *                If the working copy fails to save.
     * @exception NullPointerException -
     *                If <i>specialFolder</i> or <i>folder</i> is <b>null</b>,
     *                or the <code>WorkingCopy</code> or
     *                <code>EditingDomain</code> used to execute the
     *                <code>Command</code> is <b>null</b>.<br/>
     * @exception IllegalArgumentException -
     *                If the <i>folder</i> is marked as special folder, does
     *                not belong to the same project as this
     *                <code>ProjectConfig</code> or is not accessible.Also
     *                thrown if the conditions mentioned above are broken.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model folderDataType="com.tibco.xpd.resources.projectconfig.IFolder"
     * @generated
     */
    SpecialFolder changeFolder(SpecialFolder specialFolder, IFolder folder);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the list of registered <code>SpecialFolder</code> kinds.
     * 
     * @return <code>EList</code> containing
     *         <code>ISpecialFolderKindInfo</code> which contain the
     *         information provided in the extension. An empty list will be
     *         returned if no <code>SpecialFolder</code> kinds are registerd.
     *         </p>
     *         <!-- end-user-doc -->
     * @model kind="operation" dataType="com.tibco.xpd.resources.projectconfig.ISpecialFolderModel"
     * @generated
     */
    EList<ISpecialFolderModel> getFolderKindInfo();

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get registered extension information on the special folder <i>kind</i>
     * given.
     * 
     * @return <code>ISpecialFolderKindInfo</code> containing the information
     *         registered in the extension for this <i>kind</i>. If the <i>kind</i>
     *         is not registered then <b>null</b> will be returned.
     * 
     * @exception NullPointerException -
     *                If <i>kind</i> is <b>null</b>.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model dataType="com.tibco.xpd.resources.projectconfig.ISpecialFolderModel" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    ISpecialFolderModel getFolderKindInfo(String kind);

    /**
     * <!-- begin-user-doc -->
     * <p>
     * Get the list of eclipse IFolder of the given <i>kind</i> for this
     * project. This is convinience method of getting IFolders directly.
     * 
     * @return List of <code>IFolder</code> objects that are of the given
     *         <i>kind</i>. Empty list will be returned if no special folders
     *         of the <i>kind</i> are defined.
     * 
     * </p>
     * <!-- end-user-doc -->
     * @model dataType="com.tibco.xpd.resources.projectconfig.IFolder" kindDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<IFolder> getEclipseIFoldersOfKind(String kind);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model folderDataType="com.tibco.xpd.resources.projectconfig.IFolder" kindDataType="org.eclipse.emf.ecore.xml.type.String" generatedDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    SpecialFolder addFolder(IFolder folder, String kind, String generated);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model specialFolderRequired="true" generatedDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    void markAsGenerated(SpecialFolder specialFolder, String generated);

} // SpecialFolders
