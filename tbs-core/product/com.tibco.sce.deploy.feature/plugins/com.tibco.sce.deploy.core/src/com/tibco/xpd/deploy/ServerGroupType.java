/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Group Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerGroupType#getServerTypes <em>Server Types</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerGroupType#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerGroupType()
 * @model
 * @generated
 */
public interface ServerGroupType extends NamedElement, Loadable {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Server Types</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Types</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Types</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerGroupType_ServerTypes()
     * @model transient="true"
     * @generated
     */
    EList<ServerType> getServerTypes();

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerGroupType_Id()
     * @model id="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerGroupType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

} // ServerGroupType
