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
 * A representation of the model object '<em><b>Pojo Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.PojoApplication#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PojoApplication#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPojoApplication()
 * @model extendedMetaData="name='Pojo_._type' kind='elementOnly'"
 * @generated
 */
public interface PojoApplication extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Class</em>' containment reference.
     * @see #setClass(com.tibco.xpd.xpdl2.Class)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPojoApplication_Class()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Class' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Class getClass_();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PojoApplication#getClass_ <em>Class</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Class</em>' containment reference.
     * @see #getClass_()
     * @generated
     */
    void setClass(com.tibco.xpd.xpdl2.Class value);

    /**
     * Returns the value of the '<em><b>Method</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Method</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Method</em>' containment reference.
     * @see #setMethod(Method)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPojoApplication_Method()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Method' namespace='##targetNamespace'"
     * @generated
     */
    Method getMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PojoApplication#getMethod <em>Method</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Method</em>' containment reference.
     * @see #getMethod()
     * @generated
     */
    void setMethod(Method value);

} // PojoApplication
