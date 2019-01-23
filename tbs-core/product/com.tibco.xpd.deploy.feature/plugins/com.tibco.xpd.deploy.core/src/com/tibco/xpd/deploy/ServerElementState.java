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
 * A representation of the model object '<em><b>Server Element State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.deploy.ServerElementState#getPossibleOperations <em>Possible Operations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.deploy.DeployPackage#getServerElementState()
 * @model
 * @generated
 */
public interface ServerElementState extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * Returns the value of the '<em><b>Possible Operations</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.deploy.Operation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Possible Operations</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Possible Operations</em>' reference list.
     * @see com.tibco.xpd.deploy.DeployPackage#getServerElementState_PossibleOperations()
     * @model
     * @generated
     */
    EList<Operation> getPossibleOperations();

} // ServerElementState
