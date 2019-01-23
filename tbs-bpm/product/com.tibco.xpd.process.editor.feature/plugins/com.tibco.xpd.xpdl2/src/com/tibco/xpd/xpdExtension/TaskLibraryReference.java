/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Library Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This element is added under Activity xpdl:Reference element for 
 * reference tasks that reference an activity in another process / package.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getTaskLibraryId <em>Task Library Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getPackageRef <em>Package Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTaskLibraryReference()
 * @model extendedMetaData="name='TaskLibraryReference' kind='elementOnly'"
 * @generated
 */
public interface TaskLibraryReference extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Task Library Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Library Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Library Id</em>' attribute.
     * @see #setTaskLibraryId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTaskLibraryReference_TaskLibraryId()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='TaskLibraryId'"
     * @generated
     */
    String getTaskLibraryId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getTaskLibraryId <em>Task Library Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Library Id</em>' attribute.
     * @see #getTaskLibraryId()
     * @generated
     */
    void setTaskLibraryId(String value);

    /**
     * Returns the value of the '<em><b>Package Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Ref</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Ref</em>' attribute.
     * @see #setPackageRef(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getTaskLibraryReference_PackageRef()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='PackageRef'"
     * @generated
     */
    String getPackageRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.TaskLibraryReference#getPackageRef <em>Package Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Ref</em>' attribute.
     * @see #getPackageRef()
     * @generated
     */
    void setPackageRef(String value);

} // TaskLibraryReference
