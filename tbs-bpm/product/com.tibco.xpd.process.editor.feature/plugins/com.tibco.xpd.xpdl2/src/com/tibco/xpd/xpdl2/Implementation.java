/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Implementation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Implementation#getActivity <em>Activity</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getImplementation()
 * @model abstract="true"
 *        extendedMetaData="name='Implementation_._7_._type' kind='elementOnly'"
 * @generated
 */
public interface Implementation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Activity#getImplementation <em>Implementation</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity</em>' container reference.
     * @see #setActivity(Activity)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getImplementation_Activity()
     * @see com.tibco.xpd.xpdl2.Activity#getImplementation
     * @model opposite="implementation" transient="false"
     * @generated
     */
    Activity getActivity();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Implementation#getActivity <em>Activity</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity</em>' container reference.
     * @see #getActivity()
     * @generated
     */
    void setActivity(Activity value);

} // Implementation
