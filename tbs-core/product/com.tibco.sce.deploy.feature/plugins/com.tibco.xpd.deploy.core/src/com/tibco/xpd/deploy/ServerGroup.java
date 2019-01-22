/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerGroup#getMembers <em>Members</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerGroup#getServerGroupType <em>Server Group Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerGroup()
 * @model
 * @generated
 */
public interface ServerGroup extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Members</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.Server}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.deploy.Server#getServerGroup <em>Server Group</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Members</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Members</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerGroup_Members()
     * @see com.tibco.xpd.deploy.Server#getServerGroup
     * @model opposite="serverGroup"
     * @generated
     */
    EList<Server> getMembers();

    /**
     * Returns the value of the '<em><b>Server Group Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Group Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Group Type</em>' reference.
     * @see #setServerGroupType(ServerGroupType)
     * @see com.tibco.xpd.deploy.DeployPackage#getServerGroup_ServerGroupType()
     * @model required="true"
     * @generated
     */
    ServerGroupType getServerGroupType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.ServerGroup#getServerGroupType <em>Server Group Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Group Type</em>' reference.
     * @see #getServerGroupType()
     * @generated
     */
    void setServerGroupType(ServerGroupType value);

} // ServerGroup
