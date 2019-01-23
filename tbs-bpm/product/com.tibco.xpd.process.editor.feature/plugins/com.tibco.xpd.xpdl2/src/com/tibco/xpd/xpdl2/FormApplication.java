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
 * A representation of the model object '<em><b>Form Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.FormApplication#getFormLayout <em>Form Layout</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormApplication()
 * @model extendedMetaData="name='Form_._type' kind='elementOnly'"
 * @generated
 */
public interface FormApplication extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Form Layout</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Form Layout</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Form Layout</em>' containment reference.
     * @see #setFormLayout(FormLayout)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFormApplication_FormLayout()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='FormLayout' namespace='##targetNamespace'"
     * @generated
     */
    FormLayout getFormLayout();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FormApplication#getFormLayout <em>Form Layout</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form Layout</em>' containment reference.
     * @see #getFormLayout()
     * @generated
     */
    void setFormLayout(FormLayout value);

} // FormApplication
