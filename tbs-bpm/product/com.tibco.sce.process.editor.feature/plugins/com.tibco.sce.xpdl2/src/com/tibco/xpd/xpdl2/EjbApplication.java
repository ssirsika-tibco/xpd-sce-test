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
 * A representation of the model object '<em><b>Ejb Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.EjbApplication#getJndiName <em>Jndi Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EjbApplication#getHomeClass <em>Home Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EjbApplication#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEjbApplication()
 * @model extendedMetaData="name='Ejb_._type' kind='elementOnly'"
 * @generated
 */
public interface EjbApplication extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Jndi Name</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Jndi Name</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Jndi Name</em>' containment reference.
     * @see #setJndiName(JndiName)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEjbApplication_JndiName()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='JndiName' namespace='##targetNamespace'"
     * @generated
     */
    JndiName getJndiName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EjbApplication#getJndiName <em>Jndi Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Jndi Name</em>' containment reference.
     * @see #getJndiName()
     * @generated
     */
    void setJndiName(JndiName value);

    /**
     * Returns the value of the '<em><b>Home Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Home Class</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Home Class</em>' containment reference.
     * @see #setHomeClass(HomeClass)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEjbApplication_HomeClass()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='HomeClass' namespace='##targetNamespace'"
     * @generated
     */
    HomeClass getHomeClass();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EjbApplication#getHomeClass <em>Home Class</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Home Class</em>' containment reference.
     * @see #getHomeClass()
     * @generated
     */
    void setHomeClass(HomeClass value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEjbApplication_Method()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Method' namespace='##targetNamespace'"
     * @generated
     */
    Method getMethod();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EjbApplication#getMethod <em>Method</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Method</em>' containment reference.
     * @see #getMethod()
     * @generated
     */
    void setMethod(Method value);

} // EjbApplication
