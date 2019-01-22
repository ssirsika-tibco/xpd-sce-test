/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jdbc Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.JdbcResource#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.JdbcResource#getJdbcProfileName <em>Jdbc Profile Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getJdbcResource()
 * @model extendedMetaData="name='JdbcResource' kind='empty'"
 * @generated
 */
public interface JdbcResource extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Instance Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Instance Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Instance Name</em>' attribute.
     * @see #setInstanceName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getJdbcResource_InstanceName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='InstanceName'"
     * @generated
     */
    String getInstanceName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.JdbcResource#getInstanceName <em>Instance Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Instance Name</em>' attribute.
     * @see #getInstanceName()
     * @generated
     */
    void setInstanceName(String value);

    /**
     * Returns the value of the '<em><b>Jdbc Profile Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Jdbc Profile Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Jdbc Profile Name</em>' attribute.
     * @see #setJdbcProfileName(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getJdbcResource_JdbcProfileName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='JdbcProfileName'"
     * @generated
     */
    String getJdbcProfileName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.JdbcResource#getJdbcProfileName <em>Jdbc Profile Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Jdbc Profile Name</em>' attribute.
     * @see #getJdbcProfileName()
     * @generated
     */
    void setJdbcProfileName(String value);

} // JdbcResource
