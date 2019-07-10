/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Formal Parameters Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.FormalParametersContainer#getFormalParameters <em>Formal Parameters</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormalParametersContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface FormalParametersContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Formal Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.FormalParameter}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Formal Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Formal Parameters</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormalParametersContainer_FormalParameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='FormalParameter' namespace='##targetNamespace' wrap='FormalParameters'"
     * @generated
     */
    EList<FormalParameter> getFormalParameters();

} // FormalParametersContainer
