/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.RepositoryConfig#getConfigParameters <em>Config Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryConfig()
 * @model
 * @generated
 */
public interface RepositoryConfig extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Config Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ConfigParameter}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Config Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Config Parameters</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryConfig_ConfigParameters()
     * @model containment="true"
     * @generated
     */
    EList<ConfigParameter> getConfigParameters();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    ConfigParameter getConfigParameter(String key);

} // RepositoryConfig
