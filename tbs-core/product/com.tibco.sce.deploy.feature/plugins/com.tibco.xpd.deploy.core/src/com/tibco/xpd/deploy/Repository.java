/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.Repository#getRepositoryType <em>Repository Type</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.Repository#getRepositoryConfig <em>Repository Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getRepository()
 * @model
 * @generated
 */
public interface Repository extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Repository Type</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository Type</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository Type</em>' reference.
     * @see #setRepositoryType(RepositoryType)
     * @see com.tibco.xpd.deploy.DeployPackage#getRepository_RepositoryType()
     * @model
     * @generated
     */
    RepositoryType getRepositoryType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Repository#getRepositoryType <em>Repository Type</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repository Type</em>' reference.
     * @see #getRepositoryType()
     * @generated
     */
    void setRepositoryType(RepositoryType value);

    /**
     * Returns the value of the '<em><b>Repository Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository Config</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository Config</em>' containment reference.
     * @see #setRepositoryConfig(RepositoryConfig)
     * @see com.tibco.xpd.deploy.DeployPackage#getRepository_RepositoryConfig()
     * @model containment="true"
     * @generated
     */
    RepositoryConfig getRepositoryConfig();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.Repository#getRepositoryConfig <em>Repository Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repository Config</em>' containment reference.
     * @see #getRepositoryConfig()
     * @generated
     */
    void setRepositoryConfig(RepositoryConfig value);

} // Repository
