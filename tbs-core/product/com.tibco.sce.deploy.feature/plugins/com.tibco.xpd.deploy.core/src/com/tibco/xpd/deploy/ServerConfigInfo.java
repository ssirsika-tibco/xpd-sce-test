/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Server Config Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerConfigInfo#getConfigParameterInfos <em>Config Parameter Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerConfigInfo()
 * @model
 * @generated
 */
public interface ServerConfigInfo extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Config Parameter Infos</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ConfigParameterInfo}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Config Parameter Infos</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Config Parameter Infos</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerConfigInfo_ConfigParameterInfos()
     * @model containment="true" ordered="false"
     * @generated
     */
    EList<ConfigParameterInfo> getConfigParameterInfos();

} // ServerConfigInfo
