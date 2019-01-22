/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Object#getCategories <em>Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Object#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getObject()
 * @model extendedMetaData="name='Object_._type' kind='elementOnly' features-order='categories documentation'"
 * @generated
 */
public interface Object extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Categories</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Categories</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Categories</em>' containment reference.
     * @see #setCategories(Category)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getObject_Categories()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Category' namespace='##targetNamespace' wrap='Categories'"
     * @generated
     */
    Category getCategories();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Object#getCategories <em>Categories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Categories</em>' containment reference.
     * @see #getCategories()
     * @generated
     */
    void setCategories(Category value);

    /**
     * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Documentation</em>' containment reference.
     * @see #setDocumentation(Documentation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getObject_Documentation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Documentation' namespace='##targetNamespace'"
     * @generated
     */
    Documentation getDocumentation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Object#getDocumentation <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Documentation</em>' containment reference.
     * @see #getDocumentation()
     * @generated
     */
    void setDocumentation(Documentation value);

} // Object
