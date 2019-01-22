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
 * A representation of the model object '<em><b>Server Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerElementType#getOperations <em>Operations</em>}</li>
 *   <li>{@link com.tibco.xpd.deploy.ServerElementType#getStates <em>States</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerElementType()
 * @model
 * @generated
 */
public interface ServerElementType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Operations</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.Operation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Operations</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Operations</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElementType_Operations()
     * @model
     * @generated
     */
    EList<Operation> getOperations();

    /**
     * Returns the value of the '<em><b>States</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.ServerElementState}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>States</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>States</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElementType_States()
     * @model
     * @generated
     */
    EList<ServerElementState> getStates();

} // ServerElementType
