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
 * A representation of the model object '<em><b>Runtime Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.RuntimeType#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.RuntimeType#getRuntimeParameterInfos <em>Runtime Parameter Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getRuntimeType()
 * @model
 * @generated
 */
public interface RuntimeType extends NamedElement, Loadable {
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
     * @see com.tibco.xpd.deploy.DeployPackage#getRuntimeType_Id()
     * @model id="true"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.deploy.RuntimeType#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Runtime Parameter Infos</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ConfigParameterInfo}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Runtime Parameter Infos</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime Parameter Infos</em>' containment reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getRuntimeType_RuntimeParameterInfos()
     * @model containment="true" transient="true"
     * @generated
     */
    EList<ConfigParameterInfo> getRuntimeParameterInfos();

} // RuntimeType
