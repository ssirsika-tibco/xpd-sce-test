/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Special Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Special folder
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getKind <em>Kind</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getGenerated <em>Generated</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolder()
 * @model superTypes="com.tibco.xpd.resources.projectconfig.UniqueIdContainer com.tibco.xpd.resources.projectconfig.Adadptable"
 *        extendedMetaData="name='SpecialFolder' kind='elementOnly'"
 * @generated
 */
public interface SpecialFolder extends UniqueIdContainer, IAdaptable {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Kind</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Kind</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Kind</em>' attribute.
     * @see #setKind(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolder_Kind()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        required="true" extendedMetaData="kind='attribute' name='kind'"
     * @generated
     */
    String getKind();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getKind <em>Kind</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Kind</em>' attribute.
     * @see #getKind()
     * @generated
     */
    void setKind(String value);

    /**
     * Returns the value of the '<em><b>Location</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> This is
     * the location of the represented IFolder relative to the Project. <!--
     * end-model-doc -->
     * 
     * @return the value of the '<em>Location</em>' attribute.
     * @see #setLocation(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolder_Location()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        required="true" extendedMetaData="kind='attribute'
     *        name='location'"
     * @generated
     */
    String getLocation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getLocation <em>Location</em>}' attribute.
     * <!-- begin-user-doc -->
     * Location is a string representing a project relative path to folder. 
     * @see IResource#getProjectRelativePath()
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Location</em>' attribute.
     * @see #getLocation()
     * @generated
     */
    void setLocation(String value);

    /**
     * Returns the value of the '<em><b>Generated</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Arbitrary string to indicate that this special folder has been generated (the string can be used to indicate the purpose of this folder).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Generated</em>' attribute.
     * @see #setGenerated(String)
     * @see com.tibco.xpd.resources.projectconfig.ProjectConfigPackage#getSpecialFolder_Generated()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='generated'"
     * @generated
     */
    String getGenerated();

    /**
     * Sets the value of the '{@link com.tibco.xpd.resources.projectconfig.SpecialFolder#getGenerated <em>Generated</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Generated</em>' attribute.
     * @see #getGenerated()
     * @generated
     */
    void setGenerated(String value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" dataType="com.tibco.xpd.resources.projectconfig.IProject"
     * @generated
     */
    IProject getProject();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation" dataType="com.tibco.xpd.resources.projectconfig.IFolder"
     * @generated
     */
    IFolder getFolder();

} // SpecialFolder
