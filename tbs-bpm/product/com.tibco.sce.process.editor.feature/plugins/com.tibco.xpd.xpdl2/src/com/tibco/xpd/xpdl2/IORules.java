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
 * A representation of the model object '<em><b>IO Rules</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.IORules#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIORules()
 * @model extendedMetaData="name='IORules_._type' kind='elementOnly'"
 * @generated
 */
public interface IORules extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Expression</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getIORules_Expression()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Expression' namespace='##targetNamespace'"
     * @generated
     */
    EList<Expression> getExpression();

} // IORules
