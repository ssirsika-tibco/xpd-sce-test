/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy;

import com.tibco.xpd.deploy.model.extension.RepositoryPublisher;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.RepositoryType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.RepositoryType#getRepositoryParameterInfos <em>Repository Parameter Infos</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.RepositoryType#getRepositoryPublisher <em>Repository Publisher</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryType()
 * @model
 * @generated
 */
public interface RepositoryType extends NamedElement, Loadable {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

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
     * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryType_Id()
     * @model id="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.RepositoryType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Repository Parameter Infos</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ConfigParameterInfo}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository Parameter Infos</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository Parameter Infos</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryType_RepositoryParameterInfos()
     * @model containment="true" transient="true"
     * @generated
     */
    EList<ConfigParameterInfo> getRepositoryParameterInfos();

    /**
     * Returns the value of the '<em><b>Repository Publisher</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Repository Publisher</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Repository Publisher</em>' attribute.
     * @see #setRepositoryPublisher(RepositoryPublisher)
     * @see com.tibco.xpd.deploy.DeployPackage#getRepositoryType_RepositoryPublisher()
     * @model dataType="com.tibco.xpd.deploy.ERepositoryPublisher" transient="true"
     * @generated
     */
    RepositoryPublisher getRepositoryPublisher();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.RepositoryType#getRepositoryPublisher <em>Repository Publisher</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Repository Publisher</em>' attribute.
     * @see #getRepositoryPublisher()
     * @generated
     */
    void setRepositoryPublisher(RepositoryPublisher value);

} // RepositoryType
