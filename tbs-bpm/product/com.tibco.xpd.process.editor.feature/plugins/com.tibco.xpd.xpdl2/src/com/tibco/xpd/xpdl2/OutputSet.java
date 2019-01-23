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
 * A representation of the model object '<em><b>Output Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.OutputSet#getOutput <em>Output</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOutputSet()
 * @model extendedMetaData="name='OutputSet_._type' kind='elementOnly'"
 * @generated
 */
public interface OutputSet extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Output</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Output}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Output</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Output</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getOutputSet_Output()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Output' namespace='##targetNamespace'"
     * @generated
     */
    EList<Output> getOutput();

} // OutputSet
